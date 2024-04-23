package com.yopheu.games.aenean.service.impl;

import com.yopheu.games.aenean.models.CommDataWrapper;
import com.yopheu.games.aenean.models.ui.UIBoardRenderer;
import com.yopheu.games.aenean.models.ui.UIMessageRenderer;
import com.yopheu.games.aenean.service.UIService;

public class CUIServiceImplV1 implements UIService {

	CommDataWrapper cData;
	UIBoardRenderer boardRenderer;
	UIMessageRenderer messageRenderer;
	
	public CUIServiceImplV1(CommDataWrapper cData) {
		this.cData = cData;
		boardRenderer = new UIBoardRenderer(cData);
		messageRenderer = new UIMessageRenderer(cData.getStates());
	}
	
//	private 
	
	@Override
	public void paint() {
		boardRenderer.setBoard();
		boardRenderer.printBoard();
		messageRenderer.setMessages();
		messageRenderer.printMessages();
	}

	@Override
	public void repaint() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
