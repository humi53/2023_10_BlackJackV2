package com.yopheu.aenean.config;

public enum StrColor {
	BLACK("\u001B[30m"),
    WHITE("\u001B[37m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    RED("\u001B[31m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    END("\u001B[0m");
	private final String code;

	StrColor(String code) {
	    this.code = code;
	}

    public String getCode() {
        return code;
    }
}
