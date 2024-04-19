package com.yopheu.games.aenean.models;

import java.util.ArrayList;

import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.models.card.Card;

public class PlayerSplitDto implements ICardHand {

	private ArrayList<Card> handsCard;
	private CardsCalculator calc;
	private PlayResultState playResultState;
	private PlayerDto OriginPlayerDto;
	
	private int betChip;
	private boolean isDoubleDown;
	
	public PlayerSplitDto(PlayerDto originPlayerDto) {
		this.handsCard = new ArrayList<>();
		this.calc = new CardsCalculator();
		this.OriginPlayerDto = originPlayerDto;
		
		resetStateChips();
	}
	
	private void resetStateChips() {
		this.playResultState = PlayResultState.NONE;
		this.betChip = 0;
		this.isDoubleDown = false;
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
		return false;
	}
	
	@Override
	public void resetHands() {
		this.handsCard.clear();
		resetStateChips();
	}
	
	public boolean betting(int chip) {
		boolean result = OriginPlayerDto.abjectChips(-chip);
		if(result) {
			betChip += chip;
		}
		return result;
	}
	
	public boolean doubleDown() {
		if(betChip <= 0 || isDoubleDown) return false;
		boolean result = OriginPlayerDto.abjectChips(-betChip);
		if(result) {
			betChip += betChip;
			isDoubleDown = true;
		}
		return result;
	}
	
	public int getBetChip() {
		return betChip;
	}
	

}
