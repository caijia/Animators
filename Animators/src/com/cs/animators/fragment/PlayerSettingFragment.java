package com.cs.animators.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.cs.animators.R;
import com.cs.animators.eventbus.PlayerSizeEvent;
import com.cs.animators.util.PlayerUtils;
import de.greenrobot.event.EventBus;

public class PlayerSettingFragment extends DialogFragment {
	
	@InjectView(R.id.txt_setting_cancel)
	TextView mTxtClose ;
	
	@InjectView(R.id.skb_brightness)
	SeekBar mSkbBrightness ;
	
	@InjectView(R.id.rgoup_canvas)
	RadioGroup mRgroupCanvas ;
	
	public static final int FULL_SCREEN = 1 ;
	public static final int MEDIUM_SCREEN = 2 ;
	public static final int SMALL_SCREEN = 3 ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
		
		getExtra();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		LayoutParams params = this.getDialog().getWindow().getAttributes();
		params.dimAmount = 0.0f ;
		this.getDialog().getWindow().setAttributes(params);
		getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_player_setting, container,false);
		ButterKnife.inject(this, rootView);
		processLogic();
		return rootView;
	}

	private void processLogic() {
		
		switch (mScreenSize) {
		case FULL_SCREEN:
			mRgroupCanvas.check(R.id.rbtn_brightness_ten);
			break;

		case MEDIUM_SCREEN:
			mRgroupCanvas.check(R.id.rbtn_brightness_seven);
			break ;
			
		case SMALL_SCREEN :
			mRgroupCanvas.check(R.id.rbtn_brightness_five);
			break ;
		default:
			break;
		}
		
		//brightness 30 - 255 (避免黑屏)
		mSkbBrightness.setMax(255 - 30);
		
		int screenBrightness = PlayerUtils.getScreenBrightness(getActivity());
		setScreenBrightness(screenBrightness);
		mSkbBrightness.setProgress(screenBrightness);
		
		mSkbBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				int progress = seekBar.getProgress();
				setScreenBrightness(progress);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			}
		});
		
		mRgroupCanvas.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int videoSize = -1 ;
				group.check(checkedId);
				switch (checkedId) {
				case R.id.rbtn_brightness_five:
					videoSize = SMALL_SCREEN ;
					break;

				case R.id.rbtn_brightness_seven:
					videoSize = MEDIUM_SCREEN ;
					break;
				
				case R.id.rbtn_brightness_ten:
					videoSize = FULL_SCREEN ;
					break ;
					
					default:
						break ;
				}
				if(videoSize != -1)
				{
					EventBus.getDefault().post(new PlayerSizeEvent(videoSize));
				}
				
//				PlayerSettingFragment.this.dismiss();
			}
		});
		
	}

	public void setScreenBrightness(int screenBrightness) {
		LayoutParams params = getActivity().getWindow().getAttributes();
		if(screenBrightness < 30)
		{
			screenBrightness = 30 ;
		}
		params.screenBrightness = screenBrightness / (float) 255 ;
		getActivity().getWindow().setAttributes(params);
		PlayerUtils.setScreenBrightness(getActivity(), screenBrightness);
	}
	
	@OnClick(R.id.txt_setting_cancel)
	void closeDialog(){
		this.dismiss();
	}
	
	public static final String SURFACEVIEW_SIZE = "surfaceview_size";
	private int mScreenSize = FULL_SCREEN ;
	
	private void getExtra(){
		Bundle bundle = getArguments();
		if(bundle != null)
		{
			mScreenSize =  bundle.getInt(SURFACEVIEW_SIZE);
		}
	}
}
