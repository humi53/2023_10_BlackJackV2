package com.yopheu.aenean.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.entry.Dealer;
import com.yopheu.aenean.models.entry.Entry;
import com.yopheu.aenean.models.entry.Player;
import com.yopheu.aenean.service.GameService;
import com.yopheu.aenean.service.ViewService;
import com.yopheu.aenean.service.modules.CardDeckModule;
import com.yopheu.aenean.service.modules.CommDataModule;
import com.yopheu.aenean.service.modules.impl.CardDeckModuleImplV1;
import com.yopheu.aenean.service.modules.impl.CommDataModuleImplV1;

public class GameServiceImplV1 implements GameService {
	private Queue<Card> cardDeck;		// 카드덱
	private Dealer dealer;		// 딜러
	private List<Player> players;	// 플레이어들
	
	private Queue<Player> betWattingQueue;	// 배팅대기 player stack
	private Queue<Entry> playWattingQueue;	// 플레이대기 entry stack
	private Queue<Entry> completeWattingQueue;	// 완료대기 player queue
	
	private CommDataModule cData;
	private CardDeckModule cardDeckModule;
	private ViewService viewService;
	
	private Scanner scan;
	// bet
	// firstDeals
	// blackJackCheck
	// insuranceCheck
	
	// play
	// createResult
	
	// resultCheck
	// settle
	// reset
	
//	public GameServiceImplV1() {
//		this.cardDeckModule = new CardDeckModuleImplV1();
//		this.cData = new CommDataModuleImplV1();
//	}
	
	public GameServiceImplV1(CardDeckModule cardDeckModule, CommDataModule commDataModule, ViewService viewService) {
		this.viewService = viewService;
		this.cardDeckModule = cardDeckModule;
		this.cData = commDataModule;
	}
	private void initQueues() {
		betWattingQueue = new LinkedList<>();
		playWattingQueue = new LinkedList<>();
		completeWattingQueue = new LinkedList<>();
	}
	private void linkData() {
		dealer = cData.getDealer();
		players = cData.getPlayers();
		cardDeck = cData.getCardDeck();
		cardDeckModule.setCardDeck(cardDeck);
	}
	
	private void setData() {
		players.add(new Player("Player1"));
		cardDeckModule.addCardDeck();
		cData.setGameState(GameState.READY);
	}

	@Override
	public void init() {
		scan = new Scanner(System.in);
		initQueues();
		linkData();
		setData();
	}
	
	private void setBetQueue() {
		betWattingQueue.clear();
		betWattingQueue.addAll(players);
		cData.setGameState(GameState.WILLBET);
	}
	
	@Override
	public void start() {
		while(!cData.getGameState().equals(GameState.END)) {
			switch (cData.getGameState()) {
			case READY:
				System.out.println(GameState.READY);	
				setBetQueue();
				break;
			case WILLBET:
				System.out.println(GameState.WILLBET);
				doBetting();
				break;
			case WILL2DEALING:
				System.out.println(GameState.WILL2DEALING);
				break;
			default:
				break;
			}			
		}
		
		// 배팅하는 상태
		// 딜링하는 상태
		// 인슈어런스확인하는 상태
		// 플레이를하는 상태
		// 결과정리하는 상태
		// 초기화하는 상태
	}
	
	private void doBetting() {
		while(!betWattingQueue.isEmpty()) {
			Player player = betWattingQueue.poll();
			cData.setCurrentPlayer(player);
			
			int money = 0;
			
			// 플레이어 이름 출력 + 배팅안내 메시지 출력
			
//			while()
			// 배팅 입력.
			while(true) {
				viewService.paint();
				try {
					String sBet = scan.nextLine();
					if(sBet == "" && player.getBasicBet() > 0) {
						break;
					}
					int bet = Integer.valueOf(sBet);
					if(bet >= 1 && bet <= 7) {
						if(bet == 1) {
							money = 20;
						}else if(bet == 2) {
							money = 40;
						}else if(bet == 3) {
							money = 100;
						}else if(bet == 4) {
							money = 200;
						}else if(bet == 5) {
							money = 400;
						}else if(bet == 6) {
							money = 500;
						}else if(bet == 7) {
							money = 1000;
						}
						if(player.getBasicBet() + money > 1000) {
							// 기회 또줌. 최대금액은 1000까지
						}else {
							if(player.betting(money)) {
								// 기회 또줌. 잔고가 부족해서 실패
							}else {
								// 성공
							}
						}
					}else if(bet == 0) {
						player.cancelBet();
					}else {
						// 기회 또줌 0 ~ 7 사이의 숫자가 아닐때
						System.out.println("애러가 나버린 것이에요.");
					}				
				} catch (Exception e) {
					// 기회 또줌 숫자가 아닐때
					System.out.println("애러가 나버린 것이에요.");
				}
			}
				
			
			
			
			// 배팅 내역 처리. (잔고에서 -, 배팅금 +)
			// 완료된 플레이어 초기딜링 큐로
		}
//		gameState = GameState.WILL2DEALING;
	}
	
	// 배팅큐 만들기
	// 1. 베팅을 한다
	
	// 딜링큐 만들기 (딜러포함)
	// 2. 카드를 나눠준다
	
	// 인슈어런스 확인큐 만들기
	// 3. 인슈어런스 체크
	// 딜러카드 확인 (블랙잭이면 바로 승패 결정)
	
	// 블랙잭 확인큐 (플레이 하면서 블랙잭 확인 가능?)
	// 3.5. 플레이어가 블랙잭.
	
	// 플레이 큐 (딜러포함)
	// 4. 플레이를 한다.
	// 4-1. 더블다운
	// 4-2. 스플릿
	// 4. 결과를 만든다
	
	// 4.5. 딜러도 플레이
	// 4. 딜러 결과 만든다
	
	// 결과 정리 큐
	// 5. 결과로 승패확인
	// 각 플레이어의 상태에 따라 돈 확인
	
	// 많은것을 초기화하면서
	// 배팅큐를 만든다
}
