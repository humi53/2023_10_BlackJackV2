package com.yopheu.aenean.config;

public class CardSuit{
	private final String text;
	public CardSuit(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return text;
	}
}