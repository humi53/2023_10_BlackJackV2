import com.yopheu.aenean.config.PlayResultState;
import com.yopheu.aenean.models.CardDenomination;
import com.yopheu.aenean.models.CardSuit;
import com.yopheu.aenean.service.GameService;
import com.yopheu.aenean.service.impl.GameServiceImplV1;
import com.yopheu.aenean.service.modules.CardDeckModule;
import com.yopheu.aenean.service.modules.impl.CardDeckModuleImplV1;

// 주석 Test
public class Hello {
	public static void main(String[] args) {
		System.out.println("돌아왓구나 Java");
		TestA testA = new TestA();
		TestB testB = new TestB();
		System.out.println(testA.getClass());
		printTest(testA);
		printTest(testB);
		
		PowerSwitch powerSwitch = PowerSwitch.ON;
		powerSwitch = PowerSwitch.OFF;
		System.out.println(powerSwitch);
		
		CardSuit cardSuit = CardSuit.SPADE;
		System.out.println(CardSuit.fromValue(1));
		
		CardDenomination cardDenomination = CardDenomination.fromValue(13);
		System.out.println(cardDenomination);
//		CardDeckModule cardDeckModuleImplV1 = new CardDeckModuleImplV1();
//		cardDeckModuleImplV1.getCardDeck();
		
		
		
	}
	
	public static void printTest(Test test) {
		System.out.println(test instanceof TestA);
		if(test instanceof TestA)
		((TestA) test).a();
	}
}

