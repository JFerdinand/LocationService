package com.lingtu.bean;

import java.util.ArrayList;
import java.util.List;

public class VOEDevelopment {
	private int developid;
	private String development;
	private List<VOELevel> level = new ArrayList<VOELevel>();
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
	public List<VOELevel> getLevel() {
		return level;
	}
	public void setLevel(List<VOELevel> level) {
		this.level = level;
	}
}
