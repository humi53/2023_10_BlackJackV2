package com.yopheu.aenean.models;

public class Card {
	private String suit;
	private String denomination;
	
	public Card(String suit, String denomination) {
		this.suit = suit;
		this.denomination = denomination;
	}
	
	public String getSuit() {
		return suit;
	}
	public void setSuit(String suit) {
		this.suit = suit;
	}
	public String getDenomination() {
		return denomination;
	}
	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}
	
	@Override
	public String toString() {
		return "Card [suit=" + suit + ", denomination=" + denomination + "]";
	}
}
