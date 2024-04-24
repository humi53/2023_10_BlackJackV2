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
import com.yopheu.games.aenean.models.PlayerDto;
import com.yopheu.games.aenean.models.PlayerSplitDto;
import com.yopheu.games.aenean.models.States;
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
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		states.gameState = GameState.PLAYERBETTING;
		if(states.gameState == GameState.PLAYERBETTING) {
			receivePlayerBets();
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
		System.out.println("완료");
		states.gameState = GameState.DEALINITCARD;
	}
	
	private void dealInitialCards() {
		paint();
		for(int i = 0; i < 2; i++) {
			deal(playerDto);
			paint();
			deal(dealerDto);
			paint();
		}
		
		playerHasBlackjack();
		
		if(dealerDto.isAce()) {
			states.gameState = GameState.INSURANCE;
		}else if(dealerDto.isTenValue()){
			states.gameState = GameState.DEALERHASBLACKJACK;
		}else {
			states.gameState = GameState.PLAYERTURN;
		}		
	}
	
	private void playerHasBlackjack() {
		if(playerDto.isBlackJack()) {
			playerDto.setResultState(PlayResultState.BLACKJACK);
			paint();
		}
	}
	
	private void getPlayerInsurance() {
		states.exceptionState = ExceptionState.NONE;	// 예외메시지.
		Confirm confirm = Confirm.NONE;
		
		while(true) {
			paint();
			states.exceptionState = ExceptionState.NONE;
			confirm = sc.scanInsurance();
			
			if(!playerDto.setInsurance()) {
				states.exceptionState = ExceptionState.LOWCHIPS;
				continue;
			}
			states.gameState = GameState.DEALERHASBLACKJACK;
		}	
	}
	
	private void dealerHasBlackjack() {
		if(dealerDto.isBlackJack()) {
			dealerDto.setOpen();
			paint();
			dealerDto.setResultState();
			paint();
			states.gameState = GameState.FINISH;
		}else {
			paint();
			states.gameState = GameState.PLAYERTURN;
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
				if(playerDto.isDobleDownAllowed()) {
					states.playMenu[3] = PlayChoose.DOUBLEDOWN;
				}
				if(playerDto.isSplitAllowed()) {
					states.playMenu[4] = PlayChoose.SPLIT;
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
							paint();
						}
						states.gameState = GameState.DEALERTURN;
						break;
					}
				}else if(choose == PlayChoose.SPLIT) {
					if(!playerDto.trySplit()) {
						states.exceptionState = ExceptionState.LOWCHIPS;
						continue;
					}else {
						// 스플릿객체 생성	= 1장씩 딜링.
						paint();
						deal(playerDto);
						paint();
						deal(playerDto.getSplitDto());
						paint();
					}
				}
				
				// 버스트 확인.
				if(playerDto.getHandsScore() > 21) {
					playerDto.setResultState(PlayResultState.BUST);
					paint();
					states.gameState = GameState.DEALERTURN;
					break;
				}
			}
			// 스플릿이 있는지 확인.
			if(playerDto.isSplit()) {
				states.playMenu = new PlayChoose[] {PlayChoose.NONE,PlayChoose.HIT,PlayChoose.STAND, PlayChoose.NONE, PlayChoose.NONE};
				states.exceptionState = ExceptionState.NONE;
				choose = PlayChoose.NONE;
				PlayerSplitDto splitDto = playerDto.getSplitDto();
				while(true) {
					// 더블다운 가능 확인.
					if(playerDto.isDobleDownAllowed()) {
						states.playMenu[3] = PlayChoose.DOUBLEDOWN;
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
							deal(playerDto);
							paint();
							if(splitDto.getHandsScore() > 21) {
								splitDto.setResultState(PlayResultState.BUST);
								paint();
							}
							states.gameState = GameState.DEALERTURN;
							break;
						}
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
		while(true) {
			// 딜
			deal(dealerDto);
			paint();
			if(dealerDto.getHandsScore() >= 17 
					|| dealerDto.setResultState() == PlayResultState.BUST) {
				paint();
				states.gameState = GameState.FINISH;
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
		paint();
		
		// 인슈어런스 정산
		if(playerDto.isInsurance()) {
			if(dealerDto.getResultState() == PlayResultState.BLACKJACK) {
				playerDto.insuranceWon();
			}
		}
		
		// 칩계산
		PlayResultState playResultState = playerDto.getResultState();
		if(playResultState == PlayResultState.BLACKJACK) {
			playerDto.abjectChips(playerDto.getBetChip() * 3);
		}else if(playResultState == PlayResultState.BUST) {
			playerDto.abjectChips(0);
		}else if(playResultState == PlayResultState.WIN) {
			playerDto.abjectChips(playerDto.getBetChip() * 2);
		}else if(playResultState == PlayResultState.LOSS) {
			playerDto.abjectChips(0);
		}else if(playResultState == PlayResultState.PUSH) {
			playerDto.abjectChips(playerDto.getBetChip());
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
