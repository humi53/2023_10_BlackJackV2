package com.yopheu.games.aenean.service.impl;

import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.service.GameService;
import com.yopheu.games.aenean.service.UIService;

public class GameServiceImplV1 implements GameService {

	private CommDataWrapper cData; // 공용데이터 뭉치
	private UIService ui; // UIService
	private ScanService sc;	// 입력안내메시지 + scanner
	
	public GameServiceImplV1() {
		cData = new CommDataWrapper();
		ui = new CUIServiceImplV1(cData);
		sc = new ScanService();
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
}
