package com.yopheu.aenean.models;

public enum Denomination {
	NA("A"),	N2("2"),	N3("3"),	N4("4"),	N5("5"),
	N6("6"),	N7("7"),	N8("8"),	N9("9"),	N10("10"),
	NJ("J"),	NQ("Q"),	NK("K");	
	private final String str;
	private Denomination(String str) {
		this.str = str;
	}
	public String getStr() {
		return this.str;
	}
}
