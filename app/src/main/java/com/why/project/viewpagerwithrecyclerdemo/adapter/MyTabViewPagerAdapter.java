package com.why.project.viewpagerwithrecyclerdemo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by HaiyuKing
 * Used
 */

public class MyTabViewPagerAdapter extends FragmentStatePagerAdapter {

	/**碎片集合*/
	private List<Fragment> fragmentList;

	public MyTabViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 自定义构造函数：用于传递碎片集合过来
	 * 一般都写上*/
	public MyTabViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
		super(fm);
		this.fragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//viewpager+fragment来回滑动fragment重新加载的简单解决办法：http://blog.csdn.net/qq_28058443/article/details/51519663
		//建议保留，因为首页碎片界面含有6个界面切换，总不能设置setOffscreenPageLimit(6)吧，而且设置个数少的话，销毁后还得重新加载
		//super.destroyItem(container, position, object);
	}
}
