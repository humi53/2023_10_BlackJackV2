package com.yopheu.games.aenean.config;

public enum ANSIColor {
	BLACK("\u001B[30m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    END("\u001B[0m");
	private final String ANSI;

	ANSIColor(String ANSI) {
	    this.ANSI = ANSI;
	}

    public String getANSI() {
        return ANSI;
    }
}

