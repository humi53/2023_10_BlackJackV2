package com.yopheu.aenean.config;


public class CardConfig {
	public static final CardSuit[] SUIT;
	static {
		SUIT = new CardSuit[4];
		SUIT[0] = new CardSuit("♠");
		SUIT[1] = new CardSuit("♦");
		SUIT[2] = new CardSuit("♥");
		SUIT[3] = new CardSuit("♣");
	}
	public static final CardDenomination[] DENOMIN;
	static {
		DENOMIN = new CardDenomination[14];
		DENOMIN[0] = null;
		DENOMIN[1] = new CardDenomination("A", 11);
		DENOMIN[2] = new CardDenomination("2", 2);
		DENOMIN[3] = new CardDenomination("3", 3);
		DENOMIN[4] = new CardDenomination("4", 4);
		DENOMIN[5] = new CardDenomination("5", 5);
		DENOMIN[6] = new CardDenomination("6", 6);
		DENOMIN[7] = new CardDenomination("7", 7);
		DENOMIN[8] = new CardDenomination("8", 8);
		DENOMIN[9] = new CardDenomination("9", 9);
		DENOMIN[10] = new CardDenomination("T", 10);
		DENOMIN[11] = new CardDenomination("J", 10);
		DENOMIN[12] = new CardDenomination("Q", 10);
		DENOMIN[13] = new CardDenomination("K", 10);
	}
}

