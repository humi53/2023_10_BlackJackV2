# BlackJack (Aenean) V2
- 콘솔 BlackJack 프로그램.
- 재설계를 하고 최대한 반영하여 완성.

## 프로젝트 날짜
- 시작일 : 2023-10-26
- 딜레이 : 29 ~ 11-05
- 리워크 : 2024-03-15  ~ 2024-04-25
- 문서화 : 2024-05-02


# REWALK
## Thingking
- 03.15
  - 어떻게 기초를 다지고 계획을 세워 실행을 하여 완성에 다가설것인가?
  - 중요한 근본을 찾아내서 그것으로 바닥을 삼고 세워 올라가야 한다.
  - 무엇이 어떤것이 근본이 될 수 있을것인가?
  - 가장작은 데이터의 형태?, 객체의 구조?, flowchat?, 패키지 구조?, 기능들의 정의?, UI 디자인?, 
  - UI디자인 설계는 중요하고 시작지점이라고 보지만 프로그램적으로 봤을땐 원하는 결과 열매라고 본다. 전체의 시작이자 끝 부분.
    - 프로그램 개발적인 측면으로 봤을땐 근본으로 보기 힘들다 생각한다. 
    - (요구사항은 언제든지 어떤형식으로든지 바뀔수 있어 UI의 구조는 언제든 바뀔 수 있는 준비가 되어있어야 한다.)
    - UI 구성중 근본은 Frame이라고 생각한다. 공통적인 다형성을 추출할뿐 근본이라 보기 힘들다 생각한다.
    - 하지만 기둥들중 하나라고 생각된다.
  - 데이터의 형태, 객체의 요소, 클래스들을 하나의 근본이라 생각한다.
    - 기능적인 클래스들이 아닌, (카드, 플레이어, 딜러, 칩) 등을 표현한 클래스들이 근본이 될수 있을꺼라 생각한다.
    - 클래스 뿐만 아니라 데이터 자료구조를 통해 표현하는 모든 형태의 Structure
  - 데이터의 흐름
  - 특정상태의 정의와 변화에 대한 흐름.
  - 공통사항에 따른 분류된 패키지 구조.
  - 근본이 무엇인지는 알수가 없다. 그래서 내가 추구하는 방향성은
    - (프로젝트 유지보수와 변경사항에 따른 유연성)
    - 패키지 구조의 명확성(최대한) (이것을 근본으로 삼기로 한다.)
    - 명확히 하면 기능적 분리를 하여 틀을 분리한다.
  - 따라오는 것들
    - 데이터 형태의 정의, 사용방식, 사용처
    - 객체 상태의 정의와 흐름도
  - 아직 잘 모르겠다. 예전 생각과 비슷한 결과로 도달하는것으로 보인다.

- 04.01
  - 목표의 명확한 설정은... 모든것을 후순위로 미루고 : 심플한 구조
  - 현재 가장 중요시 하는부분은 데이터를 UI출력 데이터로 변동하는 구조이다.
  - 패키지 구조는 최대한 단순 그룹화 하여 간단하게 만든다.
  - 모듈별 테스트 할 수 있는 부분은 테스트 할 수 있게 끔 구조화 해서 만든다.
  - 결론: 심플하게 빨리 만들자.
  
- 04.17
  - board에 들어갈 조립객체들의 정의. UIStr, UIBorderStr, UICard. 
    - 그리고 관련된 인터페이스들...
  - 조립 객체들을 감싸고 있는 조립객체의 정의. UIStrBundle(1줄), UIBlockBundle(3줄, 카드등)
  - 생성하는 factory. UICardFactory, UIStrFactory
  - board에 집어넣기 직전까지 조립해주는 객체. BoardComposer : 크기지정우측정렬 등 기능추가.
  - 복잡해지는 이유는 board의 구조로 인해. 
    - 각행은 배열로 구분되지만 각열?, 각각 한칸은 문자열이 나열된것 뿐이라 자유자재로 다루기 위해선...
    - 또하나는 문자열에 색을 넣고 싶어서. 정해진 화면 크기에 출력하려 하기때문에.
      - ANSI코드를 제거한 후 간단히 길이를 구하는 코드가 있긴함. but. 상속관계와 객체를 다루는 방법을 익히는등 얻는게 많았다고 봄.
      - 색을 임의로 변경할 수 있음. 원하는 부분만 색을 칠 할 수 있음. 조립전 코드에서 그 부분의 색을 바꿀수 있음. (출력직전 객체에서 변경은 불가능)
      - 공통사항의 룰로 내용만 다르게 여러객체가 생성가능.
  - Block(3줄을 정의한명칭) 단위로 board에 샘플출력 완료.
  
- 04.18
  - deckDto, palyerDto, playerSplitDto, 간단한 정의
    - splitDto는 따로 분리하여 카드 나눠주기등 편하게 사용할 수 있을꺼 같아 시도하는중.
    - ICardHand를 상속받아 카드를 나눠주는 Handler 같은것을 만들어 사용하려 시도.
  - PlayerDto 와 PlayerSplitDto 의 Chip bet 관련 간단한 테스트 사용후 통과.
  - 둘 관계의 Interface를 만들까 싶은데 알고리즘 부분에서는 큰의미가 없다고 생각
    - 몇몇 부분이 가능성 있을꺼 같은데 특히나 UI에서 사용하는 측면으로 interface를 사용을 고려할듯 싶지만
    - UI 부분에서는 Dto보단 Card나 CardArray를 직접적으로 다루어 이미지를 구현하여 출력할것이기 때문에 큰 연관성이 없을것 같다.
    - 이둘의 관계를 interface화 시키는것은 최대한 지양할 것으로 결정했지만 가능성에 대해서는 생각해보기로 한다.

- 04.20
  - GameService의 bet 메소드 작성.
    - bet의 예외처리등의 흐름과 입력 흐름을 분리하였음.
    - 작성이 될수록 점점 복잡해져서, 원래 복잡한건지. 나눠서 더 복잡해진건지 알수 없다.
    - scan을 하면서 1차처리, 받아온것으로 2차처리 되는것 같아 보이고, 같은 내용을 중복으로 처리하는것 같은 찜찜함이 있다.
    - 하지만 입력부를 자유분방하게 아무렇게나 써도 부담이 없다.
      - 만약 나누지 않았다면 철저하게 한곳으로 몰아 명확한 절차를 계획하거나 그게 불가능하면 복붙 범벅이 될것이다.
      - 복붙 범벅 구조로 되어도 코드가 심플하게 만들어진다. 입력부와 처리부를 왔다갔다 하는 흐름을 읽어야 되는것은 혹 다시 보게될 미래의 나의 담당이.
      - 작성 직후라 보기엔 편하게 느껴진다.
  - 입력시 발생하는 예외처리.
    - 사용자 정의 익셉션으로 처리 하는게 좋아보이는데... 아직은 머릿속이 복잡하다.
  
- 04.22
  - 텍스트 입력을 받을때, 제공해주는 범위를 벗어나는 입력의 예외와, 알고리즘 조건에 맞지 않아 발생하는 예외는 다른것이다.
    - 입력을 다시 받아야 된다는 개념으로는 같은것 같지만, 하나는 원초적인것이고, 하나는 복합적이고 절차가 추가된다는것이다.
    - 그래서... 입력부분의 구조를 다른 구조로 만들어 보아야겠다. 힘들겠지만 일관성을 위해 만들었던것도 바꾸는쪽으로...
  - scan과 알고리즘 처리부분을 나눔으로써 발생하는 문제
    - scan자체적으로 예외발생시 재반복을 해야하는 경우가 명확히 발생한다.
    - 재반복 할때는 UI의 출력 메소드를 호출하게 된다.
    - 해결을 하기위해서는 2가지의 방법을 생각해본다.
      - scan객체에 ui의 객체를 넣어서 처리하는방법. (이건 별로. 왜냐하면 서로 연관이 복잡하게 얽히게 되는거 같다. 현재 프로그램에서는 큰 의미는 없지만)
      - callback을 이용아여 scan의 메소드를 실행할때 알고리즘처리 객체에 구현한 메소드를 넘겨줘 실행시켜주는것.
        - 복잡해질수도 있겠으나 1) 얻는게 많아보이고 2) scan부분을 나눠서 처리하면서 내가 원하는 결과를 만들어내기에 확실히 필요한 구조라 생각한다.
    - callback 클래스를 메소드마다 파라미터로 넣어서 호출할것인가. 아니면 set을 이용하여 클래스에 설정해놓고 모든 메소드에서 접근하게 할것인가.
  - 또다른 문제 : paint() 화면출력은 어떻게 해야하는가.
    - gameBoard를 출력, 에러나 경고및 유도 메시지, 안내메시지 + 입력 프롬프트.
    - board는 UI가, 애러 + 안내와 입력프롬프트는 scan이 가지고 있다.
    - 2가지 모두 gameService에서 실행하고 싶지만... ui 와 scan이 얽혀버린다.
      - scan이 입력과 관련된 출력을 가지고 있기 때문에, 입력 예외가 발생시 board를 같이 재 출력해야되기 때문에
      - 구조상 board 출력이 gameService와 scanService 두곳에서 호출하게 되어버린다. (일관성이 무너진다.)
      - 여기서 추구하고자 하는것은 board 출력에 대한 일관성이고.... 생각이 더 필요.
      - GameService에서 모든 출력을 담당하게 되면 scan과 gameService 관계에서 많은 데이터 교환이 필요해진다.
        - 데이터 교환이 늘어나는 부분에선 scan에서 출력을 알고리즘적인 측면에서는 GameService에서 출력을 하면 되지 않을까? (타협)
        - 확실한것은 scan에서 안내메시지와 프롬프트를 출력할땐 항상 board가 출력되어야 되기 때문에 scan이 감당하는 쪽이...
        - 그래서 입력이 필요한부분에서는 scan에서 처리하고, 입력이 없는 구조에선 board에서.
      - 다른방향... callback을 gameService의 기능을 scan에서 실행하는게 아닌
        - scan의 출력 메소드를 callback을 이용해 gameService에서 호출하는건 어떤가?
      - 해결방안제시. scan을 위한 출력 문자열들을 enum에 넣어서 가지고 있다 출력.
        - enum에는 객체가 들어갈수 없다. 정확히는 권장하지 않는다.

- 04.23
  - 입력부를 위한 출력까지 분리. 
  - 구조는 복잡해진것 같지만 분리는 최대한 온전히 이루어졌고, 순전한 입력오류와, 논리적으로 인한 예외를 분리시켰고, 입력과 입력의출력을 따로 관리함.
    - 하나를 추가할때 여러곳을 만져야 하지만 변경을 하려 하였을때 구조가 이해되면 빠른 접근이 가능.
  - 결국 입력에 관련된 출력은 UI쪽으로 속해서 그분류로 관리하게 되었음.
  - 안내메시지, 예외메시지, 프롬프트.

- 04.24
  - flow의 핵심적인 줄기만 초벌 완성.
    - 다음목표 : 각 모듈별로 테스트하면서 완성도 올리기 

- 04.25 (완성)
  - 구조적인 부분은 완료. 예전 파일들은 모두 제거.
  - 사소한 출력오류같은게 존재해 보임. 테스트하면서 많이 수정함.
  - 게임 종료시점이 없음.
  - UI 폰트색을 조금 바꾸고, 문서화 시키면 좋을듯
    - 게임 사용매뉴얼과
    - 프로그램 구조 설명 매뉴얼.
  - 이대로 완료시키고 다른 공부나 프로젝트로...
  - 혹시 시간이 되고 다시 관심을 가진다면 (안할듯)
    - 종료시점 만들기.
    - 세이브 로드, 메뉴 추가하기.
    
- 05.02
  - 문서화 종료.
  - 블랙잭 매뉴얼.pptx
  - 블랙잭 프로그램 구조.pptx
  - 
----------------------------------------------------
# 예전
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