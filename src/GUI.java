import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;

@SuppressWarnings("serial")
public class GUI extends JFrame implements MouseListener, MenuKeyListener, ActionListener, Runnable{

	JFrame f;
	JButton[][] buttons;
	Overlay o;
	JPanel p;
	boolean done = false;
	JMenuBar menu;
	JMenuItem configure;
	JMenuItem exit;
	JMenuItem reset;
	double startTime;
	JTextField time;
	
	public GUI(Overlay o){
		super("Minesweeper Clone v 1.0");
		this.setLayout(new BorderLayout());
		this.o = o;
		time = new JTextField(3);

		done = false;
		startTime = System.currentTimeMillis()/1000;
		
		p = new JPanel();
		p.setLayout(new GridLayout(o.width, o.height));
		buttons = new JButton[o.height][o.width];
		
		for(int r = 0; r < o.height; r++){
			for(int c = 0; c < o.width; c++){
				buttons[r][c] = new JButton();
				buttons[r][c].setVisible(true);
				buttons[r][c].addMouseListener(this);
				buttons[r][c].setBackground(new Color(100, 149, 237));
				buttons[r][c].setFocusable(false);
				p.add(buttons[r][c]);
			}
		}
		
		menu = new JMenuBar();
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_ALT);
		
		reset = new JMenuItem("reset");
		reset.setMnemonic(KeyEvent.VK_R);
		reset.addActionListener(this);
		reset.addMenuKeyListener(this);
		file.add(reset);
		
		configure = new JMenuItem("config");
		configure.setMnemonic(KeyEvent.VK_C);
		configure.addActionListener(this);
		configure.addMenuKeyListener(this);
		file.add(configure);
		
		exit = new JMenuItem("exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.addActionListener(this);
		exit.addMenuKeyListener(this);
		file.add(exit);
		
		menu.add(file);
		menu.setVisible(true);

		p.setSize(this.WIDTH, this.HEIGHT);
		p.setVisible(true);
		
		this.setSize(o.width * 75, o.height * 50);
		
		JPanel timePanel = new JPanel();
		timePanel.setSize(o.width, 100);
		JLabel L = new JLabel("time ");
		JTextField mines = new JTextField("" + o.amountOfMines);
		timePanel.add(L);
		timePanel.add(time);
		L = new JLabel("mines ");
		timePanel.add(L);
		timePanel.add(mines);
		this.add(BorderLayout.NORTH, timePanel);
		this.add(BorderLayout.CENTER, p);
		this.setJMenuBar(menu);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void uncover(int r , int c){
		if(o.layout[r][c].value == 0){
			o.layout[r][c].isClicked = true;
			for(int x = -1; x <= 1; x++){
				for(int y = -1; y <= 1; y++){
					if(r + x >= 0 && r + x < o.height && c + y >= 0 && c + y < o.width && !o.layout[r + x][c + y].isClicked){
						if(o.layout[r + x][c + y].value != 9){
							o.layout[r + x][c + y].isClicked = true;
							if(o.layout[r + x][c + y].value == 0){
								uncover(r + x, c + y);
							}
							
						}
					}
				}
			}	
		}
	}
	


	
	public void updateButtons(){
		//time.setText("" + (int)(System.currentTimeMillis()/1000 - startTime));
		for(int r = 0; r < o.width; r++){
			for(int c = 0; c < o.height; c++){
				
				if(o.layout[r][c].value == 9 && o.layout[r][c].isClicked){
					isWin(false);
					done = true;
					
				}
				
				if(checkWin()){
					isWin(true);
					done = true;
				}
				if(!done){
					if(o.layout[r][c].isClicked){
						buttons[r][c].setText("" + o.layout[r][c].value);
						buttons[r][c].setBackground(o.layout[r][c].getColor());
					}
					else if(o.layout[r][c].isFlagged){
						buttons[r][c].setText("F");
						buttons[r][c].setBackground(Color.GRAY);
					}
					else{
						buttons[r][c].setBackground(new Color(100, 149, 237));
						buttons[r][c].setText(" ");
					}
				}
			}
		}
		
	}
	
	public void isWin(boolean b){
		if(b){
			for(int r = 0; r < o.width; r++){
				for(int c = 0; c < o.height; c++){
					if(o.layout[r][c].value == 9){
						buttons[r][c].setBackground(o.layout[r][c].getColor());
						buttons[r][c].setText(":)");
					}
					else{
						buttons[r][c].setBackground(o.layout[r][c].getColor());
						buttons[r][c].setText("" + o.layout[r][c].value);
					}
				}
			}
			
		}
		else{
			for(int r = 0; r < o.width; r++){
				for(int c = 0; c < o.height; c++){
					if(o.layout[r][c].isFlagged && o.layout[r][c].value == 9){
						buttons[r][c].setBackground(o.layout[r][c].getColor());
						buttons[r][c].setText(":)");
					}
					else if(o.layout[r][c].value == 9){
						buttons[r][c].setBackground(o.layout[r][c].getColor());
						buttons[r][c].setText("X");
					}
					else{
						buttons[r][c].setBackground(o.layout[r][c].getColor());
						buttons[r][c].setText("" + o.layout[r][c].value);
					}
				}
			}
			
			
			
		}
	}
	
	public boolean checkWin(){
		
		int counter = 0;
		
		for(int r = 0; r < o.width; r++){
			for(int c = 0; c < o.height; c++){
				if(o.layout[r][c].isClicked)
					counter++;
			}
		}
		
		return (o.width*o.height) - counter == o.amountOfMines;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getButton() == me.BUTTON3){
			for(int r = 0; r < o.width; r++){
				for(int c = 0; c < o.height; c++){
					if( me.getSource() == buttons[r][c]){
						if(o.layout[r][c].isFlagged){
							o.layout[r][c].isFlagged = false;
						}
						else{
							o.layout[r][c].isFlagged = true;
						}
					
					}
				}
			}
		}
		else if(me.getButton() == me.BUTTON1){
			for(int r = 0; r < o.height; r++){
				for(int c = 0; c < o.width; c++){
					if(me.getSource() == buttons[r][c] && !o.layout[r][c].isClicked){
						o.layout[r][c].isClicked = true;
							if(o.layout[r][c].value == 0)
								uncover(r, c);
					}
				}
			}
			
		}
		updateButtons();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem i = (JMenuItem)e.getSource();
		if(i == configure){
			int w = Integer.parseInt(JOptionPane.showInputDialog(this, "enter width"));
			int h = Integer.parseInt(JOptionPane.showInputDialog(this, "enter height"));
			int b = Integer.parseInt(JOptionPane.showInputDialog(this, "enter amount of bombs"));
			this.setVisible(false);
			GUI g = new GUI(new Overlay(w, h, b));
		}
		else if(i == reset){
				for(int r = 0; r < o.width; r++){
					for(int c = 0; c < o.height; c++){
						o.layout[r][c].isClicked = false;
						o.layout[r][c].isFlagged = false;
						
					}
			}
			done = true;		
		}
		else if(i == exit)
			this.setVisible(false);
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuKeyPressed(MenuKeyEvent e) {
		/*if(e.getKeyCode() == KeyEvent.VK_R){
			for(int r = 0; r < o.width; r++){
				for(int c = 0; c < o.height; c++){
					o.layout[r][c].isClicked = false;
					o.layout[r][c].isFlagged = false;
					
				}
			}
			this.setVisible(false);
			GUI g = new GUI(o);
		}
		else if(e.getKeyCode() == KeyEvent.VK_C){
			int w = Integer.parseInt(JOptionPane.showInputDialog(this, "enter width"));
			int h = Integer.parseInt(JOptionPane.showInputDialog(this, "enter height"));
			int b = Integer.parseInt(JOptionPane.showInputDialog(this, "enter amount of bombs"));
			this.setVisible(false);
			GUI g = new GUI(new Overlay(w, h, b));
		}	
		else if(e.getKeyCode() == KeyEvent.VK_E){
			this.setVisible(false);
		}*/
		
	}

	@Override
	public void menuKeyReleased(MenuKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuKeyTyped(MenuKeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		time.setText("" + (int)(System.currentTimeMillis()/1000 - startTime));
		if(done){
			startTime = System.currentTimeMillis()/1000;
			done = false;
			updateButtons();
		}
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {}
		
	}
	
	public static void main(String[] args){
		GUI g = new GUI(new Overlay(10, 10, 7));
		Thread t = new Thread(g);
		while(g!=null)
			t.run();
	
	}





}
