package com.yopheu.games.aenean.service.impl;

import java.util.Scanner;

import com.yopheu.games.aenean.callback.GameServiceCallback;
import com.yopheu.games.aenean.config.Chip;
import com.yopheu.games.aenean.config.Confirm;
import com.yopheu.games.aenean.config.ExceptionState;
import com.yopheu.games.aenean.config.PlayChoose;
import com.yopheu.games.aenean.models.States;
import com.yopheu.games.exceptions.ScanErrException;

public class ScanService {
	private Scanner scan;
	private GameServiceCallback callback;
	private States states;
	public ScanService(GameServiceCallback callback, States states) {
		this.scan = new Scanner(System.in);
		this.callback = callback;
		this.states = states;
	}
	
	// 입력받는 부분을 분리하여 처리한다.
	// 입력과 관련된. 예외처리, 애러, 입력자체의 반복처리를 분리해
	// 데이터 처리에 대한 흐름을 집중할 수 잇게 해준다.
	// GameService와 ScanService의 메소드가 1쌍을 이룰수 잇게 한다.
	// 2개 이상의 입력메소드가 잇을경우 연관된 명칭으로 해준다.
	
	public Chip scanPlayerBets() {
		Chip result = Chip.NONE;
		
		// 정상 입력값이면 탈출
		// 입력 예외만 반복으로 입력시도.
		while(true) {
			states.exceptionState = ExceptionState.NONE;
			String strChoose = scan.nextLine();
			
			if(strChoose.equalsIgnoreCase("")) {
				result = Chip.ENTER;
				break;
			}else {
				try {
					int intChoose = Integer.valueOf(strChoose);
					if(intChoose >= 0 && intChoose < states.chipMenu.length) {
						result = states.chipMenu[intChoose];
						break;
					}else {
						states.exceptionState = ExceptionState.SCANERR;
						callback.performPaint();
						continue;
					}
				} catch (Exception e) {
					states.exceptionState = ExceptionState.SCANERR;
					callback.performPaint();
					continue;
				}
			}
		}
		
		return result;
	}
//	public void printScanErr() {
//		System.out.println("잘못된 값을 입력했습니다. 안내된 값을 입력해주세요.");
//	}
//	public void printLowChips() {
//		System.out.println("잔액이 부족합니다.");
//	}
//	public void printBetMaxOver() {
//		System.out.println("배팅 최대금액은 1000 입니다.");
//	}
//	public void printBetMsg() {
//		System.out.println("배팅이 되지 않았습니다. 배팅해주세요.");
//	}
	
	public Confirm scanInsurance() {
		Confirm result = Confirm.NONE;
		while(true) {
			states.exceptionState = ExceptionState.NONE;
			String strChoose = scan.nextLine();
			try {
				int intChoose = Integer.valueOf(strChoose);
				if(intChoose > 0 && intChoose < states.confirmMenu.length) {
					result = states.confirmMenu[intChoose];
					break;
				}else {
					states.exceptionState = ExceptionState.SCANERR;
					callback.performPaint();
					continue;
				}
			} catch (Exception e) {
				states.exceptionState = ExceptionState.SCANERR;
				callback.performPaint();
				continue;
			}
		}
		return result;
	}
	
	public PlayChoose scanPlayerTurn() {
		PlayChoose result = PlayChoose.NONE;
		while(true) {
			states.exceptionState = ExceptionState.NONE;
			String strChoose = scan.nextLine();
			try {
				int intChoose = Integer.valueOf(strChoose);
				if(intChoose > 0 && intChoose < states.playMenu.length
						&& states.playMenu[intChoose] != PlayChoose.NONE) {
					result = states.playMenu[intChoose];
					break;
				}else {
					states.exceptionState = ExceptionState.SCANERR;
					callback.performPaint();
					continue;
				}
			} catch (Exception e) {
				states.exceptionState = ExceptionState.SCANERR;
				callback.performPaint();
				continue;
			}
		}
		return result;
	}
	
	public boolean scanSplit() throws ScanErrException{
		boolean result = false;
		System.out.println("스플릿 하시겠습니까? (1[Y] 2[N])");
		System.out.print("입력: ");
		String strChoose = scan.nextLine();
		if(strChoose.equalsIgnoreCase("1")) {
			result = true;
		}else if(strChoose.equalsIgnoreCase("2")) {
			result = false;
		}else {
			throw new ScanErrException();
		}
		return result;
	}
}
