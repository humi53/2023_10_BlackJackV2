package com.yopheu.games.aenean.models;

import java.util.ArrayList;

import com.yopheu.games.aenean.models.card.Card;

public interface ICardHand {
	public void addCard(Card card);	// 카드 딜링시.
	public void resetHands();	// 핸드 초기화.	여러가지 설정도 초기화.
	public ArrayList<Card> getHands();	// 핸드의 카드 가져오기. UI에서 필요.
	// handsCardCount 카드의 갯수를 어디에 쓰지?
	public int getHandsScore();	// 핸드의 점수 합산.
	public boolean isBlackJack();	// 블랙잭 확인.
}
