package com.yopheu.aenean.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import com.yopheu.aenean.config.BetConfig;
import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.config.TodoState;
import com.yopheu.aenean.models.CommDataWrapper;
import com.yopheu.aenean.models.DealerDataDto;
import com.yopheu.aenean.models.DeckDto;
import com.yopheu.aenean.models.PlayerDataDto;
import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.models.card.Denomination;
import com.yopheu.aenean.models.card.Suit;
import com.yopheu.aenean.service.GameService;
import com.yopheu.aenean.service.ViewService;

public class GameServiceImplV1 implements GameService {

	CommDataWrapper cData; // DataWrapper
	ViewService viewService;
	Scanner scan;

	public GameServiceImplV1() {
		cData = new CommDataWrapper();
		viewService = new ViewServiceImplV1(cData);
		cData.state = TodoState.ProcSET;
		scan = new Scanner(System.in);
	}
	
	private void paint() {
		viewService.paint();
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		// 배팅 전처리.
		// 배팅	
		boolean isLoop = true;
		while(isLoop) {
			if(cData.state == TodoState.ProcSET) {	// 카드, 배팅 초기화
				processSet();
			}else if(cData.state == TodoState.PromBET) {	// 배팅 (입력) 받기
				promptBet();
			}else if(cData.state == TodoState.ProcCARD2DEALING) { // 카드 2장씩 딜링.
				processCard2Dealing();
			}else if(cData.state == TodoState.CheckALLPLAYERBJ) {
				checkPlayerAllBJ();
			}else if(cData.state == TodoState.CheckDEALERA10) {		// 딜러 A, 10 카드확인.
				checkDealerA10();
			}else if(cData.state == TodoState.PromINSURANCE) {		// A 카드일때 인슈어런스 입력받기.
				promptInsurance();
			}else if(cData.state == TodoState.CheckDEALERBJ) {		// 딜러가 블랙잭인지 확인.
				checkDealerBJ();
			}else {
				System.out.println("전체루프 한바퀴");
			}
		}
//		switch (state) {
//		case ProcSET: 
//			processSet();
//		default:
//			System.out.println("공용");
//		}
	}

	private void checkPlayerAllBJ() {
		// TODO Auto-generated method stub
		if(cData.isPlayerAllBJ()) {
			// 마무리
		}else {
			// A10 확인.
			cData.state = TodoState.CheckDEALERA10;
		}
		
	}

	private void processSet() {
		// TODO Auto-generated method stub
		DeckDto deck = cData.getDeck();
		DealerDataDto dealer = cData.getDealer();
		ArrayList<PlayerDataDto> players = cData.getPlayers();
		
		// 덱 20장 이하면 추가.
		if(deck.deckSize() <= 20) {
			deck.addDeck();
		}
		// 딜러 준비
		dealer.resetCards(); // 카드비우기
		
		// 플레이어 준비
		for(PlayerDataDto player : players) {	
			player.resetCards();	// 카드비우기
			player.resetSplitCards();	// 스플릿카드비우기
			player.resetBetChip();	// 배팅칩
			player.resetInsurance();	// 인슈어런스칩
			player.resetSplitChip();	// 스플릿칩
		}
		cData.state = TodoState.PromBET;	// =>> 배팅으로
	}
	private void promptBet() {
		ArrayList<PlayerDataDto> players = cData.getPlayers();
		
		for(PlayerDataDto player : players) {
			cData.setBetPlayer(player); // 현재 배팅플레이어
			boolean isBetting = true;
			while(isBetting) {
				paint(); // 배팅플레이어 기준 출력.
				// 해당플레이어 배팅 입력.
				try {
					String scanBet = scan.nextLine();
					if(scanBet.equalsIgnoreCase("")
							&& player.getBetting() == 0) {
						if(!player.reBetting()) {
							cData.betErrMsg = "칩 부족";
						}
					}else if(scanBet.equalsIgnoreCase("") 
							|| scanBet.equalsIgnoreCase("y")
							|| scanBet.equalsIgnoreCase("Y")
							|| scanBet.equalsIgnoreCase("ㅛ")) {
						// bet이 0인지 확인
						if(player.getBetting() == 0) {
							cData.betErrMsg = "배팅해주세요";
						}else {
							player.setPreBet();		// 배팅값을 이전배팅값으로 보존.
							cData.setBetPlayer(null);
							isBetting = false; // 배팅금이 잇으면 해당플레이어 배팅종료.
						}
						
					}else if(scanBet.equalsIgnoreCase("n")
						|| scanBet.equalsIgnoreCase("N")
						|| scanBet.equalsIgnoreCase("ㅜ")) {
						player.resetBetChip();
					}else {
						int intBetNum = Integer.valueOf(scanBet); 
						for(int i = 0; i < cData.getTypeChips().length; i++) {
							if(intBetNum == i+1) {
								int bet =cData.getTypeChips()[i];
								if(bet > player.getChip()) {
									cData.betErrMsg = "칩 부족";
									// 잔고부족
								}else if(bet + player.getBetting() > cData.getMaxBet()) {
									cData.betErrMsg = "최대배팅넘음1000";
									// 배팅 맥시멈 넘음
								}else {
									// 배팅처리.
									player.betting(bet);
									break;
								}
							}
						}
					}
				}catch (Exception e) {}
			}
		}
//		System.out.println("종료.");
		cData.state = TodoState.ProcCARD2DEALING;	// =>> 2장 딜링으로
	}
	
	private void processCard2Dealing() {
		DeckDto deck = cData.getDeck();
		int delay = 1000;
		try {
			if(cData.getDealer().size() < 2) {
				paint();
				for(int i = 0; i < 2; i++) {
					for(PlayerDataDto player : cData.getPlayers()) {
						player.addCard(deck.getCard());
						Thread.sleep(delay);
						paint();
					}
					cData.getDealer().addCard(deck.getCard());
					Thread.sleep(delay);
					paint();
				}				
			}else {
				// TODO : 2장 있다고 생각하고 다른 상태로 바꿔야됨.
			}
		} catch (Exception e) {	}
		cData.state = TodoState.CheckALLPLAYERBJ;	// =>> 플레이어들 블랙잭 확인으로
	}
	
	private void checkDealerA10() {
		// 딜러 A 와 10 확인
		if(cData.getDealer().isAce()) {
			// 인슈어런스로
			cData.state = TodoState.PromINSURANCE;	// =>> A : 인슈어런스 입력부.
		}else if(cData.getDealer().is10()) {
			// 딜러 블랙잭 확인
			cData.state = TodoState.CheckDEALERBJ;	// =>> 10 : 딜러 블랙잭 확인.
		}else {
			// 일반 게임플레이로.
			cData.state = TodoState.PromPlay;
		}
	}
	
	private void promptInsurance() {
		// 해당플레이어가 블랙잭인지 확인. (인슈어런스 필요없음)
		paint();
		for(PlayerDataDto player : cData.getPlayers()) {
			if(!player.isBlackJack()) {
				cData.setInsurancePlayer(player);
				paint();
				// 입력 받는다.
				while(true) {
					try {
						paint();
						String scanLine = scan.nextLine();
						if(scanLine.equalsIgnoreCase("") 
								|| scanLine.equalsIgnoreCase("y")
								|| scanLine.equalsIgnoreCase("Y")
								|| scanLine.equalsIgnoreCase("ㅛ")) {
							if(player.setInsurance()) {
								// 인슈어런스 시도 완료.
								break;
							}else {
								// 인슈어런스 실패 ( 칩이 부족함)
								cData.insuranceErrMsg = "칩 부족";
							}						
						}else if(scanLine.equalsIgnoreCase("n")
								|| scanLine.equalsIgnoreCase("N")
								|| scanLine.equalsIgnoreCase("ㅜ")) {
							// 인슈어런스 시도 안함.
							break;
						}else {
							cData.insuranceErrMsg = "Y/N 중 입력";
						}
					}catch (Exception e) {}
				}
			}else {
				// 플레이어가 블랙잭이면 패스
			}
		}
		
		cData.state = TodoState.CheckDEALERBJ;	// =>> 딜러 블랙잭 확인.
	}
	private void checkDealerBJ() {
		boolean isAllBJ = true;
		for(PlayerDataDto player : cData.getPlayers()) {
			if(!player.isBlackJack()) {
				isAllBJ = false;
				break;
			}
		}
		if(cData.getDealer().isBlackJack()) {
			// 딜러가 블랙잭이면
			for(PlayerDataDto player : cData.getPlayers()) {
				if(player.isInsurance()) {
					// 인슈어런스면 성공 처리
				}else {
					// 인슈어런스 아니면 패배처리.
				}
				// 화면 출력.
			}
		}else {
			// 블랙잭이 아니면 게임 진행.
			cData.state = TodoState.PromPlay;
		}
			// 인슈어런스 성공으로 부분 정산.
		// 플레이어 블랙잭이 아니면 전부 패배.
		cData.state = TodoState.PROCDEALER;
		// 딜러가 블랙잭이 아니면
			// 인슈어런스 실패로 부분 정산. 
		cData.state = TodoState.PLAY;
	}
	
	
	
	private void processDealer() {
		
	}
	
	private void processEnding() {
		// 1. 딜러가 블랙잭		선처리.
		// 2. 딜러가 블랙잭 x
		// 3. 점수계산
		// 4. 승패계산
		
		// 다계산하고...
	}
	
	private void processPlay() {
		// 스플릿 여부
		// 첫턴 여부
		
		// 스플릿, 더블, 힛, 스탠드 [입력]
		
		// 스플릿 처리.
		
		// 일반 처리
		// 힛, 스탠드 [입력]
		
		// 스플릿 확인후 처리
		// 힛, 스탠드 [입력]
		
		// 턴 종료.
	}
	
	// 플레이어 배팅
	// 2장드로우
	
	// 플레이어 bj 확인
	// 딜러 A 확인
		// 플레이어 인슈어런스 입력
	// 딜러 10 확인
	
	// 딜러 bj 확인.
	
//	private Queue<Card> cardDeck;		// 카드덱
//	private Dealer dealer;		// 딜러
//	private List<Player> players;	// 플레이어들
//	
//	private Queue<Player> betWattingQueue;	// 배팅대기 player queue
//	private List<Entry> firstDealsArry;	// 처음2장 딜링. queue
//	private Queue<Player> playWattingQueue;	// 플레이대기 entry queue
//	private Queue<Entry> completeWattingQueue;	// 완료대기 player queue
//	
//	private CommDataModule cData;
//	private CardDeckModule cardDeckModule;
//	private ViewService viewService;
//	
//	private Scanner scan;
//	// bet	(완료)
//	// firstDeals (완료)
//	// checkBlackjack
//	// checkDealerCard
//	// insuranceCheck
//	// blackJackCheck
//	
//	// play
//	// createResult
//	
//	// resultCheck
//	// settle
//	// reset
//	
////	public GameServiceImplV1() {
////		this.cardDeckModule = new CardDeckModuleImplV1();
////		this.cData = new CommDataModuleImplV1();
////	}
//	
//	public GameServiceImplV1(CardDeckModule cardDeckModule, CommDataModule commDataModule, ViewService viewService) {
//		this.viewService = viewService;
//		this.cardDeckModule = cardDeckModule;
//		this.cData = commDataModule;
//	}
//	private void initQueues() {
//		betWattingQueue = new LinkedList<>();
//		firstDealsArry = new LinkedList<>();
//		playWattingQueue = new LinkedList<>();
//		completeWattingQueue = new LinkedList<>();
//	}
//	private void linkData() {
//		dealer = cData.getDealer();
//		players = cData.getPlayers();
//		cardDeck = cData.getCardDeck();
//		cardDeckModule.setCardDeck(cardDeck);
//	}
//	
//	private void setData() {
//		players.add(new Player("Player1"));
//		cardDeckModule.addCardDeck();
//		cData.setGameState(GameState.READY);
//	}
//
//	@Override
//	public void init() {
//		scan = new Scanner(System.in);
//		initQueues();
//		linkData();
//		setData();
//	}
//	
//	private void setBetQueue() {
//		betWattingQueue.clear();
//		betWattingQueue.addAll(players);
//		cData.setGameState(GameState.WILLBET);
//	}
//	
//	@Override
//	public void start() {
//		while(!cData.getGameState().equals(GameState.END)) {
//			switch (cData.getGameState()) {
//			case READY:
//				System.out.println(GameState.READY);	
//				setBetQueue();
//				break;
//			case WILLBET:
//				System.out.println(GameState.WILLBET);
//				doBetting();
//				break;
//			case WILL2DEALING:
//				System.out.println(GameState.WILL2DEALING);
//				do2Dealing();
//				break;
//			case CHECKDEALEACE:
//				System.out.println(GameState.CHECKDEALEACE);
//				doCheckDealerAce();
//				break;
//			case WILLINSURANCE:
//				System.out.println(GameState.WILLINSURANCE);
//				doInsurance();
//				break;
//			case WILLPLAY:
//				System.out.println(GameState.WILLPLAY);
//				doPlay();
//				break;
//			default:
//				break;
//			}			
//		}
//		System.out.println(GameState.END);
//		
//		// 배팅하는 상태
//		// 딜링하는 상태
//		// 인슈어런스확인하는 상태
//		// 플레이를하는 상태
//		// 결과정리하는 상태
//		// 초기화하는 상태
//	}
//	
//	private void doPlay() {
//		// TODO Auto-generated method stub
//		
//	}
//	private void doInsurance() {
//		int insurance = 0;
//		for(int i=0; i < players.size(); i++) {
//			while(true) {
//				viewService.paint();
//				try {
//					String sInput = scan.nextLine();
//					if(sInput.equalsIgnoreCase("Y") ||
//						sInput.equalsIgnoreCase("1")) {
//						// y라고 할때.
//						break;
//					}else if(sInput.equalsIgnoreCase("N") ||
//							sInput.equalsIgnoreCase("2")) {
//						// n라고 할때.
//						break;
//					}else {
//						// 입력값이 벗어나버린것이에요.
//					}
//				} catch (Exception e) {
//					System.out.println("애러가 나버린 것이에요.");
//				}
//			}
//			
//			// 인슈어런스를 할껀지 묻는다.
//			// 한다고 하면 account가 있는지 확인.
//			// account -
//			// 인슈어런스 true;
//			// insurance ++;
//		}
//		// 딜러 두번째 카드가 10인지 확인.
//		// 10이면 딜러 blackJack이 된다.
//		// 인슈어런스 성공한 유져만 인슈어런스 금액 돌려줌.
//		// player도 blackJack인지 확인.
//		// 정산.
//		// 게임 종료.
//		
//		
//		// 10이 아니면
//		// 인슈어런스 실패.
//		// player 게임 진행.
//		if(insurance >= 1) {
//			for(int i=0; i < players.size(); i++) {
//				
//			}
//		}
//		
//	}
//	private void doCheckDealerAce() {
////		if(dealer.getCard().get(0).getDenomination() == 1) {
////			cData.setGameState(GameState.WILLINSURANCE);
////		}else if(dealer.getCard().get(0).getDenomination() >= 10) {
////			cData.setGameState(GameState.CHECKDEALERBLACKJACK);
////		}else {
////			playWattingQueue.addAll(players);
////			cData.setGameState(GameState.WILLPLAY);
////		}
//	}
//	private void do2Dealing() {
//		for(int i=0; i < 2; i++) {
//			for(int j=0; j < firstDealsArry.size(); j++) {
//				firstDealsArry.get(j).addCard(cardDeck.poll());
//				viewService.paint();
//			}
//		}
//		firstDealsArry.clear();
//		cData.setGameState(GameState.END);
//	}
//	private void doBetting() {
//		while(!betWattingQueue.isEmpty()) {
//			Player player = betWattingQueue.poll();
//			cData.setCurrentPlayer(player);
//			
//			int money = 0;
//			// 배팅 입력.
//			while(true) {
//				viewService.paint();
//				try {
//					String sBet = scan.nextLine();
//					if(sBet == "" && player.getBasicBet() > 0) {
//						break;
//					}
//					int bet = Integer.valueOf(sBet);
//					if(bet >= 1 && bet < BetConfig.BET.length) {
//						money = BetConfig.BET[bet];
//						if(player.getBasicBet() + money > 1000) {
//							// 기회 또줌. 최대금액은 1000까지
//						}else {
//							if(player.betting(money)) {
//								// 기회 또줌. 잔고가 부족해서 실패
//							}else {
//								// 성공
//							}
//						}
//					}else if(bet == 0) {
//						player.cancelBet();
//					}else {
//						// 기회 또줌 0 ~ 7 사이의 숫자가 아닐때
//						System.out.println("애러가 나버린 것이에요.");
//					}				
//				} catch (Exception e) {
//					// 기회 또줌 숫자가 아닐때
//					System.out.println("애러가 나버린 것이에요.");
//				}
//			}
//			firstDealsArry.add(player);
//		}
//		firstDealsArry.add(dealer);
//		cData.setGameState(GameState.WILL2DEALING);
//	}
	
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
