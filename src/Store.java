import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Store implements ActionListener{
	private JFrame frame = new JFrame();
	private Container newContentPane = frame.getContentPane();
	private Item[] inventory = new Item[5];
	private int level = 0;
	private SinglePlayerGame currentGame;
	
	public Store(int level, SinglePlayerGame currentGame){
		this.level = level;
		this.currentGame = currentGame;
		this.createWindow();
	}// End Constructor

	private void createWindow() {
		// Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Create and set up the content pane.
		newContentPane.removeAll();
		newContentPane.setLayout(new BorderLayout());
		JPanel storePanel = createStorePanel();
		newContentPane.add(storePanel, BorderLayout.NORTH);
		frame.setContentPane(newContentPane);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}// End createWindow

	private JPanel createStorePanel() {
		JPanel potionsForSale = new JPanel();
		JButton potion = new JButton();
		for (int i = 0; i < inventory.length; i++){
			if(i == 0){
				potion = new JButton((10*level)+" HP potion for $"+(5*level));
				potion.setName("HP");
				potion.setActionCommand("HP");
			}else if ( i == 1){
				potion = new JButton((10*level)+" XP potion for $"+(15*level));
				potion.setName("XP");
				potion.setActionCommand("Xp");
			}else if ( i == 2){
				potion = new JButton("Wings for $"+(50*level));
				potion.setName("Wings");
				potion.setActionCommand("Wings");
			}else if ( i == 3){
				potion = new JButton("Boat for $"+(20*level));
				potion.setName("Boat");
				potion.setActionCommand("Boat");
			}// End if-else
			potion.addActionListener(this);
			potionsForSale.add(potion);
		}// End for
		JButton leave = new JButton("Leave");
		leave.setName("leave");
		leave.setActionCommand("leave");
		leave.addActionListener(this);
		potionsForSale.add(leave);
		return potionsForSale;
	}// End createStorePanel

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equals("leave")){
			frame.dispose();
			currentGame.createCompass();
			currentGame.createGameWindow();
		}else if(source.equals("HP")){
			if(currentGame.getPlayer().getMoney() <= (currentGame.getPlayer().getMoney() - 5*level)){
				currentGame.getPlayer().setMoney(currentGame.getPlayer().getMoney() - 5*level);
				currentGame.getPlayer().setHp(currentGame.getPlayer().getHp()+10*level);
			}else{
				JOptionPane.showConfirmDialog(null, "You don't have enough money.");
			}// End if-else
		}else if(source.equals("XP")){
			if(currentGame.getPlayer().getMoney() <= (currentGame.getPlayer().getMoney() - 15*level)){
				currentGame.getPlayer().setMoney(currentGame.getPlayer().getMoney() - 15*level);
				currentGame.getPlayer().setXp(currentGame.getPlayer().getXp()+10*level);
			}else{
				JOptionPane.showConfirmDialog(null, "You don't have enough money.");
			}// End if-else
		}else if(source.equals("Wings")){
			if(currentGame.getPlayer().getMoney() <= (currentGame.getPlayer().getMoney() - 50*level)){
				currentGame.getPlayer().setMoney(currentGame.getPlayer().getMoney() - 50*level);
				currentGame.getPlayer().setFly(true);
			}else{
				JOptionPane.showConfirmDialog(null, "You don't have enough money.");
			}// End if-else
		}else if(source.equals("Boat")){
			if(currentGame.getPlayer().getMoney() <= (currentGame.getPlayer().getMoney() - 20*level)){
				currentGame.getPlayer().setMoney(currentGame.getPlayer().getMoney() - 20*level);
				currentGame.getPlayer().setSail(true);
			}else{
				JOptionPane.showConfirmDialog(null, "You don't have enough money.");
			}// End if-else
		}// End if-else
	}// End actionPerformed
	
}// End Class
