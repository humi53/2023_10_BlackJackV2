package com.yopheu.games.aenean.models;

import com.yopheu.games.aenean.config.PlayResultState;

public interface IPlayerChip {
	public int getBetChip();
	public int getInsuranceWin();
	public boolean abjectChips(int chip);
	public int getHandsScore();
	public PlayResultState getResultState();
	public void setResultState(PlayResultState playResultState);
}
