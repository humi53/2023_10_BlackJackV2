package com.yopheu.games.test;

import com.yopheu.games.aenean.models.ui.UIBoardConstants;
import com.yopheu.games.aenean.models.ui.UIBoardRenderer;

public class UIBoardTestExec {
	public static void main(String[] args) {
//		UIBoardConstants board = new UIBoardConstants(9, null);
//		int boardHeight = 1; 
//		String[] boardString = new String[boardHeight];
//		System.out.println(boardString.length);
//		String cardSlot = "-──-".repeat(7);
//		String leftPadding = " ".repeat(28);
//		int boardSize = 9 * 10;
//		for(int i = 0; i < boardString.length; i++) {
////			boardString[i] = "*".repeat(9*10);
//			int conSize = leftPadding.length() + cardSlot.length();
//			boardString[i] = leftPadding + cardSlot + " ".repeat(boardSize-conSize);
//		} 
//		
//		boardString = board.wrapBoardData(boardString);
//		
//		for (String string : boardString) {
//			System.out.println(string);
//		}
		
		UIBoardRenderer boardRenderer = new UIBoardRenderer(null);
		boardRenderer.setBoard();
		boardRenderer.printBoard();
	}
}
