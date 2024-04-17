package com.yopheu.games.aenean.models.ui;
/**
 * 보드에 들어갈 구성을 조립하는
 * @author HumiDK
 *
 */
public class BoardComposer {
	
	private UIBlockBundle blockLine;
	private int blockLineWidth;
	private UIStrBundle strLine;
	private int strLineWidth;
	
	public BoardComposer() {
		blockLine = new UIBlockBundle();
		blockLineWidth = 0;
		strLine = new UIStrBundle();
		strLineWidth = 0;
	}
	
	public void initBlockLine(IUIBlock block, int lineWidth) {
		reset();
		blockLineWidth = lineWidth;
		blockLine.addBlock(block);
	}
	
	public void initStrLine(IUIStr str, int lineWidth) {
		reset();
		strLineWidth = lineWidth;
		strLine.addStr(str);		
	}
	public void addBlock(IUIBlock block) {
		int sumWidth = blockLine.width() + block.width();
		if(sumWidth > blockLineWidth) {
			System.out.println("범위를 넘어서서 추가하지 않음.");
		}else {
			blockLine.addBlock(block);
		}
	}
	public void addLeftBlock(IUIBlock block) {
		int sumWidth = blockLine.width() + block.width();
		if(sumWidth > blockLineWidth) {
			System.out.println("범위를 넘어서서 추가하지 않음.");
		}else {
			blockLine.addLeftBlock(block);
		}
	}
	
	public void addStr(IUIStr str) {
		int sumWidth = strLine.width() + str.width();
		if(sumWidth > strLineWidth) {
			System.out.println("범위를 넘어서서 추가하지 않음.");
		}else {
			strLine.addStr(str);
		}
	}
	
	public void addLeftStr(IUIStr str) {
		int sumWidth = strLine.width() + str.width();
		if(sumWidth > strLineWidth) {
			System.out.println("범위를 넘어서서 추가하지 않음.");
		}else {
			strLine.addLeftStr(str);
		}
	}
	
	// block을 합쳐줌
	public UIBlockBundle composeBlock(IUIBlock left, IUIBlock right) {
		UIBlockBundle result = new UIBlockBundle(left);
		result.addBlock(right);
		return result;
	}
	
	// str를 합쳐줌
	public UIStrBundle composeStr(IUIStr left, IUIStr right) {
		UIStrBundle result = new UIStrBundle(left);
		result.addStr(right);
		return result;
	}
	
	// 초기화
	private void reset() {
		blockLine = new UIBlockBundle();
		blockLineWidth = 0;
		strLine = new UIStrBundle();
		strLineWidth = 0;
	}
}
