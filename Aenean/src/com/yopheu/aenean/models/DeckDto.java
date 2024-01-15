package com.yopheu.aenean.models;

import java.util.ArrayList;

import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.models.card.Denomination;
import com.yopheu.aenean.models.card.Suit;

public class DeckDto {
	ArrayList<Card> arrCard; // 현재 카드덱

	public DeckDto() {
		arrCard = new ArrayList<>();
		arrCard.addAll(create1Deck());
		printDeck();
	}
	
	public int deckSize() {
		return arrCard.size();
	}
	
	public void addDeck() {
		
	}
	
	private ArrayList<Card> create1Deck(){
		ArrayList<Card> result = new ArrayList<>();
		Suit[] arrSuit = Suit.values();
		Denomination[] arrDenomi = Denomination.values();
		
		// 생성
		for(int i = 0; i < arrSuit.length; i++) {
			for(int j = 0; j < arrDenomi.length; j++) {
				result.add(new Card(arrSuit[i], arrDenomi[j]));
			}
		}
		
		// 섞기
		for(int i = 0; i < 10000; i++) {
			Card temp1;
			Card temp2;
		}
		
		return result;
	}
	
	private void printDeck() {
		for(int i = 0; i < arrCard.size(); i++) {
			System.out.println("" + arrCard.get(i).getSuit() + arrCard.get(i).getDenomination());
		}
	}
	
	// 덱 1뭉치 생성기.
		// 만든다
		// 섞는다
		// 옮긴다
	// 덱 추가.
}
