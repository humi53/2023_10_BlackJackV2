package com.yopheu.games.aenean.service.impl;

import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.service.GameService;
import com.yopheu.games.aenean.service.UIService;

public class GameServiceImplV1 implements GameService {

	private CommDataWrapper cData; // 공용데이터 뭉치
	private UIService ui; // UIService
	
	public GameServiceImplV1() {
		cData = new CommDataWrapper();
		ui = new CUIServiceImplV1(cData);
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

}
