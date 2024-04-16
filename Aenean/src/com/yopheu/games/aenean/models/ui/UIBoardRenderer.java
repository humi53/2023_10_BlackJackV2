package com.yopheu.games.aenean.models.ui;

import java.util.Arrays;

import com.yopheu.games.aenean.models.CommDataWrapper;

/**
 * 
 * @author HumiDK
 *
 */
public class UIBoardRenderer {
	private final int BOARD_HEIGHT = 10;	// 보드 높이
	private final int BOARD_WIDTH = 9;	// 보드 너비 * 10
	private UIBoardConstants boardConstants;	// Board 기본틀.
	private CommDataWrapper cData;	// 공용 데이터
	// UI카드 변환기.
	private String[] arrStrBoard; // 보드의 출력용 데이터.
	
	public UIBoardRenderer(CommDataWrapper cData) {
		boardConstants = new UIBoardConstants(BOARD_WIDTH, null);
		this.cData = cData;
		arrStrBoard = new String[BOARD_HEIGHT];
		Arrays.fill(arrStrBoard, " ".repeat(BOARD_WIDTH*10));
	}
	
	public void printBoard() {
		for (String element : arrStrBoard) {
			System.out.println(element);
		}
	}
	
	public void setBoard() {
		// 보드 데이터 세팅
		// 라인을 정한다
		setDealerBoard(0);
		
		arrStrBoard = boardConstants.wrapBoardData(arrStrBoard);	// 보드 테두리 세팅
	}
	
	private void setDealerBoard(int index) {
		int leftPadding = 10;
		
		// 왼쪽 패딩
		String[] strLeftPadding = new String[3];
		for(int i = index; i < index+3; i++) {
			strLeftPadding[i] = " ".repeat(leftPadding);
		}
		// 배팅금 
		String message = "[Test Message]";
//		String[] strMessage
		// 카드 공간
		// 우측 패딩
		
		add3Line(0, 
				strLeftPadding);
	}
	private void init3Line(int index) {
		for(int i = index; i < index+3; i++) {
			arrStrBoard[i] = "";
		}
	}
	
	private void add3Line(int index,String[]...args) {
		init3Line(index);
		for(String[] element : args) {
			for(int i = 0; i < 3; i++) {
				arrStrBoard[index+i] = element[i];
			}
		}
	}
	
	private void setLine(int index) {
		
	}
	
	
}
