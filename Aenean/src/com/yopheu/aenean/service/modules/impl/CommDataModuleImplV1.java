package com.yopheu.aenean.service.modules.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.entry.Dealer;
import com.yopheu.aenean.models.entry.Player;
import com.yopheu.aenean.service.modules.CommDataModule;

public class CommDataModuleImplV1 implements CommDataModule {
	private Queue<Card> cardDeck;		// 카드덱
	private Dealer dealer;		// 딜러
	private List<Player> players;	// 플레이어들
	private GameState gameState;
	private Player currentPlayer;
	
	public CommDataModuleImplV1() {
		cardDeck = new LinkedList<>();
		players = new ArrayList<>();
		dealer = new Dealer();
		gameState = GameState.READY;
		currentPlayer = null;
	}
	
	@Override
	public Queue<Card> getCardDeck() {
		return cardDeck;
	}
	@Override
	public Dealer getDealer() {
		return dealer;
	}
	@Override
	public List<Player> getPlayers() {
		return players;
	}
	@Override
	public GameState getGameState() {
		return gameState;
	}
	@Override
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	@Override
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
}
