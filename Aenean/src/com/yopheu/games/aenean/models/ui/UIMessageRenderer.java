package com.yopheu.games.aenean.models.ui;

public class UIMessageRenderer {
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
}
