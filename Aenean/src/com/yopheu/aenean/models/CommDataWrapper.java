package com.yopheu.aenean.models;

public class CommDataWrapper {
	DealerDataDto dealerDataDto; // 딜러 데이터
	PlayerDataDto playerDataDto; // 플레이어 데이터
	DeckDto deckDto; // 덱 데이터
	// 게임 상태?
	public CommDataWrapper() {
		dealerDataDto = new DealerDataDto();
		playerDataDto = new PlayerDataDto();
		deckDto = new DeckDto();
	}
	
	// 
}
