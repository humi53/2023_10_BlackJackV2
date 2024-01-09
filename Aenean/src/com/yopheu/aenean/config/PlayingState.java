package com.yopheu.aenean.config;

public enum PlayingState {
//	INSURANCECHECK,	// INSURANCE 확인중.
	WAITING,	// 입력대기중.
	HIT,		// HIT을 결정한 상태.
	STAND,		// STAND 선택한 상태.
	BLACKJACK,
	BUST,	
	COMPLETE;		// Blackjack, bust, stand로 결과가 정해진 상태.
}
