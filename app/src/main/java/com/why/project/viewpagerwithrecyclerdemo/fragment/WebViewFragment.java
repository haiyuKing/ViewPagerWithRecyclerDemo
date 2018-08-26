package com.why.project.viewpagerwithrecyclerdemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.why.project.viewpagerwithrecyclerdemo.R;


/**
 * @Created HaiyuKing
 * @Used  首页界面——碎片界面
 */
public class WebViewFragment extends BaseLazyFragment{
	
	private static final String TAG = "WebViewFragment";
	/**View实例*/
	private View myView;

	private WebView web_view;

	/**传递过来的参数*/
	private String bundle_param;

	//重写
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		//使用FragmentTabHost时，Fragment之间切换时每次都会调用onCreateView方法，导致每次Fragment的布局都重绘，无法保持Fragment原有状态。
		//http://www.cnblogs.com/changkai244/p/4110173.html
		if(myView == null){
			myView = inflater.inflate(R.layout.fragment_web, container, false);
			//接收传参
			Bundle bundle = this.getArguments();
			bundle_param = bundle.getString("param");
		}
		//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) myView.getParent();
		if (parent != null) {
			parent.removeView(myView);
		}

		return myView;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
		//初始化控件以及设置
		initView();
		//初始化控件的点击事件
		initEvent();

        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
    }

    //实现禁止预加载的功能
	@Override
	public void fetchData() {
		//初始化数据
		initData();
	}
	//实现禁止预加载的功能
	@Override
	public void onInvisible() {

	}

	@Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    }
    
    @Override
	public void onDestroy() {
    	super.onDestroy();
    }
    
    /**
	 * 初始化控件
	 */
	private void initView() {
		web_view = (WebView) myView.findViewById(R.id.web_view);
		//设置支持js脚本
//		web_view.getSettings().setJavaScriptEnabled(true);
		web_view.setWebViewClient(new WebViewClient() {
			/**
			 * 重写此方法表明点击网页内的链接由自己处理，而不是新开Android的系统browser中响应该链接。
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView webView, String url) {
				//webView.loadUrl(url);
				return false;
			}
		});
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		Log.e("tag","{initData}bundle_param="+bundle_param);
		web_view.loadUrl(bundle_param);//加载网页
	}

	/**
	 * 初始化点击事件
	 * */
	private void initEvent(){
	}
	
}
