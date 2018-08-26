package com.why.project.viewpagerwithrecyclerdemo.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by HaiyuKing
 * Used MyCustomViewPager对应的适配器模板【放在这里，用于模板】
 * 当viewpager中fragment数量多的时候用FragmentStatePagerAdapter，反之则用FragmentPagerAdapter。
 */

public class MyCustomViewPagerAdapter extends FragmentStatePagerAdapter {

	/**碎片集合*/
	private List<Fragment> fragmentList;

	public MyCustomViewPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 自定义构造函数：用于传递碎片集合过来
	 * 一般都写上*/
	public MyCustomViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
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
		//viewpager+fragment来回滑动fragment重新加载的简单解决办法：注释下面的代码
		//不建议使用，因为当选项卡过多的时候，如果不销毁的是，担心内存溢出
		//http://blog.csdn.net/qq_28058443/article/details/51519663
		super.destroyItem(container, position, object);
	}
}
