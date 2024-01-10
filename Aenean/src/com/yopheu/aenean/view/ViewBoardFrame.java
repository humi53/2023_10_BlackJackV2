package com.yopheu.aenean.view;

import com.yopheu.aenean.config.StrColor;

public class ViewBoardFrame {
	private String leftTop = "┌";
	private String rightTop = "┐";
	private String leftBottom = "└";
	private String rightBottom = "┘";
	private String edge = "│";
	private String roof = "-────────-";
	private int width = 9;
	private StrColor color = null;
	
	public ViewBoardFrame() {
		// TODO Auto-generated constructor stub
	}
	public ViewBoardFrame(int width, StrColor color) {
		this.width = width;
		this.color = color;
	}
	
	public String getEdge() {
		return String.format("%s%s%s", colorCode(),edge,end());
	}
	
	private String tempRoof() {
		String temp = "";
		for(int i = 0; i < width; i++) {
			temp += roof;
		}
		return temp;
	}
	
	public String getRoof() {
		return String.format("%s%s%s%s%s", colorCode(),leftTop,tempRoof(),rightTop,end());
	}
	
	public String getFloor() {
		return String.format("%s%s%s%s%s", colorCode(),leftBottom,tempRoof(),rightBottom,end());
	}
	
	private String colorCode() {
		if(color == null) {
			return "";			
		}else {
			return color.getCode();
		}
	}
	private String end() {
		if(color == null) {
			return "";
		}else {
			return StrColor.END.getCode();
		}
	}
	
}
