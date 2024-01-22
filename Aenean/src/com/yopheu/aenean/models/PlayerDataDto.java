package com.yopheu.aenean.models;

import java.util.ArrayList;

import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.models.card.Denomination;

public class PlayerDataDto {
	String name;
	ArrayList<Card> arrCard; 	// 현재 카드
	ArrayList<Card> arrSplitCard;	// 스플릿 카드
	int totalChip; // 현재 칩
	int betChip; // 배팅 칩
	int splitChip;	// 스플릿 칩.
	int insuranceChip;  // 인슈어런스 칩
	boolean isInsurance;	// 인슈어런스 여부.
	int preBetChip;	// 이전 대칭 칩. (편한 배팅을 위해)
	// 스플릿 가능 여부
	// 스플릿 여부
	// 더블 (스플릿해도)
	// 힛
	// 스탠드
	
	int comState = 0; // 플레이어 상태.	//0.미정, 1.블랙잭, 2.버스트, 3.게임완료(점수계산완료)
	int actionState = 0; // 액션 상태. //0.미정, 1.힛, 2.스탠드, 3.
	public PlayerDataDto(String name) {
		this.name = name;
		this.arrCard = new ArrayList<>();
		this.arrSplitCard = new ArrayList<>();
		this.totalChip = 2000;
		this.betChip = 0;
		this.splitChip = 0;
		this.insuranceChip = 0;
		this.isInsurance = false;
		preBetChip = 0;
	}
	
	public String getName() {
		return name;
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
	
	// 스플릿 가능 여부
	public void checkSplit() {
		boolean result = false;
		if(totalChip - betChip < 0)
			result = false;
		if(arrCard.size() != 2)
			result = false;
		
	}
	
	// 스플릿 세팅 // 스플릿 걸기.
	public void setSplit() {
		if(totalChip - betChip < 0)
			System.out.println("err : 스플릿 할 칩이 부족.");
		if(arrCard.size() != 2)
			System.out.println("err : 손패가 2장이 아님");
			
		arrSplitCard.add(arrCard.remove(arrCard.size()-1));
		totalChip -= betChip;
		splitChip = betChip;
	}
	
	// 스플릿 확인.
	public boolean isSplit() {
		boolean result = false;
		if(splitChip > 0 && arrSplitCard.size() > 0) {
			result = true;
		}else {
			result =false;
		}
		return result;
	}
	
	// 블랙잭 확인.
	public boolean isBlackJack() {
		boolean result = false;
		// 스플릿이 아니면서, 2장이면서, 21일때
		if(!isSplit() && arrCard.size() == 2 
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
	
	// 스플릿카드 합산
	public int getSplitCardSum() {
		return calcCardSum(arrSplitCard);
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
	
	// 스플릿 초기화
	public void resetSplit() {
		splitChip = 0;
		arrSplitCard.clear();
	}
	
	public int getSplitBet() {
		return splitChip;
	}
	
	// 인슈어런스 세팅  // 인슈어런스 걸기.
	public boolean setInsurance() {
		boolean result = false;
		if(totalChip - betChip/2 < 0) {
			result = false;
		}else {
			totalChip -= betChip/2;
			insuranceChip = betChip/2;
			result = true;
		}
		return result;
	}
	
	// 스플릿 카드 추가
	public void addSplitCard(Card card) {
		this.arrSplitCard.add(card);
	}
	
	// 스플릿 손패 리셋 (비우기)
	public void resetSplitCards() {
		this.arrSplitCard.clear();
	}
	
	// 스플릿 손패 가져오기 (뭐잇는지 확인)
	public ArrayList<Card> getSplitCards(){
		return this.arrSplitCard;
	}
	
	// 칩 설정.
	public void setChip(int chip) {
		this.totalChip = chip;
	}
	
	public void addChip(int chip) {
		this.totalChip += chip;
	}
	
	public void subChip(int chip) {
		this.totalChip -= chip;
	}
	public int getChip() {
		return totalChip;
	}
	
	// 배팅 걸기.
	public void betting(int chip) {
		totalChip -= chip;
		this.betChip += chip;
	}
	
	// 이전 배팅값으로 배팅
	public boolean reBetting() {
		if(preBetChip > totalChip) {
			return false;
		}else {
			totalChip -= preBetChip;
			betChip = preBetChip;
			return true;
		}		
	}
	// 이전 배팅값 셋
	public void setPreBet() {
		preBetChip = betChip;
	}
	
	public int getBetting() {
		return this.betChip;
	}
	
	// 배팅 정산.
	// 스플릿 정산.
	// 인슈어런스 정산.
	
	public void resetBetChip() {
		this.betChip = 0;
	}
	public void resetSplitChip() {
		this.splitChip = 0;
	}
	public void resetInsurance() {
		this.insuranceChip = 0;
	}
	public boolean isInsurance() {
		return insuranceChip > 0;
	}
	
	
	
}
