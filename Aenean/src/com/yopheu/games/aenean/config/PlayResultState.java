package com.yopheu.games.aenean.config;

public enum PlayResultState {
	ONGOING("진행중..."),
	HIT("HIT"),
	STAND("STAND"),
	BLACKJACK("BlackJack"),
	WIN("우승"),
	BUST("BUST"),
	LOSS("손실"),
	PUSH("PUSH"),
	NONE("");
	private final String TEXT;
	
	PlayResultState(String TEXT) {
		this.TEXT = TEXT;
	}
	
	public String getText() {
		return TEXT;
	}
}
