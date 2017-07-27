package com.sm.interfaces;

import com.sm.view.AppCell;

public interface OnAppCellLinstener {
	  //点击应用
      public void onCellClick(AppCell cell);
      
      //删除应用
      public void onDelClick(AppCell cell);
      
      //添加应用
      public void onAddClick(AppCell cell);
}
