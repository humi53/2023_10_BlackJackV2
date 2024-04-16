package com.yopheu.games.aenean.models.ui;

/**
 * 문자열에 이미 ANSI 코드가 섞여있기 때문에 입력은 Block형태로만 받는다.
 * Block에서 width를 추출해서 totalWidth를 유지한다.
 * : board에 배치하기위해 합쳐서 나오는 객체.
 * @author HumiDK
 *
 */
public class UIBlockBundle implements IUIBlock{

	private String[] arrStr;
	private int totalWidth;
	
	public UIBlockBundle() {
		this.arrStr = new String[] {"","",""};
		totalWidth = 0;
	}
	
	public UIBlockBundle(IUIBlock block) {
		this.arrStr = block.getData();
		totalWidth = block.width();
	}
	
	public void addBlock(IUIBlock block) {
		String[] data = block.getData();
		for(int i = 0; i < arrStr.length; i++) {
			arrStr[i] += data[i];
		}
		totalWidth += block.width();
	}
	
	public void addLeftBlock(IUIBlock block) {
		String[] data = block.getData();
		for(int i = 0; i < arrStr.length; i++) {
			String temp = arrStr[i];
			arrStr[i] = data[i];
			arrStr[i] += temp;
		}
		totalWidth += block.width();
	}
	
	@Override
	public String[] getData() {
		return arrStr;
	}

	@Override
	public void print() {
		System.out.println(toString());			
	}
	
	@Override
	public String toString() {
		String result = "";
		String[] data = getData();
		for (int i = 0; i < data.length; i++) {
			result += data[i];
			if(i < data.length - 1) {
				result += "\n";
			}
		}
		return result;
	}

	@Override
	public int width() {
		return totalWidth;
	}
	
}
