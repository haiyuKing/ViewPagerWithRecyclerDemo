package com.why.project.viewpagerwithrecyclerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.why.project.viewpagerwithrecyclerdemo.R;
import com.why.project.viewpagerwithrecyclerdemo.bean.TabItemBean;

import java.util.ArrayList;

/**
 * Created by HaiyuKing
 * Used
 */

public class TabAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	/**上下文*/
	private Context myContext;
	/**集合*/
	private ArrayList<TabItemBean> listitemList;

	private int selectedPosition = -1;//选中的列表项

	/**
	 * 构造函数
	 */
	public TabAdapter(Context context, ArrayList<TabItemBean> itemlist) {
		myContext = context;
		listitemList = itemlist;
	}

	/**
	 * 获取总的条目数
	 */
	@Override
	public int getItemCount() {
		return listitemList.size();
	}

	/**
	 * 创建ViewHolder
	 */
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(myContext).inflate(R.layout.tab_list_item, parent, false);
		ItemViewHolder itemViewHolder = new ItemViewHolder(view);
		return itemViewHolder;
	}

	/**
	 * 声明grid列表项ViewHolder
	 */
	static class ItemViewHolder extends RecyclerView.ViewHolder {
		public ItemViewHolder(View view) {
			super(view);

			listItemLayout = (RelativeLayout) view.findViewById(R.id.listitem_layout);
			mTabTitle = (TextView) view.findViewById(R.id.top_title);
			mTabUnderLine = (TextView) view.findViewById(R.id.top_underline);
		}

		RelativeLayout listItemLayout;
		TextView mTabTitle;
		TextView mTabUnderLine;
	}

	/**
	 * 将数据绑定至ViewHolder
	 */
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int index) {

		//判断属于列表项
		if (viewHolder instanceof ItemViewHolder) {
			TabItemBean tabItemBean = listitemList.get(index);
			final ItemViewHolder itemViewHold = ((ItemViewHolder) viewHolder);

			itemViewHold.mTabTitle.setText(tabItemBean.getTabTitle());

			//设置下划线的宽度值==文本的宽度值
			itemViewHold.mTabTitle.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
			Log.w("why", "top_title.getMeasuredWidth()="+itemViewHold.mTabTitle.getMeasuredWidth());
			itemViewHold.listItemLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
			Log.w("why", "toptabLayout.getMeasuredWidth()="+itemViewHold.listItemLayout.getMeasuredWidth());
			itemViewHold.mTabUnderLine.setWidth(itemViewHold.mTabTitle.getMeasuredWidth());//手动设置下划线的宽度值

			if(selectedPosition == index){
				itemViewHold.mTabTitle.setTextColor(myContext.getResources().getColor(R.color.tab_text_selected_top));
				itemViewHold.mTabUnderLine.setBackgroundColor(myContext.getResources().getColor(R.color.tab_auto_selected_top));
			}else{
				itemViewHold.mTabTitle.setTextColor(myContext.getResources().getColor(R.color.tab_text_normal_top));
				itemViewHold.mTabUnderLine.setBackgroundColor(myContext.getResources().getColor(R.color.tab_auto_normal_top));
			}

			//如果设置了回调，则设置点击事件
			if (mOnItemClickLitener != null) {
				itemViewHold.listItemLayout.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						int position = itemViewHold.getLayoutPosition();//在增加数据或者减少数据时候，position和index就不一样了
						mOnItemClickLitener.onItemClick(itemViewHold.listItemLayout, position);
						setSelected(position);
					}
				});
			}

		}
	}

	/**更改选中的列表项下标值*/
	public void setSelected(int selected) {
		this.selectedPosition = selected;
		notifyDataSetChanged();
	}

	/**
	 * 添加Item--用于动画的展现
	 */
	public void addItem(int position, TabItemBean listitemBean) {
		listitemList.add(position, listitemBean);
		notifyItemInserted(position);
	}

	/**
	 * 删除Item--用于动画的展现
	 */
	public void removeItem(int position) {
		listitemList.remove(position);
		notifyItemRemoved(position);
	}

	/*=====================添加OnItemClickListener回调================================*/
	public interface OnItemClickLitener {
		void onItemClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
}
