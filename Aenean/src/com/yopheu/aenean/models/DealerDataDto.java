package com.yopheu.aenean.models;

import java.util.ArrayList;

import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.models.card.Denomination;

public class DealerDataDto {
	ArrayList<Card> arrCard; // 현재 카드
	// 현재 칩.	
	
	public DealerDataDto() {
		this.arrCard = new ArrayList<>();
	}
	
	// 카드 추가
	public void addCard(Card card) {
		this.arrCard.add(card);
	}
	// 손패 리셋 (비우기)
	public void resetCards() {
		this.arrCard.clear();
	}
	
	// 손패 가져오기 (뭐있는지 확인)
	public ArrayList<Card> getCards(){
		return this.arrCard;
	}
	
	// 앞장 A 확인
	public boolean isAceFCard() {
		Card card = arrCard.get(0);
		boolean isAce = false;
		if(card.getDenomination() == Denomination.NA)
			isAce = true;
		else
			isAce = false;
		return isAce;
	}
	
	// 뒷장 값이 10인지 확인.
	public boolean is10BCard() {
		Card card = arrCard.get(1);
		boolean is10 = false;
		if(card.getDenomination().getValue() == 10)
			is10 = true;
		else
			is10 = false;
		return is10;
	}
}
