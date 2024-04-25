package com.yopheu.games.aenean.service.impl;

import com.yopheu.games.aenean.config.GameState;
import com.yopheu.games.aenean.config.PlayChoose;
import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.callback.GameServiceCallback;
import com.yopheu.games.aenean.config.Chip;
import com.yopheu.games.aenean.config.Confirm;
import com.yopheu.games.aenean.config.ExceptionState;
import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.models.DealerDto;
import com.yopheu.games.aenean.models.DeckDto;
import com.yopheu.games.aenean.models.ICardHand;
import com.yopheu.games.aenean.models.IPlayerChip;
import com.yopheu.games.aenean.models.PlayerDto;
import com.yopheu.games.aenean.models.PlayerSplitDto;
import com.yopheu.games.aenean.models.ResultCalculator;
import com.yopheu.games.aenean.models.States;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;
import com.yopheu.games.aenean.models.card.Suit;
import com.yopheu.games.aenean.service.GameService;
import com.yopheu.games.aenean.service.UIService;
import com.yopheu.games.exceptions.ScanErrException;

public class GameServiceImplV1 implements GameService {

	private CommDataWrapper cData; // 공용데이터 뭉치
	private UIService ui; // UIService
	private ScanService sc;	// 입력안내메시지 + scanner
	private States states;
	
	private PlayerDto playerDto;
	private DealerDto dealerDto;
	
	private ResultCalculator resultCalculator;
	
	public GameServiceImplV1() {
		cData = new CommDataWrapper();
		ui = new CUIServiceImplV1(cData);
		
		playerDto = cData.getPlayerDto();
		dealerDto = cData.getDealerDto();
		
		states = cData.getStates();
		states.gameState = GameState.READY;
		
		sc = new ScanService(new GameServiceCallback() {
			@Override
			public void performPaint() {
				paint();
			}
		}, states);
		
		resultCalculator = new ResultCalculator();
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		states.gameState = GameState.PLAYERBETTING;
		while(true) {
			if(states.gameState == GameState.PLAYERBETTING) {
				receivePlayerBets();
			}else if(states.gameState == GameState.DEALINITCARD) {
				dealInitialCards();
			}else if(states.gameState == GameState.DEALERHASBLACKJACK) {
				dealerHasBlackjack();
			}else if(states.gameState == GameState.INSURANCE) {
				getPlayerInsurance();
			}else if(states.gameState == GameState.PLAYERTURN) {
				playerTurn();
			}else if(states.gameState == GameState.DEALERTURN) {
				dealerTurn();
			}else if(states.gameState == GameState.FINISH) {
				finish();
			}else {
				System.out.println("종료");
				break;
			}
		}

	}
	
	public void receivePlayerBets() {
		Chip[] type = new Chip[] {Chip.CENCEL, Chip.C20, Chip.C40, Chip.C100, Chip.C200, Chip.C400, Chip.C1000};
		states.chipMenu = type;
		states.chipBet = 0;		// 안내메시지 출력의 정보.
		states.exceptionState = ExceptionState.NONE;	// 예외메시지.
		Chip chip = Chip.NONE;

		if (playerDto.repeatPreviousBet()) {	// 이전값 자동배팅
			states.chipBet = playerDto.getBetChip();
		}else {	// 칩이 부족해 배팅값 0으로 설정.
			states.chipBet = 0;	// 없어도 되는데 명확성 확보.
			states.exceptionState = ExceptionState.LOWCHIPS;
		}
		
		while(true) {
			playerDto.setResultState(PlayResultState.ONGOING);
			paint(); 	// 출력부
			states.exceptionState = ExceptionState.NONE;
			chip = sc.scanPlayerBets();	// 입력부
			
			// 출력부 설정 & 입력값으로 인한 반복문 종료.
			if(chip == Chip.ENTER) {
				if(playerDto.getBetChip() == 0) {
					states.exceptionState = ExceptionState.BETZERO;
				}else {
					break;
				}
			}else if(chip == Chip.CMAX || chip == Chip.NONE) {
				// 의미없는 chip값이라 넘긴다. 
				continue;
			}else if(chip == Chip.CENCEL) {
				playerDto.cancelBetting();
				states.chipBet = 0;
				states.exceptionState = ExceptionState.BETCANCEL;
			}else {
				// max가 넘었는지 확인.
				int tempBet = playerDto.getBetChip() + chip.value();
				if(tempBet > Chip.CMAX.value()) {
					states.chipBet = playerDto.getBetChip();
					states.exceptionState = ExceptionState.BETMAXSOVER;
				}else {
					boolean betSucess = playerDto.betting(chip.value());
					if(!betSucess) {
						states.exceptionState = ExceptionState.LOWCHIPS;
					}
					states.chipBet = playerDto.getBetChip();
				}
			}
		}
		playerDto.setResultState(PlayResultState.NONE);
		states.gameState = GameState.DEALINITCARD;
	}
	
	private void dealInitialCards() {
		paint();
//		for(int i = 0; i < 2; i++) {
//			deal(playerDto);
//			paint();
//			deal(dealerDto);
//			paint();
//		}
		
		paint();
		playerDto.addCard(new Card(Suit.S, Denomination.N2));
		paint();
		dealerDto.addCard(new Card(Suit.D, Denomination.NA));
		paint();
		playerDto.addCard(new Card(Suit.H, Denomination.N2));
		paint();
		dealerDto.addCard(new Card(Suit.D, Denomination.N2));
		paint();
		
		// 플레이어가 블랙잭일때 플레이어 결과 상태 메시지 표시 필요.
		
		// 플레이어 블랙잭 (둘다 확인 완료)
			// ace & ten -> 딜러블랙잭 확인	
			// 그외 - > 피니쉬.
		// 그외
			// 인슈어런스 테스트
			// 딜러 10 & 블랙잭 테스트
			// 플레이어 턴으로 테스트
		
		if(playerHasBlackjack()) {
			if(dealerDto.isAce() || dealerDto.isTenValue()) {
				System.out.println("딜러블랙잭으로");
				states.gameState = GameState.DEALERHASBLACKJACK;
			}else {
				System.out.println("마무리로");
				states.gameState = GameState.FINISH;
			}
		}else {
			if(dealerDto.isAce()) {
				System.out.println("인슈어런스로");
				states.gameState = GameState.INSURANCE;
			}else if(dealerDto.isTenValue()){
				System.out.println("딜러블랙잭으로");
				states.gameState = GameState.DEALERHASBLACKJACK;
			}else {
				System.out.println("플레이어 턴으로");
				states.gameState = GameState.PLAYERTURN;
			}
		}
	}
	
	private boolean playerHasBlackjack() {
		boolean result = true;
		if(playerDto.isBlackJack()) {
			playerDto.setResultState(PlayResultState.BLACKJACK);
			paint();
		}else {
			result = false;
		}
		return result;
	}
	
	private void getPlayerInsurance() {
		states.exceptionState = ExceptionState.NONE;	// 예외메시지.
		Confirm confirm = Confirm.NONE;
		
		while(true) {
			playerDto.setResultState(PlayResultState.ONGOING);
			paint();
			states.exceptionState = ExceptionState.NONE;
			confirm = sc.scanInsurance();
			
			if(confirm == Confirm.YES) {
				if(!playerDto.setInsurance()) {
					states.exceptionState = ExceptionState.LOWCHIPS;
					continue;
				}
			}
			playerDto.setResultState(PlayResultState.NONE);
			states.gameState = GameState.DEALERHASBLACKJACK;
			break;
		}	
	}
	
	private void dealerHasBlackjack() {
		if(dealerDto.isBlackJack()) {
			dealerDto.setOpen();
			paint();
			dealerDto.setResultState();
			paint();
			System.out.println("마무리로");
			states.gameState = GameState.FINISH;
		}else {
			if(playerDto.getResultState() == PlayResultState.BLACKJACK) {
				paint();
				System.out.println("마무리로");
				states.gameState = GameState.FINISH;
			}else {
				paint();
				System.out.println("플레이어 턴으로");
				states.gameState = GameState.PLAYERTURN;
			}
		}
	}
	
	private void playerTurn() {
		if(playerDto.getResultState() == PlayResultState.BLACKJACK) {
			states.gameState = GameState.FINISH;
		}else {
			states.playMenu = new PlayChoose[] {PlayChoose.NONE,PlayChoose.HIT,PlayChoose.STAND, PlayChoose.NONE, PlayChoose.NONE};
			states.exceptionState = ExceptionState.NONE;
			PlayChoose choose = PlayChoose.NONE;
			while(true) {
				playerDto.setResultState(PlayResultState.ONGOING);
				if(playerDto.isDobleDownAllowed()) {
					states.playMenu[3] = PlayChoose.DOUBLEDOWN;
				}else {
					states.playMenu[3] = PlayChoose.NONE;
				}
				if(playerDto.isSplitAllowed()) {
					states.playMenu[4] = PlayChoose.SPLIT;
				}else {
					states.playMenu[4] = PlayChoose.NONE;
				}
				
				// 힛, 스탠드 , (더블), (스플릿) 입력 출력부.
				paint();
				states.exceptionState = ExceptionState.NONE;
				// 힛, 스탠드 , (더블), (스플릿) 입력.
				choose = sc.scanPlayerTurn();
				
				if(choose == PlayChoose.HIT) {
					// 힛 = 딜링
					deal(playerDto);
					paint();
				}else if(choose == PlayChoose.STAND) {
					// 스탠드 = break;
					playerDto.setResultState(PlayResultState.STAND);
					states.gameState = GameState.DEALERTURN;
					paint();
					break;
				}else if(choose == PlayChoose.DOUBLEDOWN) {
					// 더블 = 추가배팅, 딜링 후 break;
					if(!playerDto.doubleDown()) {
						states.exceptionState = ExceptionState.LOWCHIPS;
						continue;
					}else {
						deal(playerDto);
						paint();
						if(playerDto.getHandsScore() > 21) {
							playerDto.setResultState(PlayResultState.BUST);
						}else {
							playerDto.setResultState(PlayResultState.STAND);
						}
						paint();
						
						states.gameState = GameState.DEALERTURN;
						break;
					}
				}else if(choose == PlayChoose.SPLIT) {
					if(!playerDto.trySplit()) {
						states.exceptionState = ExceptionState.LOWCHIPS;
						continue;
					}else {
						// 스플릿객체 생성	= 1장씩 딜링.
						playerDto.setResultState(PlayResultState.NONE);
						paint();
						deal(playerDto);
						paint();
						deal(playerDto.getSplitDto());
						paint();
					}
				}
				
				// 21 이면 자동 Stand
				if(playerDto.getHandsScore() == 21) {
					playerDto.setResultState(PlayResultState.STAND);
					paint();
					states.gameState = GameState.DEALERTURN;
					break;
				}
				
				// 버스트 확인.
				if(playerDto.getHandsScore() > 21) {
					playerDto.setResultState(PlayResultState.BUST);
					paint();
					states.gameState = GameState.DEALERTURN;
					break;
				}
			}
//			playerDto.setResultState(PlayResultState.NONE);
			
			// 스플릿이 있는지 확인.
			if(playerDto.isSplit()) {
				states.gameState = GameState.PLAYERTURN;
				states.playMenu = new PlayChoose[] {PlayChoose.NONE,PlayChoose.HIT,PlayChoose.STAND, PlayChoose.NONE, PlayChoose.NONE};
				states.exceptionState = ExceptionState.NONE;
				choose = PlayChoose.NONE;
				PlayerSplitDto splitDto = playerDto.getSplitDto();
				while(true) {
					splitDto.setResultState(PlayResultState.ONGOING);
					// 더블다운 가능 확인.
					if(splitDto.isDobleDownAllowed()) {
						states.playMenu[3] = PlayChoose.DOUBLEDOWN;
					}else {
						states.playMenu[3] = PlayChoose.NONE;
					}
					
					// 힛, 스탠드, (더블) 입력 출력부
					paint();
					states.exceptionState = ExceptionState.NONE;
					// 힛, 스탠드, (더블) 입력.
					choose = sc.scanPlayerTurn();
					
					if(choose == PlayChoose.HIT) {
						// 힛 = 딜링
						deal(splitDto);
						paint();
					}else if(choose == PlayChoose.STAND) {
						// 스탠드 = break;
						splitDto.setResultState(PlayResultState.STAND);
						states.gameState = GameState.DEALERTURN;
						paint();
						break;
					}else if(choose == PlayChoose.DOUBLEDOWN) {
						// 더블 = 추가배팅, 딜링 후 break;
						if(!splitDto.doubleDown()) {
							states.exceptionState = ExceptionState.LOWCHIPS;
							continue;
						}else {
							deal(splitDto);
							paint();
							if(splitDto.getHandsScore() > 21) {
								splitDto.setResultState(PlayResultState.BUST);
								paint();
							}
							splitDto.setResultState(PlayResultState.STAND);
							states.gameState = GameState.DEALERTURN;
							break;
						}
					}
					
					// 21이면 자동 스탠드
					if(splitDto.getHandsScore() == 21) {
						splitDto.setResultState(PlayResultState.STAND);
						paint();
						states.gameState = GameState.DEALERTURN;
						break;
					}	
					
					// 버스트 확인.
					if(splitDto.getHandsScore() > 21) {
						splitDto.setResultState(PlayResultState.BUST);
						paint();
						states.gameState = GameState.DEALERTURN;
						break;
					}					
				}
			}
		}
	}
	
	private void dealerTurn(){
		dealerDto.setOpen();
		paint();
		while(true) {
			// 딜
			deal(dealerDto);
			paint();
			if(dealerDto.setResultState() == PlayResultState.BUST 
					|| dealerDto.getHandsScore() >= 17) {
				System.out.println(dealerDto.getResultState());
				paint();
				states.gameState = GameState.FINISH;
				System.out.println(dealerDto.getHandsScore());
				break;
			}
		}
	}
	
	private void finish() {
		// 딜러 블랙잭 확인
			// 플레이어 인슈어런스 확인
			// 플레이어 블랙잭 확인
		// 블랙잭 확인
		// 버스트 확인
		// 인슈어런스 확인
		// 점수 확인
		
		
		paint();
		
		// 인슈어런스 정산
		if(playerDto.isInsurance()) {
			if(dealerDto.getResultState() == PlayResultState.BLACKJACK) {
				playerDto.insuranceWon();
			}
		}
		
		calcResult(playerDto);
		paint();
		if(playerDto.isSplit()) {
			calcResult(playerDto.getSplitDto());
			paint();
		}
		
		// 칩계산
		resultCalculator.calcResultState(playerDto);
		if(playerDto.isSplit()) {
			resultCalculator.calcResultState(playerDto.getSplitDto());
		}
		paint();
		
		states.gameState = GameState.READY;
	}

	private void calcResult(IPlayerChip playerDto) {
		if(playerDto.getResultState() == PlayResultState.BLACKJACK) {
			// 무조건 이김 (2배)
		}else if(playerDto.getResultState() == PlayResultState.BUST) {
			// 무조건 패배
		}else if(playerDto.getResultState() == PlayResultState.STAND) {
			if(dealerDto.getResultState() == PlayResultState.BLACKJACK) {
				// 무조건 패배
				playerDto.setResultState(PlayResultState.LOSS);
			}else if(dealerDto.getResultState() == PlayResultState.BUST) {
				playerDto.setResultState(PlayResultState.WIN);
				// 무조건 승리
			}else if(dealerDto.getResultState() == PlayResultState.NONE) {
				// 점수 계산.
				int playerScore = playerDto.getHandsScore();
				int dealerScore = dealerDto.getHandsScore();
				if(playerScore > dealerScore) {
					// 우승
					playerDto.setResultState(PlayResultState.WIN);
				}else if(playerScore < dealerScore) {
					// 패배
					playerDto.setResultState(PlayResultState.LOSS);
				}else if(playerScore == dealerScore) {
					// 비김.
					playerDto.setResultState(PlayResultState.PUSH);
				}else {
					System.out.println("문제 발생.");
				}
			}
		}else {
			System.out.println("문제 발생.");
		}
	}
	// 플레이어 진행
		// 스플릿 가능확인
		// 스플릿 시도확인
		// 플레이어 남은 진행
			// 스플릿 진행
	// 딜러 진행
	// 결과 계산
	// 초기화
	
	private void deal(ICardHand dto) {
		dto.addCard(cData.getDeckDto().takeCard());	
	}
	
	private void paint() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ui.paint();
	}
}
