package com.lingtu.bean;

import java.util.List;

import com.lingtu.entity.TabDevice;

public class VOELevel {
	private int parentid;
	private int developid;
	private String development;
	private List<TabDevice> list;
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public int getDevelopid() {
		return developid;
	}
	public void setDevelopid(int developid) {
		this.developid = developid;
	}
	public String getDevelopment() {
		return development;
	}
	public void setDevelopment(String development) {
		this.development = development;
	}
	public List<TabDevice> getList() {
		return list;
	}
	public void setList(List<TabDevice> list) {
		this.list = list;
	}

}
