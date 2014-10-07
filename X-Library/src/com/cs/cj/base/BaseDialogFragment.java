package com.cs.cj.base;

import com.caijia.library.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class BaseDialogFragment extends DialogFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle);
	}
}
