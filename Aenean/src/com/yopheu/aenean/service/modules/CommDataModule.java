package com.yopheu.aenean.service.modules;

import java.util.List;
import java.util.Queue;

import com.yopheu.aenean.config.GameState;
import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.entry.Dealer;
import com.yopheu.aenean.models.entry.Player;

public interface CommDataModule {
	public Queue<Card> getCardDeck();
	public Dealer getDealer();
	public List<Player> getPlayers();
	public void setGameState(GameState gameState);
	public GameState getGameState();
	public Player getCurrentPlayer();
	void setCurrentPlayer(Player currentPlayer);
}
