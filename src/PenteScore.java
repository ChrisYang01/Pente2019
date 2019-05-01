import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PenteScore extends JPanel implements ActionListener{
	
	//Data
	private JLabel p1Name, p2Name;
	private JTextField p1Captures, p2Captures;
	private JTextField whoseTurnField;
	
	
	private JButton resetButton;
	
	
	private Color backColor = new Color(219, 232, 255);
	private PenteGameBoard myBoard = null;
	
	private boolean firstGame = true;
	
	
	int spWidth;
	int spHeight;
	
	private Font myFont= new Font("Arial", Font.PLAIN, 24);
	private Color bBlack = new Color(5,11,91);
	
	
	
	public PenteScore(int w, int h) {
		
		spWidth = w;
		spHeight = h;
		System.out.println(spWidth);
		
		
		this.setSize(spWidth, spHeight);
		this.setBackground(backColor);
		
		this.setVisible(true);
		
		addInfoPlaces();
		
	}
	
	
	
	public void addInfoPlaces() {
		
		JPanel p1Panel = new JPanel();
		
		p1Panel.setLayout(new BoxLayout(p1Panel, BoxLayout.	Y_AXIS));
		
		p1Panel.setSize(spWidth, (int)(spHeight*0.45));
		p1Panel.setBackground(new Color(137,156,249));
		//p1Panel.setOpaque(false);
		
		p1Name = new JLabel("p1Name");
		p1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1Name.setFont(myFont);
		p1Name.setForeground(Color.BLACK);
		p1Name.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		p1Captures = new JTextField("Player1 Captures");
		p1Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1Captures.setFont(myFont);
		p1Captures.setForeground(Color.WHITE);
		p1Captures.setBackground(bBlack);
		p1Captures.setHorizontalAlignment(SwingConstants.CENTER);
		p1Captures.setFocusable(false);
		
		p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		p1Panel.add(p1Name);
		p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		p1Panel.add(p1Captures);
		p1Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		
		
		Border b = BorderFactory.createLineBorder(Color.BLUE,10,true);
		
		p1Panel.setBorder(b);
		
		this.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
			
		this.add(p1Panel);
		//this.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
		
		resetButton = new JButton("New Game");
		resetButton.setFont(myFont);
		resetButton.addActionListener(this);
		
		
		
		
		JPanel p2Panel = new JPanel();
	
		p2Panel.setLayout(new BoxLayout(p2Panel, BoxLayout.	Y_AXIS));
		
		p2Panel.setSize(spWidth, (int)(spHeight*0.45));
		p2Panel.setOpaque(false);
		
		p2Name = new JLabel("p2Name");
		p2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
		p2Name.setFont(myFont);
		p2Name.setForeground(Color.WHITE) ;
		p2Name.setHorizontalAlignment(SwingConstants.CENTER);
		
		p2Captures = new JTextField("Player2 Captures");
		p2Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
		p2Captures.setFont(myFont);
		p2Captures.setForeground(Color.BLACK);
		//p2Captures.setBackground(bBlack);
		p2Captures.setHorizontalAlignment(SwingConstants.CENTER);
		p2Captures.setFocusable(false);
		p2Captures.setText("0");
		
		
		p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		p2Panel.add(p2Name);
		p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		p2Panel.add(p2Captures);
		p2Panel.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		
		Border b2 = BorderFactory.createLineBorder(Color.BLUE,10,true);
		
		p2Panel.setBorder(b2);
		
		this.add(Box.createRigidArea(new Dimension(spWidth-40,40)));
		
		this.add(p2Panel);
		
		//resetButton = new JButton("New Game");
		//resetButton.setFont(myFont);
		//resetButton.addActionListener(this);
		
		this.add(resetButton);
		
		JPanel whoseTurn = new JPanel();
		whoseTurn.setLayout(new BoxLayout(whoseTurn, BoxLayout.Y_AXIS));
		whoseTurn.setSize(spWidth, (int)(spHeight*0.45));
		whoseTurn.setOpaque(false);
		
		whoseTurnField = new JTextField("Its ??? Turn Now");
		whoseTurnField.setAlignmentX(Component.CENTER_ALIGNMENT);
		whoseTurnField.setFont(myFont);
		whoseTurnField.setForeground(bBlack);
		whoseTurnField.setHorizontalAlignment(SwingConstants.CENTER);
	
		whoseTurn.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
		whoseTurn.add(whoseTurnField);
		whoseTurn.add(Box.createRigidArea(new Dimension(spWidth-40,20)));
		
		Border b3 = BorderFactory.createLineBorder(Color.BLUE,4,true);
		
		whoseTurn.setBorder(b3);
			
		this.add(Box.createRigidArea(new Dimension (spWidth-40,30)));
		this.add(whoseTurn);
		this.repaint();
	}
	
	public void setName(String n, int whichPlayer) {
		
		if(whichPlayer == PenteGameBoard.BLACKSTONE) {
			p1Name.setText("Player 1: " + n);

		} else {
			p2Name.setText("Player 2: " + n);
			
		}
		
		repaint();
	}
	
//	public void setCaptures(int c, int whichPlayer) {
//		
//		if(whichPlayer == PenteGameBoard.BLACKSTONE) {
//			p1Captures.setText(Integer.toString(c));
//			Rectangle r = p1Captures.getVisibleRect();
//			p1Captures.paintImmediately(r);
//		}else {
//			p2Captures.setText(Integer.toString(c));
//			Rectangle r = p2Captures.getVisibleRect();
//			p2Captures.paintImmediately(r);
//		
//	
//		}
//		
//		repaint();
//	}
	
	public void setCaptures(int c, int whichPlayer) {
		
		if(whichPlayer == PenteGameBoard.BLACKSTONE) {
			p1Captures.setText(Integer.toString(c));
			Rectangle r = p1Captures.getVisibleRect();
			p1Captures.paintImmediately(r);
		} else if(whichPlayer == PenteGameBoard.WHITESTONE) {
			p2Captures.setText(Integer.toString(c));
			Rectangle r = p2Captures.getVisibleRect();
			p2Captures.paintImmediately(r);
	
		}
		
		repaint();
	}
	
	public void setPlayerTurn(int whichPlayer) {
		if(whichPlayer == PenteGameBoard.BLACKSTONE) {
			whoseTurnField.setBackground(bBlack);
			whoseTurnField.setForeground(Color.WHITE);
			System.out.print(p1Name.getText());
			
			int cLoc = p1Name.getText().indexOf(":");
			String n = p1Name.getText().substring(cLoc+ 2, p1Name.getText().length());
			
			
			
			whoseTurnField.setText("It's " + n + "'s Turn Now");
			
			
			
		} else {
			
			whoseTurnField.setBackground(Color.WHITE);
			whoseTurnField.setForeground(bBlack);
			
			System.out.print(p2Name.getText());
			
			int cLoc = p2Name.getText().indexOf(":");
			String n = p2Name.getText().substring(cLoc+ 2, p2Name.getText().length());
			
			
			
			whoseTurnField.setText("It's " + n + "'s Turn Now");
		}
		
		if(firstGame) {
			whoseTurnField.repaint();
		} else {
		
			Rectangle r = whoseTurnField.getVisibleRect();
			whoseTurnField.paintImmediately(r);
		}
			//System.out.print(p1Name.getText());
			
			//whoseTurnField.setText(p2Name.getText());
			
		
	}

	public void setGameBoard(PenteGameBoard gb) {
		myBoard = gb;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, "Ok starting new game");
		firstGame = false;
		System.out.println("Hi clicked me!!");
		if(myBoard != null )myBoard.startNewGame(false);
		
	}
	
	
	
	
	
	}
	
	


