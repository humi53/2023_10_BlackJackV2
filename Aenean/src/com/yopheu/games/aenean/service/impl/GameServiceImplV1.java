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
	// 초기화상태
	// 2장딜링
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
}
