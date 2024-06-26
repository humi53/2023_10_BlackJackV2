package com.yopheu.games.aenean.models;

import java.util.ArrayList;

import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.models.card.Card;

public class PlayerDto implements ICardHand, IPlayerChip{
	private ArrayList<Card> handsCard;
	private CardsCalculator calc;
	private PlayResultState playResultState; 
	
	private PlayerSplitDto splitDto;
	private int totalChip;	// 가지고 있는 전체 칩.
	private int betChip;	// 처음 배팅되어 있는 칩.
	private int insuranceChip;	// 인슈어런스 칩.
	private int lastBetChip; // 마지막에 걸었던 칩 크기
	private boolean isDoubleDown;
	private boolean isSplit;
	private int insuranceWin;
	
	public PlayerDto() {
		this.handsCard = new ArrayList<>();
		this.calc = new CardsCalculator();
		this.totalChip = 800;	// 임시로 부여된 칩.
		this.splitDto = new PlayerSplitDto(this);	// 생성하면서 자신을 넣을수 있나?
		resetStateChips();
		this.lastBetChip = this.betChip;
	}
	
	private void resetStateChips() {
		this.betChip = 0;
		this.insuranceChip = 0;
		this.insuranceWin = 0;
		this.isDoubleDown = false;
		this.isSplit = false;
		this.playResultState = PlayResultState.NONE;
		
		this.splitDto.resetHands();
	}
	
	@Override
	public void setResultState(PlayResultState playResultState) {
		this.playResultState = playResultState;
	}
	
	@Override
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
		boolean result = false;
		if(!isDoubleDown) {	// TODO : 스플릿이 아닐때만 블랙잭 확인.
			result = calc.isBlackJack(handsCard);
		}
		return result;
	}
	
	@Override
	public void resetHands() {
		this.handsCard.clear();	// 카드 초기화
		this.resetStateChips();	// 상태값 칩 초기화
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
	
	public boolean repeatPreviousBet() {
		return betting(lastBetChip);
	}
	
	public boolean doubleDown() {
		if (betChip <= 0 || isDoubleDown) return false;	// 배팅이 안걸려 있으면 나가.
		boolean result = abjectChips(-betChip);
		if(result) {
			betChip += betChip;
			isDoubleDown = true;
		}
		return result;
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
		insuranceWin = insuranceChip * 2;
	}
	
	@Override
	public boolean abjectChips(int chip) {
		if (totalChip + chip < 0) {
			return false;
		}else {
			totalChip += chip;
			return true;
		}
	}
	
	@Override
	public int getBetChip() {
		return betChip;
	}
	
	@Override
	public int getInsuranceWin() {
		return insuranceWin;
	}
	
	public boolean isSplitAllowed() {
		boolean result = false;
		if(handsCard.size() == 2 
				&& splitDto.getBetChip() == 0
				&& handsCard.get(0).getDenomination() == handsCard.get(1).getDenomination()) {
			result = true;
		}
		return result;
	}
	public boolean isSplit() {
		return isSplit;
	}
	public boolean isDobleDownAllowed() {
		boolean result = false;
		if(handsCard.size() == 2) {
			result = true;
		}
		return result;
	}
	
	// 스플릿Dto 가져오기
	public PlayerSplitDto getSplitDto() {
		return splitDto;
	}
	// 스플릿하기
	public boolean trySplit() {
		this.isSplit = splitDto.betting(betChip); 
		splitDto.addCard(handsCard.remove(handsCard.size()-1));
		return this.isSplit;
	}
	
	public int getTotalBet(){
		return betChip + insuranceChip;
	}
}
