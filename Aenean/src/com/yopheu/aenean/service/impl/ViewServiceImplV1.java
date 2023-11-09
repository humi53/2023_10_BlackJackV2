package com.yopheu.aenean.service.impl;

import java.util.List;
import java.util.Queue;

import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.entry.Dealer;
import com.yopheu.aenean.models.entry.Player;
import com.yopheu.aenean.service.ViewService;
import com.yopheu.aenean.service.modules.CommDataModule;

public class ViewServiceImplV1 implements ViewService{
	
	private Queue<Card> cardDeck;		// 카드덱
	private Dealer dealer;		// 딜러
	private List<Player> players;	// 플레이어들
	
	private CommDataModule cData;
	
	private String singleMessage;
	private Player currentPlayer;
	
	
	public ViewServiceImplV1(CommDataModule commDataModule){
		this.cData = commDataModule;
	}
	
	private void linkData() {
		dealer = cData.getDealer();
		players = cData.getPlayers();
		cardDeck = cData.getCardDeck();
	}
	
	@Override
	public void init() {
		linkData();
	}
	
	
	// GameState
	// 상태별 출력.
	
	// 단순 출력이 있다.
	//		입력안내 출력
	//		결과안내 출력
	// 반복 출력이 있다.
	
	// 출력은 항상 전체화면을 뿌려줄것.
	
	// 하단 메시지 선택기
	private void selectMessage() {
		singleMessage = "";
		switch (cData.getGameState()) {
		case READY:
			singleMessage = "게임이 준비중인 것이에요.";
			break;
		case WILLBET:
			singleMessage = "배팅 [20(1),40(2),100(3),200(4),400(5),500(6),1000(7)] 확인(Enter) 취소(0)";
			singleMessage = String.format("%s\n%s[%s] : ", singleMessage, 
					cData.getCurrentPlayer().getName(),
					cData.getCurrentPlayer().getNowBet());
			break;
		
		default:
			break;
		}
	}
	
	private void printView() {
		selectMessage();
		System.out.print(singleMessage);
	}
	
	@Override
	public void paint() {
		printView();
	}
	@Override
	public void repaint() {
		
	}
}
