package com.yopheu.games.aenean.models;

import com.yopheu.games.aenean.config.PlayResultState;

public class ResultCalculator {
	public int getCalcChips(IPlayerChip playerDto) {
		int winChips = 0;
		// 인슈어런스
		winChips +=playerDto.getInsuranceWin();
		
		PlayResultState resultState = playerDto.getResultState();
		if(resultState == PlayResultState.BLACKJACK) {
			winChips += playerDto.getBetChip() * 3;
		}else if(resultState == PlayResultState.WIN) {
			winChips += playerDto.getBetChip() * 2;
		}else if(resultState == PlayResultState.PUSH) {
			winChips += playerDto.getBetChip();
		}
		
		return winChips;
	}
	public void calcResultState(IPlayerChip playerDto) {
		playerDto.abjectChips(getCalcChips(playerDto));
	}
}
