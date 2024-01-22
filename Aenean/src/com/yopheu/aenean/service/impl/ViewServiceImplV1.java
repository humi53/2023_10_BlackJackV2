package com.yopheu.aenean.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yopheu.aenean.config.BetConfig;
import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.config.StrColor;
import com.yopheu.aenean.config.TodoState;
import com.yopheu.aenean.models.CommDataWrapper;
import com.yopheu.aenean.models.card.Card;
import com.yopheu.aenean.service.ViewService;
import com.yopheu.aenean.view.ViewBoardFrame;
import com.yopheu.aenean.view.ViewCard;
import com.yopheu.aenean.view.ViewDataWrapper;
import com.yopheu.aenean.view.ViewStr;

public class ViewServiceImplV1 implements ViewService{
	
	private CommDataWrapper cData;
	private ViewDataWrapper viewData;
	
	public ViewServiceImplV1(CommDataWrapper commDataWrapper){
		cData = commDataWrapper;
		viewData = new ViewDataWrapper(cData);
	}
	
	private void linkData() {
	}
	
	@Override
	public void init() {
		linkData();
	}
	
	
	// GameState
	// 상태별 출력.
	
	// 단순 출력이 있다.
	//		입력안내 출력
	//		결과안내 출력
	// 반복 출력이 있다.
	
	// 출력은 항상 전체화면을 뿌려줄것.
	
	// 하단 메시지 선택기
	private void selectMessage() {
	}
	
	private void printView() {
		viewData.init();	// 보드의 출력데이터 준비
		prepareBaord();		// 보드준비
		for(String str : simpleBoard) {	// 보드출력.
			System.out.println(str);
		}
		bottomMessage();	// 하단 메시지 출력.
		
	}
	private void bottomMessage() {
		if(cData.state == TodoState.PromBET) {
			betMessage();
		}if(cData.state == TodoState.PromINSURANCE) {
			insuranceMessage();
		}else {
			defaultMessage(); 	// 공통 (빈) 메시지 3줄.
		}
	}
	
	private void insuranceMessage() {
		String playerName = cData.getInsurancePlayer().getName();
		String choiceMessage = "";
		ViewStr yesm = new ViewStr("Y",StrColor.YELLOW);
		ViewStr nom = new ViewStr("N",StrColor.RED);
		String errMsg = (new ViewStr(cData.insuranceErrMsg,StrColor.RED)).getColorStr();
		choiceMessage += String.format("%s{%s} %s{%s}",yesm.getColorStr() , "확인", nom.getColorStr(), "아니요");
		System.out.printf(" 인슈어런스를 하실까요? %s", errMsg);
		System.out.println(choiceMessage);
		System.out.printf("%s: ", playerName);
		cData.insuranceErrMsg = "";
	}
	
	private void defaultMessage() {
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	private void betMessage() {
		String playerName = cData.getBetPlayer().getName();
		
		int[] chips = cData.getTypeChips();
		String chipMessage = "";
		for(int i = 0; i < chips.length; i++) {
			ViewStr num = new ViewStr(i+1,StrColor.CYAN);
			chipMessage += String.format("%s{%d} ", num.getColorStr(), chips[i]);
		}
		ViewStr yesm = new ViewStr("Y",StrColor.YELLOW);
		ViewStr nom = new ViewStr("N",StrColor.RED);
		chipMessage += String.format("%s{%s} %s{%s}",yesm.getColorStr() , "완료", nom.getColorStr(), "취소");

		String totalChip = "";
		totalChip += (new ViewStr("[",StrColor.CYAN)).getColorStr();
		totalChip += (new ViewStr(cData.getBetPlayer().getChip())).getColorStr();
		totalChip += (new ViewStr("]",StrColor.CYAN)).getColorStr();
		String betChip = "";
		betChip += (new ViewStr("{",StrColor.YELLOW)).getColorStr();
		betChip += (new ViewStr(cData.getBetPlayer().getBetting())).getColorStr();
		betChip += (new ViewStr("}",StrColor.YELLOW)).getColorStr();
		int maxBet = cData.getMaxBet();
		String errMsg = (new ViewStr(cData.betErrMsg,StrColor.RED)).getColorStr();
		
		System.out.printf(" 최대[%,d] 배팅을 하세요. Chip:%s Bet:%s %s\n", maxBet, totalChip, betChip, errMsg);
		System.out.println(chipMessage);
		System.out.printf("%s: ", playerName);
		cData.betErrMsg = "";	// 에러메시지는 한번 출력후 초기화.
	}
	
	// 보드 정보
	private int boardTWidht = 9;
	private int totalSpace = boardTWidht * 10;
	private String[] simpleBoard = new String[15];
	private ViewBoardFrame simpleFrame = new ViewBoardFrame(boardTWidht, StrColor.GREEN);
	private void prepareBaord() {
		for(int i = 0; i < simpleBoard.length; i++) simpleBoard[i] = getVoidPan();
		
		int boardLine = 0;
		simpleBoard[boardLine++] = simpleFrame.getRoof();		// 게임보드 상단
		simpleBoard[boardLine++] = getDealerPan();			// 딜러 이름					
		for(String line : getDealerCardPan()) {				// 딜러 카드
			simpleBoard[boardLine++] = line;
		}
		// 공백
		simpleBoard[boardLine++] = getVoidPan();
		simpleBoard[boardLine++] = getVoidPan();

		// 플레이어 스플릿 카드
		for(String line : getPlayerSplitCardPan()) {
			simpleBoard[boardLine++] = line;
		}
		
		// 플레이어 카드
		for(String line : getPlayerCardPan()) {
			simpleBoard[boardLine++] = line;
		}
		
		simpleBoard[boardLine++] = getPlayerPan();			// 플레이어 이름	
		
		boardLine = simpleBoard.length - 1;
		simpleBoard[boardLine] = simpleFrame.getFloor();	// 게임보드 하단
	}
	
	private String getVoidPan() {
		String result = "";
		result += String.format("%s%s%s", simpleFrame.getEdge(), " ".repeat(totalSpace), simpleFrame.getEdge());
		return result;
	}
	
	private String[] getPlayerSplitCardPan() {
		String[] result = new String[] {"","",""};
		int space0 = 28;
		int remaining0 = space0;
		int space1 = totalSpace - space0;
		int remaining1 = space1;
		ArrayList<ViewCard> arrViewCard = viewData.getPlayerSplitCard();
		ArrayList<ViewStr> arrViewBet = viewData.getPlayerSplitBet();
		String[] betStr = new String [] {"","",""};
		
		// 배팅 패널 준비
		for(ViewStr str : arrViewBet) {
			int strSpace = str.space();
			remaining0 -= strSpace;
			betStr[0] += " ".repeat(strSpace);
			betStr[1] += str.getColorStr();
			betStr[2] += " ".repeat(strSpace);
		}
		
		// 앞 빈공간 작성.
		for(int i = 0; i < result.length; i++) {
			result[i] += simpleFrame.getEdge();
			result[i] += " ".repeat(remaining0);
		}
		
		// 배팅 패널 작성.
		for(int i = 0; i < betStr.length; i++) {
			result[i] += betStr[i];
		}
		
		// 카드 작성.
		for(ViewCard element : arrViewCard) {
			String[] viewCard = element.getCard();
			remaining1 -= element.space();
			for(int i = 0; i < viewCard.length; i++) {
				result[i] += viewCard[i];
			}
		}
		for(int i = 0; i < result.length; i++) {
			result[i] += " ".repeat(remaining1);
			result[i] += simpleFrame.getEdge();
		}
		return result;
	}
	private String[] getPlayerCardPan() {
		String[] result = new String[] {"","",""};
		int space0 = 28;
		int space1 = totalSpace - space0;
		int remaining0 = space0;
		int remaining1 = space1;
		ArrayList<ViewCard> arrViewCard = viewData.getPlayerCard();

		ArrayList<ViewStr> arrViewBet = viewData.getPlayerBet();
		String[] betStr = new String [] {"","",""};
		
		// 배팅 패널 준비
		for(ViewStr str : arrViewBet) {
			int strSpace = str.space();
			remaining0 -= strSpace;
			betStr[0] += " ".repeat(strSpace);
			betStr[1] += str.getColorStr();
			betStr[2] += " ".repeat(strSpace);
		}
		
		// 앞 빈공간 작성.
		for(int i = 0; i < result.length; i++) {
			result[i] += simpleFrame.getEdge();
			result[i] += " ".repeat(remaining0);
		}
		
		// 배팅 패널 작성.
		for(int i = 0; i < betStr.length; i++) {
			result[i] += betStr[i];
		}
		
		// 카드 작성.
		for(ViewCard element : arrViewCard) {
			String[] viewCard = element.getCard();
			remaining1 -= element.space();
			for(int i = 0; i < viewCard.length; i++) {
				result[i] += viewCard[i];
			}
		}
		for(int i = 0; i < result.length; i++) {
			result[i] += " ".repeat(remaining1);
			result[i] += simpleFrame.getEdge();
		}
		return result;
	}
	private String getPlayerPan() {
		int space0 = 28;
		int space1 = totalSpace - space0;
		int remaining1 = space1;
		String result = "";
		ArrayList<ViewStr> playerStr = viewData.getPlayerStr();
		
		// 왼쪽 공백.
		result = simpleFrame.getEdge();
		result += " ".repeat(space0);
		
		// 플레이어이름 : 잔고.
		for(ViewStr viewStr : playerStr) {
			remaining1 -= viewStr.space();
			result += viewStr.getColorStr();
		}
		
		// 남은 공백.
		result += " ".repeat(remaining1);
		result += simpleFrame.getEdge();
		
		return result;
	}
	
	private String[] getDealerCardPan() {
		String [] result = new String[] {"","",""};
		int space0 = 28;		// 공백0
		int space2 = 7;		// 공백2 (우측정렬)
		int space1 = totalSpace - space0 - space2;	// 공백1
		int remaining1 = space1;
		int remaining2 = space2;
		ArrayList<ViewCard> arrViewCard = viewData.getDealerCard();// 카드뭉치
		ViewCard backViewCard = viewData.getCardBack(); // 카드 뒷면

		// 앞 빈공간 작성.
		for(int i = 0; i < result.length; i++) {
			result[i] += simpleFrame.getEdge();
			result[i] += " ".repeat(space0);
		}
		
		// 카드 작성.
		for(ViewCard element : arrViewCard) {
			String[] viewCard = element.getCard();
			remaining1 -= element.space();
			for(int i = 0; i < viewCard.length; i++) {
				result[i] += viewCard[i];
			}
		}
		for(int i = 0; i < result.length; i++) {
			result[i] += " ".repeat(remaining1);
		}
		
		// 덱 뒷면 작성.
		String[] backView = backViewCard.getCard();
		remaining2 -= backViewCard.space();
		for(int i = 0; i < backView.length; i++) {
			result[i] += backView[i];
		}
		for(int i = 0; i < result.length; i++) {
			result[i] += " ".repeat(remaining2);
			result[i] += simpleFrame.getEdge();
		}
		
		return result;
	}
	private String getDealerPan() {
		int space0 = 28;
		int space1 = totalSpace - space0;
		int remaining1 = space1;
		ArrayList<ViewStr> dealerStr = viewData.getDealerStr();
		String result = "";
		
		// 왼쪽 공백.
		result = simpleFrame.getEdge();
		result += " ".repeat(space0);
		
		// 딜러 이름.
		for(ViewStr viewStr : dealerStr) {
			remaining1 -= viewStr.space();
			result += viewStr.getColorStr();
		}
		
		// 남은 공백.
		result += " ".repeat(remaining1);
		result += simpleFrame.getEdge();
		
		return result;
	}
	
	@Override
	public void paint() {
		printView();
	}
	@Override
	public void repaint() {
		
	}
	
}
