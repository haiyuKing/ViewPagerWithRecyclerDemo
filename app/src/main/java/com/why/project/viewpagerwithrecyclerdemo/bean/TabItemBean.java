package com.why.project.viewpagerwithrecyclerdemo.bean;

/**
 * Created by HaiyuKing
 * Used
 */

public class TabItemBean {
	private String tabTitle;
	private String tabUrl;

	public TabItemBean(String tabTitle, String tabUrl){
		this.tabTitle = tabTitle;
		this.tabUrl = tabUrl;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public String getTabUrl() {
		return tabUrl;
	}

	public void setTabUrl(String tabUrl) {
		this.tabUrl = tabUrl;
	}
}
