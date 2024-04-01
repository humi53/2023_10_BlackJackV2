package com.yopheu.games.aenean.models.ui;

import com.yopheu.games.aenean.config.ANSIColor;

/**
 * 
 * @author HumiDK
 *	board를 그리기 위한 조립품 3가지
 */
public class UIBoardConstants {
	private final String LEFT_TOP = "┌";
	private final String RIGHT_TOP = "┐";
	private final String LEFT_BOTTOM = "└";
	private final String RIGHT_BOTTOM = "┘";
	private final String SIDE = "│";
	private final String ROOF = "-────────-";
	private int width;
	private ANSIColor color;
	
	public UIBoardConstants(int width, ANSIColor color) {
		this.width = width;
		this.width = 9; 	// 임시 9로 제한.
		this.color = color;
	}
	
	private String tempRoof() {
		String temp = "";
		for(int i = 0; i < width; i++) {
			temp += ROOF;
		}
		return temp;
	}
	
	// │
	public String getSIDE() {
		return String.format("%s%s%s", colorANSI(),SIDE,endANSI());
	}
	
	// ┌-────────-┐  (-────────- * width)
	public String getROOF() {
		return String.format("%s%s%s%s%s", colorANSI(),LEFT_TOP,tempRoof(),RIGHT_TOP,endANSI());
	}
	
	// └-────────-┘  (-────────- * width)
	public String getFLOOR() {
		return String.format("%s%s%s%s%s", colorANSI(),LEFT_BOTTOM,tempRoof(),RIGHT_BOTTOM,endANSI());
	}
	
	private String colorANSI() {
		if(color == null) {
			return "";			
		}else {
			return color.getANSI();
		}
	}
	private String endANSI() {
		if(color == null) {
			return "";
		}else {
			return ANSIColor.END.getANSI();
		}
	}
}
