package com.cs.animators.base;

import java.lang.reflect.Method;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewStub;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animators.R;
import com.cs.cj.http.httplibrary.RequestParams;
import com.cs.cj.http.parserinterface.BaseParser;
import com.cs.cj.http.work.DataCallback;
import com.cs.cj.http.work.FastJsonParser;
import com.cs.cj.http.work.JDataCallback;
import com.cs.cj.http.work.JHttpClient;
import com.cs.cj.http.work.Response;

public abstract class BaseActivity extends ActionBarActivity {
	
    protected ActionBarActivity mContext ;
    protected ActionBar mActionBar ;
    private View mProgress ;
    private View mError  ;
    private ViewHolder mHolder ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mActionBar = getSupportActionBar();
      //去掉ActionBar的图标
  		mActionBar.setIcon(new ColorDrawable(android.R.color.transparent));
  		
        mContext = this ;
        
        View v = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        mHolder = new ViewHolder(v);
        
        //加载布局   在子类方法中调用setContentView(int resid);
        loadLayout();
        
        super.setContentView(mHolder.mainContent);
        
        ButterKnife.inject(this);
        
        //逻辑
        processLogic();
        
    }
    
    protected abstract void loadLayout();
    protected abstract void processLogic();
    
	class ViewHolder {

		@InjectView(R.id.activity_content_base)LinearLayout mainContent;
		@InjectView(R.id.viewstub_http_loading)ViewStub mViewStubProgress;
		@InjectView(R.id.viewstub_http_error)ViewStub mViewStubError;

		public ViewHolder(View v) {
			ButterKnife.inject(this, v);
		}
	}
    
    /**
     * 要将子类的布局文件加载进BaseActivity布局文件中
     */
    public void setContentView(int layoutId) {
    	View childView = LayoutInflater.from(this).inflate(layoutId, null);
    	mHolder.mainContent.addView(childView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
    
    
    protected <T> void get( String url , RequestParams params ,  BaseParser<T> parser ,  DataCallback<T> callback )
    {
    	get( url, params, parser, jHttpDataCallback( url, params, parser, callback,true),true);
    }
    
    protected <T> void get(String url ,  RequestParams params ,  Class<T> clazz ,  DataCallback<T> callback )
    {
    	get(url, params, clazz, jHttpDataCallback(url, params, new FastJsonParser<T>(clazz), callback,true),true);
    }
	
	protected <T> void get( String url , RequestParams params ,  BaseParser<T> parser ,  DataCallback<T> callback , boolean showProgress)
    {
		if(showProgress)
			httpStart();
    	JHttpClient.get(this, url, params, parser,jHttpDataCallback( url, params, parser, callback , showProgress));
    }
    
    protected <T> void get(String url ,  RequestParams params ,  Class<T> clazz ,  DataCallback<T> callback , boolean showProgress)
    {
    	if(showProgress)
			httpStart();
    	JHttpClient.get(this, url, params, clazz,  jHttpDataCallback(url, params, new FastJsonParser<T>(clazz), callback,showProgress) );
    }

	public <T> JDataCallback<T> jHttpDataCallback(final String url, final RequestParams params, final BaseParser<T> parser,final DataCallback<T> callback, final boolean showProgress) {
		return new JDataCallback<T>() {

			@Override
			public void onStart() {
				//httpStart()不放在这里调用的原因是由于 一进入界面就要显示进度条 覆盖界面上的Ui 在这里调用有时不能立即覆盖ui界面
			}

			@Override
			public void onFinish() {
				dismissProgress();
			}

			
			@Override
			public void onFailure(String message) {
				if(showProgress)
				{
					failure(url, params, parser , this);
				}
				else
				{
					callback.onFailure(message);
				}
			}

			@Override
			public void onSuccess(Response<T> data) {
				callback.onSuccess(data);
				dismissProgress();
			}
		};
	}
    
    public void httpStart()
    {
    	if(mError != null)
    	{
    		mError.setVisibility(View.GONE);
    	}
    	
    	if(mProgress == null)
    	{
    		mProgress = mHolder.mViewStubProgress.inflate();
    	}
    	else
    	{
    		mProgress.setVisibility(View.VISIBLE);
    	}
    }
    
    
    private void dismissProgress(){
    	if(mProgress != null)
    	{
    		mProgress.setVisibility(View.GONE);
    	}
    }
    
    public <T> void failure(final String url , final RequestParams params , final BaseParser<T> parser , final JDataCallback<T> callback)
    {
    	
    	dismissProgress();
    	
    	if(mError == null)
    	{
    		mError = mHolder.mViewStubError.inflate();
    	}
    	else
    	{
    		mError.setVisibility(View.VISIBLE);
    	}
    	
    	mError.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//点击重新加载
				get(url, params, parser, callback);
				return false;
			}
		});
    }
    
    @Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{
	    if(featureId == Window.FEATURE_ACTION_BAR && menu != null){
	        if(menu.getClass().getSimpleName().equals("MenuBuilder")){
	            try{
	                Method m = menu.getClass().getDeclaredMethod(
	                    "setOptionalIconsVisible", Boolean.TYPE);
	                m.setAccessible(true);
	                m.invoke(menu, true);
	            }
	            catch(NoSuchMethodException e){
	                Log.e("aa", "onMenuOpened", e);
	            }
	            catch(Exception e){
	                throw new RuntimeException(e);
	            }
	        }
	    }
	    return super.onMenuOpened(featureId , menu);
	}

}
