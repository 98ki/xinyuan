/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */
package com.xlj.erp.movefield.chart;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.xclcharts.chart.PieData;
import org.xclcharts.chart.RoseChart;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @ClassName RoseChart01View
 * @Description  南丁格尔玫瑰图 的例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class RoseChart01View extends TouchView {

	private String TAG = "RoseChart01View";
	private RoseChart chart = new RoseChart();
	
	LinkedList<PieData> roseData = new LinkedList<PieData>();	
	
	public RoseChart01View(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			initView();
	}
	
	public RoseChart01View(Context context, AttributeSet attrs){   
        	super(context, attrs);   
        	initView();
	 }
	 
	 public RoseChart01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 	//chartDataSet();
			//chartRender();
	 }
	 
	 
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  	
	
	public void chartRender(LinkedList<PieData> pieData)
	{
		try {			
			//设置标签显示位置,当前设置标签显示在扇区中间
			chart.setLabelPosition(XEnum.SliceLabelPosition.LINE);
			
			chart.getLabelPaint().setTextSize(65);
			
			//设置绘图区默认缩进px值
			//int [] ltrb = getPieDefaultSpadding();
			int [] ltrb = new int[4];
			ltrb[0] = DensityUtil.dip2px(getContext(), 30); //top	
			ltrb[1] = DensityUtil.dip2px(getContext(), 80); //bottom	
			ltrb[2] = DensityUtil.dip2px(getContext(), 30); //left		
			ltrb[3] = DensityUtil.dip2px(getContext(), 40); //right	
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);	
			
			//背景 
			chart.setApplyBackgroundColor(false);
			chart.setBackgroundColor(Color.WHITE);
			
			//数据源
			chart.setDataSource(pieData);			
			//显示图例
			chart.getPlotLegend().showLegend();
			
			chart.hideGradient();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}
	
	@Override
    public void render(Canvas canvas) {
        try{
        	//canvas.drawColor(Color.WHITE);
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}

	@Override
	public List<XChart> bindChart() {
		// TODO Auto-generated method stub		
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);		
		return lst;
	}
}
