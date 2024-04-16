package com.yopheu.games.aenean.models.ui;

public interface IUIBlock {
	public String[] getData();	// 3줄데이터 반환.
	public void print();	// 바로출력
	public String toString();	// 배열을 1개의 문자열로 바꾸어 반환.
	public int width();
}
