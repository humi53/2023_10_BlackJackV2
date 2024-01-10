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
		simpleBoard[1] = getDealerPan();
		simpleBoard[2] = getDealerCardPan();
		simpleBoard[17] = simpleFrame.getFloor();	// 게임보드 하단
	}
	
	private String getDealerCardPan() {
		int remaining = totalSpace;
		int leftSpace = 28;
		String result = "";
		
		result = simpleFrame.getEdge();
		remaining -= leftSpace;
		result += " ".repeat(leftSpace);
		
		result += " ".repeat(remaining);
		result += simpleFrame.getEdge();
		return result;
	}
	
	private String getDealerPan() {
		int remaining = totalSpace;
		int leftSpace = 28;
		ViewStr dealerName = new ViewStr("Dealer", StrColor.YELLOW);
		String result = "";
		
		result = simpleFrame.getEdge();
		remaining -= leftSpace;
		result += " ".repeat(leftSpace);
		
		remaining -= dealerName.space();
		result += dealerName.getColorStr();
		
		result += " ".repeat(remaining);
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
