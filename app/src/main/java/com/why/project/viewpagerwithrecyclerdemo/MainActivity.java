package com.why.project.viewpagerwithrecyclerdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.why.project.viewpagerwithrecyclerdemo.adapter.MyTabViewPagerAdapter;
import com.why.project.viewpagerwithrecyclerdemo.adapter.TabAdapter;
import com.why.project.viewpagerwithrecyclerdemo.bean.TabItemBean;
import com.why.project.viewpagerwithrecyclerdemo.fragment.WebViewFragment;
import com.why.project.viewpagerwithrecyclerdemo.viewpager.MyCustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = MainActivity.class.getSimpleName();

	//分类列表
	private RecyclerView mTabRecycleView;
	private ArrayList<TabItemBean> mTabItemBeanArrayList;
	private TabAdapter mTabAdapter;

	/**保存的选项卡的下标值*/
	private int savdCheckedIndex = 0;
	/**当前的选项卡的下标值*/
	private int mCurrentIndex = -1;

	//横向滚动的ViewPager
	private MyCustomViewPager mViewPager;
	/**滑屏适配器*/
	private MyTabViewPagerAdapter viewPagerAdapter;
	/**碎片集合*/
	private List<Fragment> fragmentList;
	//碎片界面
	private WebViewFragment mWebViewFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//初始化控件以及设置
		initView();
		//初始化数据
		initData();
		//初始化控件的点击事件
		initEvent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.e(TAG,"{onResume}savdCheckedIndex="+savdCheckedIndex);
		//设置保存的或者初始的选项卡标红显示
		setTabsDisplay(savdCheckedIndex);

		mCurrentIndex = -1;//解决按home键后长时间不用，再次打开显示空白的问题
		//设置保存的或者初始的选项卡展现对应的fragment
		setFragmentDisplay(savdCheckedIndex);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		mTabRecycleView = findViewById(R.id.tab_top_recycler);
		mViewPager = (MyCustomViewPager) findViewById(R.id.vp_tab);
	}
	/**
	 * 初始化数据
	 */
	public void initData() {
		//初始化集合
		mTabItemBeanArrayList = new ArrayList<TabItemBean>();

		mTabItemBeanArrayList.add(new TabItemBean("百度","http://www.baidu.com"));
		mTabItemBeanArrayList.add(new TabItemBean("CSDN","http://blog.csdn.net"));
		mTabItemBeanArrayList.add(new TabItemBean("博客园","http://www.cnblogs.com"));
		mTabItemBeanArrayList.add(new TabItemBean("极客头条","http://geek.csdn.net/mobile"));
		mTabItemBeanArrayList.add(new TabItemBean("优设","http://www.uisdc.com/"));
		mTabItemBeanArrayList.add(new TabItemBean("玩Android","http://www.wanandroid.com/index"));
		mTabItemBeanArrayList.add(new TabItemBean("掘金","https://juejin.im/"));

		//设置布局管理器【表情分类列表】
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
		mTabRecycleView.setLayoutManager(linearLayoutManager);
		//设置适配器
		if(mTabAdapter == null){
			//设置适配器
			mTabAdapter = new TabAdapter(this, mTabItemBeanArrayList);
			mTabRecycleView.setAdapter(mTabAdapter);
			//添加分割线
			//设置添加删除动画
			//调用ListView的setSelected(!ListView.isSelected())方法，这样就能及时刷新布局
			mTabRecycleView.setSelected(true);
		}else{
			mTabAdapter.notifyDataSetChanged();
		}

		//将碎片添加到集合中
		fragmentList = new ArrayList<>();
		for(int i=0;i<mTabItemBeanArrayList.size();i++){
			TabItemBean tabItemBean = mTabItemBeanArrayList.get(i);

			Bundle bundle = new Bundle();
			bundle.putString("param", tabItemBean.getTabUrl());
			fragmentList.add(WebViewFragment.getInstance(WebViewFragment.class,bundle));
		}
		//给ViewPager设置适配器
		viewPagerAdapter = new MyTabViewPagerAdapter(getSupportFragmentManager(),fragmentList);
		mViewPager.setAdapter(viewPagerAdapter);
	}

	/**
	 * 初始化点击事件
	 * */
	private void initEvent() {
		//列表适配器的点击监听事件
		mTabAdapter.setOnItemClickLitener(new TabAdapter.OnItemClickLitener() {
			@Override
			public void onItemClick(View view, int position) {
				if (mCurrentIndex == position) {
					return;
				}
				setFragmentDisplay(position);//独立出来，用于OnResume的时候初始化展现相应的Fragment

				savdCheckedIndex = position;
				mCurrentIndex = position;
			}
		});

		//viewpager的滑动事件监听
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			/*此方法是页面跳转完后得到调用，arg0是你当前选中的页面的Position（位置编号）。*/
			public void onPageSelected(int arg0) {
				//解决滑动后，无法点击上一个fragment选项卡的问题
				setTabsDisplay(arg0);
				savdCheckedIndex = arg0;
				mCurrentIndex = arg0;
			}

			@Override
			/*
			 * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到调用。其中三个参数的含义分别为：
			 * arg0 :当前页面，及你点击滑动的页面
			 * arg1:当前页面偏移的百分比
			 * arg2:当前页面偏移的像素位置   */
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			/*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
			 * arg0==0的时候默示什么都没做
			 * arg0 ==1的时候默示正在滑动
			 * arg0==2的时候默示滑动完毕了*/
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 设置导航栏中选项卡之间的切换
	 */
	private void setTabsDisplay(int index){
		if(mTabAdapter != null){
			mTabAdapter.setSelected(index);
			mTabRecycleView.smoothScrollToPosition(index);//平移滑动到指定position
		}
	}

	/**
	 * 设置碎片之间的切换
	 */
	private void setFragmentDisplay(int index){
		mViewPager.setCurrentItem(index, false);//smoothScroll false表示切换的时候,不经过两个页面的中间页
	}

	/**
	 * http://blog.csdn.net/caesardadi/article/details/20382815
	 * */
	// 自己记录fragment的位置,防止activity被系统回收时，fragment错乱的问题【按home键返回到桌面一段时间，然后在进程里面重新打开，会发现RadioButton的图片选中状态在第二个，但是文字和背景颜色的选中状态在第一个】
	//onSaveInstanceState()只适合用于保存一些临时性的状态，而onPause()适合用于数据的持久化保存。
	protected void onSaveInstanceState(Bundle outState) {
		//http://www.cnblogs.com/chuanstone/p/4672096.html?utm_source=tuicool&utm_medium=referral
		//总是执行这句代码来调用父类去保存视图层的状态”。其实到这里大家也就明白了，就是因为这句话导致了重影的出现
		super.onSaveInstanceState(outState);
		outState.putInt("selectedCheckedIndex", savdCheckedIndex);
		outState.putInt("mCurrentIndex", mCurrentIndex);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		savdCheckedIndex = savedInstanceState.getInt("selectedCheckedIndex");
		mCurrentIndex = savedInstanceState.getInt("mCurrentIndex");
		super.onRestoreInstanceState(savedInstanceState);
	}

}
