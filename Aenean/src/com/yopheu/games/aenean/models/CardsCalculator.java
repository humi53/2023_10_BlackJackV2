package com.yopheu.games.aenean.models;

import java.util.ArrayList;

import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;

public class CardsCalculator {
	// 카드 합산
	public int getCardsScore(ArrayList<Card> cards) {
		int result = 0;
		boolean hasAce = false;
		// 첫A만 11 나머지 A는 1로. 21을 넘기면 다음 if문에서 재처리.
		for(Card element : cards) {
			Denomination eleDenomi = element.getDenomination();
			if(hasAce && eleDenomi == Denomination.NA) {
				result += 1;
			}else {
				if(eleDenomi == Denomination.NA) {
					hasAce = true;
				}
				result += eleDenomi.getValue();
			}
		}
		
		// A카드 포함되고 21을 넘었을때 모든 A카드 1 처리.
		if(hasAce && result > 21) {
			result = 0;
			for(Card element : cards) {
				Denomination eleDenomi = element.getDenomination();
				if(eleDenomi == Denomination.NA) {
					result += 1;
				}else {
					result += eleDenomi.getValue();
				}
			}
		}
		
		return result;
	}
	
	// 블랙잭 확인
	public boolean isBlackJack(ArrayList<Card> cards) {
		boolean result = false;
		if(cards.size() == 2 
				&& getCardsScore(cards) == 21) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
}
