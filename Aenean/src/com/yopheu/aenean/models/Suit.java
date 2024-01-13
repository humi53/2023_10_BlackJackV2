package com.yopheu.aenean.models;

public enum Suit {
	S("♠"),D("♦"),H("♥"),C("♣");
	private final String str;
	Suit(String str) {
		this.str = str;
	}
	public String getStr() {
		return this.str;
	}
}
