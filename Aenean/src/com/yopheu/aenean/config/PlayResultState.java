package com.yopheu.aenean.config;

public enum PlayResultState {
	PENDING,	// 미결정
	BUST,		// 버스트 (21넘어감)
//	INSURANCE,	// 인슈어런스 (상대가 블랙잭인것으로 겜블) (따로처리)
//	DOUBLEDOWN,	// 더블다운 (2배배팅)  (배팅을 2배로 늘리는것으로 처리)
	NORMAL,		// 일반상태 (일반점수)
	BLACKJACK;	// 블랙잭 (2장으로 21)
}
