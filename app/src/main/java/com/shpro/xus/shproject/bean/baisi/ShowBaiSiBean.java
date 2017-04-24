package com.shpro.xus.shproject.bean.baisi;

import com.shpro.xus.shproject.bean.Basebean;

/**
 * 公司: 云筑网 -------------- 作者: 王旭
 * 
 * 日期: 2017年4月20日 说明:
 * 
 * 网站: http://www.yunzhuw.com/ 修订: 日期: 作者: 说明：
 * 
 */

public class ShowBaiSiBean extends Basebean{
	private String text;
	private String create_time;
	private String profile_image;
	private String video_uri;
	private String love;
	private String hate;
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getProfile_image() {
		return profile_image;
	}
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
	public String getVideo_uri() {
		return video_uri;
	}
	public void setVideo_uri(String video_uri) {
		this.video_uri = video_uri;
	}
	public String getLove() {
		return love;
	}
	public void setLove(String love) {
		this.love = love;
	}
	public String getHate() {
		return hate;
	}
	public void setHate(String hate) {
		this.hate = hate;
	}
}
