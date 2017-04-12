package com.shpro.xus.shproject.bean.tb;

import com.shpro.xus.shproject.bean.Basebean;

/**
 * 公司: 云筑网 -------------- 作者: 王旭
 * 
 * 日期: 2017年4月11日 说明:
 * 
 * 网站: http://www.yunzhuw.com/ 修订: 日期: 作者: 说明：
 * 
 */

public class TBOneDay extends Basebean {
	private String title;
	private String salary;
	private String images;
	private String content;
	private String url;
	private String tbid;

	public String getTbid() {
		return tbid;
	}

	public void setTbid(String tbid) {
		this.tbid = tbid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
