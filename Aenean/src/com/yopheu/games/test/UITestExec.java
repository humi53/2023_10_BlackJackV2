package com.yopheu.games.test;

import com.yopheu.games.aenean.config.ANSIColor;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Denomination;
import com.yopheu.games.aenean.models.card.Suit;
import com.yopheu.games.aenean.models.ui.UIBlockBundle;
import com.yopheu.games.aenean.models.ui.UIBoardConstants;
import com.yopheu.games.aenean.models.ui.UICard;
import com.yopheu.games.aenean.models.ui.UICardFactory;
import com.yopheu.games.aenean.models.ui.UIStr;
import com.yopheu.games.aenean.models.ui.UIStrBundle;
import com.yopheu.games.aenean.models.ui.UIStrFactory;
import com.yopheu.games.aenean.models.ui.UIBorderStr;

public class UITestExec {
	public static void main(String[] args) {
		UIBoardConstants board = new UIBoardConstants(0, null);
//		System.out.println(board.getSIDE());
//		System.out.println(board.getROOF());
//		System.out.println(board.getFLOOR());
		
		UICardFactory uiCardFactory = new UICardFactory(ANSIColor.YELLOW);
		UICard uiCard = uiCardFactory.getUICard(new Card(Suit.S, Denomination.NA));
		System.out.println(uiCard.toString());
		
		UIStrFactory uiStrFactory = new UIStrFactory(ANSIColor.YELLOW);
		UIBorderStr uiBorderStr = uiStrFactory.getUIBorderStr("안녕", ANSIColor.CYAN);
		uiBorderStr.print();
		System.out.println(uiBorderStr.width());
		
		UIStr uiStr = uiStrFactory.getUIStr("뭐임 ", ANSIColor.YELLOW, 3);
		System.out.println(uiStr.width());
		uiStr.print();
		uiStr.StrPrint();
		UIStr uiStr2 = uiStrFactory.getUIStr("뭐임", ANSIColor.RED);
		
		UIBlockBundle block = new UIBlockBundle(uiCard);
		block.addBlock(uiBorderStr);
		block.addLeftBlock(uiStr);
		block.print();
		System.out.println(block.width());
		
		UIStrBundle strBundle = new UIStrBundle(uiStr);
		strBundle.addStr(uiStr2);
		strBundle.addLeftStr(uiStr2);
		strBundle.StrPrint();
		System.out.println(strBundle.width());
	}
}
