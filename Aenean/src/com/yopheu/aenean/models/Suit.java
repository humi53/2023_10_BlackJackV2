package com.yopheu.aenean.models;

public enum Suit {
	S("♠"),D("♦"),H("♥"),C("♣");
	private final String symbol;
	Suit(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbol() {
		return this.symbol;
	}
}
