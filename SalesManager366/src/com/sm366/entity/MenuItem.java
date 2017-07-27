package com.sm366.entity;

public class MenuItem {
   private int menuId;
   private String menuName;
   private String imgName;
   private String action;
   private String activityId;
   
   public MenuItem(int menuId,String menuName,String imgName,String action,String activityId){
	   this.menuId=menuId;
	   this.menuName=menuName;
	   this.imgName=imgName;
	   this.action=action;
	   this.activityId=activityId;	   
   }
public int getMenuId() {
	return menuId;
}
public void setMenuId(int menuId) {
	this.menuId = menuId;
}
public String getMenuName() {
	return menuName;
}
public void setMenuName(String menuName) {
	this.menuName = menuName;
}
public String getImgName() {
	return imgName;
}
public void setImgName(String imgName) {
	this.imgName = imgName;
}
public String getAction() {
	return action;
}
public void setAction(String action) {
	this.action = action;
}
public String getActivityId() {
	return activityId;
}
public void setActivityIdString(String activityId) {
	this.activityId = activityId;
}
   
   
   
}
