package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public interface IUIStr {
	// 문자열 세팅
	public void setStr(String data);
	public void setStrColor(ANSIColor strColor);
	public String getStr();
	public int width();
	public void StrPrint();
	public String StrToString();
}
