# BlackJack (Aenean) V2
- 콘솔 BlackJack 프로그램.
- 재설계를 하고 최대한 반영하여 완성.

## 프로젝트 날짜
- 시작일 : 2023-10-26
- 딜레이 : 29 ~ 11-05
- 

## 계획과 목적
- 가장짧은시간에 완성하기
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
  + `게임상태변수`의 기능을 축소하고 각플레이어의 상태를 위한`플레이어상태변수`가 필요하다고 생각된다.
    * `플레이어진행`과 `플레이어완료` 상태가 각각 필요하다 판단됨.
    * `플레이어진행` : 화면출력과 플레이진행과 관련됨.
    * `플레이어완료` : 결과 계산과 금액정산과 관련됨 (BLACKJACK 등등)
  
## 게임용어정리
- 배팅 : 플레이어가 금액을 배팅.
- split : 첫 드로우때 같은숫자의 카드가 2장이면 쪼개서 각각 진행할수 있다.
- hit : 카드를 더 받겠다
- stay : 카드받는것을 멈추겠다.
- double down : 배팅금을 두배로 걸고, 한장만 더 받겠다
- bust : 21이 넘어가서 패배확정
- blackjack : 첫카드2장이 21. 게임에서 가장 큰 숫자.
  + 본 게임에서는 블랙잭일 경우 배팅금액의 2배를 돌려준다. (보통 1.5배 1.2배)
- insurance : 배팅금의 반을 수수료로 넣고 블랙잭이면 본전치기, 아니면 수수로만 잃고 게임진행.
  
## 알고리즘
### 시스템상태
- `시스템상태변수`
- `MAINMENU("메인메뉴")` : 메인메뉴를 고르고 있는 상태
- `PLAYGAME("게임진행중")` : 현재 게임을 진행하고 있는 상태
- `LOADING("게임진입중")` : 처음에 게임들어가기전 화면 보여주고 있는 상태
### 게임상태
- `게임상태변수`
- `READY("대기상태")` : 게임 대기상태. 대기상태가 필요하면 쓰려고일단 만듬.(삭제가능성)
- `PLAYERBET("플레이어들배팅중")` : 플레이어(들)이 돌아가며 배팅한다.
  + 배팅하기`전` QUEUE에 플레이어를 넣고 꺼내서 배팅.
- `INITDEAL("플레이어들과딜러에게카드2장딜링")` : 배팅이 완료되면 P,D 둘다 2장씩 딜링.
  + 초기딜링이 완료되면 QUEUE에 (D,P1...3) 순서대로 넣어준다.
- `PLAY("게임진행중")` : Queue에서 P,D 를 꺼내어 게임진행. (삭제가능성)
- `PLAYERPLAY("플레이어들게임진행중")` : Queue에서 순서대로 꺼낸 플레이어(들) 게임진행
- `DEALERPLAY("딜러게임진행중")` : Queue에서 딜러를 꺼내 게임진행
- `RESULTCALC("게임결과계산")` : 딜러와 플레이어를 비교하여 결과를 계산한다.
  + `CALC("게임결과에맞춰금액을정산")` : 아마도 결과계산하며 정산까지 하지 않을까?
### 플레이어상태
- `플레이어상태변수`
- 블랙잭
- 버스트
- 스탠드
- split 채크
  + split 입력대기에 추가.
- hit/stand/double 입력대기.
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
#### 기술적 서브미션.
- 상수를 Enum으로 관리.
  + 싱글톤 개념이 들어간 상수.
- 플레이어당 게임을 진행하는 구조는 Stack을 이용하면 될것 같다.
- PlayMachine 을 만들어 하부 구조를 먼저 만든다

### CardDto의 구조.
  + `suit`문양 과 `denomination` 숫자로 이루어짐
  + `suit`
    * ♠ Spade 
    * ◆ Diamond 
    * ♥ Heart 
    * ♣ Clover 
  + `denomination`
    *  숫자 2 ~ 9
    * 10(T), J, Q, K = 10 : 여기서는 10을 T로 표현해본다.
    * A = 1 or 11
- 프로그램 상에서는 문자열이나 특수문자로 가지고 있을필요에 대해서 모르겠다.
- 숫자형태의 데이터를 가지고 있다. 필요할때만 이미지나 문자열을 이용해 출력하면 될것이라 생각.
- 그래서 `Card` 객체의 `suit`와 `denomination`는 숫자를 가지고 잇으면 될것 같다.
    * 리스트in데이터 : A(1), T(10), J(11), Q(12), K(13)
    * SPADE(0, "♠"), DIAMOND(1, "♦"), HART(2, "♥"), CLOVER(3, "♣");
#### enum 이나 array냐
- 어떻게
  + `suit` : 숫자로 관리 하면서 문자열 출력.
    * 0~3 : 0 -> ♠
    * `suit[0] -> ♠`
  + `denomination` : 숫자로 관리하면서 (문자 숫자혼합) 출력. 값(숫자) 출력
    * 1~13 : 1 -> A, 1 -> 11, 2 -> 2, 2 -> 2
    * `demin[1].img -> A`
    * `demin[1].value> 11`
    * `demin[2].img -> 2`
    * `demin[2].value -> 2`
  + enum 보다 array로 관리하는것이 사용측면에서 더 편리해보임. (구조상)
  + enum은 숫자를 키값으로 사용할수 없어보임. (찾아보았지만 보이지 않음)
  + enum을 `card`객체에 직접 넣을수도 있겠지만. 사용이 복잡해지고
  + `denomination` 같은 경우는 2~9 사이의 값은 관리가 불가능
- 그래서 일관성 있게 `suit`, `denomination` 둘다 `array`로 관리하는게 좋아보임

### View 와 GameService의 연관성
- 공통으로 사용될 데이터가 필요.
- GameService에서 데이터를 처리후 view의 paint()를 실행함으로 화면을 출력처리.
- 그래서 GameService와 View 와의 공통 데이터를 가지고 있을 필요성이 있음. (예전에도 같은 형식으로 처리.)
- CommDataModule 을 만들어 공통으로 처리.
- 
### 객체의 누락에 대한.
- 주요객체를 누락해버릴수 있다고 생각함.
- 싱글톤으로 처리할수도 있지만 객체를 버전을 여러개 두어 만든다면 일치하지 않는 문제 발생 가능.
- 그래서. 최상의 클래스에서 설정해야할 객체들을(버전별로 여러개 만들수도 잇는) 생성해서 GameService나 View에 주입시킨다.

### 출력부 컬러문제.
- `│                              Dealer: 100,000                                             │`
- 공백을 처리하여 게임보드 틀을 만들 생각.
- 공백의 처리를 문자열 길이를 계산하여 +- 계산으로 처리할 예정
  + 문제는 문자열에 색을 입힐때 `\u001B[33m` 같은 코드를 사용하는데 문자열 갯수에 포함되어 틀이 깨짐
- 해결책은 (생각중) * 심플하게 처리할 방법으로 찾아서 선택.
  + 코드를 제거하고 겟수를 카운트 해주는 함수를 작성
  + 아니면 문자열과 코드를 함께 가지고 있는 객체를 따로 생성하여 처리.
- 해결 : 출력용 문자열 객체를 만들어 필요한 속성은 메소드를 통해 추출.
  - 해결키 : 출력 내용은 출력에만 집중하고 다른것들은 신경쓰지 않는다 
  - 하나의 문제는 하나의 솔루션으로.

### 여기까지 문제점 (24-01-13)
- 완성이 왜 더딘가?
  - 개인의 환경을 제외한 프로그램적 문제점만 바라보는 관점.
  - 집중하고 있던것. : 분리, 알고리즘 흐름.
  - 놓치고 있던것. : 최소단위의 확립, 객체의 변화에 대한 규칙.
  - 예를들어 카드라는 객체가 생성되고 어떤 형태로 변화되고 어떤 형태로 도달하여 화면에 출력하는지
    - 또한 각각 형태 상태에서 어떤 처리가 가능한지에 대한 규칙이 필요하다. 
- 너무 복잡하게 생각을 했다.
  - 최대한 심플하게 정리를 하고 일단 완성하고 나서 더 많은것을 배우고 그것을 적용할 새로운 프로젝트를 하자.
  - 핵심 프레임과 핵심 흐름을 정리 하고 구현하는게 중요.
    - (나중에 추가할때 문제가 생길수도 있다.) but 그냥 틀리고 문제가 생기자 그리고 그다음에 고치는 편이 좋을듯
      - 실제 커다란 프로젝트에선 논외 (작은 프로젝트에서 이것저것 해보는게 좋다고 판단.)
- 카드의 문자열 데이터(suit, denomination) 와 값의 분리
- 한 덩어리의 문제를 한꺼번에 해결하려고 시도하면 지치게 된다.
  - 하나의 고민 해결중에 다른 고민들이 한꺼번에 생각이 떠오르게 되어서 방해가 된다.
  - 가장 기초적인 부분을 먼저 정리하고 프레임을 짜고 그다음에 알고리즘을 생각하는 방향으로 결정.

### Card
- 카드 심볼, 카드 벨류, 카드 뷰어.
- 심볼 to 벨류
  - 매칭 배열을 사용하기도 애매 (연관성이 없음)
  - if문을 사용하기도 애매. (enum의 명칭이 숫자가 아닌 문자라서.)
- enum 자체에 value를 가지고 잇는게 가장적합해 보임. 

### 상태값
- 각각 처리메소드를 독립적으로 만들고, 상태값에 따라 처리하게끔 만들려고 함.
- 문제 : 여러상황을 복합적으로 처리하는 상태값을 만들려고 했음.
- 분석과 해결 : DB에서 배우는 정규화와 같은 개념이 아닌가 싶음 최소한의 단일 처리하는 부분을 만들고 합쳐야되는 명확한 이해득실을 따져 합칠것.

### 순서 메모
- 배팅받기전까지 세팅 
  - 플레이어 : 카드비우기, 스플릿카드비우기, 배팅칩, 스플릿칩, 인슈어런스칩, 인슈어런스 여부
  - 딜러 : 카드비우기
  - 덱 : 20장 이하 카드추가.
- 배팅
- 초기카드딜링 2장.
  - 인슈어런스 확인.
  - 블랙잭 확인.
- 인슈어런스
- 스플릿
- 더블다운
- 힛
- 스탠드
- 플레이어 플레이
- 딜러 플레이
- 버스트
- 17over