package com.shpro.xus.shproject.bean.people;

import com.shpro.xus.shproject.bean.Basebean;
import com.shpro.xus.shproject.bean.user.UserDetail;

import java.util.Date;

/**
 * 公司: 云筑网 -------------- 作者: 王旭
 * 
 * 日期: 2017年3月27日 说明:
 * 
 * 网站: http://www.yunzhuw.com/ 修订: 日期: 作者: 说明：
 * 
 */

public class NearPeople extends Basebean {
	private String updateTime;
	private String userId;
	private String userdatailid;
	private UserDetail userDetail;
	private String lat;
	private String lng;
	private String distance;
	private String latlng;

	public String getLatlng() {
		return latlng;
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserdatailid() {
		return userdatailid;
	}

	public void setUserdatailid(String userdatailid) {
		this.userdatailid = userdatailid;
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
}
