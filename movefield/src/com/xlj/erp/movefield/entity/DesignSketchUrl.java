package com.xlj.erp.movefield.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 效果图url
 * 
 * @author chaohui.yang
 *
 */
public class DesignSketchUrl implements Serializable {
	private List<String> designpicurllist;
	private List<String> realisticpicurllist;
	private List<String> templatepicurllist;
	private List<String> trafficpicurlist;

	public List<String> getDesignpicurllist() {
		return designpicurllist;
	}

	public void setDesignpicurllist(List<String> designpicurllist) {
		this.designpicurllist = designpicurllist;
	}

	public List<String> getRealisticpicurllist() {
		return realisticpicurllist;
	}

	public void setRealisticpicurllist(List<String> realisticpicurllist) {
		this.realisticpicurllist = realisticpicurllist;
	}

	public List<String> getTemplatepicurllist() {
		return templatepicurllist;
	}

	public void setTemplatepicurllist(List<String> templatepicurllist) {
		this.templatepicurllist = templatepicurllist;
	}

	public List<String> getTrafficpicurlist() {
		return trafficpicurlist;
	}

	public void setTrafficpicurlist(List<String> trafficpicurlist) {
		this.trafficpicurlist = trafficpicurlist;
	}

}
