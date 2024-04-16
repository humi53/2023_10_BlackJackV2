package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public class UIStr implements IUIStr, IUIMargin{
	private final String SPACE = "c";
	private String str;
	private ANSIColor strColor;
	
	public UIStr(String str, ANSIColor strColor) {
		this.str = str;
		this.strColor = strColor;
	}
	
	@Override
	public void setStr(String str) {
		this.str = str;
	}
	
	@Override
	public void setStrColor(ANSIColor strColor) {
		this.strColor = strColor;
		
	}
	
	// 컬러를 곁들인 1라인 문자열
	private String getColorStr() {
		return getANSI(strColor) + str + getEndANSI(strColor);
	}
	
	@Override
	public String getStr() {
		return getColorStr();
	}
	
	@Override
	public String[] getData() {
		String[] result = new String[3];
		result[0] = SPACE.repeat(width());
		result[1] = getStr();
		result[2] = SPACE.repeat(width());
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
	
	// 한글 2칸, 영어 1칸 차지
	@Override
	public int width() {
		int count = str.length();
		for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }
		return count;
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

	@Override
	public void StrPrint() {
		System.out.println(getStr());		
	}

	@Override
	public String StrToString() {
		return getStr();
	}

}
