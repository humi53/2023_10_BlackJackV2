package com.yopheu.games.aenean.models.card;

public enum Denomination {
	NA("A", 11),	N2("2", 2),	N3("3", 3),	N4("4", 4),	N5("5", 5),
	N6("6", 6),	N7("7", 7),	N8("8", 8),	N9("9", 9),	N10("T", 10),
	NJ("J", 10),	NQ("Q", 10),	NK("K", 10);	
	private final String symbol;
	private final int value;
	private Denomination(String symbol, int value) {
		this.symbol = symbol;
		this.value = value;
	}
	public String getSymbol() {
		return this.symbol;
	}
	public int getValue() {
		return this.value;
	}
}
