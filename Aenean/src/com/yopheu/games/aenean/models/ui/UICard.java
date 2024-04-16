package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public class UICard implements IUIBorderBlock{
	private String str;
	private ANSIColor strColor;
	private ANSIColor borderColor;
	private final String BORDER_TOP = "┌──┐";
	private final String BORDER_SIDE = "│";
	private final String BORDER_BOTTOM = "└──┘";
	
	public UICard(String str, ANSIColor strColor, ANSIColor borderColor) {
		setStr(str);
		this.strColor = strColor;
		this.borderColor = borderColor;
	}	
	
	/**
	 * 카드는 2칸 공간밖에 없음
	 * @param str 2글자 초과시 공백처리.
	 */
	@Override
	public void setStr(String str) {
		if(getStrSpace(str) > 2) {
			this.str = "  ";
			System.out.println("ViewCard : 2글자가 초과되어 공백처리");
		}else {
			this.str = str;
		}
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
		String[] card = new String[3];
		card[0] = String.format("%s%s%s", getANSI(borderColor), BORDER_TOP, getEndANSI(borderColor));
		String cSide = String.format("%s%s%s", getANSI(borderColor), BORDER_SIDE, getEndANSI(borderColor));
		String cStr = String.format("%s%s%s", getANSI(strColor), str, getEndANSI(strColor));
		card[1] = String.format("%s%s%s", cSide, cStr, cSide);
		card[2] = String.format("%s%s%s", getANSI(borderColor), BORDER_BOTTOM, getEndANSI(borderColor));
		return card;
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
	public int width() {
		return BORDER_TOP.length();
	}
	
	// color의 null을 체크후 처리.
	private String getANSI(ANSIColor color) {
		if(color == null) {
			return "";
		} else {
			return color.getANSI();
		}
	}
	// color의 null을 체크후 처리.
	private String getEndANSI(ANSIColor color) {
		if(color == null) {
			return "";
		}else {
			return ANSIColor.END.getANSI();
		}
	}
	
	private int getStrSpace(String str) {
		int count = str.length();
		for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }
		return count;
	}
}
