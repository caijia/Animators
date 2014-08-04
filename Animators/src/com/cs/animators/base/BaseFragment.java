package com.cs.animators.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewStub;
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

public abstract class BaseFragment extends Fragment{
	
	private ViewHolder mHolder ;
	private View mProgress ;
    private View mError  ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//没有这句在Fragment中显示不出ActionBar
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		//加载基类的布局文件
		View mainView = inflater.inflate(R.layout.fragment_base, container, false);
		mHolder = new ViewHolder(mainView);
		
		//加载子类的布局 在子类的实现类里面调用setContentView();
		loadLayout();
		
		ButterKnife.inject(this, mainView);
		
		//逻辑
		processLogic();
		
		return mainView;
	}
	
	class ViewHolder{
		
		@InjectView(R.id.base_fragment_content) LinearLayout parentContent ;
		@InjectView(R.id.viewstub_http_loading) ViewStub mViewStubProgress ;
		@InjectView(R.id.viewstub_http_error) ViewStub mViewStubError ;
		
		public ViewHolder(View v)
		{
			ButterKnife.inject(this, v);
		}
	}
	
	protected void setContentView(int resId) {
		View childView = LayoutInflater.from(getActivity()).inflate(resId, null);
		mHolder.parentContent.addView(childView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	
	protected abstract void loadLayout() ;
	protected abstract void processLogic() ;
	
	
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
    	JHttpClient.get(getActivity(), url, params, parser,jHttpDataCallback( url, params, parser, callback , showProgress));
    }
    
    protected <T> void get(String url ,  RequestParams params ,  Class<T> clazz ,  DataCallback<T> callback , boolean showProgress)
    {
    	if(showProgress)
			httpStart();
    	JHttpClient.get(getActivity(), url, params, clazz,  jHttpDataCallback(url, params, new FastJsonParser<T>(clazz), callback,showProgress) );
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
    
}

