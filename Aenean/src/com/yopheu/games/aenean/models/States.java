package com.yopheu.games.aenean.models;

import com.yopheu.games.aenean.config.Chip;
import com.yopheu.games.aenean.config.Confirm;
import com.yopheu.games.aenean.config.ExceptionState;
import com.yopheu.games.aenean.config.GameState;
import com.yopheu.games.aenean.config.PlayChoose;

public class States {
	// 게임상태
	public GameState gameState = GameState.READY;
	// 입력 예외 상태
	public ExceptionState exceptionState = ExceptionState.NONE;
	// 출력용 벳메뉴리스트
	public Chip[] chipMenu = new Chip[0];
	public int chipBet = 0;
	public Confirm[] confirmMenu = new Confirm[] {Confirm.NONE, Confirm.YES, Confirm.NO};
	
	// 출력용 진행메뉴 리스트
	public PlayChoose[] playMenu = new PlayChoose[0];
	
	public void setBetMenu(Chip[] chipMenu, int chipBet) {
		this.chipMenu = chipMenu;
		this.chipBet = chipBet;		
	}
	
	public void reset() {
		gameState = GameState.READY;
		exceptionState = ExceptionState.NONE;
		chipBet = 0;
	}
	
}
