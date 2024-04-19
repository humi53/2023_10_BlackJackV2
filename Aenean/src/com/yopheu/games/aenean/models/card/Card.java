package com.yopheu.games.aenean.models.card;


public class Card {
	private Suit suit;
	private Denomination denomination;
	
	public Card(Suit suit, Denomination denomination) {
		this.suit = suit;
		this.denomination = denomination;
	}
	
	public Suit getSuit() {
		return suit;
	}
	public String getStrSuit() {
		return suit.getSymbol();
	}
	
	public Denomination getDenomination() {
		return denomination;
	}
	public String getStrDenomination() {
		return denomination.getSymbol();
	}
	
	@Override
	public String toString() {
		return "[" + getStrSuit() + getStrDenomination() + "]";
	}
}
