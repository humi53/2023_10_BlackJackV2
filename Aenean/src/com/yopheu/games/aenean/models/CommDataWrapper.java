package com.yopheu.games.aenean.models;

public class CommDataWrapper {
	DeckDto deckDto; // 덱 뭉치
	DealerDto dealerDto; // 딜러 카드 핸드
	PlayerDto playerDto; // 플레이어 카드 핸드
	States states;	//
	public CommDataWrapper() {
		deckDto = new DeckDto();
		dealerDto = new DealerDto();
		playerDto = new PlayerDto();
		states = new States();
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
	
	public States getStates() {
		return states;
	}
	
	public void reset() {
		if(deckDto.countInDeck() < 30) {
			deckDto.addDeck();
			deckDto.addDeck();
			deckDto.addDeck();
		}
		dealerDto.resetHands();
		playerDto.resetHands();		// split 객체도 여기서 초기화됨.
		states.reset();
	}
}
