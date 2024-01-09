package com.yopheu.aenean.config;

public enum GameState {

	READY,
	WILLBET,
	WILL2DEALING,
	CHECKBLACKJACK,
	CHECKDEALEACE,
	WILLINSURANCE,
	CHECKDEALERBLACKJACK,
	WILLPLAY,
	WILLEND,
	END;
//	PLAYERBET("플레이어들배팅중"),
//	INITDEAL("플레이어들과딜러에게카드2장딜링"),
//	PLAY("게임진행중"),	// Queue 에서 하나씩 꺼내 진행.
//	PLAYERPLAY("플레이어들게임진행중"),
//	DEALERPLAY("딜러게임진행중"),
//	RESULTCALC("게임결과계산"),
//	CALC("게임결과에맞춰금액을정산"),
//	READY("대기상태");
}
