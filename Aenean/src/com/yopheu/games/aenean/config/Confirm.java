package com.yopheu.games.aenean.config;

public enum Confirm {
	YES("YES"),
	NO("NO"),
	NONE("");
	
	private String str;
	private Confirm(String str) {
		this.str = str;
	}
	
	public String getStr() {
		return str;
	}
}
