package com.yopheu.games.aenean.models;

import java.util.ArrayList;

import com.yopheu.games.aenean.models.card.Card;

public class PlayerDto implements ICardHand{
	ArrayList<Card> handsCard;
	private CardsCalculator calc;
	// TODO 칩관련 : 칩관련을 분리해야되나 이곳에서 함께 처리해야되나?
	// 가지고 있는 전체 칩.
	int totalChip;
	// 일반 배팅 칩.
	int betChip;
	// 스플릿 칩.
//	int splitChip;
	// 인슈어런스 칩.
	int insuranceChip;
	// 더블다운 <- 일반 배팅 칩에 포함해도 문제 없음.
	// 임시 저장된 일반 배팅 칩 금액 (금액 그대로 다시 걸기 위해서 필요)
	int lastBetChip;
	
	// TODO 진행관련 : 진행관련을 이곳에 가지고 있어야 되나?
	
	// 스플릿	// 칩 두배로 추가. // 더 진행	// 더블다운 가능
	// 인슈어런스	// 50% 따로 배팅 // 더 진행 (블랙잭 아닐때)
	// 더블다운	// 칩 두배로 추가. // 한장만 진행
	// 힛	// 일반적인 진행
	// 스탠드	// 멈춤.
	
	// 스플릿은 어떻게 확인? : null로 확인, SplitDto 객체의 Chip을 확인.
	// 인슈어런스	// 0 으로 확인.
	// 힛 & 스탠드 : hit true, stand false; 상태값이 2가지만 있으면 가능.
	// 상태 예외값, 블랙잭, 버스트, 입력대기상태.
	//	(입력대기, 힛, 스탠드), (블랙잭, 버스트).
	// 점수계산완료 상태가 따로 필요한가?
	// 알고리즘에 필요한 상태 : 입력대기, 힛, 스탠드, 블랙잭.
	// 출력에 필요한 상태 : 블랙잭, 버스트, 스탠드, 점수계산. 우승, 손실, 비김. 진행중.
				// 배팅금. 인슈어런스 금액.
	// TODO 상태값이 없어도 UI쪽에서도 문제없이 출력가능?
	
	public PlayerDto() {
		this.handsCard = new ArrayList<>();
		this.calc = new CardsCalculator();
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
		this.handsCard.clear();
	}

}
