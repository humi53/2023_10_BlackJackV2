package com.yopheu.aenean.service.impl;

import java.util.ArrayList;
import java.util.Queue;

import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.entry.Dealer;
import com.yopheu.aenean.models.entry.Entry;
import com.yopheu.aenean.models.entry.Player;
import com.yopheu.aenean.service.GameService;

public class GameServiceImplV1 implements GameService {
	private ArrayList<Card> arrCardDeck = null;		// 카드덱
	private Dealer dealer = null;		// 딜러
	private Player player = null;		// 플레이어
	private Queue<Entry> betWattingQueue = null;	// 배팅대기 player stack
	private Queue<Entry> playWattingQueue = null;	// 플레이대기 entry stack
	private Queue<Entry> completeWattingQueue = null;	// 완료대기 player queue
	
	// bet
	// firstDeals
	// blackJackCheck
	// insuranceCheck
	
	// play
	// createResult
	
	// resultCheck
	// settle
	// reset
	
	public GameServiceImplV1() {
		
	}
	
	private void init() {
		
	}
	
	// 카드덱을 준비한다.
	// 카드리스트를 만든다.
	// 카드를 섞는다.
	// 카드를 카드덱(리스트)에 집어넣는다.
	
	
	// 1. 베팅을 한다
	
	// 2. 카드를 나눠준다
	
	// 3. 인슈어런스 체크
	// 딜러카드 확인 (블랙잭이면 바로 승패 결정)
	
	// 3.5. 플레이어가 블랙잭.
	
	// 4. 플레이를 한다.
	// 4-1. 더블다운
	// 4-2. 스플릿
	// 4. 결과를 만든다
	
	// 4.5. 딜러도 플레이
	// 4. 딜러 결과 만든다
	
	// 5. 결과로 승패확인
	// 각 플레이어의 상태에 따라 돈 확인
}
