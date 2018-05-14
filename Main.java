package f2.spw;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args){
	
		JFrame frame = new JFrame("Space War");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 650);
		frame.getContentPane().setLayout(new BorderLayout());

		/*JButton button = new JButton("START");
		button.addActionListener(new MyActionListenerDONE());
		this.add(button, BorderLayout.PAGE_END);*/

		SpaceShip v = new SpaceShip(180, 550, 20, 20);
		SpaceShip v2 = new SpaceShip(110, 550, 20, 20);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v,v2);
		frame.addKeyListener(engine);
		frame.getContentPane().add(gp, BorderLayout.CENTER);
		frame.setVisible(true);
		
		engine.start();
	}
}