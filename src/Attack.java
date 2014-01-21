public class Attack {
	private String name;
	private String type;
	private int damage;
	private int cost;

	public Attack(String name, String type, int damage) {
		super();
		this.name = name;
		this.type = type;
		this.damage = damage;
	}// End Constructor
	
	// Getters and Setters 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	// End Getters and Setters 
	
}// End Class
