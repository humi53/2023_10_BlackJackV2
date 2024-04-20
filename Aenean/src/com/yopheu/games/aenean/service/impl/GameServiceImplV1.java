package com.yopheu.games.aenean.service.impl;

import com.yopheu.games.aenean.config.GameState;
import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.config.Chip;
import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.models.DealerDto;
import com.yopheu.games.aenean.models.DeckDto;
import com.yopheu.games.aenean.models.ICardHand;
import com.yopheu.games.aenean.models.PlayerDto;
import com.yopheu.games.aenean.service.GameService;
import com.yopheu.games.aenean.service.UIService;
import com.yopheu.games.exceptions.ScanErrException;

public class GameServiceImplV1 implements GameService {

	private CommDataWrapper cData; // 공용데이터 뭉치
	private UIService ui; // UIService
	private ScanService sc;	// 입력안내메시지 + scanner
	private GameState gState;
	
	public GameServiceImplV1() {
		cData = new CommDataWrapper();
		ui = new CUIServiceImplV1(cData);
		sc = new ScanService();
		gState = GameState.READY;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
	
	// 초기화상태
	private void receivePlayerBets() {
		PlayerDto playerDto = cData.getPlayerDto();
		Chip chip = Chip.NONE;
		if (playerDto.repeatPreviousBet()) {
			chip = sc.scanPlayerBets(playerDto.getBetChip());
		}else {
			sc.printLowChips();
			chip = sc.scanPlayerBets(0);
			System.out.println(chip);
		}
		while(true) {
			if(chip == Chip.CMAX || chip == Chip.ENTER) {				
				if(playerDto.getBetChip() <= 0) {
					paint();
					sc.printBetMsg();
					chip = Chip.NONE;
				}else {
					break;	// 탈출. 배팅완료.
				}
			}else if(chip == Chip.ERR) {
				paint();
				sc.printScanErr();
				chip = sc.scanPlayerBets(playerDto.getBetChip());
			}else if(chip == Chip.CENCEL) {
				// 배팅을 취소
				playerDto.cancelBetting();
				chip = Chip.NONE;
			}else {
				// max가 넘었는지 확인.
				int tempBet = playerDto.getBetChip() + chip.value();
				if(tempBet > Chip.CMAX.value()) {
					paint();
					sc.printBetMaxOver();
					chip = sc.scanPlayerBets(playerDto.getBetChip());
				}else {
					boolean betSucess = playerDto.betting(chip.value());
					paint();
					if(!betSucess) {
						sc.printLowChips();
					}
					chip = sc.scanPlayerBets(playerDto.getBetChip());
				}				
			}
		}
		System.out.println("완료");
		gState = GameState.DEALINITCARD;
	}
	private void dealInitialCards() {
		PlayerDto playerDto = cData.getPlayerDto();
		DealerDto dealerDto = cData.getDealerDto();
		for(int i = 0; i < 2; i++) {
			deal(playerDto);
			deal(dealerDto);
		}
		
		if(dealerDto.isAce()) {
			gState = GameState.INSURANCE;
		}else if(dealerDto.isTenValue()){
			if(dealerDto.isBlackJack()) {
				dealerDto.setOpen();
				paint();
				gState = GameState.FINISH;
			}else {
				gState = GameState.PLAYERTURN;
			}
		}else {
			gState = GameState.PLAYERTURN;
		}		
	}
	
	private void getPlayerInsurance() {
		DealerDto dealerDto = cData.getDealerDto();
		PlayerDto playerDto = cData.getPlayerDto();
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
					gState = GameState.FINISH;
				}else {
					gState = GameState.PLAYERTURN;
				}
				break;
			} catch (ScanErrException e) {
				paint();
				sc.printScanErr();
			}			
		}
	}
	
	private void playerTurn() {
		DealerDto dealerDto = cData.getDealerDto();
		PlayerDto playerDto = cData.getPlayerDto();
		// 블랙잭 확인
		if(playerDto.isBlackJack()) {
			playerDto.setResultState(PlayResultState.BLACKJACK);
			paint();
			gState = GameState.FINISH;
		}else if(playerDto.isSplitAllowed()) {
			// 스플릿 시도 입력.
		}
		
		// 힛, 스탠드 입력.
		
		// 스플릿 확인 (2장의 카드의 값이 같은지)
		// 스플릿 시도 입력.
		
		// 힛, 스탠드 입력.
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
