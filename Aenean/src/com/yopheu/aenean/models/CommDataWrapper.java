package com.yopheu.aenean.models;

import java.util.ArrayList;

public class CommDataWrapper {
	DealerDataDto dealerDataDto; // 딜러 데이터
	ArrayList<PlayerDataDto> arrPlayerDataDto; // 플레이어 데이터
	DeckDto deckDto; // 덱 데이터
	// 게임 상태?
	public CommDataWrapper() {
		dealerDataDto = new DealerDataDto();
		arrPlayerDataDto = new ArrayList<>();
		arrPlayerDataDto.add(new PlayerDataDto("Player0"));
		deckDto = new DeckDto();
	}
	
	public DealerDataDto getDealer() {
		return dealerDataDto;
	}
	
	public ArrayList<PlayerDataDto> getPlayers() {
		return arrPlayerDataDto;
	}
	
	public DeckDto getDeck() {
		return deckDto;
	}
	
	// 
}
