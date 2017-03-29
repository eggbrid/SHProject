package com.shpro.xus.shproject.bean;
/**
 * 公司:  云筑网 
 * -------------- 
 * 作者:  王旭
 * 
 * 日期:  2017年3月29日
 * 说明:
 * 
 * 网站:	 http://www.yunzhuw.com/
 * 修订:
 * 日期:      作者:
 * 说明：
 * 
 */

public class BagMap extends Basebean{
	private String bagTId;
	private String lat;
	private String lng;
	private String latlng;
	private BagTemplate bagTemplate ;
	public BagTemplate getBagTemplate() {
		return bagTemplate;
	}
	public void setBagTemplate(BagTemplate bagTemplate) {
		this.bagTemplate = bagTemplate;
	}
	public String getBagTId() {
		return bagTId;
	}
	public void setBagTId(String bagTId) {
		this.bagTId = bagTId;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLatlng() {
		return latlng;
	}
	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

}
