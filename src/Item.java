public class Item {
	private String name;
	private int resistance;
	private String typeResistance;

	public Item (String name, String typeResistance, int resistance) {
			this.name = name;
			this.resistance = resistance;
			this.typeResistance = typeResistance;
	}// End Constructor 

	// Getters and Setters 
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResistance() {
		return resistance;
	}

	public void setResistance(int resistance) {
		this.resistance = resistance;
	}

	public String getTypeResistance() {
		return typeResistance;
	}

	public void setTypeResistance(String typeResistance) {
		this.typeResistance = typeResistance;
	}
	
	// End Getters and Setters 
	
}// End Class
