package com.sm.interfaces;

import com.sm.view.AppCell;

public interface OnAppCellLinstener {
	  //���Ӧ��
      public void onCellClick(AppCell cell);
      
      //ɾ��Ӧ��
      public void onDelClick(AppCell cell);
      
      //���Ӧ��
      public void onAddClick(AppCell cell);
}
