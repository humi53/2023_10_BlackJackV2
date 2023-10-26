package com.yopheu.aenean.config;

public enum SystemState {
	
	PLAYGAME("게임진행중"),
	LOADING("게임진입중"),
	MAINMENU("메인메뉴");
	
	private String krDescription;
	private SystemState() {
		// TODO Auto-generated constructor stub
	}
	private SystemState(String description) {
		this.krDescription = description;
	}
	public String getDescription() {
		return krDescription;
	}
}
