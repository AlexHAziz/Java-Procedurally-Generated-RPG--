import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Create Shops
 */
public class SinglePlayerGame implements ActionListener {
	private Player player;
	private int x = 100;
	private int y = 100;
	private ArrayList<int[][][]> relems = new ArrayList<int[][][]>();
	private int[][][] worldMap = new int[x][y][2];
	private int[] spawnPoint = new int[2];
	private int[] location = new int[2];
	private int currentRelem = 0;
	private JFrame compass;
	private JFrame miniMap;
	private JFrame gameWindow;
	private JFrame playerWindow;
	private terrianTile[][] adjacentTiles = new terrianTile[5][5];
	private String[] terrainTypes = { "Water", "Beach", "Plains", "Forest",
			"Mountain", "Store" };
	private String[] waterSubTypes = { "Ocean", "Lake", "River", "Swamp" };
	private String[] beachSubTypes = { "Sand", "Pebble" };
	private String[] plainsSubTypes = { "Grass", "Flood", "Marsh", "Valley",
			"Hilly", "Desert" };
	private String[] forestSubTypes = { "Jungle", "Pine", "Redwood", "Rain" };
	private String[] mountianSubTypes = { "Small", "Medium", "Large", "Volcano" };
	private String[] storeTypes = {"General"};
	private ArrayList<String[]> subTypeArrays = new ArrayList<String[]>();

	public SinglePlayerGame(Player player, int numberOfRelems,
			int currentRelem, ArrayList<int[][][]> relems, int locationX,
			int locationY, int spawnX, int spawnY) {
		this.player = player;
		this.relems = relems;
		this.currentRelem = currentRelem;
		this.worldMap = new int[x][y][2];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				for (int k = 0; k < 2; k++) {
					this.worldMap[i][j][k] = relems.get(currentRelem)[i][j][k];
				}// End for
			}// End for
		}// End for
		this.location[0] = locationX;
		this.location[1] = locationY;
		this.spawnPoint[0] = spawnX;
		this.spawnPoint[0] = spawnY;
		this.relems.add(worldMap);
		this.createRelem();
		this.createCompass();
		this.createGameWindow();
	}// End Constructor for game loaded from save

	public SinglePlayerGame(Player player) {
		this.player = player;
		this.createRelem();
		this.createCompass();
		this.createPlayerWindow();
	}// End Constructor for new game

	private void loadRelem() {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < x; j++) {
				for (int k = 0; k < 2; k++) {
					worldMap[i][j][k] = relems.get(currentRelem)[i][j][k];
				}// End for
			}// End for
		}// End for
		this.createGameWindow();
	}// End createMap

	private void createRelem() {
		subTypeArrays.add(waterSubTypes);
		subTypeArrays.add(beachSubTypes);
		subTypeArrays.add(plainsSubTypes);
		subTypeArrays.add(forestSubTypes);
		subTypeArrays.add(mountianSubTypes);
		Random rand = new Random();
		int seed = rand.nextInt(terrainTypes.length);
		for (int i = 0; i < worldMap.length; i++) {
			for (int j = 0; j < worldMap[0].length; j++) {
				if (i == 0 && j == 0) {
					worldMap[i][j][0] = seed;
					worldMap[i][j][0] = rand
							.nextInt(subTypeArrays.get(seed).length);
				} else if (i == 0 && j > 0) {
					ArrayList<Integer> aviableTypes = pickTerrain(worldMap[i][j - 1][0]);
					worldMap[i][j][0] = rand.nextInt(aviableTypes.size());
					ArrayList<Integer> aviableSubTypes = pickSubType(
							worldMap[i][j][0], worldMap[i][j - 1][0],
							worldMap[i][j - 1][1]);
					worldMap[i][j][1] = rand.nextInt(aviableSubTypes.size());
				} else if (j == 0 && i > 0) {
					ArrayList<Integer> aviableTypes = pickTerrain(worldMap[i - 1][j][0]);
					worldMap[i][j][0] = rand.nextInt(aviableTypes.size());
					ArrayList<Integer> aviableSubTypes = pickSubType(
							worldMap[i][j][0], worldMap[i - 1][j][0],
							worldMap[i - 1][j][1]);
					worldMap[i][j][1] = rand.nextInt(aviableSubTypes.size());
				} else {
					ArrayList<Integer> aviableTypesRight = pickTerrain(worldMap[i][j - 1][0]);
					ArrayList<Integer> aviableTypesAbove = pickTerrain(worldMap[i][j - 1][0]);
					ArrayList<Integer> aviableTypes = new ArrayList<Integer>();
					for (int k = 0; k < aviableTypesRight.size(); k++) {
						for (int l = 0; l < aviableTypesAbove.size(); l++) {
							if (aviableTypesRight.get(k) == aviableTypesAbove
									.get(l)) {
								aviableTypes.add(aviableTypesRight.get(k));
							}// End if
						}// End for
					}// End for
					worldMap[i][j][0] = rand.nextInt(aviableTypes.size());
					ArrayList<Integer> aviableSubTypes = pickSubType(
							worldMap[i][j][0], worldMap[i][j - 1][0],
							worldMap[i][j - 1][1]);
					worldMap[i][j][1] = rand.nextInt(aviableSubTypes.size());
				}// End if-else
			}// End for
		}// End for
		boolean validSpawnPoint = false;
		do {
			spawnPoint[0] = rand.nextInt(x - 1);
			spawnPoint[1] = rand.nextInt(y - 1);
			if (!terrainTypes[worldMap[spawnPoint[0]][spawnPoint[1]][0]]
					.equals("Water")
					|| !terrainTypes[worldMap[spawnPoint[0]][spawnPoint[1]][0]]
							.equals("Mountain")) {
				if (spawnPoint[0] - 3 > 0 || spawnPoint[0] + 3 <= x
						|| spawnPoint[1] - 3 > 0 || spawnPoint[1] + 3 <= y) {
					validSpawnPoint = true;
				}// End if
			}// End if
		} while (validSpawnPoint == false);// End While
		location[0] = spawnPoint[0];
		location[1] = spawnPoint[1];

		for(int i=0; i<5; i++){
			int[] storeLocation = { rand.nextInt(x), rand.nextInt(y) };
			// Prevents stores being put on water
			while(worldMap[storeLocation[0]][storeLocation[1]][0] != 0){
				storeLocation[0] = rand.nextInt(x);
				storeLocation[1] = rand.nextInt(y);
			}// End while
			worldMap[storeLocation[0]][storeLocation[1]][0] = 5;
			worldMap[storeLocation[0]][storeLocation[1]][1] = rand.nextInt(storeTypes.length);
		}// End for
		
		int[] upPortalLocation = { rand.nextInt(x), rand.nextInt(y) };
		worldMap[upPortalLocation[0]][upPortalLocation[1]][0] = -1;
		worldMap[upPortalLocation[0]][upPortalLocation[1]][1] = 1;
		System.out.println("Up Portal is @ (" + upPortalLocation[0] + " , "
				+ upPortalLocation[1] + ")");

		if (this.currentRelem != 0) {
			int[] downPortalLocation = { rand.nextInt(x), rand.nextInt(y) };
			worldMap[downPortalLocation[0]][downPortalLocation[1]][0] = -1;
			worldMap[downPortalLocation[0]][downPortalLocation[1]][1] = -1;
			System.out.println("Down Portal is @ (" + downPortalLocation[0]
					+ " , " + downPortalLocation[1] + ")");
		}// End if
		int darkPortal = rand.nextInt(1000);
		if (darkPortal == 500) {
			int[] darkPortalLocation = { rand.nextInt(x), rand.nextInt(y) };
			worldMap[darkPortalLocation[0]][darkPortalLocation[1]][0] = -1;
			worldMap[darkPortalLocation[0]][darkPortalLocation[1]][1] = 0;
			System.out.println("Dark Portal is @ (" + darkPortalLocation[0]
					+ " , " + darkPortalLocation[1] + ")");
		}// End if
		relems.add(worldMap);
		createGameWindow();
		//createMiniMap();
	}// End createMap

	private ArrayList<Integer> pickTerrain(int adjacentType) {
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		if (adjacentType == 0) {
			returnValue.add(1);
			returnValue.add(2);
		} else if (adjacentType == 1) {
			returnValue.add(1);
			returnValue.add(2);
			returnValue.add(3);
		} else if (adjacentType == 2) {
			returnValue.add(1);
			returnValue.add(2);
			returnValue.add(3);
			returnValue.add(4);
		} else if (adjacentType == 3) {
			returnValue.add(2);
			returnValue.add(3);
			returnValue.add(4);
		} else if (adjacentType == 4) {
			returnValue.add(3);
			returnValue.add(4);
		}// End if-else
		return returnValue;
	}// End pickTerrain

	private ArrayList<Integer> pickSubType(int type, int adjacentType,
			int adjacentSubType) {
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		if (adjacentType == 0 && type == 0) {
			returnValue.add(adjacentSubType);
		} else {
			for (int i = 0; i < subTypeArrays.get(type).length; i++) {
				returnValue.add(i);
			}// End for
		}// End if-else
		return returnValue;
	}// End pickSubType

	public void createGameWindow() {
		gameWindow = new JFrame();
		JPanel visableArea = new JPanel(new GridLayout(5, 5));
		System.out.println("(" + location[0] + "," + location[1] + ")");
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				int a = location[0] - 2 + i;
				int b = location[1] - 2 + j;
				// Wraps world so that if player goes off one edge the come out
				// at the opposite side
				if (a < 0) {
					a = y + a;
				}// End if
				if (b < 0) {
					b = x + b;
				}// End if
				if (a >= y) {
					a = a - y;
				}// End if
				if (b >= x) {
					b = b - x;
				}// End if
				boolean enemy = false;
				boolean chest = false;
				Random rand = new Random();
				// Random number that makes an enemy on a tile
				int randInt = rand.nextInt(10);
				if (randInt < 9) {
					enemy = false;
				} else {
					enemy = true;
				}// End if
					// Random number that makes a chest on a tile
				randInt = rand.nextInt(20);
				if (randInt < 19) {
					chest = false;
				} else {
					chest = true;
				}// End if
				String subType = "";
				if (worldMap[a][b][0] == 0) {
					subType = waterSubTypes[worldMap[a][b][1]];
				} else if (worldMap[a][b][0] == 1) {
					subType = beachSubTypes[worldMap[a][b][1]];
				} else if (worldMap[a][b][0] == 2) {
					subType = plainsSubTypes[worldMap[a][b][1]];
				} else if (worldMap[a][b][0] == 3) {
					subType = forestSubTypes[worldMap[a][b][1]];
				} else if (worldMap[a][b][0] == 4) {
					subType = mountianSubTypes[worldMap[a][b][1]];
				} else if (worldMap[a][b][0] == 5) {
					subType = storeTypes[worldMap[a][b][1]];
				} else if (worldMap[a][b][0] == -1) {
					if (worldMap[a][b][1] == -1) {
						subType = "Down Portal";
					} else if (worldMap[a][b][1] == 0) {
						subType = "Dark Portal";
					} else if (worldMap[a][b][1] == 1) {
						subType = "Up Portal";
					}// End if-else
				}// End if-else
				terrianTile newButton;
				if (worldMap[a][b][0] == -1) {
					newButton = new terrianTile(subType, subType, enemy, chest);
				} else {
					newButton = new terrianTile(
							terrainTypes[worldMap[a][b][0]], subType, enemy,
							chest);
				}// End if-else
				newButton.setText(subType);
				if (newButton.getTerrainType().equals("Water")) {
					newButton.setBackground(Color.BLUE);
				} else if (newButton.getTerrainType().equals("Beach")) {
					newButton.setBackground(Color.YELLOW);
				} else if (newButton.getTerrainType().equals("Plains")) {
					newButton.setBackground(Color.ORANGE);
				} else if (newButton.getTerrainType().equals("Forest")) {
					newButton.setBackground(Color.GREEN);
				} else if (newButton.getTerrainType().equals("Mountain")) {
					newButton.setBackground(Color.WHITE);
				} else if (newButton.getTerrainType().equals("Store")) {
					newButton.setBackground(Color.CYAN);
				} else if (newButton.getTerrainType().equals("Up Portal")) {
					newButton.setBackground(Color.magenta);
				} else if (newButton.getTerrainType().equals("Down Portal")) {
					newButton.setBackground(Color.BLACK);
					newButton.setForeground(Color.magenta);
				} else if (newButton.getTerrainType().equals("Dark Portal")) {
					newButton.setBackground(Color.BLACK);
					newButton.setForeground(Color.WHITE);
				}// End if-else// End if-else
				if (newButton.isEnemy() == true) {
					newButton.setForeground(Color.RED);
				}// End if
				if (newButton.isChest() == true) {
					newButton.setForeground(Color.GREEN);
				}// End if
				if (a == location[0] && b == location[1]) {
					newButton.setBackground(Color.GRAY);
					newButton.setText(player.getName());
				}// End if
				newButton.setActionCommand("terrainTile");
				newButton.addActionListener(this);
				System.out.print("(" + a + "," + b + ")");
				adjacentTiles[i][j] = newButton;
				// updates the location to the correct numeric value
				if (i == 2 && j == 2) {
					location[0] = a;
					location[1] = b;
				}// End if
				visableArea.add(newButton);
			}// End for
			System.out.println();
		}// End for
		gameWindow.add(visableArea);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		gameWindow.setLocation(dim.width / 2 - gameWindow.getSize().width / 2,
				dim.height / 2 - gameWindow.getSize().height / 2);
		gameWindow.pack();
		gameWindow.setJMenuBar(this.createFileMenu());
		gameWindow.setVisible(true);
	}// End createGameWindow

	private JMenuBar createFileMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setVisible(true);
		JMenu file = new JMenu("File");
		
		JMenuItem newGame = new JMenuItem("New Game");
		newGame.setActionCommand("newGame");
		newGame.addActionListener(this);
		file.add(newGame);
		
		JMenuItem map = new JMenuItem("Show Map");
		map.setActionCommand("showMap");
		map.addActionListener(this);
		file.add(map);
		
		JMenuItem save = new JMenuItem("Save Game");
		save.setActionCommand("save");
		save.addActionListener(this);
		file.add(save);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);

		file.add(exit);
		file.add(newGame);
		file.add(save);
		file.add(exit);
		menuBar.add(file);
		return menuBar;
	}// End createFileMenu

	public void createCompass() {
		compass = new JFrame();
		JPanel buttons = new JPanel(new GridLayout(3, 3));
		String upOrDown = "";
		String leftOrRight = "";
		for (int i = 0; i < 3; i++) {
			JButton newButton;
			for (int j = 0; j < 3; j++) {
				if (i == 0) {
					upOrDown = "North";
					if (j == 0) {
						leftOrRight = "West";
					} else if (j == 1) {
						leftOrRight = "";
					} else {
						leftOrRight = "East";
					}// End if-else
				} else if (i == 1) {
					upOrDown = "";
					if (j == 0) {
						leftOrRight = "West";
					} else if (j == 1) {
						leftOrRight = "";
					} else {
						leftOrRight = "East";
					}// End if-else
				} else {
					upOrDown = "South";
					if (j == 0) {
						leftOrRight = "West";
					} else if (j == 1) {
						leftOrRight = "";
					} else {
						leftOrRight = "East";
					}// End if-else
				}// End if-else
				newButton = new JButton(upOrDown + " " + leftOrRight);
				newButton.setActionCommand(upOrDown + " " + leftOrRight);
				newButton.addActionListener(this);
				buttons.add(newButton);
			}// End for
		}// End for
		compass.add(buttons);
		compass.pack();
		compass.setVisible(true);
	}// End createCompass

	public void createPlayerWindow() {
		playerWindow = new JFrame();
		JPanel playerInfo = new JPanel(new GridLayout(5, 1));
		JLabel playerName = new JLabel(player.getName());
		JLabel playerLevel = new JLabel("Lv"+ player.getLevel());
		JLabel playerXp = new JLabel("Xp "+ player.getXp());
		JLabel playerHp = new JLabel("HP "+ player.getHp());
		JLabel playerMoney = new JLabel("Money "+ player.getMoney());
		playerInfo.add(playerName);
		playerInfo.add(playerLevel);
		playerInfo.add(playerXp);
		playerInfo.add(playerHp);
		playerInfo.add(playerMoney);
		playerWindow.add(playerInfo);
		playerWindow.pack();
		playerWindow.setVisible(true);
	}// End 

	public void createMiniMap() {
		miniMap = new JFrame();
		JPanel mMiniMapButtons = new JPanel(new GridLayout(x, y));
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				JButton newButton = new JButton();
				newButton.setSize(new Dimension(-1, -1));
				int type = worldMap[i][j][currentRelem];
				if (type == 0) {
					newButton.setBackground(Color.BLUE);
				} else if (type == 1) {
					newButton.setBackground(Color.YELLOW);
				} else if (type == 2) {
					newButton.setBackground(Color.ORANGE);
				} else if (type == 3) {
					newButton.setBackground(Color.GREEN);
				} else if (type == 4) {
					newButton.setBackground(Color.WHITE);
				} else if (type == 5) {
					newButton.setBackground(Color.RED);
				} else if (type == -1) {
					newButton.setBackground(Color.magenta);
				}// End if-else// End if-else
				mMiniMapButtons.add(newButton);
				if(i == location[0] && j == location[1]){
					newButton.setBackground(Color.BLACK);
				}// End if
			}// End for
		}// End for
		miniMap.add(mMiniMapButtons);
		miniMap.pack();
		miniMap.setVisible(true);
	}// End createMiniMap

	private void battle() {
		Random rand = new Random();
		int enemyClass = rand.nextInt(4);
		Player enemy = null;
		if (enemyClass == 0) {
			enemy = new Player("Fire Mage", "fire");
		} else if (enemyClass == 1) {
			enemy = new Player("Water Mage", "water");
		} else if (enemyClass == 2) {
			enemy = new Player("Earth Mage", "earth");
		} else if (enemyClass == 3) {
			enemy = new Player("Air Mage", "air");
		}// End if else
		compass.dispose();
		playerWindow.dispose();
		gameWindow.dispose();
		@SuppressWarnings("unused")
		BattleSystem battle = new BattleSystem(player, enemy, this);
	}// End battle

	private void openChest(boolean trap, int level) {
		compass.dispose();
		playerWindow.dispose();
		gameWindow.dispose();
		@SuppressWarnings("unused")
		Chest chest = new Chest(trap, level, this);
	}// End openChest

	private void enterStore() {
		compass.dispose();
		playerWindow.dispose();
		gameWindow.dispose();
		@SuppressWarnings("unused")
		Store store = new Store(player.getLevel(), this);
	}// End enterStore

	private void move(int moveX, int moveY) {
		if (adjacentTiles[moveX][moveY].getTerrainType().equals("Water")
				&& (player.isFly() == false || player.isSail() == false)) {
			JOptionPane.showConfirmDialog(null, "You can't move that way");
			gameWindow.dispose();
			this.createGameWindow();
		} else {
			location[0] = location[0] + (moveX - 2);
			location[1] = location[1] + (moveY - 2);
			if (adjacentTiles[moveX][moveY].isEnemy() == true) {
				this.battle();
			}else if (adjacentTiles[moveX][moveY].isChest() == true) {
				Random rand = new Random();
				boolean trap = false;
				int randInt = rand.nextInt(100);
				if (randInt < 40) {
					trap = true;
				}// End if
				this.openChest(trap, player.getLevel());
			}else if (adjacentTiles[moveX][moveY].getTerrainType().equals("Store")) {
				this.enterStore();
			}else if (adjacentTiles[moveX][moveY].getSubType().equals("Up Portal")) {
				System.out.println("TRUE!");
				this.currentRelem = this.currentRelem++;
				this.createRelem();
			} else if (adjacentTiles[moveX][moveY].getSubType().equals(
					"Down Portal")) {
				if (this.currentRelem == 0) {

				} else {
					this.currentRelem = this.currentRelem--;
				}
				this.loadRelem();
			} else if (adjacentTiles[moveX][moveY].getSubType().equals(
					"Dark Portal")) {
				Random rand = new Random();
				this.currentRelem = rand.nextInt(relems.size());
				this.loadRelem();
			}else{
				if(player.getHp() <  player.getBaseHP()){
					player.setHp(player.getHp()+10);
				}//End if
				playerWindow.dispose();
				this.createPlayerWindow();
				gameWindow.dispose();
				this.createGameWindow();
			}// End if-else
		}// End if-else
	}// End move

	public void loadMap() {
		int[][][] map = this.relems.get(currentRelem);
		this.worldMap = new int[x][y][2];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				for (int k = 0; k < 2; k++) {
					this.worldMap[i][j][k] = map[i][j][k];
				}// End for
			}// End for
		}// End for
		this.createGameWindow();
	}// End Constructor for game loaded from save
	
	//Getters and Setters
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public JFrame getCompass() {
		return compass;
	}

	public void setCompass(JFrame compass) {
		this.compass = compass;
	}

	public JFrame getMiniMap() {
		return miniMap;
	}

	public void setMiniMap(JFrame miniMap) {
		this.miniMap = miniMap;
	}

	public JFrame getGameWindow() {
		return gameWindow;
	}

	public void setGameWindow(JFrame gameWindow) {
		this.gameWindow = gameWindow;
	}
	//End Getters and Setters
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String source = event.getActionCommand();
		if (source.equals("North West")) {
			this.move(1, 1);
		} else if (source.equals("North ")) {
			this.move(1, 2);
		} else if (source.equals("North East")) {
			this.move(1, 3);
		} else if (source.equals(" West")) {
			this.move(2, 1);
		} else if (source.equals(" East")) {
			this.move(2, 3);
		} else if (source.equals("South West")) {
			this.move(3, 1);
		} else if (source.equals("South ")) {
			this.move(3, 2);
		} else if (source.equals("South East")) {
			this.move(3, 3);
		} else if (source.equals("exit")) {
			System.exit(0);
		} else if (source.equals("newGame")) {
			gameWindow.dispose();
			compass.dispose();
			Dungeons newGame = new Dungeons();
			newGame.startSinglePlayerGame();
		} else if (source.equals("showMap")) {
			createMiniMap();
		}  else if (source.equals("save")) {
			File outputFile = new File(player.getName() + ".txt");
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(player.getName() + ".txt", false);
			} catch (FileNotFoundException e) {
				try {
					outputFile.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}// End try-catch
			}// End try-catch
			PrintStream ps = null;
			ps = new PrintStream(out);
			ps.println(relems.size());
			ps.println(currentRelem);
			for(int z=0; z<relems.size(); z++){
				for (int i = 0; i < x; i++) {
					for (int j = 0; j < x; j++) {
						ps.println(relems.get(z)[i][j][0] + "~" + relems.get(z)[i][j][1]);
					}// End for
				}// End for
			}// End for
			ps.println(player.getName() + "~" + player.getPlayerClass() + "~"
					+ player.getLevel() + "~" + player.getXp() + "~"
					+ player.getStrength() + "~" + player.getSpeed() + "~"
					+ player.getHp() + "~" + location[0] + "~" + location[1]
					+ "~" + spawnPoint[0] + "~" + spawnPoint[1]);

			Scanner scan = null;
			outputFile = new File("Saved Games.txt");
			try {
				scan = new Scanner(outputFile);
			} catch (FileNotFoundException e2) {
				e2.printStackTrace();
			}
			ArrayList<String> saves = new ArrayList<String>();
			while (scan.hasNextLine()) {
				saves.add(scan.nextLine());
			}// End While
			for (int i = 0; i < saves.size(); i++) {
				if (saves.get(i).equals(player.getName())) {
					player.setName(JOptionPane
							.showInputDialog("Please Choose a different name."));
				}// End if
			}// End for
			out = null;
			try {
				out = new FileOutputStream("Saved Games.txt", true);
			} catch (FileNotFoundException e) {
				try {
					outputFile.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}// End try-catch
			}// End try-catch
			ps = null;
			ps = new java.io.PrintStream(out);
			ps.println(player.getName());
			ps.close();
		}// End if-else
	}// End actionPerformed
}// End Class
