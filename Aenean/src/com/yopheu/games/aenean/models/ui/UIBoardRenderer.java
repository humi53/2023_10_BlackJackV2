package com.yopheu.games.aenean.models.ui;

import java.util.ArrayList;
import java.util.Arrays;

import com.yopheu.games.aenean.config.ANSIColor;
import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;
import com.yopheu.games.aenean.models.card.Suit;

/**
 * 
 * @author HumiDK
 *
 */
public class UIBoardRenderer {
	private final int BOARD_HEIGHT = 5;	// 보드 높이
	private final int BOARD_WIDTH = 9;	// 보드 너비 * 10
	private UIBoardConstants boardConstants;	// Board 기본틀.
	private CommDataWrapper cData;	// 공용 데이터
	// UI카드 변환기.
	private String[] arrStrBoard; // 보드의 출력용 데이터.
	
	private UICardFactory uiCardFactory;
	private UIStrFactory uiStrFactory;
	private BoardComposer boardComposer;
	
	public UIBoardRenderer(CommDataWrapper cData) {
		boardConstants = new UIBoardConstants(BOARD_WIDTH, null);
		this.cData = cData;
		arrStrBoard = new String[BOARD_HEIGHT];
		Arrays.fill(arrStrBoard, " ".repeat(BOARD_WIDTH*10));
		
		uiCardFactory = new UICardFactory(null);
		uiStrFactory = new UIStrFactory(null);
		boardComposer = new BoardComposer();
	}
	
	public void printBoard() {
		for (String element : arrStrBoard) {
			System.out.println(element);
		}
	}
	
	public void setBoard() {
		// 보드 데이터 세팅
		// 라인을 정한다
		setDealerBoard(0);
		
		arrStrBoard = boardConstants.wrapBoardData(arrStrBoard);	// 보드 테두리 세팅
	}
	
	private void setDealerBoard(int index) {
		int boardWidth = BOARD_WIDTH * 10; // 고정 전체 공간
		
		// boardComposer로 출력 데이터 정리 (Block : 3줄짜리 문자열)
		boardComposer.initBlockLine(null, boardWidth);
		
		// 왼쪽 텍스트. 28공간의 우측정렬.
		int leftTextSpaceWidth = 28;
		String leftText = "BlackJack";
		UIBlockBundle leftStr = uiStrFactory.getUIBlockBundle(leftText, null, "[", "]", ANSIColor.CYAN);
		UIBlockBundle leftStrBlock = boardComposer.rightBlock(leftStr, leftTextSpaceWidth);
		boardComposer.addBlock(leftStrBlock);
		
		// 카드.
		ArrayList<Card> arrCard = new ArrayList<>();
		arrCard.add(new Card(Suit.S, Denomination.NA));
		arrCard.add(new Card(Suit.S, Denomination.NK));
		arrCard.add(new Card(Suit.S, Denomination.NQ));
		arrCard.add(new Card(Suit.S, Denomination.NJ));
		arrCard.add(new Card(Suit.S, Denomination.N10));
		UIBlockBundle dealerHands = getUICardList(arrCard);
		boardComposer.addBlock(dealerHands);
		
		// 카드덱 뒷면.
		UICard backOfCard = uiCardFactory.getUIBackOfCard(9, ANSIColor.CYAN, ANSIColor.YELLOW);
		boardComposer.addLastBlock(backOfCard);
		
		// 완성된 blockline을 board에 입력.
		addBlockLineToBoard(index, boardComposer.getBlockLine());
	}
	
	private UIBlockBundle getUICardList(ArrayList<Card> arrCard) {
		UIBlockBundle result = new UIBlockBundle();
		for(Card element : arrCard) {
			result.addBlock(uiCardFactory.getUICard(element)); 
		}
		return result;
	}
	
	private void addBlockLineToBoard(int index, UIBlockBundle blockLine) {
		String[] blockData = blockLine.getData();
		for(int i = index; i < index+3; i++) {
			arrStrBoard[i] = blockData[i];
		}
	}
	
	private void addStrLineToBoard(int index, UIStrBundle strLine) {
		arrStrBoard[index] = strLine.getStr();
	}
	
	
}
