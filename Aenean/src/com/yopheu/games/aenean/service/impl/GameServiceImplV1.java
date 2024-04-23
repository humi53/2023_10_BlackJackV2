package com.yopheu.games.aenean.service.impl;

import com.yopheu.games.aenean.config.GameState;
import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.callback.GameServiceCallback;
import com.yopheu.games.aenean.config.Chip;
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
		for(int i = 0; i < 2; i++) {
			deal(playerDto);
			deal(dealerDto);
		}
		
		if(dealerDto.isAce()) {
			states.gameState = GameState.INSURANCE;
		}else if(dealerDto.isTenValue()){
			if(dealerDto.isBlackJack()) {
				dealerDto.setOpen();
				paint();
				states.gameState = GameState.FINISH;
			}else {
				states.gameState = GameState.PLAYERHASBLACKJACK;
			}
		}else {
			states.gameState = GameState.PLAYERHASBLACKJACK;
		}		
	}
	
	private void getPlayerInsurance() {
		paint();
		while(true) {
			try {
				
				if(sc.scanInsurance()) {
					if(!playerDto.setInsurance()) {
						paint();
						sc.printLowChips();
						continue;
					}
				}
				
				if(dealerDto.isBlackJack()) {
					dealerDto.setOpen();
					paint();
					states.gameState = GameState.FINISH;
				}else {
					states.gameState = GameState.PLAYERHASBLACKJACK;
				}
				break;
			} catch (ScanErrException e) {
				paint();
				sc.printScanErr();
			}			
		}
	}
	
	private void playerHasBlackjack() {
		if(playerDto.isBlackJack()) {
			playerDto.setResultState(PlayResultState.BLACKJACK);
			paint();
			states.gameState = GameState.FINISH;
		}else {
			states.gameState = GameState.PLAYERTURN;
		}
	}
	
	private void playerTurn() {
		if(playerDto.isSplitAllowed()) {
			// 스플릿 시도 입력.
			
		}
		
		// 힛, 스탠드 입력.
		
		// 스플릿 확인 (2장의 카드의 값이 같은지)
		// 스플릿 시도 입력.
		
		// 힛, 스탠드 입력.
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
		paint();
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
