package com.yopheu.aenean.models.entry;

import java.util.ArrayList;

import com.yopheu.aenean.config.PlayResultState;
import com.yopheu.aenean.config.PlayingState;
import com.yopheu.aenean.models.Card;

public class Dealer implements Entry{
	protected PlayingState playingState = PlayingState.WAITING;
	protected PlayResultState playResultState = PlayResultState.PENDING;
	protected ArrayList<Card> arrCard = null;
	
	public Dealer() {
		init();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		arrCard = new ArrayList<>();
	}
	
	// 딜러는 돈관련 설정이 없음. (아직)
	@Override
	public boolean setMoney(int money) {
		// Dealer는 돈설정 없음
		return false;
	}
	@Override
	public int getMoney() {
		// Dealer는 돈설정 없음
		return 0;
	}
	@Override
	public boolean addMoney(int money) {
		// Dealer는 돈설정 없음
		return false;
	}

	// 진행상태
	@Override
	public PlayingState setPlayingState(PlayingState playingState) {
		this.playingState = playingState;
		return this.playingState;
	}
	@Override
	public PlayingState getPlayingState() {
		return this.playingState;
	}
	
	// 결과상태
	@Override
	public PlayResultState setPlayResultState(PlayResultState playResultState) {
		this.playResultState = playResultState;
		return this.playResultState;
	}
	@Override
	public PlayResultState getPlayResultState() {
		return this.playResultState;
	}

	@Override
	public void clearArrCard() {
		arrCard.clear();
	}

	@Override
	public ArrayList<Card> getCard() {
		return arrCard;
	}

	@Override
	public void addCard(Card card) {
		arrCard.add(card);
	}

	@Override
	public boolean processCompletion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getCardScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	// 받은카드들
	
	// 게임진행상태
	// 카드결과상태
	
	// add 돈 (딜러승)
	// sub 돈 (딜러패)
	
	// 공통기능
	// 카드받기
	// 카드버리기 (결과완료시)
	// get결과상태
	// set결과상태
	// get진행상태
	// get진행상태
}
