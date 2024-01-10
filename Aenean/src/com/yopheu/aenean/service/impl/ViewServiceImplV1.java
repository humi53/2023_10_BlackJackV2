package com.yopheu.aenean.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yopheu.aenean.config.BetConfig;
import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.config.StrColor;
import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.entry.Dealer;
import com.yopheu.aenean.models.entry.Player;
import com.yopheu.aenean.service.ViewService;
import com.yopheu.aenean.service.modules.CommDataModule;
import com.yopheu.aenean.view.ViewBoardFrame;
import com.yopheu.aenean.view.ViewCard;
import com.yopheu.aenean.view.ViewStr;

public class ViewServiceImplV1 implements ViewService{
	
	private Queue<Card> cardDeck;		// 카드덱
	private Dealer dealer;		// 딜러
	private List<Player> players;	// 플레이어들
	
	private CommDataModule cData;
	
	private String singleMessage;
	private Player currentPlayer;
	
	
	public ViewServiceImplV1(CommDataModule commDataModule){
		this.cData = commDataModule;
	}
	
	private void linkData() {
		dealer = cData.getDealer();
		players = cData.getPlayers();
		cardDeck = cData.getCardDeck();
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
		singleMessage = "";
		switch (cData.getGameState()) {
		case READY:
			singleMessage = "게임이 준비중인 것이에요.";
			break;
		case WILLBET:
			singleMessage = "배팅 [ ";
			for(int i=1; i<BetConfig.BET.length; i++) {
				singleMessage += BetConfig.BET[i];
				singleMessage += "(" + i + ") "; 
			}
			singleMessage += "] 확인(Enter) 취소(0)";
			
			singleMessage = String.format("%s\n%s[%s] : ", singleMessage, 
					cData.getCurrentPlayer().getName(),
					cData.getCurrentPlayer().getNowBet());
			break;
		
		default:
			break;
		}
	}
	
	private void printView() {
//		selectMessage();
//		System.out.print(singleMessage);
	}
	private int boardTWidht = 9;
	private int totalSpace = boardTWidht * 10;
	private int dealerChipLeftSpace = 29;  
	
	private String dealerChipPan = "";
	private int sampleDealerChip = 10000;
	
	private String[] dealerCardPan = new String[] {"1","2","3"};
	private String[] sampleDealerCard = new String[] {"┌──┐","│♠J│","└──┘"};
	private String[] sampleDeck = new String[] {"┌──┐","│  │","└──┘"};
	
	private String[] simpleBoard = new String[18];
	private ViewBoardFrame simpleFrame = new ViewBoardFrame(boardTWidht, StrColor.GREEN);
	private void prepareBaord() {
		
		simpleBoard[0] = 	"┌-────────--────────--────────--────────--────────--────────--────────--────────--────────-┐";
		simpleBoard[1] = 	"│                              Dealer: 100,000                                             │";
		simpleBoard[2] = 	"│                            ┌──┐┌──┐┌──┐┌──┐┌──┐                                   ┌──┐   │";
		simpleBoard[3] = 	"│                            │♠J││♠J││♠J││♠J││♠J│                                   │BJ│   │";
		simpleBoard[4] = 	"│                            └──┘└──┘└──┘└──┘└──┘                                   └──┘   │";
		simpleBoard[5] = 	"│                                                                                          │";
		simpleBoard[6] = 	"│                                                                                          │";
		simpleBoard[7] = 	"│                                                                                          │";
		simpleBoard[8] = 	"│                                                                                          │";
		simpleBoard[9] = 	"│                                                                                          │";
		simpleBoard[10] = 	"│                            ┌──┐┌──┐┌──┐┌──┐┌──┐                                          │";
		simpleBoard[11] = 	"│                            │♠J││♠J││♠J││♠J││♠J│                                          │";
		simpleBoard[12] = 	"│                            └──┘└──┘└──┘└──┘└──┘                                          │";
		simpleBoard[13] = 	"│                            ┌──┐┌──┐┌──┐┌──┐┌──┐                                          │";
		simpleBoard[14] = 	"│                    Bet: 40 │♠J││♠J││♠J││♠J││♠J│                                          │";
		simpleBoard[15] = 	"│                            └──┘└──┘└──┘└──┘└──┘                                          │";
		simpleBoard[16] = 	"│                             Player: 100,000                                              │";
		simpleBoard[17] = 	"└-────────--────────--────────--────────--────────--────────--────────--────────--────────-┘";
		simpleBoard[0] = simpleFrame.getRoof();		// 게임보드 상단
		simpleBoard[1] = getDealerPan();		// 딜러 이름
		int position1 = 2;						// 딜러 카드
		for(String line : getDealerCardPan()) {
			simpleBoard[position1++] = line;
		}
		// 공백
		simpleBoard[5] = getVoidPan();
		simpleBoard[6] = getVoidPan();
		simpleBoard[7] = getVoidPan();
		simpleBoard[8] = getVoidPan();
		simpleBoard[9] = getVoidPan();

		// 조건부 카드
		simpleBoard[10] = getVoidPan();
		simpleBoard[11] = getVoidPan();
		simpleBoard[12] = getVoidPan();
		
		simpleBoard[17] = simpleFrame.getFloor();	// 게임보드 하단
	}
	
	private String getVoidPan() {
		String result = "";
		result += String.format("%s%s%s", simpleFrame.getEdge(), " ".repeat(totalSpace), simpleFrame.getEdge());
		return result;
	}
	
	private String[] getDealerCardPan() {
		String [] result = new String[] {"","",""};
		int space0 = 28;		// 공백0
		int space2 = 7;		// 공백2 (우측정렬)
		int space1 = totalSpace - space0 - space2;	// 공백1
		int remaining1 = space1;
		int remaining2 = space2;
		ViewCard[] arrViewCard = new ViewCard[]{
				new ViewCard("♠A", StrColor.GREEN),
				new ViewCard("♦A", StrColor.RED),
				new ViewCard("♥A", StrColor.RED),
				new ViewCard("♣A", StrColor.GREEN),
				new ViewCard("BJ", StrColor.YELLOW, StrColor.YELLOW)
		};// 카드뭉치 [배열]
		ViewCard backViewCard = new ViewCard("BJ", StrColor.YELLOW, StrColor.YELLOW); // 카드 뒷면

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
		ViewStr dealerName = new ViewStr("Dealer", StrColor.YELLOW);
		String result = "";
		
		result = simpleFrame.getEdge();
		result += " ".repeat(space0);
		
		remaining1 -= dealerName.space();
		result += dealerName.getColorStr();
		result += " ".repeat(remaining1);
		result += simpleFrame.getEdge();
		
		return result;
	}
	
	public void sample() {
		prepareBaord();
		for(String str : simpleBoard) {
			System.out.println(str);
		}
//		System.out.println("┌──┐┌──┐┌──┐┌──┐┌──┐┌──┐┌──┐┌──┐");
//		System.out.println("│♠J││♠J││♠J││♠J││♠J││♠J││딜││러│");
//		System.out.println("└──┘└──┘└──┘└──┘└──┘└──┘└──┘└──┘");
		
//		System.out.println("시스템 메시지");
//		System.out.println("{오류 메시지}");
//		System.out.println("입력 : ");
	}
	
	@Override
	public void paint() {
		printView();
	}
	@Override
	public void repaint() {
		
	}
	
}
