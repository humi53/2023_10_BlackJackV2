package com.yopheu.games.aenean.models;

import java.util.ArrayList;

import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;

public class DealerDto implements ICardHand{
	private ArrayList<Card> handsCard;
	private CardsCalculator calc;
	private boolean isOpen; // 2장일때 뒷장 오픈 여부.
	

	public DealerDto() {
		this.handsCard = new ArrayList<>();
		this.calc = new CardsCalculator();
		this.isOpen = false;
	}
	
	@Override
	public void addCard(Card card) {
		handsCard.add(card);	
	}

	@Override
	public ArrayList<Card> getHands() {
		return handsCard;
	}

	@Override
	public int getHandsScore() {
		return calc.getCardsScore(handsCard);
	}

	@Override
	public boolean isBlackJack() {
		return calc.isBlackJack(handsCard);
	}
	
	@Override
	public void resetHands() {
		this.isOpen = false;		
		this.handsCard.clear();
	}
	
	public void setOpen() {
		isOpen = true;
	}
	
	public boolean isOpen() {
		if(handsCard.size() <= 2) {
			return isOpen;
		}else {
			return true;	// 2장을 넘어가면 관계없이 카드 보여주기.
		}
	}
	
	public boolean isAce() {
		boolean result = false;
		if(!handsCard.isEmpty() 
				&& handsCard.get(0).getDenomination() == Denomination.NA)
			result = true;
		return result;
	}
	
	public boolean isTenValue() {
		boolean result = false;
		if(!handsCard.isEmpty() 
				&& handsCard.get(0).getDenomination().getValue() == 10)
			result = true;
		return result;
	}
	
	// UI에서 쓰일 State
	public PlayResultState getResultState(){
		if(isBlackJack()) {
			return PlayResultState.BLACKJACK;
		}else if(getHandsScore() > 21) {
			return PlayResultState.BUST;
		}else {
			return PlayResultState.NONE;
		}
	}
}
