package com.yopheu.aenean.service.impl;

import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.models.entry.Player;
import com.yopheu.aenean.service.ViewService;

public class ViewServiceImplV1 implements ViewService{
	
	private String singleMessage;
	private GameState gameState;
	private Player currentPlayer;
	// GameState
	// 상태별 출력.
	
	// 단순 출력이 있다.
	//		입력안내 출력
	//		결과안내 출력
	// 반복 출력이 있다.
	
	// 출력은 항상 전체화면을 뿌려줄것.
	
	// 하단 메시지 선택기
	private void selectMessage() {
		singleMessage = "";
		switch (gameState) {
		case READY:
			singleMessage = "게임이 준비중입니다.";
			break;
		case WILLBET:
			singleMessage = "게임이 준비중입니다.";
			break;
		
		default:
			break;
		}
		
	}
	
	public void paint() {
		
	}
	public void repaint() {
		
	}
}
