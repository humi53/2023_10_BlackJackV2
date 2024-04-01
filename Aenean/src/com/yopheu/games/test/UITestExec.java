package com.yopheu.games.test;

import com.yopheu.games.aenean.config.ANSIColor;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;
import com.yopheu.games.aenean.models.card.Suit;
import com.yopheu.games.aenean.models.ui.UIBoardConstants;
import com.yopheu.games.aenean.models.ui.UICard;
import com.yopheu.games.aenean.models.ui.UICardFactory;

public class UITestExec {
	public static void main(String[] args) {
		UIBoardConstants board = new UIBoardConstants(0, null);
//		System.out.println(board.getSIDE());
//		System.out.println(board.getROOF());
//		System.out.println(board.getFLOOR());
		
//		Card card = new Card(Suit.S, Denomination.NA);
//		System.out.println(card.getStrSuit());
//		System.out.println(card.getStrDenomination());
		
		UICardFactory uiCardFactory = new UICardFactory(ANSIColor.YELLOW);
		UICard uiCard = uiCardFactory.getUICard(new Card(Suit.H, Denomination.NA));
		System.out.println(uiCard.toString());
	}
}
