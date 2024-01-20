package com.yopheu.aenean.view;

import java.util.ArrayList;

import com.yopheu.aenean.config.StrColor;
import com.yopheu.aenean.models.CommDataWrapper;
import com.yopheu.aenean.models.DealerDataDto;
import com.yopheu.aenean.models.PlayerDataDto;
import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.models.card.Denomination;
import com.yopheu.aenean.models.card.Suit;
 
public class ViewDataWrapper {
	// 모든 출력데이터는 set메소드에서 게임 데이터 기반으로 만들어져야됨.
	private CommDataWrapper cData;	// 게임 실 데이터.
	private ViewCard cardBack; // 덱 뒷면
	private ArrayList<ViewStr> dealerStr; // 딜러 표시줄
	private ArrayList<ViewCard> dealerCard; // 딜러 카드
	private ArrayList<ViewStr> player0Str; // 플레이어 표시줄
	private ArrayList<ViewCard> player0Card; // 플레이어 카드
	private ArrayList<ViewStr> player0Bet; // 베팅
	private ArrayList<ViewCard> player0SplitCard; // 플레이어 스플릿 카드	
	private ArrayList<ViewStr> player0SplitBet; // 스플릿 베팅
	
	public ViewDataWrapper(CommDataWrapper commDataWrapper) {
		cData = commDataWrapper;
		cardBack = new ViewCard("BJ", StrColor.YELLOW, StrColor.YELLOW); // 덱 뒷면(상단 우측)
		dealerStr = new ArrayList<>(); // 딜러 표시줄
		dealerCard = new ArrayList<>(); // 딜러 카드
		player0Str = new ArrayList<>(); // 플레이어 표시줄
		player0Card = new ArrayList<>(); // 플레이어 카드
		player0Bet = new ArrayList<>(); // 베팅
		player0SplitCard = new ArrayList<>(); // 플레이어 스플릿 카드	
		player0SplitBet = new ArrayList<>(); // 스플릿 베팅
		init();
	}
	
	public void init() {
		setDealerStr();
		setDealerCard();
		if(cData.getPlayers().size() > 0 ) {
			setPlayer0Str();
			setPlayer0Card();
			if(cData.getPlayers().get(0).getBetting() > 0) {
				setPlayer0Bet();
			}
			if(cData.getPlayers().get(0).isSplit()) {
				setPlayer0SplitCard();
				setPlayer0SplitBet();
			}
		}else {
			System.out.println("err: player0가 없습니다.");
		}
	}
	
	public void setDealerStr() {
		String dealerName = cData.getDealer().getName();
		ViewStr str = new ViewStr(String.format("%s ", dealerName), StrColor.RED);
		dealerStr.clear();
		dealerStr.add(str);
	} 
	public void setDealerCard() {
		boolean isOpen = cData.getDealer().isOpen();
		ArrayList<Card> arrCard = cData.getDealer().getCards();
		// sample
//		arrCard.add(new Card(Suit.S, Denomination.NA));
//		arrCard.add(new Card(Suit.D, Denomination.N10));
		dealerCard.clear();
		if(!isOpen && arrCard.size() == 2) {
			dealerCard.add(convertCardToViewCard(arrCard.get(0)));
			dealerCard.add(cardBack);	// 뒷장
		}else {
			for(Card card : arrCard) {
				dealerCard.add(convertCardToViewCard(card));
			}
		}
	}
	
	public void setPlayer0Str() {
		PlayerDataDto player0 = cData.getPlayers().get(0);	

		String playerName = player0.getName();
		int totalChip = player0.getChip();
		ViewStr viewName = new ViewStr(String.format("%s: ", playerName), StrColor.YELLOW);
		player0Str.clear();
		player0Str.add(viewName);
		player0Str.add(new ViewStr(totalChip));
	}
	
	public void setPlayer0Card() {
		PlayerDataDto player0 = cData.getPlayers().get(0);	
		ArrayList<Card> arrCard = player0.getCards();
		// sample
//		arrCard.add(new Card(Suit.S, Denomination.NA));
//		arrCard.add(new Card(Suit.D, Denomination.N10));
		player0Card.clear();
		for(Card card : arrCard) {
			player0Card.add(convertCardToViewCard(card));
		}
	}
	
	public void setPlayer0Bet() {
		PlayerDataDto player0 = cData.getPlayers().get(0);	
		int bet = player0.getBetting();
		player0Bet.clear();
		player0Bet.add(new ViewStr("{", StrColor.YELLOW));
		player0Bet.add(new ViewStr(bet));
		player0Bet.add(new ViewStr("} ", StrColor.YELLOW));
		
	}
	public void setPlayer0SplitCard() {
		PlayerDataDto player0 = cData.getPlayers().get(0);	
		ArrayList<Card> arrCard = player0.getSplitCards();
		// sample
//		arrCard.add(new Card(Suit.D, Denomination.NA));
		player0SplitCard.clear();
		for(Card card : arrCard) {
			player0SplitCard.add(convertCardToViewCard(card));
		}
	}
	
	public void setPlayer0SplitBet() {
		PlayerDataDto player0 = cData.getPlayers().get(0);	
		int bet = player0.getSplitBet();
		player0SplitBet.clear();
		player0SplitBet.add(new ViewStr("{", StrColor.YELLOW));
		player0SplitBet.add(new ViewStr(bet));
		player0SplitBet.add(new ViewStr("} ", StrColor.YELLOW));
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
		return player0Str;
	}
	public ArrayList<ViewCard> getPlayerCard() {
		return player0Card;
	}
	public ArrayList<ViewStr> getPlayerBet() {
		return player0Bet;
	}
	public ArrayList<ViewCard> getPlayerSplitCard() {
		return player0SplitCard;
	}
	public ArrayList<ViewStr> getPlayerSplitBet() {
		return player0SplitBet;
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
