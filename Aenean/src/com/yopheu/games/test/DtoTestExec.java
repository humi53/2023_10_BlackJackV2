package com.yopheu.games.test;

import com.yopheu.games.aenean.models.DeckDto;
import com.yopheu.games.aenean.models.PlayerDto;
import com.yopheu.games.aenean.models.PlayerSplitDto;
import com.yopheu.games.aenean.models.card.Card;

public class DtoTestExec {
	public static void main(String[] args) {
		PlayerDto playerDto = new PlayerDto();
		PlayerSplitDto splitDto = playerDto.getSplitDto();
		
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		playerDto.betting(300);
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		playerDto.trySplit();
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		playerDto.doubleDown();
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		splitDto.doubleDown();
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		playerDto.resetHands();
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		playerDto.repeatPreviousBet();
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		playerDto.trySplit();
		splitDto.doubleDown();
		System.out.println("total : " + playerDto.totalChip());
		System.out.println("bet : " + playerDto.getBetChip());
		System.out.println("split : " + splitDto.getBetChip());
		
		DeckDto deckDto = new DeckDto();
		deckDto.printDeck();
		Card card = deckDto.takeCard();
		System.out.println(card);
		card = deckDto.takeCard();
		System.out.println(card);
		deckDto.printDeck();
		
	}
}
