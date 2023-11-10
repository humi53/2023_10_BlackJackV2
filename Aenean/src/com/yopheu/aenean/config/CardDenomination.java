package com.yopheu.aenean.config;

public class CardDenomination{
	private final String text;
	private final int value;
	public CardDenomination(String text, int value){
		this.text = text;
		this.value = value;
	}
	@Override
	public String toString() {
		return text;
	}
	
	public int value() {
		return value;
	}
}