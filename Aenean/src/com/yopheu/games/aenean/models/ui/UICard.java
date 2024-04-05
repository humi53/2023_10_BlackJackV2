package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public class UICard {
	private String data;
	private ANSIColor strColor;
	private ANSIColor frameColor;
	private final String FRAME_TOP = "┌──┐";
	private final String FRAME_SIDE = "│";
	private final String FRAME_BOTTOM = "└──┘";
	
	public UICard(String data, ANSIColor strColor, ANSIColor frameColor) {
		this.data = data;
		this.strColor = strColor;
		this.frameColor = frameColor;
	}
	
	/**
	 * 
	 * @param data 2글자 초과시 공백처리.
	 */
	public void setData(String data) {
		if(getStrSpace(data) > 2) {
			this.data = "  ";
			System.out.println("ViewCard : 2글자가 초과되어 공백처리");
		}else {
			this.data = data;
		}
	}
	
	private String strColorANSI() {
		if(strColor == null) {
			return "";
		} else {
			return strColor.getANSI();
		}
	}
	
	private String frameColorANSI() {
		if(frameColor == null) {
			return "";			
		}else {
			return frameColor.getANSI();
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
	
	private String frameEnd() {
		if(frameColor == null) {
			return "";
		}else {
			return ANSIColor.END.getANSI();
		}
	}
	private String strEnd() {
		if(strColor == null) {
			return "";
		}else {
			return ANSIColor.END.getANSI();
		}
	}
	
	public String[] getCard() {
		String[] card = new String[3];
		card[0] = String.format("%s%s%s", frameColorANSI(), FRAME_TOP, frameEnd());
		String side = String.format("%s%s%s", frameColorANSI(), FRAME_SIDE, frameEnd());
		String str = String.format("%s%s%s", strColorANSI(), data, strEnd());
		card[1] = String.format("%s%s%s", side, str, side);
		card[2] = String.format("%s%s%s", frameColorANSI(), FRAME_BOTTOM, frameEnd());
		return card;
	}
	
	public int width() {
		return FRAME_TOP.length();
	}
	
	@Override
	public String toString() {
		String result = "";
		for (String element : getCard()) {
			result += element + "\n";
		}
		return result;
	}
}
