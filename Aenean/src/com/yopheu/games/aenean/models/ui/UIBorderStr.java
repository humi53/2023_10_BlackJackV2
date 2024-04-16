package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public class UIBorderStr implements IUIBorderBlock{
	private final String LEFT_TOP = "┌";
	private final String RIGHT_TOP = "┐";
	private final String LEFT_BOTTOM = "└";
	private final String RIGHT_BOTTOM = "┘";
	private final String SIDE = "│";
	private final String ROOF = "─";
	private String str;
	private ANSIColor strColor;
	private ANSIColor borderColor;
	
	public UIBorderStr(String str, ANSIColor strColor) {
		this.str = str;
		this.strColor = strColor;
	}
	
	public UIBorderStr(String str, ANSIColor strColor, ANSIColor borderColor) {
		this.str = str;
		this.strColor = strColor;
		this.borderColor = borderColor;
	}
	
	@Override
	public void setStr(String str) {
		this.str = str;
	}
	
	@Override
	public void setStrColor(ANSIColor strColor) {
		this.strColor = strColor;
		
	}

	@Override
	public void setBorderColor(ANSIColor borderColor) {
		this.borderColor = borderColor;		
	}

	@Override
	public String[] getData() {
		String[] result = new String[3];
		result[0] = getANSI(borderColor) + LEFT_TOP + ROOF.repeat(strWidth()) + RIGHT_TOP + getEndANSI(borderColor);
		result[1] = getANSI(borderColor) + SIDE  + getEndANSI(borderColor);
		result[1] += getStr();
		result[1] += getANSI(borderColor) + SIDE  + getEndANSI(borderColor);
		result[2] = getANSI(borderColor) + LEFT_BOTTOM + ROOF.repeat(strWidth()) + RIGHT_BOTTOM + getEndANSI(borderColor);
		return result;
	}

	@Override
	public void print() {
		System.out.println(toString());		
	}
	
	@Override
	public String toString() {
		String result = "";
		String[] data = getData();
		for (int i = 0; i < data.length; i++) {
			result += data[i];
			if(i < data.length - 1) {
				result += "\n";
			}
		}
		return result;
	}


	@Override	
	// borderStr이 차지하는 공간 수를 return
	public int width() {
		int count = str.length();
		for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }
		return count;
	}
	
	// 문자열이 차지하고 있는 공간. 한글2, 영어1
	private int strWidth() {
		int count = str.length();
		for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }
		return count;
	}
	
	// 컬러를 곁들인 1라인 문자열
	private String getStr() {
		return getANSI(strColor) + str + getEndANSI(strColor);
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
