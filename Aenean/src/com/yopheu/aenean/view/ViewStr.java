package com.yopheu.aenean.view;

import com.yopheu.aenean.config.StrColor;

public class ViewStr {
	private String data;
	private StrColor color = null;
	public ViewStr(String str) {
		this.data = str;
	}
	public ViewStr(String str, StrColor color) {
		this.data =str;
		this.color = color;
	}
	public void setStr(String str) {
		this.data = str;
	}
	public void setColor(StrColor color) {
		this.color = color;
	}
	public String getColor() {
		if(color == null) {
			return "";
		}else {
			return color.getCode();			
		}
	}
	public String getEnd() {
		if(color == null) {
			return "";
		}else {
			return StrColor.END.getCode();
		}
	}
	public String getColorStr() {
		return String.format("%s%s%s", getColor(), data, getEnd());
	}
	public int space() {
		int count = data.length();
		for (char c : data.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }
		return count;
	}
}
