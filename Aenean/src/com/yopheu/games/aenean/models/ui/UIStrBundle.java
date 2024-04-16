package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public class UIStrBundle implements IUIStr{
	String str;
	int width;
	public UIStrBundle() {
		this.str = "";
		this.width = 0;
	}
	public UIStrBundle(IUIStr iuiStr) {
		this.str = iuiStr.getStr();
		this.width = iuiStr.width();
	}
	
	public void addStr(IUIStr iuiStr) {
		this.str += iuiStr.getStr();
		this.width += iuiStr.width();
	}
	
	public void addLeftStr(IUIStr iuiStr) {
		String temp = this.str;
		this.str = iuiStr.getStr();
		this.str += temp;
		this.width += iuiStr.width();
	}

	@Override
	/**
	 * 이 메소드는 사용이 권장되지 않습니다. 대신 다른 대안을 사용해주세요.
	 * @deprecated 사용을 지양하고 대안을 고려해주세요.
	 */
	public void setStr(String data) {
		System.out.println("작동안함");
	}

	@Override
	/**
	 * 이 메소드는 사용이 권장되지 않습니다. 대신 다른 대안을 사용해주세요.
	 * @deprecated 사용을 지양하고 대안을 고려해주세요.
	 */
	public void setStrColor(ANSIColor strColor) {
		System.out.println("작동안함");		
	}

	@Override
	public String getStr() {
		return str;
	}

	@Override
	public void StrPrint() {
		System.out.println(getStr());
	}

	@Override
	public String StrToString() {
		return getStr();
	}

	@Override
	public int width() {
		return width;
	}
	
}
