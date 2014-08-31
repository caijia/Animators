package com.cs.animators.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.view.ViewStub;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.cs.animationvideo.R;
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
    private BaseHandler mHandler ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mActionBar = getSupportActionBar();
      //去掉ActionBar的图标
//  		mActionBar.setIcon(new ColorDrawable(android.R.color.transparent));
  		mActionBar.setIcon(getResources().getDrawable(R.drawable.actionbar_title_icon));
  		
        mContext = this ;
        
        View v = LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        mHolder = new ViewHolder(v);
        
        //加载布局   在子类方法中调用setContentView(int resid);
        loadLayout();
        
        super.setContentView(mHolder.mainContent);
        
        //不管有没有实体菜单键都显示OverFlow ActionBar Item
        showOverFlowMenu();
        
        ButterKnife.inject(this);
        
        //是否有返回键
        mActionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled());
        
        //取得传递过来的参数
        getExtra(getIntent().getExtras());
        
        if(switchThread()){
        	
        	mHandler = new BaseHandler(this);
        	 Runnable runnable = new Runnable() {
     			public void run() {
     				mHandler.sendMessage(doInBackground());
     			}
     		};
             new Thread(runnable).start();
        }
        
		// 逻辑
		processLogic();
        
    }
    
	static class BaseHandler extends Handler{
    	
    	private final WeakReference<? extends BaseActivity> mTarget ;
		
		public BaseHandler(BaseActivity target)
		{
			mTarget = new WeakReference<BaseActivity>(target);
		}
    	
    	@Override
    	public void handleMessage(Message msg) {
    		BaseActivity activity = mTarget.get();
    		activity.onPostExecute(msg);
    	}
    }
    
	/**
     * supportV7 里面的ActionBar overFlowMenu 如果有实体菜单键 就不会显示OverFlowMenu  
     * 这个方法是让有实体菜单键的手机显示OverFlowMenu
     */
    private void showOverFlowMenu() {
    	try { 

            ViewConfiguration config =ViewConfiguration.get(this); 

            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey"); 

            if(menuKeyField != null ){

            menuKeyField.setAccessible(true); 

            menuKeyField.setBoolean(config, false);

            }

        } catch (Exception e) { 

            e.printStackTrace();

        }       
	}

	protected abstract void loadLayout();
    protected abstract void processLogic();
    protected void getExtra(Bundle bundle) {
		
	}
    protected boolean displayHomeAsUpEnabled() {
		return false;
	}
    protected Message doInBackground() {
		return null ;
	}
    protected void onPostExecute(Message msg) {
		
	}
    protected boolean switchThread() {
		return false;
	}
    
    
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
			showProgress();
    	JHttpClient.get(this, url, params, parser,jHttpDataCallback( url, params, parser, callback , showProgress));
    }
    
    protected <T> void get(String url ,  RequestParams params ,  Class<T> clazz ,  DataCallback<T> callback , boolean showProgress)
    {
    	if(showProgress)
			showProgress();
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
				dismiss();
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
				dismiss();
			}
		};
	}
    
	protected void showProgress()
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
    
    
    protected void dismiss(){
    	if(mProgress != null)
    	{
    		mProgress.setVisibility(View.GONE);
    	}
    }
    
    public <T> void failure(final String url , final RequestParams params , final BaseParser<T> parser , final JDataCallback<T> callback)
    {
    	
    	dismiss();
    	
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
    
    @Override
    public Intent getSupportParentActivityIntent() {
    	finish();
    	return super.getSupportParentActivityIntent();
    }
    

}
