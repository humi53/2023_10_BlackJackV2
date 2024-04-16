package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

public interface IUIBorderBlock extends IUIBlock{
	// 문자열 세팅
	public void setStr(String str);
	// 문자열 컬러 설정
	public void setStrColor(ANSIColor strColor);
	// 테두리 컬러 설정
	public void setBorderColor(ANSIColor borderColor);
}
