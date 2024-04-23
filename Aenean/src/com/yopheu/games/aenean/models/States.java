package com.yopheu.games.aenean.models;

import com.yopheu.games.aenean.config.Chip;
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
	// 출력용 진행메뉴 리스트
	public PlayChoose[] palyMenu = new PlayChoose[0];
	
	public void setBetMenu(Chip[] chipMenu, int chipBet) {
		this.chipMenu = chipMenu;
		this.chipBet = chipBet;		
	}
	
}
