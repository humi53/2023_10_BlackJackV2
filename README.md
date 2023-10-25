# BlackJack (Aenean) V2
- 콘솔 BlackJack 프로그램.
- 재설계를 하고 최대한 반영하여 완성.
## 프로젝트 날짜
- 시작일 : 2023-10-26
### 계획과 목적
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
  + 
  
### 알고리즘
#### 게임상태
- 메인화면
- 배팅 입력대기
- 2장씩 드로우
- split 채크
	+ split 입력대기에 추가.
- hit/stay 입력대기.
- 버스트체크
	+ 플레이어 버스트
	+ 딜러 버스트