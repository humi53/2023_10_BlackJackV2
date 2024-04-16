package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;
import com.yopheu.games.aenean.models.card.Card;
import com.yopheu.games.aenean.models.card.Suit;

public class UICardFactory {
	private ANSIColor borderColor;	// 기본 테두리 컬러
	
	public UICardFactory(ANSIColor defaultBorderColor) {
		this.borderColor = defaultBorderColor;
	}

	public void setDefaultBorderColor(ANSIColor defBorderColor) {
		this.borderColor = defBorderColor;
	} // 기본 프레임 컬러 설정.
	
	public UICard getUICard(String str, ANSIColor strColor, ANSIColor borderColor) {
		return new UICard(str, strColor, borderColor);
	}
	
	public UICard getUICard(Card card, ANSIColor strColor, ANSIColor borderColor) {
		String str = card.getStrSuit() + card.getStrDenomination();
		return getUICard(str, strColor, borderColor);
	}
	
	public UICard getUICard(Card card, ANSIColor borderColor) {
		ANSIColor strColor = getSuitColor(card.getSuit());
		return getUICard(card, strColor, borderColor);
	}
	
	public UICard getUICard(Card card) {
		return getUICard(card, borderColor);
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
