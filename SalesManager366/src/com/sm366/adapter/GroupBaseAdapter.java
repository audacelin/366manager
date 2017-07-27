package com.sm366.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class GroupBaseAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count=0;
		int len=getSectionCount();
		for(int i=0;i<len;i++){
			count=1 + getRowCount(i) + count;
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Object object=null;
		IndexPath indexPath=getIndexPath(position);
		if(indexPath.getRow()==-1){
			object=getGroupItem(indexPath.getSection());
		}else{
			object=getRowItem(indexPath.getRow(), indexPath.getSection());
		}
		return object;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		long id=0;
		IndexPath indexPath=getIndexPath(position);
		
		if(indexPath.getRow()==-1){
			id=getGroupItemId(indexPath.getSection());
		}else{
			id=getRowItemId(indexPath.getRow(), indexPath.getSection());
		}
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=null;
		IndexPath indexPath=getIndexPath(position);
		if(indexPath.getRow()==-1){
			view=getSectionView(indexPath.getSection(), position, convertView, parent);
		}else{
			view=getRowView(indexPath.getRow(), indexPath.getSection(), position, convertView, parent);
		}
		return view;
	}
	public IndexPath getIndexPath(int position){
		int section=0;
		int pos=1+getRowCount(0);
		while(section < getSectionCount()){
			if(position == 0) return new IndexPath(-1, 0);
			if(pos > position){
				int row=position - ( pos - getRowCount(section));
				return new IndexPath(row, section);
			}else if(pos == position){
				return new IndexPath(-1, section+1);
			}
			pos=pos + 1 + getRowCount(++section);
		}
		return null;
	}
	public abstract int getSectionCount();
	public abstract int getRowCount(int section);
	public abstract View getSectionView(int section,int position,View convertView,ViewGroup parent);
	public abstract View getRowView(int row,int section,int position,View convertView,ViewGroup parent);

	public Object getRowItem(int row,int section){
		return null;
	}
	public long getRowItemId(int row,int section){
		return 0;
	}
	public Object getGroupItem(int section){
		return null;
	}
	public long getGroupItemId(int section){
		return 0;
	}
	public static class IndexPath {
		private int section;
		private int row;
		public IndexPath(int row,int section){
			this.section=section;
			this.row=row;
		}
		public int getSection(){
			return this.section;
		}
		public int getRow(){
			return this.row;
		}
	}
}
