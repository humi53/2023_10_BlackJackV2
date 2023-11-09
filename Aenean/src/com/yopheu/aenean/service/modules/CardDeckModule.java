package com.yopheu.aenean.service.modules;

import java.util.Queue;

import com.yopheu.aenean.models.Card;

public interface CardDeckModule {
	Queue<Card> getCardDeck();
	void addCardDeck();
	void setCardDeck(Queue<Card> cardDeck);
}
