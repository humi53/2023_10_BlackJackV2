package com.yopheu.games.aenean.config;

public enum PlayChoose {
	SPLIT("스플릿"),
	DOUBLEDOWN("더블"),
	HIT("힛"),
	STAND("스탠드"),
	NONE("없음");
	
	private final String TEXT;
	private PlayChoose(String TEXT) {
		this.TEXT = TEXT;
	}
	
	public String getText() {
		return TEXT;
	}
}
