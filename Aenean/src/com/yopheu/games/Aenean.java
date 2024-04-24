package com.yopheu.games;

import com.yopheu.games.aenean.service.GameService;
import com.yopheu.games.aenean.service.impl.GameServiceImplV1;

public class Aenean {
	public static void main(String[] args) {
		System.out.println("프로그램의 시작점.");
		/* 
		 * 1. 블랙잭
		 * 2. 레벨 업.
		 * 3. 계좌 입금.
		 * 4. 시스템 설정.
		 */
		
		GameService service = new GameServiceImplV1();
		service.start();
	}
}
