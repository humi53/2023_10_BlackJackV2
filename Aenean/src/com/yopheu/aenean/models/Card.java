package com.yopheu.aenean.models;

public class Card {
	private int suit;
	private int denomination;
	
	public Card(int suit, int denomination) {
		this.suit = suit;
		this.denomination = denomination;
	}
	public Card() {	}
	
	public int getSuit() {
		return suit;
	}
	public void setSuit(int suit) {
		this.suit = suit;
	}
	public int getDenomination() {
		return denomination;
	}
	public void setDenomination(int denomination) {
		this.denomination = denomination;
	}
	
	@Override
	public String toString() {
		return "Card [suit=" + suit + ", denomination=" + denomination + "]";
	}
}
