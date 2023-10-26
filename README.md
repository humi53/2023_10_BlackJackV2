# BlackJack (Aenean) V2
- 콘솔 BlackJack 프로그램.
- 재설계를 하고 최대한 반영하여 완성.

## 프로젝트 날짜
- 시작일 : 2023-10-26

## 계획과 목적
- 공통상수를 가지고 있는 Config.
- 게임 상태에 따라 진행을 하게 만든다
  - 게임 상태를 먼저 정의 필요. `게임상태변수`
- 게임을 진행하는 `서비스`와 출력하는 `출력부`를 최대한 분리.
  - 의존성 주입과 제어권은 어느쪽으로 붙일것인가.
  - 초기셋팅
  - Controller
  - Service는 View 와 DataModel을 가지고 있는다
  - View는 DataModel을 가지고 있는다.
  - Service에서 DataModel의 Data를 변경하고 View에 paint() 신호를 보낸다.
- 시스템반복과 게임반복의 분리.
  + 화면출력부의 반복. (시스템상태로 처리) `시스템상태변수`
- 출력반복을 위한
  + 출력상태를 따로 저장할것인가, 시스템상태에 남길것인가.
  + 시스템상태는 고유의 상태로 남겨져 있어야 될것같다. 기능상분리가 좋다고 판단 `출력상태변수`
  
## 게임용어정리
- 배팅 : 플레이어가 금액을 배팅.
- split : 첫 드로우때 같은숫자의 카드가 2장이면 쪼개서 각각 진행할수 있다.
- hit : 카드를 더 받겠다
- stay : 카드받는것을 멈추겠다.
- double down : 배팅금을 두배로 걸고, 한장만 더 받겠다
- bust : 21이 넘어가서 패배확정
- blackjack : 카드의 총합이 21. 게임에서 가장 큰 숫자.
  + 본 게임에서는 블랙잭일 경우 배팅금액의 2배를 돌려준다. (보통 1.5배 1.2배)
  
## 알고리즘
### 시스템상태
- `시스템상태변수`
- 시스템매뉴
- 게임진입
### 게임상태
- `게임상태변수`
- 메인화면
- 배팅 입력대기
- 2장씩 드로우
- split 채크
	+ split 입력대기에 추가.
- hit/stay 입력대기.
- 버스트체크
	+ 플레이어 버스트
	+ 딜러 버스트
### 출력상태
- `출력상태변수`
- 입력맨트를 위한 출력
  + bet
  + hit/stay
  + hit/stay/split
- 게임진행 단일출력
- 게임진행 자동반복출력
  + 게임서비스와 관계없이 자동으로 반복할만한 부분이 있는가?
  + 매인화면밖에... 더생각해보자.
  
### 일단 만들면서.
- 만들면서 구조를 설계해보자.