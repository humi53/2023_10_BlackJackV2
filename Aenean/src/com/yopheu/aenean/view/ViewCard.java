package com.yopheu.aenean.view;

import com.yopheu.aenean.config.StrColor;

public class ViewCard {
	private String data;
	private StrColor strColor = null;
	private StrColor frameColor = null;
	private String frameTop = "┌──┐";
	private String frameSide = "│";
	private String frameBottm = "└──┘";
	
	public ViewCard(String data) {
		setData(data);
	}
	
	public ViewCard(String data, StrColor strColor) {
		setData(data);
		setStrColor(strColor);
	}
	
	public ViewCard(String data, StrColor strColor, StrColor frameColor) {
		setData(data);
		setStrColor(strColor);
		setFrameColor(frameColor);
	}
	
	public void setStrColor(StrColor color) {
		this.strColor = color;
	}
	
	public void setFrameColor(StrColor color) {
		this.frameColor = color;
	}
	
	/**
	 * 
	 * @param data 2글자 초과시 공백처리.
	 */
	public void setData(String data) {
		if(dataSpace(data) > 2) {
			this.data = "  ";
			System.out.println("ViewCard : 2글자가 초과되어 공백처리");
		}else {
			this.data = data;
		}
	}
	
	private String strColorCode() {
		if(strColor == null) {
			return "";
		} else {
			return strColor.getCode();
		}
	}
	
	private String frameColorCode() {
		if(frameColor == null) {
			return "";			
		}else {
			return frameColor.getCode();
		}
	}
	
	private int dataSpace(String str) {
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
			return StrColor.END.getCode();
		}
	}
	private String strEnd() {
		if(strColor == null) {
			return "";
		}else {
			return StrColor.END.getCode();
		}
	}
	
	public String[] getCard() {
		String[] card = new String[3];
		card[0] = String.format("%s%s%s", frameColorCode(), frameTop, frameEnd());
		String side = String.format("%s%s%s", frameColorCode(), frameSide, frameEnd());
		String str = String.format("%s%s%s", strColorCode(), data, strEnd());
		card[1] = String.format("%s%s%s", side, str, side);
		card[2] = String.format("%s%s%s", frameColorCode(), frameBottm, frameEnd());
		return card;
	}
	
	public int space() {
		return frameTop.length();
	}
}
