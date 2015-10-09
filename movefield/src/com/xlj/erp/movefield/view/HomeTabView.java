package com.xlj.erp.movefield.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xlj.erp.movefield.R;

public class HomeTabView extends LinearLayout {
	// 正常图片、选中图片
	// text
	// textcolor 正常颜色、选中颜色（写死）

	private ImageView mHomeTabIcon;
	private TextView mHomeTabText;

	private Drawable mNormalDrawable;
	private Drawable mCheckedDrawable;

	private int mNormalColor;
	private int mCheckedColor;

	public HomeTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.layout_home_tab, this, true);
		mHomeTabIcon = (ImageView) findViewById(R.id.home_tab_icon);
		mHomeTabText = (TextView) findViewById(R.id.home_tab_text);

		mNormalColor = context.getResources().getColor(R.color.gray);
		mCheckedColor = context.getResources().getColor(R.color.md_color_primary);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HomeTabView);
		CharSequence text = a.getText(R.styleable.HomeTabView_tab_text);
		mNormalDrawable = a.getDrawable(R.styleable.HomeTabView_tab_icon_normal);
		mCheckedDrawable = a.getDrawable(R.styleable.HomeTabView_tab_icon_checked);
		a.recycle();

		if (text != null) {
			mHomeTabText.setText(text);
		}
		if (mNormalDrawable != null) {
			mHomeTabIcon.setImageDrawable(mNormalDrawable);
		}
	}

	public HomeTabView(Context context) {
		this(context, null);
	}

	public void setChecked(boolean checked) {
		if (checked) {
			if (mCheckedDrawable != null) {
				mHomeTabIcon.setImageDrawable(mCheckedDrawable);
			}
			mHomeTabText.setTextColor(mCheckedColor);
		} else {
			if (mNormalDrawable != null) {
				mHomeTabIcon.setImageDrawable(mNormalDrawable);
			}
			mHomeTabText.setTextColor(mNormalColor);
		}
	}

}
