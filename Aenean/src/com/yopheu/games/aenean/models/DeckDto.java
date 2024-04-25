package com.yopheu.games.aenean.models;

import java.util.ArrayList;
import java.util.Collections;

import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;
import com.yopheu.games.aenean.models.card.Suit;

public class DeckDto {
	ArrayList<Card> deckCards;
	public DeckDto() {
		deckCards = new ArrayList<>();
		addDeck();
	}
	
	public int countInDeck() {
		return deckCards.size();
	}
	
	public void addDeck() {
		deckCards.addAll(prepareSuffledDeck());
	}
	
	public Card takeCard() {
		return deckCards.remove(0);
	}
	
	private ArrayList<Card> prepareSuffledDeck(){
		ArrayList<Card> result = new ArrayList<>();
		Suit[] arrSuit = Suit.values();
		Denomination[] arrDenomi = Denomination.values();
		
		for(int i = 0; i < arrSuit.length; i++) {
			for(int j = 0; j < arrDenomi.length; j++) {
				result.add(new Card(arrSuit[i], arrDenomi[j]));
			}
		}
		
		for(int i = 0; i < 10000; i++) {
			int index1 =  (int)(Math.random() * result.size());
			int index2 =  (int)(Math.random() * result.size());
			Collections.swap(result, index1, index2);
		}
		
		return result;
	}
	
	public void printDeck() {
		for(int i = 0; i < deckCards.size(); i++) {
			System.out.print(deckCards.get(i).toString());
		}
		System.out.println();
		System.out.println("deck Size : " + deckCards.size());
	}
	
}
