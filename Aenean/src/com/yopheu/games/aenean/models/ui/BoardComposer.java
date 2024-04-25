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
		if(block == null) {
			block = new UIBlockBundle();
		}
		blockLineWidth = lineWidth;
		blockLine.addBlock(block);
	}
	
	public void initStrLine(IUIStr str, int lineWidth) {
		reset();
		if(str == null) {
			str = new UIStrBundle();
		}
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
	
	public void addLastBlock(IUIBlock lastBlock) {
		int sumWidth = blockLine.width() + lastBlock.width();
		if(sumWidth > blockLineWidth) {
			System.out.println("범위를 넘어서서 추가하지 않음.");
		}else {
			// 전체 길이 - (기존블럭 + 마지막블럭) 길이 = 사이의 남은 공간.
			int spaceWidth = blockLineWidth - (blockLine.width() + lastBlock.width());
			// 공간 block 생성.
			UIStr spaceBlock = new UIStr(" ".repeat(spaceWidth), null);
			blockLine.addBlock(spaceBlock);
			blockLine.addBlock(lastBlock);
		}
	}
	
	public void addLastStr(IUIStr lastStr) {
		int sumWidth = strLine.width() + lastStr.width();
		if(sumWidth > strLineWidth) {
			System.out.println("범위를 넘어서서 추가하지 않음.");
		}else {
			// 전체 길이 - (기존블럭 + 마지막블럭) 길이 = 사이의 남은 공간.
			int spaceWidth = strLineWidth - (strLine.width() + lastStr.width());
			// 공간 block 생성.
			UIStr spaceStr = new UIStr(" ".repeat(spaceWidth), null);
			strLine.addStr(spaceStr);
			strLine.addStr(lastStr);
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
	
	public UIBlockBundle getBlockLine() {
		// 길이가 채워지지 않았으면 공백을 채워준다.
		if(blockLine.width() < blockLineWidth) {
			int spaceWidth = blockLineWidth - blockLine.width();
			UIStr spaceBlock = new UIStr(" ", null, spaceWidth);
			blockLine.addBlock(spaceBlock);
		}
		// 리턴.
		return blockLine;
	}
	
	public UIStrBundle getStrLine() {
		// 길이가 채워지지 않았으면 공백을 채워준다.
		if(strLine.width() < strLineWidth) {
			int spaceWidth = strLineWidth - strLine.width();
			UIStr spaceStr = new UIStr(" ", null, spaceWidth);
			strLine.addStr(spaceStr);
		}
		// 리턴.
		return strLine;
	}
	
	// block을 합쳐줌
	public UIBlockBundle composeBlock(IUIBlock left, IUIBlock right) {
		UIBlockBundle result = new UIBlockBundle(left);
		result.addBlock(right);
		return result;
	}
	
	public UIBlockBundle rightBlock(IUIBlock block, int aimWidth) {
		UIBlockBundle result = new UIBlockBundle();
		int spaceWidth = aimWidth - block.width();
		UIStr spaceBlock = new UIStr(" ", null, spaceWidth);
		result.addBlock(spaceBlock);
		result.addBlock(block);		
		return result;
	}
	
	// str를 합쳐줌
	public UIStrBundle composeStr(IUIStr left, IUIStr right) {
		UIStrBundle result = new UIStrBundle(left);
		result.addStr(right);
		return result;
	}
	
	public UIStrBundle rightStr(IUIStr str, int aimWidth) {
		UIStrBundle result = new UIStrBundle();
		int spaceWidth = aimWidth - str.width();
		UIStr spaceStr = new UIStr(" ", null, spaceWidth);
		result.addStr(spaceStr);
		result.addLeftStr(str);
		return result;
	}
	
	public int getBlockLineWidth() {
		return blockLineWidth;
	}
	
	public int getStrLineWidth() {
		return strLineWidth;
	}
	
	// 초기화
	private void reset() {
		blockLine = new UIBlockBundle();
		blockLineWidth = 0;
		strLine = new UIStrBundle();
		strLineWidth = 0;
	}
}
