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

	}
	
	public void receivePlayerBets() {
		//		GameState.PLAYERBETTING; 이미 상태로 들어와 있음.
		states.gameState = GameState.PLAYERBETTING;	// 임시로 설정.
		
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
			while(true) {
				// 더블다운 가능 확인.
				
				// 힛, 스탠드, (더블) 입력 출력부
				// 힛, 스탠드, (더블) 입력.
				
				// 상태 확인.
				break;
			}
		}
	}
	
	private void receiveSplitDecision() {
		
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
