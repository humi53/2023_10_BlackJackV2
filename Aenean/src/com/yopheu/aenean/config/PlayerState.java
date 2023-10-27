package com.yopheu.aenean.config;

public enum PlayerState {
	BUST(-1),
	PLAYING(0),
	DOUBLE(3),
	SPLIT(1),
	BLACKJACK(4),
	NORMAL(2);
	
	private int state;

	private PlayerState(int state) {
		this.state = state;
	}
	
	private PlayerState() {
		
	}
	
}
