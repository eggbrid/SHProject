package com.shpro.xus.shproject.bean.response;

import com.shpro.xus.shproject.bean.Bag;
import com.shpro.xus.shproject.bean.BagMap;

import java.util.List;


/**
 * 公司:  云筑网 
 * -------------- 
 * 作者:  王旭
 * 
 * 日期:  2017年3月25日
 * 说明:
 * 
 * 网站:	 http://www.yunzhuw.com/
 * 修订:
 * 日期:      作者:
 * 说明：
 * 
 */

public class BagMapListResponse extends Response{
	private List<BagMap> list;

	public List<BagMap> getList() {
		return list;
	}

	public void setList(List<BagMap> list) {
		this.list = list;
	}

}
