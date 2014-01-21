import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * Rewrite load algorithm after finalized world elements
 */
public class Dungeons implements ActionListener {

	private ButtonGroup group = new ButtonGroup();
	private JFrame frame;
	private JFrame loadPlayer;
	private Player player1;
	private Player player2;
	private JComboBox playerSaves;

	public static void main(String[] args) {
		Dungeons newGame = new Dungeons();
		/*
		 * Standardizes look and feel across all platforms
		 */
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		newGame.startSinglePlayerGame();
	}// End Main

	public void startSinglePlayerGame() {
		group = new ButtonGroup();
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		// Make Buttons
		JRadioButton newGameButton = new JRadioButton("New Game");
		newGameButton.setActionCommand("newGame");
		newGameButton.setSelected(true);
		newGameButton.addActionListener(this);
		group.add(newGameButton);
		radioPanel.add(newGameButton);

		JRadioButton loadGameButton = new JRadioButton("Load Game");
		loadGameButton.setActionCommand("loadGame");
		loadGameButton.setSelected(true);
		loadGameButton.addActionListener(this);
		group.add(loadGameButton);
		radioPanel.add(loadGameButton);

		JButton doneButton = new JButton("Done");
		doneButton.setActionCommand("done");
		doneButton.addActionListener(this);
		radioPanel.add(doneButton);

		frame = new JFrame();
		frame.setTitle("Load or New Game?");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(radioPanel);
		frame.pack();
		frame.setVisible(true);
	}// End startSinglePlayerGame

	@Override
	public void actionPerformed(ActionEvent event) {
		String source = event.getActionCommand();
		if (source.equals("modeSelectDone")) {
			if (group.getSelection().getActionCommand()
					.equals(("singlePlayer"))) {
				frame.dispose();
				this.startSinglePlayerGame();
			}
		} else if (source.equals("done")) {
			if (group.getSelection().getActionCommand().equals("newGame")) {
				@SuppressWarnings("unused")
				Player newPlayer = new Player();// Creates newPlayer
				frame.dispose();
			} else if (group.getSelection().getActionCommand()
					.equals("loadGame")) {
				loadPlayer = new JFrame();
				JPanel panel = new JPanel();
				ArrayList<String> availableCategoriesArrayList = getAvailableChacters();
				String[] availableCategories = new String[availableCategoriesArrayList
						.size()];
				for (int i = 0; i < availableCategoriesArrayList.size(); i++) {
					availableCategories[i] = availableCategoriesArrayList
							.get(i);
				}// End for
				playerSaves = new JComboBox(availableCategories);
				panel.add(playerSaves);
				JButton doneButton = new JButton("Done");
				doneButton.setActionCommand("loadDoneButton");
				doneButton.addActionListener(this);
				panel.add(doneButton);
				loadPlayer.add(panel);
				loadPlayer.pack();
				loadPlayer.setVisible(true);
			}// End if-else
		} else if (source.equals("loadDoneButton")) {
			String fileName = (String) playerSaves.getSelectedItem();
			this.loadPlayer(fileName);
		}// End if-else
	}// End actionPerformed

	private ArrayList<String> getAvailableChacters() {
		ArrayList<String> availableCategories = new ArrayList<String>();
		String fileName = "Saved Games.txt";
		File myFile = new File(fileName);
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(myFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// End try catch
		while (inputFile.hasNext()) {
			String category = inputFile.nextLine();
			availableCategories.add(category);
		}// End while
		return availableCategories;
	}// End getAvailableCategoryNames

	private void loadPlayer(String fileName) {
		
		fileName = fileName + ".txt";
		File myFile = new File(fileName);
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(myFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// End try catch
		String numberOfRelems = inputFile.nextLine();
		System.out.println(numberOfRelems+"");
		String currentfRelems = inputFile.nextLine();
		System.out.println(currentfRelems+"");
		ArrayList<int[][][]> relems = new ArrayList<int[][][]>();
		for(int z=0; z<Integer.parseInt(numberOfRelems); z++){
			int[][][] terrainTypes = new int[100][100][2];
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 100; j++) {
					String[] terrainType = inputFile.nextLine().split("~");
					for (int k = 0; k <= 1; k++) {
						//System.out.println(terrainType[k]);
						terrainTypes[i][j][k] = Integer.parseInt(terrainType[k]);
					}// End for
				}// End for
			}// End for
			System.out.println("TEST");
			relems.add(terrainTypes);
		}// End for
		String[] playerInfo = inputFile.nextLine().split("~");
		for(int i=0; i<playerInfo.length; i++){
			System.out.println("palyerInfo "+playerInfo[i]);
		}
		inputFile.close();
		player1 = new Player(playerInfo[0], playerInfo[1], playerInfo[2],
				playerInfo[3], playerInfo[4], playerInfo[5], playerInfo[6],
				playerInfo[7], playerInfo[8], playerInfo[9], playerInfo[10],
				numberOfRelems, currentfRelems, relems);
	}// End readQuestions

	// Getters and Setters
	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	// End Getters and Setters
}// End Class
