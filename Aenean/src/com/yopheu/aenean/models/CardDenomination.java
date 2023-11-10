package com.yopheu.aenean.models;

public enum CardDenomination {
	A(1), T(10), J(11), Q(12), K(13);
	
	private final int value;
	private CardDenomination(int value) {
		this.value = value;
	}
	
	public static CardDenomination fromValue(int value) {
		for (CardDenomination element : CardDenomination.values()) {
			if(element.value == value) {
				return element;
			}
		}
		return null;
	}
//	public static int fromEnum(CardDenomination cardDenomination) {
//		return 
//	}
}
