package com.yopheu.games.aenean.models;

public class CommDataWrapper {
	DeckDto deckDto; // 덱 뭉치
	DealerDto dealerDto; // 딜러 카드 핸드
	PlayerDto playerDto; // 플레이어 카드 핸드
	
	public CommDataWrapper() {
		deckDto = new DeckDto();
		dealerDto = new DealerDto();
		playerDto = new PlayerDto();
	}
	
	public DeckDto getDeckDto() {
		return deckDto;
	}
	
	public DealerDto getDealerDto() {
		return dealerDto;
	}
	
	public PlayerDto getPlayerDto() {
		return playerDto;
	}
}
