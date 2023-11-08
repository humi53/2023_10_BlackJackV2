package com.yopheu.aenean.models.entry;

import java.util.ArrayList;

import com.yopheu.aenean.config.PlayResultState;
import com.yopheu.aenean.config.PlayingState;
import com.yopheu.aenean.models.Card;

public interface Entry {
	// 초기화
	public void init();

	// account에 money 설정 기능.
	public boolean setMoney(int money);
	public int getMoney();
	public boolean addMoney(int money);

	// 카드 기능
	public void clearArrCard();	// 카드 버리기
	public ArrayList<Card> getCard();	// 카드뭉치 꺼내기(출력용)
	public void addCard(Card card);	// 카드추가하기 (머신에서 Entry로)
	
	// 결과상태
	public PlayResultState getPlayResultState();
	public PlayResultState setPlayResultState(PlayResultState playResultState);
	// 진행상태
	public PlayingState getPlayingState();
	public PlayingState setPlayingState(PlayingState playingState);

	
	public int getCardScore();		// 카드점수 계산
	public boolean processCompletion();	// 완료후 초기화설정.

	
	
}
