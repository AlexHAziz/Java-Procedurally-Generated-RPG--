import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Chest implements ActionListener{
	private JFrame frame = new JFrame("Chest");
	private Container newContentPane = frame.getContentPane();
	private Random rand = new Random();
	private int treasure = 10;
	private boolean trap = false;
	private int level = 0;
	private int damge = 5;
	private int xpGained = 5; 
	private SinglePlayerGame currentGame;
	
	public Chest(boolean trap, int level, SinglePlayerGame currentGame){
		this.currentGame = currentGame;
		this.trap = trap;
		if(this.trap == true){
			this.level = level * 2;
		}else{
			this.level = level;
		}// End If
		this.createWindow();
	}// End Constructor
	
	private void createWindow() {
		// Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Create and set up the content pane.
		newContentPane.removeAll();
		newContentPane.setLayout(new BorderLayout());
		JPanel optionsPanel = createOptionsButtons();
		newContentPane.add(optionsPanel, BorderLayout.SOUTH);
		JPanel chestPanel = createChestPanel();
		newContentPane.add(chestPanel, BorderLayout.NORTH);
		frame.setContentPane(newContentPane);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}// End createWindow

	private JPanel createChestPanel() {
		JPanel returnPanel = new JPanel();
		JButton openButton = new JButton("Open");
		openButton.setActionCommand("open");
		openButton.addActionListener(this);
		returnPanel.add(openButton);
		
		if(trap == true){
			int detectTrap = rand.nextInt(99);
			if(detectTrap>80){
				// Trap detected
				JButton disarmTrap = new JButton("Attempt to disarm tarp?");
				disarmTrap.setActionCommand("disarm");
				disarmTrap.addActionListener(this);
			}// End if
		}// End if
		
		JButton leaveButton = new JButton("Leave?");
		leaveButton.setActionCommand("leave");
		leaveButton.addActionListener(this);
		returnPanel.add(leaveButton);
		return returnPanel;
	}// End createChestPanel

	private JPanel createOptionsButtons() {
		JPanel returnPanel = new JPanel();
		JLabel chestPic = new JLabel();
		JLabel chestLevel = new JLabel();
		chestPic.setBackground(Color.BLUE);
		returnPanel.add(chestLevel);
		chestPic.setIcon(new javax.swing.ImageIcon("PlaceholderChest.png"));
		returnPanel.add(chestPic);
		return returnPanel;
	}// End createOptionsButtons
	
	//Getters and Setters
	public int getTreasure() {
		return treasure;
	}

	public void setTreasure(int treasure) {
		this.treasure = treasure;
	}

	public boolean isTrap() {
		return trap;
	}

	public void setTrap(boolean trap) {
		this.trap = trap;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDamge() {
		return damge;
	}

	public void setDamge(int damge) {
		this.damge = damge;
	}

	public int getXpGained() {
		return xpGained;
	}

	public void setXpGained(int xpGained) {
		this.xpGained = xpGained;
	}
	//End Getters and Setters
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String source = event.getActionCommand();
		if(source.equals("open")){
			if(trap == true){
				int springTrap = rand.nextInt(100);
				if(springTrap<70){
					JOptionPane.showConfirmDialog(null,"You sprung a trap!");
					damge = damge*level;
					currentGame.getPlayer().setHp(currentGame.getPlayer().getHp() - damge);
					frame.dispose();
					currentGame.createCompass();
					currentGame.createPlayerWindow();
					currentGame.createGameWindow();
				} else {
					treasure = treasure * level;
					JOptionPane.showConfirmDialog(null, "You got "+treasure+" gold.");
					currentGame.createCompass();
					currentGame.createPlayerWindow();
					currentGame.createGameWindow();
				}// End if-else
			}else{
				treasure = treasure * level;
				currentGame.getPlayer().setMoney(currentGame.getPlayer().getMoney() + treasure);
				JOptionPane.showConfirmDialog(null, "You got "+treasure+" gold.");
				frame.dispose();
				currentGame.createCompass();
				currentGame.createPlayerWindow();
				currentGame.createGameWindow();
			}// End if-else
			
		}else if(source.equals("disarm")){
			int disarmTrap = rand.nextInt(100);
			if(disarmTrap<50){
				JOptionPane.showConfirmDialog(null,"You disarmed the trap!");
				xpGained = xpGained * level;
				currentGame.getPlayer().setXp(currentGame.getPlayer().getXp() + xpGained);
				trap = false;
			}else{
				JOptionPane.showConfirmDialog(null,"You sprung a trap!");
				damge = damge*level;
				currentGame.getPlayer().setHp(currentGame.getPlayer().getHp() - damge);
				frame.dispose();
				currentGame.createCompass();
				currentGame.createPlayerWindow();
				currentGame.createGameWindow();			
			}// End if-else
		}else if(source.equals("leave")){
			frame.dispose();
			currentGame.createCompass();
			currentGame.createPlayerWindow();
			currentGame.createGameWindow();
		}// End if-else
	}// End ActionListener
}// End Class
