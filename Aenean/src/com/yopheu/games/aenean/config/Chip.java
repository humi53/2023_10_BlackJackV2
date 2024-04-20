package com.yopheu.games.aenean.config;

public enum Chip {
	C20(20),
	C40(40),
	C100(100),
	C200(200),
	C400(400),
	C1000(1000),
	CMAX(1000),
	CENCEL(0),
	ENTER(0),
	ERR(0),
	NONE(0);
	
	private final int VALUE;
	private Chip(int value) {
		this.VALUE = value;
	}
	public int value() {
		return VALUE;
	}
}
