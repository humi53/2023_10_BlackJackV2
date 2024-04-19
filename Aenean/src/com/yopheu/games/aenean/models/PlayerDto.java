package com.yopheu.games.aenean.models;

import java.util.ArrayList;

import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.models.card.Card;

public class PlayerDto implements ICardHand{
	private ArrayList<Card> handsCard;
	private CardsCalculator calc;
	private PlayResultState playResultState; 
	// TODO 칩관련 : 칩관련을 분리해야되나 이곳에서 함께 처리해야되나?
	// 스플릿 칩.
//	int splitChip;	// 스플릿 객체가 따로 가진다.
	private int totalChip;	// 가지고 있는 전체 칩.
	private int betChip;	// 처음 배팅되어 있는 칩.
	private int insuranceChip;	// 인슈어런스 칩.
	private int lastBetChip; // 마지막에 걸었던 칩 크기
	private boolean isDoubleDown;
	// 더블다운 <- 일반 배팅 칩에 포함해도 문제 없음.
	
	// TODO 진행관련 : 진행관련을 이곳에 가지고 있어야 되나?
	
	// 스플릿	// 칩 두배로 추가. // 더 진행	// 더블다운 가능
	
	// 힛	// 일반적인 진행
	// 스탠드	// 멈춤.
	
	// 스플릿은 어떻게 확인? : null로 확인, SplitDto 객체의 Chip을 확인.
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
		// TODO : 스플릿 객체 생성.
		this.playResultState = PlayResultState.NONE;
		this.totalChip = 10000;	// 임시로 부여된 칩.
		resetStateChips();
		this.lastBetChip = this.betChip;
	}
	
	public void resetStateChips() {
		this.betChip = 0;
		this.insuranceChip = 0;
		this.isDoubleDown = false;
		// TODO : 스플릿의 betChip 초기화.
	}
	
	public void setResultState(PlayResultState playResultState) {
		this.playResultState = playResultState;
	}
	public PlayResultState getResultState() {
		return playResultState;
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
	
	
	public boolean abjectChips(int chip) {
		if (totalChip + chip < 0) {
			return false;
		}else {
			totalChip += chip;
			return true;
		}
	}
	
	public int totalChip() {
		return totalChip;
	}

	public boolean betting(int chip) {
		boolean result = abjectChips(-chip);
		if(result) {
			betChip += chip;
			lastBetChip = betChip;
		}
		return result;
	}
	
	public boolean doubleDown() {
		if (betChip <= 0 || isDoubleDown) return false;	// 배팅이 안걸려 있으면 나가.
		boolean result = abjectChips(-betChip);
		if(result) {
			betChip += betChip;
		}
		return result;
	}
	
	public int getBetChip() {
		return betChip;
	}
	
	public void cancelBetting() {
		abjectChips(betChip);
		betChip = 0;
		lastBetChip = betChip;
	}
	
	public boolean setInsurance() {
		if(isInsurance()) return false;
		int tempInsuranceChip = betChip/2;
		boolean result = abjectChips(-tempInsuranceChip);
		if(result) insuranceChip = tempInsuranceChip;
		return result;
	}
	
	public boolean isInsurance() {
		boolean result = false;
		if(insuranceChip > 0) result = true;
		return result;
	}
	
	public void insuranceWon() {
		totalChip += insuranceChip;
	}
	
	//	int splitChip;	// 스플릿 객체가 따로 가진다.
	// 인슈어런스	// 50% 따로 배팅 // 더 진행 (블랙잭 아닐때)
}
