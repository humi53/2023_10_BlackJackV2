package com.yopheu.games.aenean.service.impl;

import java.util.Scanner;

import com.yopheu.games.aenean.callback.GameServiceCallback;
import com.yopheu.games.aenean.config.Chip;
import com.yopheu.games.aenean.config.Confirm;
import com.yopheu.games.aenean.config.ExceptionState;
import com.yopheu.games.aenean.config.PlayChoose;
import com.yopheu.games.aenean.models.States;

public class ScanService {
	private Scanner scan;
	private GameServiceCallback callback;
	private States states;
	public ScanService(GameServiceCallback callback, States states) {
		this.scan = new Scanner(System.in);
		this.callback = callback;
		this.states = states;
	}
	
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
	public void pressAndKey() {
		String non = scan.nextLine();
	}
}
