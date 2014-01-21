import javax.swing.JButton;

public class terrianTile extends JButton {
	private static final long serialVersionUID = 1L;
	private String terrainType;
	private String subType;
	private boolean chest = false;
	private boolean enemy = false;
	
	public terrianTile(String type, String subType, boolean enemy, boolean chest){
		this.terrainType = type;
		this.subType = subType;
		this.enemy = enemy;
		this.chest = chest;
	}// End Constructor

	public String getTerrainType() {
		return terrainType;
	}

	public void setTerrainType(String terrainType) {
		this.terrainType = terrainType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public boolean isEnemy() {
		return enemy;
	}

	public void setEnemy(boolean enemy) {
		this.enemy = enemy;
	}

	public boolean isChest() {
		return chest;
	}

	public void setChest(boolean chest) {
		this.chest = chest;
	}

}// End Class
