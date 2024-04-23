package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ExceptionState;
import com.yopheu.games.aenean.config.GameState;
import com.yopheu.games.aenean.models.States;

public class UIMessageRenderer {
	private States states;
	private String exceptionMsg;
	private String guideMsg;
	private String promptMsg;
	public UIMessageRenderer(States states) {
		this.states = states;
		exceptionMsg = "";
		guideMsg = "";
		promptMsg = getPrompt();
	}
	
	public void printMessages() {
//		if(states.gameState == GameState.PLAYERBETTING) {
//			System.out.println(exceptionMsg);
//			System.out.println(guideMsg);
//			System.out.println(promptMsg);
//		}else {
//			System.out.println(guideMsg);
//			System.out.println("");
//			System.out.println("");
//		}
		System.out.println(exceptionMsg);
		System.out.println(guideMsg);
		System.out.print(promptMsg);
	}
	
	public void setMessages() {
		// 예외 메시지
		if(states.exceptionState == ExceptionState.NONE) {
			exceptionMsg = "";
		}else if(states.exceptionState == ExceptionState.SCANERR) {
			exceptionMsg = getScanErr();
		}else if(states.exceptionState == ExceptionState.LOWCHIPS) {
			exceptionMsg = getLowChips();
		}else if(states.exceptionState == ExceptionState.BETMAXSOVER) {
			exceptionMsg = getBetMaxOver();
		}else if(states.exceptionState == ExceptionState.BETZERO) {
			exceptionMsg = getBetZero();
		}else if(states.exceptionState == ExceptionState.BETCANCEL) {
			exceptionMsg = getBetCencel();
		}
		
		// 안내 메시지 & 프롬프트.
		if(states.gameState == GameState.PLAYERBETTING) {
			guideMsg = getBetMenu();
			promptMsg = getPrompt();
		}
	}
	// 예외메시지
	// 안내메시지
	// 프롬프트
	
	// 메시지 설정.
		// 예외메시지 설정
		// 안내메시지 설정.
			// 복합 안내 메시지(값을 받아야 구성됨)
			// 단순 안내 메시지(세부값은 필요없음)
		// 프롬프트 설정.
	// 메시지 출력.
	
	// 안내메시지 만들기. (무엇으로 할까)
		// 1. 매개변수로 받기.
		// 2. cData로 받기.
	
	
	private String getBetMenu() {
		String selectionsMsg = "";
		for(int i = 1; i < states.chipMenu.length; i++) {
			 selectionsMsg += i + "[" + states.chipMenu[i].value() + "] ";
		}
		selectionsMsg += 0 + "[취소] " + "Enter" + "[완료] ";
		selectionsMsg += "(now : " + states.chipBet + ")";
		return selectionsMsg;
	}
	
	
	private String getScanErr() {
		return "잘못된 값을 입력하였습니다. 안내된 값을 입력해주세요.";
	}
	private String getLowChips() {
		return "잔액이 부족합니다.";
	}
	private String getBetMaxOver() {
		return "배팅 최대금액은 1,000 입니다.";
	}
	private String getBetZero() {
		return "배팅이 되지 않았습니다. 배팅해주세요.";
	}
	private String getBetCencel() {
		return "배팅이 취소되었습니다. 배팅값이 0이 되었습니다.";
	}
	private String getEnterThePass() {
		return "다음으로 진행하려면 Enter를 누르세요";
	}
	private String getPrompt() {
		return "입력: ";
	}
}
