package com.yopheu.games.aenean.models.ui;

import java.util.ArrayList;
import java.util.Arrays;

import com.yopheu.games.aenean.config.ANSIColor;
import com.yopheu.games.aenean.config.GameState;
import com.yopheu.games.aenean.config.PlayResultState;
import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.models.DealerDto;
import com.yopheu.games.aenean.models.DeckDto;
import com.yopheu.games.aenean.models.ICardHand;
import com.yopheu.games.aenean.models.PlayerDto;
import com.yopheu.games.aenean.models.PlayerSplitDto;
import com.yopheu.games.aenean.models.ResultCalculator;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;
import com.yopheu.games.aenean.models.card.Suit;

/**
 * 
 * @author HumiDK
 *
 */
public class UIBoardRenderer {
	private final int BOARD_HEIGHT = 11;	// 보드 높이
	private final int BOARD_WIDTH = 9;	// 보드 너비 * 10
	private UIBoardConstants boardConstants;	// Board 기본틀.
	private CommDataWrapper cData;	// 공용 데이터
	private DealerDto dealerDto;
	private DeckDto deckDto;
	private PlayerDto playerDto;
	
	// UI카드 변환기.
	private String[] arrStrBoard; // 보드의 출력용 데이터.
	private String[] printStrBoard;	// 출력용 보드
	
	private UICardFactory uiCardFactory;
	private UIStrFactory uiStrFactory;
	private BoardComposer boardComposer;
	
	private ResultCalculator resultCalculator;
	
	public UIBoardRenderer(CommDataWrapper cData) {
		boardConstants = new UIBoardConstants(BOARD_WIDTH, null);
		this.cData = cData;
		dealerDto = cData.getDealerDto();
		playerDto = cData.getPlayerDto();
		deckDto = cData.getDeckDto();
		arrStrBoard = new String[BOARD_HEIGHT];
		Arrays.fill(arrStrBoard, " ".repeat(BOARD_WIDTH*10));
		
		uiCardFactory = new UICardFactory(null);
		uiStrFactory = new UIStrFactory(null);
		boardComposer = new BoardComposer();
		
		resultCalculator = new ResultCalculator();
	}
	
	public void printBoard() {
		for (String element : printStrBoard) {
			System.out.println(element);
		}
	}
	
	public void setBoard() {
		// 보드 데이터 세팅
		// 라인을 정한다
		setDealerBoard(0);
		
		if(playerDto.isSplit()) {
			setSplitBoard(4);
		}
		setPlayerBoard(7);
		
		setChipBoard(10);
		
		printStrBoard = boardConstants.wrapBoardData(arrStrBoard);	// 보드 테두리 세팅
	}
	
	private void setDealerBoard(int index) {
		int boardWidth = BOARD_WIDTH * 10; // 고정 전체 공간
		
		// boardComposer로 출력 데이터 정리 (Block : 3줄짜리 문자열)
		boardComposer.initBlockLine(null, boardWidth);
		
		// 왼쪽 텍스트. 28공간의 우측정렬.
		// 딜러의 상태.
		int leftTextSpaceWidth = 28;
		String leftText = "";
		UIBlockBundle leftStrBlock;
		if(leftText.equals("")) {
			UIStr non = new UIStr("", null);
			leftStrBlock = boardComposer.rightBlock(non, leftTextSpaceWidth);
		}else {
			UIBlockBundle leftStr = uiStrFactory.getUIBlockBundle(leftText, null, "[", "]", ANSIColor.CYAN);
			leftStrBlock = boardComposer.rightBlock(leftStr, leftTextSpaceWidth);
		}
		boardComposer.addBlock(leftStrBlock);
		
		// 딜러의 핸드 카드.
		UIBlockBundle dealerHands = getUIDealerCardList(dealerDto);
		boardComposer.addBlock(dealerHands);
		
		// 딜러 상태 블럭.
		boardComposer.addBlock(getUIResultStateBlock(dealerDto.getResultState()));
		
		// 카드덱 뒷면.
		int deckCount = deckDto.countInDeck();
		UICard backOfCard = uiCardFactory.getUIBackOfCard(deckCount, ANSIColor.CYAN, ANSIColor.YELLOW);
		boardComposer.addLastBlock(backOfCard);
		
		// 완성된 blockline을 board에 입력.
		addBlockLineToBoard(index, boardComposer.getBlockLine());
	}
	
	private void setPlayerBoard(int index) {
		int boardWidth = BOARD_WIDTH * 10; // 고정 전체 공간
		
		// boardComposer로 출력 데이터 정리 (Block : 3줄짜리 문자열)
		boardComposer.initBlockLine(null, boardWidth);
		
		// 왼쪽 텍스트. 28공간의 우측정렬.
		// 플레이어의 배팅 상태.
		int leftTextSpaceWidth = 28;
		int totalBet = playerDto.getTotalBet();
		String leftText = "벳: " + totalBet;
		UIBlockBundle leftStr = uiStrFactory.getUIBlockBundle(leftText, null, "[", "]", ANSIColor.CYAN);
		UIBlockBundle leftStrBlock = boardComposer.rightBlock(leftStr, leftTextSpaceWidth);
		boardComposer.addBlock(leftStrBlock);
		
		// 플레이어의 핸드 카드.
		UIBlockBundle playerHands = getUICardList(playerDto.getHands());
		boardComposer.addBlock(playerHands);
		
		// 플레이어의 상태 블럭.
		boardComposer.addBlock(getUIResultStateBlock(playerDto.getResultState()));
		
		// 완성된 blockline을 board에 입력.
		addBlockLineToBoard(index, boardComposer.getBlockLine());
	}
	
	private void setSplitBoard(int index) {
		int boardWidth = BOARD_WIDTH * 10; // 고정 전체 공간
		
		// boardComposer로 출력 데이터 정리 (Block : 3줄짜리 문자열)
		boardComposer.initBlockLine(null, boardWidth);
		PlayerSplitDto splitDto = playerDto.getSplitDto();
		
		// 왼쪽 텍스트. 28공간의 우측정렬.
		// 플레이어의 배팅 상태.
		int leftTextSpaceWidth = 28;
		int totalBet = splitDto.getBetChip();
		String leftText = "스플릿: " + totalBet;
		UIBlockBundle leftStr = uiStrFactory.getUIBlockBundle(leftText, null, "[", "]", ANSIColor.CYAN);
		UIBlockBundle leftStrBlock = boardComposer.rightBlock(leftStr, leftTextSpaceWidth);
		boardComposer.addBlock(leftStrBlock);
		
		// 플레이어의 핸드 카드.
		UIBlockBundle playerHands = getUICardList(splitDto.getHands());
		boardComposer.addBlock(playerHands);
		
		// 플레이어의 상태 블럭.
		boardComposer.addBlock(getUIResultStateBlock(splitDto.getResultState()));
		
		// 완성된 blockline을 board에 입력.
		addBlockLineToBoard(index, boardComposer.getBlockLine());
	}
	
	private void setChipBoard(int index) {
		int boardWidth = BOARD_WIDTH * 10; // 고정 전체 공간
		
		// boardComposer로 출력 데이터 정리 (Block : 3줄짜리 문자열)
		boardComposer.initStrLine(null, boardWidth);
		UIStr left = uiStrFactory.getUIStr("[", ANSIColor.CYAN);
		UIStr right = uiStrFactory.getUIStr("]", ANSIColor.CYAN);
		UIStr totalTitle = uiStrFactory.getUIStr("Total: ");
		UIStr totalChip = uiStrFactory.getUIStr(""+playerDto.totalChip(),ANSIColor.YELLOW);
		
		UIStrBundle uiTotal = new UIStrBundle();
		uiTotal.addStr(left);
		uiTotal.addStr(totalTitle);
		uiTotal.addStr(totalChip);
		uiTotal.addStr(right);
		boardComposer.addStr(uiTotal);
		
		// totalbet
		int totalBets = playerDto.getTotalBet() + playerDto.getSplitDto().getBetChip();
		UIStr betTitle = uiStrFactory.getUIStr("Bet: ");
		UIStr betChips = uiStrFactory.getUIStr("" + totalBets, ANSIColor.RED);
		UIStrBundle uiBets = new UIStrBundle();
		uiBets.addStr(left);
		uiBets.addStr(betTitle);
		uiBets.addStr(betChips);
		uiBets.addStr(right);

		int intWinChips = 0;
		if(cData.getStates().gameState == GameState.FINISH) {
			intWinChips += resultCalculator.getCalcChips(playerDto);
			if(playerDto.isSplit()) {
				intWinChips += resultCalculator.getCalcChips(playerDto.getSplitDto());
			}
		}
		UIStr winTitle = uiStrFactory.getUIStr("Win: ");
		UIStr winChips = uiStrFactory.getUIStr("" + intWinChips, ANSIColor.GREEN);
		UIStrBundle uiWin = new UIStrBundle();
		uiWin.addStr(left);
		uiWin.addStr(winTitle);
		uiWin.addStr(winChips);
		uiWin.addStr(right);
		
		UIStrBundle rightScore = new UIStrBundle();
		rightScore.addStr(uiBets);
		rightScore.addStr(uiWin);
		boardComposer.addLastStr(rightScore);
		
		addStrLineToBoard(index, boardComposer.getStrLine());
	}
	private UIBlockBundle getUICardList(ArrayList<Card> arrCard) {
		UIBlockBundle result = new UIBlockBundle();
		for(Card element : arrCard) {
			result.addBlock(uiCardFactory.getUICard(element)); 
		}
		return result;
	}
	
	private IUIBlock getUIResultStateBlock(PlayResultState playResultState) {
		IUIBlock result;
		if(playResultState == PlayResultState.ONGOING) {
			result = uiStrFactory.getUIBlockBundle(playResultState.getText(), null, "[", "]", ANSIColor.YELLOW);
		}else if(playResultState == PlayResultState.BLACKJACK
				|| playResultState == PlayResultState.WIN) {
			result = uiStrFactory.getUIBorderStr(playResultState.getText(), ANSIColor.YELLOW, ANSIColor.YELLOW);
		}else if(playResultState == PlayResultState.LOSS
				|| playResultState == PlayResultState.BUST) {
			result = uiStrFactory.getUIBorderStr(playResultState.getText(), ANSIColor.RED, ANSIColor.RED);
		}else if(playResultState == PlayResultState.PUSH) {
			result = uiStrFactory.getUIBorderStr(playResultState.getText(),ANSIColor.GREEN,ANSIColor.GREEN);
		}else if(playResultState == PlayResultState.STAND) {
			result = uiStrFactory.getUIBorderStr(playResultState.getText());
		}else {
			result = new UIStr("", null);
		}
		return result;
	}
	
	private UIBlockBundle getUIDealerCardList(DealerDto dealerDto) {
		UIBlockBundle result = new UIBlockBundle();
		ArrayList<Card> cards = dealerDto.getHands();
		for(int i = 0; i < cards.size(); i++) {
			if(i == 1 && !dealerDto.isOpen()) {
				result.addBlock(uiCardFactory.getUICard("BJ",ANSIColor.YELLOW,ANSIColor.YELLOW));
			}else {
				result.addBlock(uiCardFactory.getUICard(cards.get(i)));
			}
		}
		return result;
	}
	
	private void addBlockLineToBoard(int index, UIBlockBundle blockLine) {
		String[] blockData = blockLine.getData();
		for(int i = 0; i < 3; i++) {
			arrStrBoard[i+index] = blockData[i];
		}
	}
	
	private void addStrLineToBoard(int index, UIStrBundle strLine) {
		arrStrBoard[index] = strLine.getStr();
	}
	
	
}
