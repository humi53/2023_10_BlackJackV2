package com.yopheu.games.test;

import java.util.ArrayList;

import com.yopheu.games.aenean.models.CardsCalculator;
import com.yopheu.games.aenean.models.DealerDto;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;
import com.yopheu.games.aenean.models.card.Suit;

public class CardTestExec {
	public static void main(String[] args) {
		CardsCalculator calc = new CardsCalculator();
		ArrayList<Card> arrCard = new ArrayList<>();
		arrCard.add(new Card(Suit.C, Denomination.NA));
		arrCard.add(new Card(Suit.S, Denomination.N8));
		arrCard.add(new Card(Suit.S, Denomination.N2));
		
		System.out.println(calc.getCardsScore(arrCard));
		System.out.println("블랙잭 : " + calc.isBlackJack(arrCard));
		
		DealerDto dealer = new DealerDto();
		dealer.addCard(new Card(Suit.C, Denomination.N9));
		dealer.addCard(new Card(Suit.C, Denomination.NA));
		System.out.println("첫A : " + dealer.isAce());
		System.out.println("오픈 : " + dealer.isOpen());
	}
}
