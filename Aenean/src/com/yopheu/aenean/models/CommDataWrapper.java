package com.yopheu.aenean.models;

import java.util.ArrayList;

import com.yopheu.aenean.config.TodoState;

public class CommDataWrapper {
	private DealerDataDto dealerDataDto; // 딜러 데이터
	private ArrayList<PlayerDataDto> arrPlayerDataDto; // 플레이어 데이터
	private DeckDto deckDto; // 덱 데이터
	public TodoState state;		// 게임상태
	private PlayerDataDto nowBetPlayer;
	private int maxBet;
	private int[] typeChips; 
	public String betErrMsg;
	private PlayerDataDto nowInsurancePlayer;
	public String insuranceErrMsg;
	private PlayerDataDto nowPlayer;
	
	// 게임 상태?
	public CommDataWrapper() {
		dealerDataDto = new DealerDataDto();
		arrPlayerDataDto = new ArrayList<>();
		arrPlayerDataDto.add(new PlayerDataDto("Player0"));
		deckDto = new DeckDto();
		state = TodoState.NoN;
		nowBetPlayer = null;	
		maxBet = 1000;
		typeChips = new int[] {20,40,100,200,400,1000};
		betErrMsg = "";
		insuranceErrMsg = "";
	}
	
	public int getMaxBet() {
		return maxBet;
	}
	
	public int[] getTypeChips() {
		return typeChips;
	}
	
	public void setBetPlayer(PlayerDataDto player) {
		this.nowBetPlayer = player;
	}
	public PlayerDataDto getBetPlayer() {
		return nowBetPlayer;
	}
	public void setInsurancePlayer(PlayerDataDto player) {
		this.nowInsurancePlayer = player;
	}
	public PlayerDataDto getInsurancePlayer() {
		return nowInsurancePlayer;
	}
	
	public void resetState() {
		state = TodoState.NoN;
		nowBetPlayer = null;
	}
	
	public DealerDataDto getDealer() {
		return dealerDataDto;
	}
	
	public ArrayList<PlayerDataDto> getPlayers() {
		return arrPlayerDataDto;
	}
	
	public DeckDto getDeck() {
		return deckDto;
	}
	
	// 플레이어 전체가 블랙잭인지 확인.
	public boolean isPlayerAllBJ() {
		boolean result = true;
		for(PlayerDataDto player : arrPlayerDataDto) {
			if(!player.isBlackJack()) {
				result = false;
				break;
			}
		}
		return result;
	}
}
