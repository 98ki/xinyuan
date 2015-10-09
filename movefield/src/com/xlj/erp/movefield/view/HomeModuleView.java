package com.xlj.erp.movefield.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xlj.erp.movefield.R;

public class HomeModuleView extends RelativeLayout {
	private ImageView mHomeModuleIcon;
	private TextView mHomeModuleText;

	public HomeModuleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		LayoutInflater.from(context).inflate(R.layout.layout_home_module, this, true);
		mHomeModuleIcon = (ImageView) findViewById(R.id.home_module_icon);
		mHomeModuleText = (TextView) findViewById(R.id.home_module_text);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HomeModuleView);
		CharSequence text = a.getText(R.styleable.HomeModuleView_module_text);
		Drawable iconDrawable = a.getDrawable(R.styleable.HomeModuleView_module_icon);
		a.recycle();
		
		if(text!=null){
			mHomeModuleText.setText(text);
		}
		if(iconDrawable!=null){
			mHomeModuleIcon.setImageDrawable(iconDrawable);
		}
	}

	public HomeModuleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HomeModuleView(Context context) {
		this(context, null);
	}

}
