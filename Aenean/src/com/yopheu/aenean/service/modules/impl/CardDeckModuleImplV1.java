package com.yopheu.aenean.service.modules.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.Denomination;
import com.yopheu.aenean.models.Suit;
import com.yopheu.aenean.service.modules.CardDeckModule;

public class CardDeckModuleImplV1 implements CardDeckModule{
	private List<Card> arrCardDeck;	
	private Queue<Card> cardDeck;
	public CardDeckModuleImplV1() {
		arrCardDeck = new ArrayList<>();
		cardDeck = new LinkedList<Card>();
	}
	
	// 카드를 생성한다.
	// 섞인 카드덱을 추가한다.
	@Override
	public void addCardDeck() {
		Suit[] allSuit = Suit.values();
		Denomination[] allDenomi = Denomination.values();
		for(int i=0; i < allSuit.length; i++) {
			for(int j=0; j < allDenomi.length; j++) {
				arrCardDeck.add(new Card(allSuit[i], allDenomi[j]));
			}
		}
		mixCard(arrCardDeck);
		cardDeck.addAll(arrCardDeck);
		arrCardDeck.clear();
	}
	
	// 카드를 섞는다
	private List<Card> mixCard(List<Card> arrCard){
		for(int i = 0; i < 10000; i++) {
			Collections.swap(arrCard, 
					(int)(Math.random() * arrCard.size()), 
					(int)(Math.random() * arrCard.size()));
		}
		return arrCard;
	}
	
	// get 카드덱
	@Override
	public Queue<Card> getCardDeck(){
		if(cardDeck.isEmpty()) {
			addCardDeck();
		}
		return cardDeck;
	}
	
	@Override
	public void setCardDeck(Queue<Card> cardDeck){
		this.cardDeck = cardDeck;
	}
}
