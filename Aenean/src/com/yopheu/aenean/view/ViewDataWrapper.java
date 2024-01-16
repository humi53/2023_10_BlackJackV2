package com.yopheu.aenean.view;

import java.util.ArrayList;

import com.yopheu.aenean.config.StrColor;
import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.models.card.Denomination;
import com.yopheu.aenean.models.card.Suit;
 
public class ViewDataWrapper {
	// 모든 출력데이터는 set메소드에서 게임 데이터 기반으로 만들어져야됨. 
	private ViewCard cardBack = new ViewCard("BJ", StrColor.YELLOW, StrColor.YELLOW); // 덱 뒷면
	private ArrayList<ViewStr> dealerStr = new ArrayList<>(); // 딜러 표시줄
	private ArrayList<ViewCard> dealerCard = new ArrayList<>(); // 딜러 카드
	private ArrayList<ViewStr> playerStr = new ArrayList<>(); // 플레이어 표시줄
	private ArrayList<ViewCard> playerCard = new ArrayList<>(); // 플레이어 카드
	private ArrayList<ViewStr> playerBet = new ArrayList<>(); // 베팅
	private ArrayList<ViewCard> playerSplitCard = new ArrayList<>(); // 플레이어 스플릿 카드	
	private ArrayList<ViewStr> playerSplitBet = new ArrayList<>(); // 스플릿 베팅
	
	public ViewDataWrapper() {
		setDealerStr();
		setDealerCard();
		setPlayerStr();
		setPlayerCard();
		setPlayerBet();
		setPlayerSplitCard();
		setPlayerSplitBet();
	}
	
	public void setDealerStr() {
		// 샘플데이터
		dealerStr.clear();
		dealerStr.add(new ViewStr("Dealer ", StrColor.RED));
	} 
	public void setDealerCard() {
		// sample
		dealerCard.clear();
		dealerCard.add(new ViewCard("♠A", StrColor.GREEN));
		dealerCard.add(new ViewCard("♦A", StrColor.RED));
		dealerCard.add(new ViewCard("♥A", StrColor.RED));
		dealerCard.add(new ViewCard("♣A", StrColor.GREEN));
		dealerCard.add(new ViewCard("BJ", StrColor.YELLOW, StrColor.YELLOW));
	}
	public void setPlayerStr() {
		// sample
		playerStr.clear();
		playerStr.add(new ViewStr("Player: ", StrColor.YELLOW));
		playerStr.add(new ViewStr("100,000"));
	}
	public void setPlayerCard() {
		// sample
		playerCard.clear();
		playerCard.add(new ViewCard("♠A", StrColor.GREEN));
		playerCard.add(new ViewCard("♦A", StrColor.RED));
		playerCard.add(new ViewCard("♥A", StrColor.RED));
		playerCard.add(new ViewCard("♣A", StrColor.GREEN));
		playerCard.add(new ViewCard("BJ", StrColor.YELLOW, StrColor.YELLOW));
	}
	public void setPlayerBet() {
		playerBet.clear();
		playerBet.add(new ViewStr("{", StrColor.YELLOW));
		playerBet.add(new ViewStr("30"));
		playerBet.add(new ViewStr("} ", StrColor.YELLOW));
	}
	public void setPlayerSplitCard() {
		playerSplitCard.clear();
		playerSplitCard.add(convertCardToViewCard(new Card(Suit.S, Denomination.NA)));
		playerSplitCard.add(convertCardToViewCard(new Card(Suit.D, Denomination.N10)));
		playerSplitCard.add(new ViewCard("♠A", StrColor.GREEN));
		playerSplitCard.add(new ViewCard("♦A", StrColor.RED));
		playerSplitCard.add(new ViewCard("♥A", StrColor.RED));
		playerSplitCard.add(new ViewCard("♣A", StrColor.GREEN));
		playerSplitCard.add(new ViewCard("BJ", StrColor.YELLOW, StrColor.YELLOW));
	}
	public void setPlayerSplitBet() {
		playerSplitBet.clear();
		playerSplitBet.add(new ViewStr("{", StrColor.YELLOW));
		playerSplitBet.add(new ViewStr("30"));
		playerSplitBet.add(new ViewStr("} ", StrColor.YELLOW));
	}
	

	public ViewCard getCardBack() {
		return cardBack;
	}	
	public ArrayList<ViewStr> getDealerStr(){
		return dealerStr;
	}
	public ArrayList<ViewCard> getDealerCard() {
		return dealerCard;
	}
	public ArrayList<ViewStr> getPlayerStr(){
		return playerStr;
	}
	public ArrayList<ViewCard> getPlayerCard() {
		return playerCard;
	}
	public ArrayList<ViewStr> getPlayerBet() {
		return playerBet;
	}
	public ArrayList<ViewCard> getPlayerSplitCard() {
		return playerSplitCard;
	}
	public ArrayList<ViewStr> getPlayerSplitBet() {
		return playerSplitBet;
	}
	
	private ViewCard convertCardToViewCard(Card card) {
		ViewCard result = null;
		String cardStr = "";	// 문자열
		StrColor strColor = null;	// 문자열 컬러.
		
		// Suit에 따라 컬러 설정.
		if(card.getSuit() == Suit.S || card.getSuit() == Suit.C) {
			strColor = StrColor.GREEN;
		}else if(card.getSuit() == Suit.D || card.getSuit() == Suit.H) {
			strColor = StrColor.RED;
		}
		
		cardStr = String.format("%s%s", 
				card.getSuit().getSymbol(), 
				card.getDenomination().getSymbol());
		result = new ViewCard(cardStr, strColor);
		return result;
	}
	
}
