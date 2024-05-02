package com.yopheu.games.aenean.models.ui;

import java.util.ArrayList;

import com.yopheu.games.aenean.config.ANSIColor;
import com.yopheu.games.aenean.config.Confirm;
import com.yopheu.games.aenean.config.ExceptionState;
import com.yopheu.games.aenean.config.GameState;
import com.yopheu.games.aenean.config.PlayChoose;
import com.yopheu.games.aenean.models.States;

public class UIMessageRenderer {
	private States states;
	private String exceptionMsg;
	private String guideMsg;
	private String promptMsg;
	
	private UIStrFactory strFactory;
	
	public UIMessageRenderer(States states) {
		this.states = states;
		exceptionMsg = "";
		guideMsg = "";
		promptMsg = getPrompt();
		strFactory = new UIStrFactory(null);
	}
	
	public void printMessages() {
		System.out.println(exceptionMsg);
		System.out.println(guideMsg);
		System.out.print(promptMsg);
		exceptionMsg = "";
		guideMsg = "";
		promptMsg = "";
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
		if(states.gameState == GameState.INSURANCE) {
			guideMsg = "인슈어런스 하시겠습니까? " + getConfirmMenu();
			promptMsg = getPrompt();
		}
		if(states.gameState == GameState.PLAYERTURN) {
			guideMsg = getPlayChooseMenu();
			promptMsg = getPrompt();
		}
		if(states.gameState == GameState.READY) {
			exceptionMsg = getEnterThePass();
			guideMsg = "";
			promptMsg = "";
		}
	}
	
	private String getPlayChooseMenu() {
		UIStrBundle msg = new UIStrBundle();
		msg.addStr(strFactory.getUIStr("선택: "));
		for(int i = 1; i < states.playMenu.length; i++) {
			if(states.playMenu[i] != PlayChoose.NONE) {
				msg.addStr(strFactory.getUIStrBundleMenuItem("" + i, ANSIColor.CYAN,
						states.playMenu[i].getText(), null, "[", "] ", null));
			}
		}
		return msg.getStr();
	}
	
	private String getConfirmMenu() {
		UIStrBundle msg = new UIStrBundle();
		for(int i = 1; i < states.confirmMenu.length; i++) {
			ANSIColor textColor = null;
			if(states.confirmMenu[i] == Confirm.YES) {
				textColor = ANSIColor.CYAN;
			}else if(states.confirmMenu[i] == Confirm.NO) {
				textColor = ANSIColor.RED;
			}
			msg.addStr(strFactory.getUIStrBundleMenuItem("" + i, ANSIColor.CYAN,
					states.confirmMenu[i].getStr(), textColor, "[", "] ", null));
		}
		return msg.getStr();
	}
	
	private String getBetMenu() {
		UIStrBundle msg = strFactory.getUIStrBundle("Now : " + states.chipBet,
				ANSIColor.YELLOW, "[", "] ", ANSIColor.CYAN);
		
		for(int i = 1; i < states.chipMenu.length; i++) {
			msg.addStr(strFactory.getUIStrBundleMenuItem("" + i, ANSIColor.GREEN,
					"" + states.chipMenu[i].value(), null, 
					"[", "] ", null));
		}
		msg.addStr(strFactory.getUIStrBundleMenuItem("0", ANSIColor.RED,
				"취소", ANSIColor.RED, "[", "] ", null));
		msg.addStr(strFactory.getUIStrBundleMenuItem("Enter", ANSIColor.CYAN,
				"완료", ANSIColor.CYAN, "[", "] ", null));
		
		return msg.getStr();
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
		return "다음으로 진행하려면 Enter를 누르세요.";
	}
	private String getPrompt() {
		return "입력: ";
	}
}
