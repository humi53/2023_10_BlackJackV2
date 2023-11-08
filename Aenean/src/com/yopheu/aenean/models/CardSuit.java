package com.yopheu.aenean.models;

public enum CardSuit {
	SPADE(0, "♠"),
	DIAMOND(1, "♦"),
	HART(2, "♥"),
	CLOVER(3, "♣");
	
	private final int value;
	private final String suit;
	private CardSuit(int value, String suit) {
		this.value = value;
		this.suit = suit;
	}
	
	public static CardSuit fromValue(int value) {
		for (CardSuit element : CardSuit.values()) {
			if(element.value == value) {
				return element;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return suit;
	}
}
