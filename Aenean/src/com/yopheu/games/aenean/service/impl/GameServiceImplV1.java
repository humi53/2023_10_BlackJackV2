package com.yopheu.games.aenean.service.impl;

import com.yopheu.aenean.config.GameState;
import com.yopheu.games.aenean.config.Chip;
import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.models.PlayerDto;
import com.yopheu.games.aenean.service.GameService;
import com.yopheu.games.aenean.service.UIService;

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
	}
	private void dealInitialCards() {
		// 모두에게 2장씩 딜링을 한다.
		
		// A카드인지 확인. (인슈어런스 가능 확인.)
			// y 인슈어런스 입력부로 ->
		// 10카드인지 확인.
			// 블랙잭인지 확인부로 ->
		// n 플레이어 진행으로 -> 
	}
	
	private void getPlayerInsurance() {
		// 인슈어런스 선택을 받는다.
		// 딜러가 블랙잭인지 확인한다.
			// y 게임 종료와 칩 계산 부분으로 ->
			// 
	}
	// 인슈어런스 상태확인
	// 인슈어런스 시도확인
	// 딜러 블랙잭 확인		// 블랙잭이면 결과계산
	// 플레이어 진행
		// 스플릿 가능확인
		// 스플릿 시도확인
		// 플레이어 남은 진행
			// 스플릿 진행
	// 딜러 진행
	// 결과 계산
	// 초기화
	
	private void paint() {
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ui.paint();
	}
}
