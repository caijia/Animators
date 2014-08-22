package com.cs.animators;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class LauncherActivity extends Activity {

	private ImageView mFlower;
	
	private ImageView mFlowerBomb ;
	
	private AnimationDrawable mFlowerBombAnimation  ;
	
	private Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher);
		
		mFlower = (ImageView) findViewById(R.id.launcher_flower);
		mFlowerBomb = (ImageView) findViewById(R.id.launcher_flower_bomb);
		
		mFlowerBombAnimation = (AnimationDrawable) getResources().getDrawable(R.drawable.launcher_animation);
		
		mFlower.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				int location[] = new int[2];
				mFlower.getLocationOnScreen(location);
				int x = location[0];
				int y = location[1];
				
				// 305 * 300
				TranslateAnimation animation = new TranslateAnimation(0, -(x-153+mFlower.getWidth()/2), 0, -(y-150+mFlower.getHeight()/2));
				animation.setDuration(1800);
				animation.setInterpolator(new AccelerateDecelerateInterpolator());
				animation.setFillAfter(true);
				animation.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						mFlower.setVisibility(View.GONE);
						mFlower.clearAnimation();
						mFlowerBomb.setVisibility(View.VISIBLE);
						//总共14张图 每张图50ms 后进入主界面
						mHandler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								welcome();
							}
						}, 14*60);
						if(mFlowerBombAnimation != null && !mFlowerBombAnimation.isRunning()){
							mFlowerBomb.setImageDrawable(mFlowerBombAnimation);
							mFlowerBombAnimation.start();
						}
					}
				});
				mFlower.startAnimation(animation);
				mFlower.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				
			}
		});
	}

	private void welcome() {
		Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

}
