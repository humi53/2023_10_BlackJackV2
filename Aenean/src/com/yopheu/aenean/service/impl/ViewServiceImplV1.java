package com.yopheu.aenean.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yopheu.aenean.config.BetConfig;
import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.entry.Dealer;
import com.yopheu.aenean.models.entry.Player;
import com.yopheu.aenean.service.ViewService;
import com.yopheu.aenean.service.modules.CommDataModule;

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
	private String TWidht = "──────────";
	private String boardTop = "";
	private String boardBottom = "";
	
	private int totalSpace = boardTWidht * 10;
	private int dealerChipLeftSpace = 29;  
	
	private String dealerChipPan = "";
	private int sampleDealerChip = 10000;
	
	private String[] dealerCardPan = new String[] {"1","2","3"};
	private String[] sampleDealerCard = new String[] {"┌──┐","│♠J│","└──┘"};
	private String[] sampleDeck = new String[] {"┌──┐","│  │","└──┘"};
	private void prepareBaord() {
		boardTop = String.format("┌%s┐", TWidht.repeat(boardTWidht));	// 게임보드 상단
		
		// 딜러 점수판.
		dealerChipPan = setLineStr(dealerChipLeftSpace, String.format("\u001B[33m Dealer:\u001B[0m %,d", sampleDealerChip), totalSpace);
		// 딜러 카드.
		
		
		boardBottom = String.format("└%s┘", TWidht.repeat(boardTWidht));	// 게임보드 하단
	}
	private String[] setCardPan1(String[] CardPan, int leftSpace, String[] data, String[] deck, int totalSpace, int rightSpace) {
		return null;
	}
	private String setLineStr(int leftSpace, String data, int totalSpace) {
		String result = "";
		result = String.format("│%s%s%s│", " ".repeat(leftSpace), data, 
				" ".repeat((totalSpace-leftSpace) - strSpace(data)));
		return result;
	}
	
	public void sample() {
		System.out.println("┌-────────--────────--────────--────────--────────--────────--────────--────────--────────-┐");
		System.out.println("│                              Dealer: 100,000                                             │");
		System.out.println("└-────────--────────--────────--────────--────────--────────--────────--────────--────────-┘");
		prepareBaord();
		System.out.println(boardTop);
		System.out.println(dealerChipPan);
		for(String str : dealerCardPan) {
			System.out.println(str);			
		}
		
		System.out.println("│                            ┌──┐┌──┐┌──┐┌──┐┌──┐                                             ┌──┐   │");
		System.out.println("│                            │♠J││♠J││♠J││♠J││♠J│                                             │BJ│   │");
		System.out.println("│                            └──┘└──┘└──┘└──┘└──┘                                             └──┘   │");
		System.out.println("│                                                                                                    │");
		System.out.println("│                                                                                                    │");
		System.out.println("│                                                                                                    │");
		System.out.println("│                                                                                                    │");
		System.out.println("│                                                                                                    │");
		System.out.println("│                                                                                                    │");
		System.out.println("│                            ┌──┐┌──┐┌──┐┌──┐┌──┐                                                    │");
		System.out.println("│                            │♠J││♠J││♠J││♠J││♠J│                                                    │");
		System.out.println("│                            └──┘└──┘└──┘└──┘└──┘                                                    │");
		System.out.println("│                            ┌──┐┌──┐┌──┐┌──┐┌──┐                                                    │");
		System.out.println("│                    Bet: 40 │♠J││♠J││♠J││♠J││♠J│                                                    │");
		System.out.println("│                            └──┘└──┘└──┘└──┘└──┘                                                    │");
		System.out.println("│                             Player: 100,000                                                        │");
		System.out.println(boardBottom);
		System.out.println(boardTop.length());
		System.out.println(boardBottom.length());
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
	private int strSpace(String str) {
		int count = str.length();
		for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }
		return count;
	}
	private int countKoreanCharacters(String str) {
		int count = 0;

        for (char c : str.toCharArray()) {
            if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HANGUL) {
                count++;
            }
        }

        return count;
    }
	
}
