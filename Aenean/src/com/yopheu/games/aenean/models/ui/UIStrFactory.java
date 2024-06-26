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
		return getUIStr(str, null, repeatCount);
	}
	
	public UIStr getUIStr(String str, ANSIColor strColor, int repeatCount) {
		return getUIStr(str, strColor, repeatCount);
	}
	
	public UIStrBundle getUIStrBundle(String str, ANSIColor strColor, String left, String right, ANSIColor sideColor) {
		UIStrBundle result = new UIStrBundle();
		result.addStr(getUIStr(left, sideColor));
		result.addStr(getUIStr(str, strColor));
		result.addStr(getUIStr(right, sideColor));
		return result;
	}
	
	public UIBlockBundle getUIBlockBundle(String str, ANSIColor strColor, String left, String right, ANSIColor sideColor) {
		UIBlockBundle result = new UIBlockBundle();
		result.addBlock(getUIStr(left, sideColor));
		result.addBlock(getUIStr(str, strColor));
		result.addBlock(getUIStr(right, sideColor));
		return result;
	}
	
	public UIStrBundle getUIStrBundleScore(String title, ANSIColor titleColor, int score, ANSIColor scoreColor, String left, String right, ANSIColor sideColor) {
		UIStr uiTitle = getUIStr(title, titleColor);
		UIStr uiScore = getUIStr("" + score, scoreColor);
		UIStr uiLeft = getUIStr(left, sideColor);
		UIStr uiRight = getUIStr(right, sideColor);
		UIStrBundle result = new UIStrBundle();
		result.addStr(uiLeft);
		result.addStr(uiTitle);
		result.addStr(uiScore);
		result.addStr(uiRight);
		
		return result;
	}
	
	public UIStrBundle getUIStrBundleMenuItem(String key, ANSIColor keyColor, String content, ANSIColor contentColor, String left, String right, ANSIColor sideColor) {
		UIStr uiKey = getUIStr(key, keyColor);
		UIStr uiContent = getUIStr(content, contentColor);
		UIStr uiLeft = getUIStr(left, sideColor);
		UIStr uiRight = getUIStr(right, sideColor);
		UIStrBundle result = new UIStrBundle();
		result.addStr(uiKey);
		result.addStr(uiLeft);
		result.addStr(uiContent);
		result.addStr(uiRight);
		
		return result;
	}
}
