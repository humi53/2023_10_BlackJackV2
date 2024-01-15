package com.yopheu.aenean.models.card;

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
	public Denomination getDenomination() {
		return denomination;
	}
	
	@Override
	public String toString() {
		return "Card [suit=" + suit.getSymbol() + ", denomination=" + denomination.getSymbol() + "]";
	}
}
