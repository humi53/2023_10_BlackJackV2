
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
	}
	
	public static void printTest(Test test) {
		System.out.println(test instanceof TestA);
		if(test instanceof TestA)
		((TestA) test).a();
	}
}

