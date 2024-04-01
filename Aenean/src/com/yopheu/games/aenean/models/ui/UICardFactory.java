package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Suit;

public class UICardFactory {
	private ANSIColor frameColor;	// 기본 프레임 컬러
	
	public UICardFactory(ANSIColor defaultFrameColor) {
		this.frameColor = defaultFrameColor;
	}

	public void setDefaultFrameColor(ANSIColor defFrameColor) {
		this.frameColor = defFrameColor;
	} // 기본 프레임 컬러 설정.
	
	public UICard getUICard(String str, ANSIColor strColor, ANSIColor frameColor) {
		return new UICard(str, strColor, frameColor);
	}
	
	public UICard getUICard(Card card, ANSIColor strColor, ANSIColor frameColor) {
		String str = card.getStrSuit() + card.getStrDenomination();
		return getUICard(str, strColor, frameColor);
	}
	
	public UICard getUICard(Card card, ANSIColor frameColor) {
		ANSIColor strColor = getSuitColor(card.getSuit());
		return getUICard(card, strColor, frameColor);
	}
	
	public UICard getUICard(Card card) {
		return getUICard(card, frameColor);
	}

	private ANSIColor getSuitColor(Suit suit) {
		ANSIColor strColor = ANSIColor.GREEN;
		if(suit == Suit.C || suit == Suit.S) {
			strColor = ANSIColor.GREEN;
		}else if(suit == Suit.D || suit == Suit.H) {
			strColor = ANSIColor.RED;
		}
		return strColor;
	}
	
}
