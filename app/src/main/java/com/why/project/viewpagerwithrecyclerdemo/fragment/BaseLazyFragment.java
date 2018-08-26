package com.why.project.viewpagerwithrecyclerdemo.fragment;

import android.os.Bundle;
import android.util.Log;

/**
 * Used 主要实现配合viewpager使用时禁止懒加载
 * 我们一般在onCreateView方法初始化视图，onActivityCreated方法初始化数据，
 * 通过setUserVisibleHint和getUserVisibleHint方法来设置和获取Fragment的显示状态，当显示了才去加载数据。
 *
 * setUserVisibleHint: isVisibleToUser = false
 onAttach
 onCreate
 setUserVisibleHint: isVisibleToUser = true
 onCreateView
 onActivityCreated
 onStart
 onResume
 onPause
 onStop
 onDestroyView
 onDestroy
 onDetach
 参考资料：https://blog.csdn.net/aiynmimi/article/details/73277836

 */

public abstract class BaseLazyFragment extends BaseFragment{

	private static final String TAG = BaseLazyFragment.class.getSimpleName();

	/**
	 * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
	 */
	protected boolean isViewInitiated;//view是否初始化
	/**
	 * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
	 */
	protected boolean isVisibleToUser;//是否可见
	protected boolean isDataInitiated;//数据是否加载完成

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		Log.e(TAG,"{setUserVisibleHint}isVisibleToUser="+isVisibleToUser);
		super.setUserVisibleHint(isVisibleToUser);
		this.isVisibleToUser = isVisibleToUser;
		if(getUserVisibleHint()) {
			prepareFetchData();
		} else {
			if(isViewInitiated){
				onInvisible();
			}
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isViewInitiated = true;
		prepareFetchData();
	}

	/**
	 * 视图销毁的时候 Fragment是否初始化的状态变为false
	 */
	@Override
	public void onDestroyView() {
		Log.e(TAG,"{onDestroyView}");
		super.onDestroyView();
		isViewInitiated = false;
		isVisibleToUser = false;
		isDataInitiated = false;
	}

	//初始化数据
	public abstract void fetchData();
	//当隐藏的时候执行的方法
	public abstract void onInvisible();

	public boolean prepareFetchData() {
		return prepareFetchData(false);
	}

	/**
	 * 请求数据
	 * @param forceUpdate : 是否强制请求数据*/
	public boolean prepareFetchData(boolean forceUpdate) {
		if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
			fetchData();
			isDataInitiated = true;
			return true;
		}
		return false;
	}
}
