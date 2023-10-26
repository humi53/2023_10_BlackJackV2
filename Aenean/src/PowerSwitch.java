

public enum PowerSwitch {
	ON("켜짐"),
	OFF("꺼짐");
//	ON,
//	OFF;
	
	private String krName;
	private PowerSwitch() {
		
	}
	
	private PowerSwitch(String krName) {
		this.krName = krName;
	}
	
	public String getKrName() {
		return krName;
	}
	
	public PowerSwitch oppsite() {
		if (this == PowerSwitch.ON) {
			return PowerSwitch.OFF;
		}else {
			return PowerSwitch.ON;
		}
	}
	
}
