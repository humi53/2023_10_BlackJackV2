package com.yopheu.aenean.models.entry;

import java.util.ArrayList;

import com.yopheu.aenean.config.PlayResultState;
import com.yopheu.aenean.config.PlayingState;
import com.yopheu.aenean.models.Card;
import com.yopheu.aenean.models.money.Account;
import com.yopheu.aenean.models.money.UQAccount;

public class Player implements Entry {
	protected Account account = null;	// 전체 잔고
	// 배팅은 3가지이다.
	// 기본배팅값 (다음배팅때 재활용) (보이진 않음?)
	// 실제걸린배팅값 (더블 등등) (보이는 값)
	// 사용된돈 (현 게임에서 사용된돈.)
	
	// 20, 40, 100, 200, 400, 500, 1000
	protected int basicBet = 0;		// 기본 배팅값
	protected int nowBet = 0;	// 실제 배팅
	protected int useMoney = 0;	// 현재 사용된 돈 (이기게 되면 +됨)
	protected int accrueMoney = 0;	// 전체 실익 누적
	
	protected boolean insurance = false;	// 딜러오픈카드가 A일경우
	protected PlayingState playingState = PlayingState.WAITING;
	protected PlayResultState playResultState = PlayResultState.PENDING;
	protected boolean cardSplit = false;
	protected PlayerSplit splitClone = null;
	
	protected ArrayList<Card> arrCard = null;
	protected String name;
	@Override
	public void init() {
		setMoney(50000);	// 기본잔고
		arrCard = new ArrayList<>();
	}
	
	/**
	 * Player 공통 account 설정
	 */
	public Player(String name) { 
		this.account = UQAccount.getInstance();
		this.name = name;
		init();
	}

	/**
	 * Player 독립 account 설정
	 * @param account : 계정별 account // split할때도 쓰임.
	 */
	public Player(Account account, String name) {
		this.account = account;
		this.name = name;
		init();
	}
	
	public String getName() {
		return name;
	}

	// account의 금액 설정.
	@Override
	public boolean setMoney(int money) {
		return account.setMoney(money);
	}
	// account에 금액 추가.
	@Override
	public boolean addMoney(int money) {
		return account.addMoney(money);
	}
	// account의 금액 확인.
	@Override
	public int getMoney() {
		return account.getMoney();
	}
	// 베팅하기 
	public boolean betting(int betMoney) {
		if(!account.subMoney(betMoney)) {
			return false;
		} else {
			this.useMoney = -betMoney;
			this.basicBet += betMoney;	
			this.nowBet += betMoney;
			return true;
		}
	}
	public void cancelBet() {
		account.addMoney(nowBet);
		this.nowBet = 0;
		this.basicBet = 0;
		this.useMoney = 0;
	}
	public int getBasicBet() {
		return basicBet;
	}
	public int getNowBet() {
		return nowBet;
	}
	// 더블배팅하기
	public boolean doubleDown() {
		if(!account.subMoney(basicBet)) {
			return false;
		} else {
			this.useMoney += -this.basicBet;
			this.nowBet += this.basicBet;
			return true;
		}
	}
	
	// 스플릿하기
	public PlayerSplit getSplit() {
		// 스플릿객체 생성
		// 스플릿객체에 계좌연결
		// 스플릿설정 on
		// 스플릿객체 반환
		return null;
	}
	// 인슈어런스
	
	
	// 배팅하기
	
	// 딜링(게임플레이)
	// 입력대기상태
	// 힛, 스탠, (스플릿), (인슈어런스)
	// 결과상태 도출 (블랙잭, 버스트)
	// 승패여부 도출 (블랙잭승, 일반승(), 버스트패, 일반패)
	
	
	
//	public boolean won(int multiplier) {
//		betMoney = 0;
//		return account.addMoney(betMoney * multiplier);
//	}
//	public int lost() {
//		int temp = betMoney;
//		betMoney = 0;
//		return temp;
//	}
//	
//	public boolean successInsurance() {
//		account.addMoney(betMoney);
//		insurance = false;
//		return true;
//	}
//	
//	public boolean setInsurance() {
//		insurance = account.subMoney(betMoney/2);
//		return insurance;
//	}
	
	// 3. 카드 받기, 카드 버리기
	@Override
	public void addCard(Card card) {
		arrCard.add(card);
	}
	
	@Override
	public ArrayList<Card> getCard(){
		return arrCard;
	}
	
	@Override
	public void clearArrCard() {
		arrCard.clear();
	}
	
	
	// 진행상태
	@Override
	public PlayingState setPlayingState(PlayingState playingState) {
		this.playingState = playingState;
		return this.playingState;
	}
	@Override
	public PlayingState getPlayingState() {
		return this.playingState;
	}
	
	// 결과상태
	@Override
	public PlayResultState setPlayResultState(PlayResultState playResultState) {
		this.playResultState = playResultState;
		return this.playResultState;
	}
	@Override
	public PlayResultState getPlayResultState() {
		return this.playResultState;
	}
	
	// 카드점수 계산
	@Override
	public int getCardScore() {
		// 카드 점수를 계산해서 반환
		return 0;		
	}
	
	// 승리여부 확인 후 처리
	public boolean processWin(boolean win) {
		// 승리확인 처리.
		//		블랙잭 (3배)
		//		일반승리 (2배)
		return true;
	}
	
	@Override
	public boolean processCompletion() {
		// 수익 account 반영
		// 카드 초기화
		return true;
	}
	
}
