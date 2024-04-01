package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.models.CommDataWrapper;

/**
 * 
 * @author HumiDK
 *
 */
public class UIBoardRenderer {
	UIBoardConstants boardConstants;	// Board 기본틀.
	CommDataWrapper cData;	// 공용 데이터
	// UI카드 변환기.
	// 보드의 출력용 데이터.
	
	public UIBoardRenderer(CommDataWrapper cData) {
		boardConstants = new UIBoardConstants(0, null);
		this.cData = cData;
	}
	
	public void printBoard() {
		
	}
	
	public void setBoard() {
		
	}
	
}
