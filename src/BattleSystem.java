import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Create basic AI so that it is actually a single player game
 */
		
public class BattleSystem implements ActionListener {
	private JFrame frame = new JFrame();
	private int whoseTurnIsIt = 0;
	private int whoseTurnItIsNot = 1;
	private int numbersOfPlayerrs = 2;
	private Player[] players = new Player[numbersOfPlayerrs];
	private Container newContentPane = frame.getContentPane();
	private SinglePlayerGame currentGame;

	public BattleSystem(Player player, Player enemy,
			SinglePlayerGame currentGame) {
		players[0] = player;
		players[1] = enemy;
		this.currentGame = currentGame;
		this.buildWindows();
	}// End constructor

	private void buildWindows() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		newContentPane.removeAll();
		newContentPane.setLayout(new BorderLayout());
		JPanel attackButtons = createAttackButtons();
		newContentPane.add(attackButtons, BorderLayout.SOUTH);
		JPanel statusPanel = createEnemyPanel();
		newContentPane.add(statusPanel, BorderLayout.NORTH);
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setVisible(true);
	}// End buildWindows

	private JPanel createAttackButtons() {
		JPanel attackButtonPanel = new JPanel();
		for (int i = 0; i < players[whoseTurnIsIt].getAttacks().length; i++) {
			JButton newAttackButton = new JButton(
					players[whoseTurnIsIt].getAttacks()[i].getName());
			newAttackButton.setActionCommand(i + "");
			newAttackButton.addActionListener(this);
			attackButtonPanel.add(newAttackButton);
		}// End for
		JLabel hpLabel = new JLabel();
		hpLabel.setText("HP: " + players[whoseTurnIsIt].getHp());
		JLabel levelLabel = new JLabel();
		levelLabel.setText("Level: " + players[whoseTurnIsIt].getLevel());
		attackButtonPanel.add(hpLabel);
		attackButtonPanel.add(levelLabel);
		return attackButtonPanel;
	}// End createButtons

	private JPanel createEnemyPanel() {
		JPanel enemyStatusPanel = new JPanel();
		JLabel hpLabel = new JLabel();
		JLabel levelLabel = new JLabel();
		hpLabel.setText("HP: " + players[whoseTurnItIsNot].getHp());
		levelLabel.setText("Level: " + players[whoseTurnItIsNot].getLevel());
		JLabel enemyPic = new JLabel();
		enemyPic.setBackground(Color.BLUE);
		enemyStatusPanel.add(hpLabel);
		enemyStatusPanel.add(levelLabel);
		enemyPic.setIcon(new javax.swing.ImageIcon("Placeholder.png"));
		enemyStatusPanel.add(enemyPic);
		return enemyStatusPanel;
	}// End createStatusPanel

	@Override
	public void actionPerformed(ActionEvent event) {
		String source = event.getActionCommand();
		int attackNumber = Integer.parseInt(source);
		double damge = players[whoseTurnItIsNot].getHp()
				- players[whoseTurnIsIt].getAttacks()[attackNumber].getDamage();
		players[whoseTurnItIsNot].setHp(damge);
		players[whoseTurnIsIt].getAttacks()[attackNumber]
				.setCost(players[whoseTurnIsIt].getAttacks()[attackNumber]
						.getCost() - 1);
		if (whoseTurnIsIt == 0) {
			whoseTurnIsIt = 1;
			whoseTurnItIsNot = 0;
		} else {
			whoseTurnIsIt = 0;
			whoseTurnItIsNot = 1;
		}// End if
		frame.dispose();
		if (players[whoseTurnItIsNot].getHp() <= 0) {
			if (whoseTurnIsIt == 0) {
				int xpGained = 10 * players[1].getLevel();
				currentGame.getPlayer().setXp(currentGame.getPlayer().getXp() + xpGained);
				int treasure = 10 *  players[1].getLevel();
				currentGame.getPlayer().setMoney(currentGame.getPlayer().getMoney() + treasure);
				JOptionPane.showConfirmDialog(null, "You won! You got "+treasure+" gold and "+xpGained+" xp!");
				frame.dispose();
				currentGame.createCompass();
				currentGame.createPlayerWindow();
				currentGame.createGameWindow();
			} else {
				JOptionPane.showConfirmDialog(null, "You Lost!");
				System.exit(0);
			}// End if
		}else{
			buildWindows();
		}// End if
	}// End actionPerformed
}// End Class
