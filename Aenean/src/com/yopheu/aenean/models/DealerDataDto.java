package com.yopheu.aenean.models;

import java.util.ArrayList;

import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.models.card.Denomination;

public class DealerDataDto {
	ArrayList<Card> arrCard; // 현재 카드
	String name;	// 딜러이름
	boolean isOpen;		// 뒷장 오픈여부.
	
	public DealerDataDto() {
		this.arrCard = new ArrayList<>();
		name = "Dealer";
		isOpen = false;
	}
	
	public String getName() {
		return name;
	}
	
	public void setOpen(boolean open) {
		isOpen = open;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	// 첫장 Ace 확인
	public boolean isAce() {
		boolean result = false;
		if(!arrCard.isEmpty() 
				&& arrCard.get(0).getDenomination() == Denomination.NA) {
			 result = true;
		}
		return result;
	}
	
	// 첫장 10 확인
	public boolean is10() {
		boolean result = false;
		if(!arrCard.isEmpty() 
				&& arrCard.get(0).getDenomination().getValue() == 10) {
			 result = true;
		}
		return result;
	}
	
	// 블랙잭 확인.
	public boolean isBlackJack() {
		boolean result = false;
		// 2장이면서, 21일때
		if(arrCard.size() == 2 
				&& calcCardSum(arrCard) == 21) {
			result = true;
		}else {
			result = false;
		}
		return result;
	}
		
	// 일반카드 합산
	public int getCardSum() {
		return calcCardSum(arrCard);
	}
	
	private int calcCardSum(ArrayList<Card> cards) {
		int result = 0;
		boolean ace = false;
		// A 카드가 1장 이상 있을때 일반적인 처리
		// 첫A는 11 다음장 A는 1로 처리.
		for(Card card : cards) {
			Denomination card_denomi = card.getDenomination();			
			if(ace && card_denomi == Denomination.NA) {
				result += 1;
			}else {
				if(card_denomi == Denomination.NA) {
					ace = true;
				}
				result += card.getDenomination().getValue();				
			}
		}
		// A 카드가 1장이상 있으며 21을 넘어갈때 처리
		// 모든 A 카드를 1로 처리.
		if(ace && result > 21) {
			result = 0;
			for(Card card : cards) {
				Denomination card_denomi = card.getDenomination();
				if(card_denomi == Denomination.NA) {
					result += 1;
				}else {
					result += card.getDenomination().getValue();
				}
			}
		}
		return result;
	}
	
	// 카드 추가
	public void addCard(Card card) {
		this.arrCard.add(card);
	}
	public int size() {
		return arrCard.size();
	}
	// 손패 리셋 (비우기)
	public void resetCards() {
		isOpen = false;
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
