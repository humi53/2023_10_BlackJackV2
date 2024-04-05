package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public class UIStr {
	private final String LEFT_TOP = "┌";
	private final String RIGHT_TOP = "┐";
	private final String LEFT_BOTTOM = "└";
	private final String RIGHT_BOTTOM = "┘";
	private final String SIDE = "│";
	private final String ROOF = "─";
	private final String SPACE = " ";
	private String str;
	private ANSIColor strColor;
	private ANSIColor borderColor;
	
	public UIStr(String str, ANSIColor strColor) {
		this.str = str;
		this.strColor = strColor;
	}
	
	public UIStr(String str, ANSIColor strColor, ANSIColor borderColor) {
		this.str = str;
		this.strColor = strColor;
		this.borderColor = borderColor;
	}
	
	public void setStrColor(ANSIColor strColor) {
		this.strColor = strColor;
	}
	
	public void setBorderColor(ANSIColor borderColor) {
		this.borderColor = borderColor;
	}
	
	// 차지하는 공간 수를 return
	public int length() {
		int count = str.length();
		for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }
		return count;
	}
	
	// 컬러를 곁들인 1라인 문자열
	public String getStr() {
		return getANSI(strColor) + str + getEndANSI(strColor);
	}
	
	public void printStr() {
		System.out.println(getStr());
	}
	
	// 위아래 공간을 차지한 문자열
	public String[] getStr3() {
		String[] result = new String[3];
		result[0] = SPACE.repeat(length());
		result[1] = getStr();
		result[2] = SPACE.repeat(length());
		return result;
	}
	
	public void printStr3() {
		for (String element : getStr3()) {
			System.out.println(element);
		}
	}
	
	// border로 둘러싼 문자열
	public String[] getStrBorder() {
		String[] result = new String[3];
		result[0] = getANSI(borderColor) + LEFT_TOP + ROOF.repeat(length()) + RIGHT_TOP + getEndANSI(borderColor);
		result[1] = getANSI(borderColor) + SIDE  + getEndANSI(borderColor);
		result[1] += getStr();
		result[1] += getANSI(borderColor) + SIDE  + getEndANSI(borderColor);
		result[2] = getANSI(borderColor) + LEFT_BOTTOM + ROOF.repeat(length()) + RIGHT_BOTTOM + getEndANSI(borderColor);
		return result;
	}
	
	public void printStrBorder() {
		for (String element : getStrBorder()) {
			System.out.println(element);
		}
	}
	
	private String getANSI(ANSIColor color) {
		if(color == null) {
			return "";
		}else {
			return color.getANSI();
		}
	}
	private String getEndANSI(ANSIColor color) {
		if(color == null) {
			return "";
		}else {
			return ANSIColor.END.getANSI();
		}
	}
}
