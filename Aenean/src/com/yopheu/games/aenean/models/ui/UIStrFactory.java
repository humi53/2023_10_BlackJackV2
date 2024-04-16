package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public class UIStrFactory {
	private ANSIColor borderColor;	// 기본 테두리 컬러
	
	public UIStrFactory(ANSIColor defBorderColor) {
		this.borderColor = defBorderColor;
	}
	
	// borderStr 기본 생성.
	public UIBorderStr getUIBorderStr(String str, ANSIColor strColor, ANSIColor borderColor) {
		return new UIBorderStr(str, strColor, borderColor);
	}
	
	public UIBorderStr getUIBorderStr(String str) {
		return getUIBorderStr(str, null, borderColor);
	}
	
	public UIBorderStr getUIBorderStr(String str, ANSIColor strColor) {
		return getUIBorderStr(str, strColor, borderColor);
	}
	
	// str 기본 생성.
	public UIStr getUIStr(String str, ANSIColor strColor) {
		return new UIStr(str, strColor);
	}
	
	public UIStr getUIStr(String str) {
		return getUIStr(str, null);
	}	
	
	public UIStr getUIStr(String str, int repeatCount) {
		String paramStr = str.repeat(repeatCount);
		return getUIStr(paramStr, null);
	}
	
	public UIStr getUIStr(String str, ANSIColor strColor, int repeatCount) {
		String paramStr = str.repeat(repeatCount);
		return getUIStr(paramStr, strColor);
	}
}
