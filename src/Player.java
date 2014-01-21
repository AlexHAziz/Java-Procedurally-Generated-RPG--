import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/*
 * Add level up system
 */
public class Player implements ActionListener {
	private String[] classList = { "fire", "water", "earth", "air" };
	private ButtonGroup group = new ButtonGroup();
	private JFrame frame = new JFrame();
	private String name;
	private String playerClass;
	private double hp = 0;
	private int xp = 0;
	private int level = 1;
	private int strength;
	private int speed;
	private boolean playerReady = false;
	private boolean fly = false;
	private boolean sail = false;
	private double money = 20;

	/*
	 * 0 = head 1 = torso 2 = legs 3 = feet
	 */
	private Item[] equipment = new Item[4];// Equipment is stored here
	private Attack[] attacks = new Attack[4];// List of Attacks,
	private int[] attackUses = { 4, 4, 4, 4 };// Keeps track of attack uses
	private double baseHP = 50;

	public Player() {
		name = JOptionPane.showInputDialog(null, "What is your name?");
		this.pickPlayerClass();
	}// End constructor

	public Player(String enemyName, String enemyClass) {
		name = enemyName;
		playerClass = enemyClass;
		this.buildPlayer();
	}// End constructor

	public Player(String name, String playerClass, String level, String xp,
			String strength, String speed, String hp, String location1,
			String location2, String spawn1, String spawn2,
			String numberOfRelemsSTR, String currentRelemSTR,
			ArrayList<int[][][]> relems) {
		this.name = name;
		this.playerClass = playerClass;
		this.level = Integer.parseInt(level);
		this.xp = Integer.parseInt(xp);
		this.strength = Integer.parseInt(strength);
		this.speed = Integer.parseInt(speed);
		this.hp = Double.parseDouble(hp);
		int locationX = Integer.parseInt(location1);
		int numberOfRelems = Integer.parseInt(numberOfRelemsSTR);
		int currentRelem = Integer.parseInt(currentRelemSTR);
		int locationY = Integer.parseInt(location2);
		int spawnX = Integer.parseInt(spawn1);
		int spawnY = Integer.parseInt(spawn2);
		this.buildPlayer();
		@SuppressWarnings("unused")
		SinglePlayerGame newGame = new SinglePlayerGame(this, numberOfRelems,
				currentRelem, relems, locationX, locationY, spawnX, spawnY); 
	}// End Constructor

	private void pickPlayerClass() {
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		// Make Buttons
		for (int i = 0; i < classList.length; i++) {
			JRadioButton newRadioButton = new JRadioButton(classList[i]);
			newRadioButton.setActionCommand(classList[i]);
			newRadioButton.addActionListener(this);
			group.add(newRadioButton);
			radioPanel.add(newRadioButton);
		}// End For
		JButton doneButton = new JButton("Done");
		doneButton.setActionCommand("done");
		doneButton.addActionListener(this);
		radioPanel.add(doneButton);
		frame.setTitle("Pick a Game Mode?");
		frame.add(radioPanel);
		frame.pack();
		frame.setVisible(true);
	}// End getClass

	private void buildPlayer() {
		if (playerClass.equals("fire")) {
			equipment[0] = new Item("Hat", "earth", 5);
			equipment[1] = new Item("Shirt", "earth", 15);
			equipment[2] = new Item("Pants", "earth", 10);
			equipment[3] = new Item("Shoes", "earth", 5);

			attacks[0] = new Attack("Punch", "none", 5);
			attacks[1] = new Attack("Kick", "none", 10);
			attacks[2] = new Attack("Fireball", "fire", 15);
			attacks[3] = new Attack("Blaze", "fire", 20);
		} else if (playerClass.equals("water")) {
			equipment[0] = new Item("Hat", "fire", 5);
			equipment[1] = new Item("Shirt", "fire", 15);
			equipment[2] = new Item("Pants", "fire", 10);
			equipment[3] = new Item("Shoes", "fire", 5);

			attacks[0] = new Attack("Punch", "none", 5);
			attacks[1] = new Attack("Kick", "none", 10);
			attacks[2] = new Attack("Water Bomb", "water", 15);
			attacks[3] = new Attack("Wave", "water", 20);
		} else if (playerClass.equals("earth")) {
			equipment[0] = new Item("Hat", "air", 5);
			equipment[1] = new Item("Shirt", "air", 15);
			equipment[2] = new Item("Pants", "air", 10);
			equipment[3] = new Item("Shoes", "air", 5);

			attacks[0] = new Attack("Punch", "none", 5);
			attacks[1] = new Attack("Kick", "none", 10);
			attacks[2] = new Attack("Rock", "earth", 15);
			attacks[3] = new Attack("Quake", "earth", 20);
		} else if (playerClass.equals("air")) {
			equipment[0] = new Item("Hat", "water", 5);
			equipment[1] = new Item("Shirt", "water", 15);
			equipment[2] = new Item("Pants", "water", 10);
			equipment[3] = new Item("Shoes", "water", 5);

			attacks[0] = new Attack("Punch", "none", 5);
			attacks[1] = new Attack("Kick", "none", 10);
			attacks[2] = new Attack("Breeze", "air", 15);
			attacks[3] = new Attack("Gust", "air", 20);
		}// End if to initialize player

		this.hp = 50;
		this.level = 1;
		this.strength = 10;
		this.speed = 10;
	}// End build

	// Getters and Setters

	public double getHp() {
		return hp;
	}

	public String getPlayerClass() {
		return playerClass;
	}

	public int[] getAttackUses() {
		return attackUses;
	}

	public void setAttackUses(int[] attackUses) {
		this.attackUses = attackUses;
	}

	public int getXp() {
		return xp;
	}

	public void setXp(int xp) {
		this.xp = xp;
		// Level up after 100 xp
		if(this.xp%100==0){
			level++;
		}// End if
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Item[] getEquipment() {
		return equipment;
	}

	public void setEquipment(Item[] equipment) {
		this.equipment = equipment;
	}

	public Attack[] getAttacks() {
		return attacks;
	}

	public void setAttacks(Attack[] attacks) {
		this.attacks = attacks;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isPlayerReady() {
		return playerReady;
	}

	public void setPlayerReady(boolean playerReady) {
		this.playerReady = playerReady;
	}

	public boolean isFly() {
		return fly;
	}

	public void setFly(boolean fly) {
		this.fly = fly;
	}

	public boolean isSail() {
		return sail;
	}

	public void setSail(boolean sail) {
		this.sail = sail;
	}

	// End Getters and Setters

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getBaseHP() {
		return baseHP;
	}

	public void setBaseHp(double baseHP) {
		this.baseHP = baseHP;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String source = event.getActionCommand();
		if (source.equals("done")) {
			frame.dispose();
			this.playerClass = this.group.getSelection().getActionCommand();
			this.buildPlayer();
			@SuppressWarnings("unused")
			SinglePlayerGame newGame = new SinglePlayerGame(this); // Starts the
																	// Single
																	// Player
																	// Game
		}// End if
	}// End actionPerformed

}// End class