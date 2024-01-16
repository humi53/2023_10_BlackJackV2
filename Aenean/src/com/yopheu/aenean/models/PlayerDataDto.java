package com.yopheu.aenean.models;

import java.util.ArrayList;

import com.yopheu.aenean.models.card.Card;

public class PlayerDataDto {
	ArrayList<Card> arrCard; 	// 현재 카드
	ArrayList<Card> arrSplitCard;	// 스플릿 카드
	int totalChip; // 현재 칩
	int betChip; // 배팅 칩
	int splitChip;	// 스플릿 칩.
	int insuranceChip;  // 인슈어런스 칩
	boolean isInsurance;
	
	int comState = 0; // 플레이어 상태.	//0.미정, 1.블랙잭, 2.버스트, 3.게임완료(점수계산완료)
	int actionState = 0; // 액션 상태. //0.미정, 1.힛, 2.스탠드, 3.
	public PlayerDataDto() {
		this.arrCard = new ArrayList<>();
		this.totalChip = 2000;
		this.betChip = 0;
		this.splitChip = 0;
		this.insuranceChip = 0;
		this.isInsurance = false;
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
	
	// 인슈어런스 세팅  // 인슈어런스 걸기.
	public void setInsurance() {
		if(totalChip - betChip/2 < 0)
			System.out.println("err : 인슈어런스 할 칩이 부족.");
		totalChip -= betChip/2;
		insuranceChip = betChip/2;
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
	public ArrayList<Card> getSplitCars(){
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
	
	// 배팅 걸기.
	public void betting(int chip) {
		this.betChip = chip;
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
	
	
	
}
