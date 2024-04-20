package com.yopheu.games.aenean.service.impl;

import java.util.Scanner;

import com.yopheu.games.aenean.config.Chip;
import com.yopheu.games.exceptions.ScanErrException;

public class ScanService {
	private Scanner scan;
	public ScanService() {
		this.scan = new Scanner(System.in);
	}
	
	// 입력받는 부분을 분리하여 처리한다.
	// 입력과 관련된. 예외처리, 애러, 입력자체의 반복처리를 분리해
	// 데이터 처리에 대한 흐름을 집중할 수 잇게 해준다.
	// GameService와 ScanService의 메소드가 1쌍을 이룰수 잇게 한다.
	// 2개 이상의 입력메소드가 잇을경우 연관된 명칭으로 해준다.
	
	public Chip scanPlayerBets(int bet) {
		Chip[] type = new Chip[] {Chip.CENCEL, Chip.C20, Chip.C40, Chip.C100, Chip.C200, Chip.C400, Chip.C1000};
		Chip result = Chip.NONE;
		for(int i = 1; i < type.length; i++) {
			System.out.print(i + "[" + type[i].value() + "] ");
		}
		System.out.print(0 + "[취소] ");
		System.out.print("Enter" + "[완료] ");
		System.out.println("(now : " + bet + ")");
		
		// 칩종류
		// 예전선택
		System.out.print("입력: ");
		String strChoose = scan.nextLine();
		if(strChoose.equalsIgnoreCase("")) {
			result = Chip.ENTER;
		}else {
			try {
				int intChoose = Integer.valueOf(strChoose);
				if(intChoose >= 0 && intChoose <= type.length) {
					result = type[intChoose];
				}else {
					result = Chip.ERR;
				}
			} catch (Exception e) {
				result = Chip.ERR;
			}
		}
		return result;
	}
	public void printScanErr() {
		System.out.println("잘못된 값을 입력했습니다. 안내된 값을 입력해주세요.");
	}
	public void printLowChips() {
		System.out.println("잔액이 부족합니다.");
	}
	public void printBetMaxOver() {
		System.out.println("배팅 최대금액은 1000 입니다.");
	}
	public void printBetMsg() {
		System.out.println("배팅이 되지 않았습니다. 배팅해주세요.");
	}
	
	public boolean scanInsurance() throws ScanErrException{
		boolean result = false;
		System.out.println("인슈어런스 하시겠습니까? (1[Y] 2[N])");
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
