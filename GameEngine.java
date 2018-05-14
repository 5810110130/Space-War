package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane; 

import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private SpaceShip v;	
	private SpaceShip v2;
	
	private Timer timer;
	
	private long score = 0;
	private long score2 = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v,SpaceShip v2) {
		this.gp = gp;
		this.v = v;	
		this.v2 = v2;		
		
		gp.sprites.add(v2);
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		EnemyTwo e2 = new EnemyTwo((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		gp.sprites.add(e2);
		enemies.add(e);
		enemies.add(e2);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}

			if((score%500) == 0 && score != 0){
				difficulty += 0.0001;
			}

			
			
		}	
		
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
		}

		Rectangle2D.Double vr2 = v2.getRectangle();
		Rectangle2D.Double er2;
		for(Enemy e : enemies){
			er2 = e.getRectangle();
			if(er2.intersects(vr2)){
				die();
				return;
			}
		}
	}

	public void die(){
		timer.stop();
		int n = JOptionPane.showConfirmDialog(
	    null,
	    "Do you want to try again ?",
	    "Your score is " + score,
	    JOptionPane.YES_NO_OPTION);
		if(n == 0){

			gp.sprites.clear();
			enemies.clear();
			gp.sprites.add(v);
			gp.sprites.add(v2);
			timer.start();

		}
		else if (n == 1){
            System.exit(0);
		}   
    }
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move(1);
			break;
		case KeyEvent.VK_A:
			v2.move(-1);
			break;
		case KeyEvent.VK_D:
			v2.move(1);
			break;
		case KeyEvent.VK_X:
			difficulty += 0.1;
			break;
		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
