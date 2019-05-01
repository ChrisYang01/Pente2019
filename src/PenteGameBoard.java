import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard extends JPanel implements MouseListener{
	
	public static final int EMPTY = 0;
	public static final int BLACKSTONE = -1;
	public static final int WHITESTONE = 1; 
	public static final int NUM_SQUARES_SIDE = 19;
	public static final int INNER_START = 7;
	public static final int INNER_END = 11;
	public static final int PLAYER1_TURN = 1;
	public static final int PLAYER2_TURN = -1;
	public static final int MAX_CAPTURES = 5;
	public static final int SLEEP_TIME = 50;
	
	
	private int bWidth, bHeight;
	
	private PenteBoardSquare testSquare;
	private int squareW, squareH;
	private JFrame myFrame;
	
	
	private int playerTurn;
	private boolean player1IsComputer = false;
	private boolean player2IsComputer = false;
	private String p1Name, p2Name;
	private boolean darkStoneMove2Token = false;
	
	private boolean gameOver = false;
	private ComputerMoveGenerator p1ComputerPlayer = null;
	private ComputerMoveGenerator p2ComputerPlayer = null;

	
	
	
	private PenteBoardSquare [][] gameBoard;
	private PenteScore myScoreBoard;
	private int p1Captures, p2Captures;
	
	
	public PenteGameBoard(int w, int h, PenteScore sb) {
		
		
		//store this variables 
		bWidth = w;
		bHeight = h;
		myScoreBoard = sb;
		
		p1Captures = 0;
		p2Captures = 0;
		
		this.setSize(w,h);
		this.setBackground(Color.CYAN);
		
		squareW = bWidth/this.NUM_SQUARES_SIDE;
		squareH = bHeight/this.NUM_SQUARES_SIDE;
		
		//testSquare = new PenteBoardSquare(0,0,squareW,squareH);
		
		gameBoard = new PenteBoardSquare [NUM_SQUARES_SIDE][NUM_SQUARES_SIDE];
		
		for (int row = 0; row< NUM_SQUARES_SIDE; row++) {
			for (int col = 0; col< NUM_SQUARES_SIDE; col++) {
				gameBoard[row][col] = new PenteBoardSquare(col* squareW,row*squareH,squareW,squareH);
				
	
					if ( col >= INNER_START && col <= INNER_END && row >= INNER_START && row <= INNER_END) {
						gameBoard[row][col].setInner();
					}
					
					
			/*if((row+col)% 2 == 0) {
				gameBoard[row][col].setState(BLACKSTONE);
			} else {
				gameBoard[row][col].setState(WHITESTONE);
			}*/
				
			
			
				}
			}
		
		initialDisplay();
		repaint();
		addMouseListener(this);
		this.setFocusable(true);
		
		
	}
	
	
	
	
	//method to do drawing 
	
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bWidth, bHeight);
		
		//testSquare.drawMe(g);
		
		
		for (int col = 0; col< NUM_SQUARES_SIDE; col++) {
		
			for(int row = 0; row < NUM_SQUARES_SIDE; row ++) {
			
			
			gameBoard[row][col].drawMe(g);
			}
		}
		
		
	}
		
		
		public void resetBoard() {
			for (int col = 0; col< NUM_SQUARES_SIDE; col++) {
				
				for(int row = 0; row < NUM_SQUARES_SIDE; row ++) {
					
					
				
				
				gameBoard[row][col].setState(EMPTY);
				gameBoard[row][col].setWinningSquare(false);
				}
			}
			
			//paintImmediately(0,0,bWidth,bHeight);
		}
		
		public void startNewGame(boolean firstGame) {
			p1Captures = 0;
			p2Captures = 0;
			gameOver = false;
			//resetBoard();
			
			if(firstGame) {
				JOptionPane myPane = new JOptionPane();
				p1Name = JOptionPane.showInputDialog("Name of player 1(or type'c' for computer");
			if(p1Name.equals('c')|| p1Name.toLowerCase().equals("computer") || p1Name.toLowerCase().equals("comp")){
				player1IsComputer = true;
				p1ComputerPlayer = new ComputerMoveGenerator(this, BLACKSTONE);
				}
			}
			
			myScoreBoard.setName(p1Name,BLACKSTONE);
			myScoreBoard.setCaptures(p1Captures, BLACKSTONE);
			
			
			if(firstGame) {
				p2Name = JOptionPane.showInputDialog("Name of player 2(or type'c' for computer");
			if(p2Name.equals("c")|| p2Name.toLowerCase().equals("computer") || p2Name.toLowerCase().equals("comp")){
				player2IsComputer = true;
				p2ComputerPlayer = new ComputerMoveGenerator(this, WHITESTONE);

				}
			}
			myScoreBoard.setName(p2Name,WHITESTONE);
			myScoreBoard.setCaptures(p2Captures, BLACKSTONE);
		
			


			
			resetBoard();

			playerTurn = this.PLAYER1_TURN;
			
			this.gameBoard[NUM_SQUARES_SIDE/2][NUM_SQUARES_SIDE/2].setState(BLACKSTONE);
			
			darkStoneMove2Token = false;
			
			changePlayerTurn();
			
			checkForComputerMove(playerTurn);

		//changePlayerTurn();
		
			this.repaint();
		}
		
		public void changePlayerTurn() {
			playerTurn *= -1;
			System.out.println("Its now the turn of:" + playerTurn);
			myScoreBoard.setPlayerTurn(playerTurn);
		}

		
//	public void updateSizes() {
//		if(myFrame.getWidth() != bWidth || myFrame.getHeight() != bHeight +20) {
//			bWidth = myFrame.getWidth();
//			bHeight = myFrame.getHeight()-20;
//		}
//	}
		

		
		
	public boolean fiveInARow(int whichPlayer) {
		boolean isFive = false;
		
		
		for(int row = 0; row < NUM_SQUARES_SIDE; row++) {
			for(int col = 0;col< NUM_SQUARES_SIDE;col++) {
				System.out.println("In fiveRow, looking at ["+row+","+col+"]");
				for(int rL =-1;rL<=1;rL++) {
					for(int uD = -1;uD<= 1;uD++) {
						 if(fiveCheck(row,col,whichPlayer,rL, uD)){
							 System.out.println("In fiveInRow, found a 5 at ["+ row+ "," + col+"]");
							 System.out.println("FiveCheck is returning true");
							 
							 isFive =true;
						 }
			}
				
			}
		}
		
		
	}
	return isFive;
		
	}
	
	public boolean fiveCheck(int r, int c, int pt, int upDown, int rightLeft) {
		try {
			boolean foundS = false;

			
			if(upDown !=0 || rightLeft !=0) {
				
					if(gameBoard[r][c].getState()==pt) 
					{
						if(gameBoard[r+upDown][c+rightLeft].getState()==pt)
						{
							if(gameBoard[r+(upDown*2)][c+(rightLeft*2)].getState()==pt)
							{
								if(gameBoard[r+(upDown*3)][c+(rightLeft*3)].getState()==pt)
								{
									if(gameBoard[r+(upDown*4)][c+(rightLeft*4)].getState()==pt)
									{
										System.out.println("ITS A WIN");
										
										foundS = true;
										gameBoard[r][c].setWinningSquare(true);
										gameBoard[r+upDown][c+rightLeft].setWinningSquare(true);
										gameBoard[r+(upDown*2)][c+(rightLeft*2)].setWinningSquare(true);
										gameBoard[r+(upDown*3)][c+(rightLeft*3)].setWinningSquare(true);
										gameBoard[r+(upDown*4)][c+(rightLeft*4)].setWinningSquare(true);
									} 
								}
							}
						}
					}
					
			}
			
					
			 	return foundS;
			 	
			
				
			
			} catch(ArrayIndexOutOfBoundsException e) {
			
				System.out.println(e.getMessage());
				return false;
				
				
			}
		}
	
		
	public void checkForWin(int whichPlayer) {
		
		if(whichPlayer == this.PLAYER1_TURN) {
			if(this.p1Captures >= MAX_CAPTURES) {
				
				JOptionPane.showMessageDialog(null, "Congratulations:" + p1Name + " Wins!!"+ 
				"\n with" +p1Captures + "captures");
				gameOver = true;
			} else {
				if(fiveInARow(whichPlayer)) {
					JOptionPane.showMessageDialog(null, "Congratulations:" + p1Name + " Wins!!");
				gameOver = true;
				}
			}
		} else {
		
		if(whichPlayer == this.PLAYER2_TURN) {
			if(this.p2Captures >= MAX_CAPTURES) {
				
				JOptionPane.showMessageDialog(null, "Congratulations:" + p2Name + " Wins!!"+ 
				"\n with" +p2Captures + "captures");
				gameOver = true;
			} else {
				if(fiveInARow(whichPlayer)) {
					JOptionPane.showMessageDialog(null, "Congratulations:" + p2Name + " Wins!!");
				gameOver = true;
				}
			}
		
		}
	}
	}


	public void checkClick(int clickX, int clickY) {
		
		if(!gameOver) {
		for(int row= 0 ; row< NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col < NUM_SQUARES_SIDE; col++) {
		
		
		boolean squareClicked  = gameBoard[row][col].isClicked(clickX, clickY);
		
		if(squareClicked) {
			//System.out.println("You clicked the square at [" + row + "," +col+ "]");
			if(gameBoard[row][col].getState() == EMPTY) {
			
			if(!darkSquareProblem(row, col))	{
				gameBoard[row][col].setState(playerTurn);
				checkForCaptures(row, col, playerTurn);
				
				
				
				
				//this.repaint();
				this.paintImmediately(0, 0, bWidth, bHeight);
				checkForWin(playerTurn);
				
				
				
				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			} else {
				JOptionPane.showMessageDialog(null, "Second dark stone move has to be outiside of the dark square");

			}
			
			} else {
				JOptionPane.showMessageDialog(null, "This square is taken, click another");
			}
		}
	}
		}
	}
	}
	
	public void checkForComputerMove (int whichPlayer) {
		
		
		
		if(playerTurn == this.PLAYER1_TURN && this.player1IsComputer) {
			int [] nextMove = this.p1ComputerPlayer.getComputerMove();
			
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			
			this.paintImmediately(0,0,bWidth,bHeight);
			
			checkForCaptures(newR, newC, playerTurn);
			
			
			this.repaint();
			checkForWin(playerTurn);
			if(!gameOver) {

				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			}
			
			
		}else if(whichPlayer ==this.PLAYER2_TURN && this.player2IsComputer) {
			
			int [] nextMove = this.p2ComputerPlayer.getComputerMove();
			
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			checkForCaptures(newR, newC, playerTurn);
			
			
			this.repaint();
			checkForWin(playerTurn);
			if(!gameOver) {

				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			}
			
		
		}
		
		this.repaint();


	}
	
	
	
	
	
	public boolean darkSquareProblem(int r, int c) {
		
		boolean dsp = false;
		
		
		if((!darkStoneMove2Token) && (playerTurn == BLACKSTONE))
		{
			if( (r >= INNER_START && r <= INNER_END) && ( c >= INNER_START && c <= INNER_END))
			 {
				dsp = true;
			}else {
				darkStoneMove2Token = true;
		}
		
		}
		
		
		
		
		return dsp;
	}
	
	//This is a big routine to check all the captures 
	public void checkForCaptures(int r, int c, int pt) {
		
		boolean didCapture;
		
		
		for(int rL =-1;rL<=1;rL++) {
			for(int uD = -1;uD<= 1;uD++) {
				didCapture = checkForCaptures(r,c,pt,rL,uD);
			}
		}
		
		didCapture = checkForCaptures(r,c,pt,0,1);
		
		didCapture = checkForCaptures(r,c,pt,0,-1);
		
		
		//Vertical check
		didCapture = checkForCaptures(r,c,pt,1,0);
		didCapture = checkForCaptures(r,c,pt,-1,0);
		
		//diagonals
		
		didCapture = checkForCaptures(r,c,pt,1,-1);
		didCapture = checkForCaptures(r,c,pt,-1,1);
		
		
	}
		
		
		

		
		
		
	
	
	public boolean checkForCaptures(int r, int c,int pt, int upDown, int rightLeft) {
		
		try {
		
		boolean cap = false;
		
		
		
		//if(c< NUM_SQUARES_SIDE -4) {
			
		
		
		if(gameBoard[r+upDown][c+rightLeft *2].getState() == pt * -1 &&
				gameBoard[r +(upDown*2)][c+(rightLeft*2)].getState() == pt * -1 &&
				gameBoard[r + (upDown*3)][c+(rightLeft*3)].getState() == pt) {
					
					cap = true;
					gameBoard[r + upDown][c+rightLeft].setState(EMPTY) ;
					gameBoard[r + (upDown*2)][c+(rightLeft*2)].setState(EMPTY) ;
					
					
				
					//System.out.println("IT'S A Horizontal CAPTURE!!!" + rightLeft);
					//take off board
					
					
				
					if(pt == this.PLAYER1_TURN) {
						p1Captures++;
						myScoreBoard.setCaptures(p1Captures,playerTurn);
						
					} else {
						p2Captures++;
						myScoreBoard.setCaptures(p2Captures,playerTurn);
					}
			}
				
		 	return cap;
			
		
		} catch(ArrayIndexOutOfBoundsException e) {
		
			System.out.println(e.getMessage());
			return false;
			
			
		}
	}
			
	



		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			//System.out.println("Mouse Clicker");
			//System.out.println("And you clicked at [" +e.getX() +" , " +e.getY() + "]");
			
			this.checkClick(e.getX(), e.getY());
			
			
			
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
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}




		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		public void initialDisplay() {
			
			this.gameBoard[1][1].setState(BLACKSTONE);
			this.gameBoard[2][2].setState(BLACKSTONE);
			this.gameBoard[3][3].setState(BLACKSTONE);
			this.gameBoard[4][4].setState(BLACKSTONE);
			this.gameBoard[5][5].setState(BLACKSTONE);
			this.gameBoard[6][6].setState(BLACKSTONE);
			this.gameBoard[7][7].setState(BLACKSTONE);
			
			this.gameBoard[8][8].setState(BLACKSTONE);
			this.gameBoard[8][10].setState(BLACKSTONE);
			
			this.gameBoard[7][11].setState(BLACKSTONE);
			this.gameBoard[6][12].setState(BLACKSTONE);
			
			this.gameBoard[5][13].setState(BLACKSTONE);
			this.gameBoard[4][14].setState(BLACKSTONE);
			
			this.gameBoard[3][15].setState(BLACKSTONE);
			this.gameBoard[2][16].setState(BLACKSTONE);
			this.gameBoard[2][16].setState(BLACKSTONE);
			this.gameBoard[1][17].setState(BLACKSTONE);
			this.gameBoard[0][18].setState(BLACKSTONE);
			this.gameBoard[10][10].setState(BLACKSTONE);
			this.gameBoard[11][11].setState(BLACKSTONE);
			this.gameBoard[12][12].setState(BLACKSTONE);
			this.gameBoard[13][13].setState(BLACKSTONE);
			this.gameBoard[14][14].setState(BLACKSTONE);
			this.gameBoard[15][15].setState(BLACKSTONE);
			this.gameBoard[17][17].setState(BLACKSTONE);
			this.gameBoard[16][16].setState(BLACKSTONE);
			this.gameBoard[18][18].setState(BLACKSTONE);
			this.gameBoard[10][8].setState(BLACKSTONE);
			this.gameBoard[11][7].setState(BLACKSTONE);
			this.gameBoard[12][6].setState(BLACKSTONE);
			this.gameBoard[13][5].setState(BLACKSTONE);
			this.gameBoard[14][4].setState(BLACKSTONE);
			this.gameBoard[15][3].setState(BLACKSTONE);
			this.gameBoard[16][2].setState(BLACKSTONE);
			this.gameBoard[17][1].setState(BLACKSTONE);
			this.gameBoard[18][0].setState(BLACKSTONE);
			this.gameBoard[9][10].setState(BLACKSTONE);
			this.gameBoard[9][11].setState(BLACKSTONE);
			this.gameBoard[9][12].setState(BLACKSTONE);
			this.gameBoard[9][13].setState(BLACKSTONE);
			this.gameBoard[9][14].setState(BLACKSTONE);
			this.gameBoard[8][9].setState(BLACKSTONE);
			this.gameBoard[7][9].setState(BLACKSTONE);
			this.gameBoard[6][9].setState(BLACKSTONE);
			this.gameBoard[5][9].setState(BLACKSTONE);
			this.gameBoard[4][9].setState(BLACKSTONE);
			this.gameBoard[9][8].setState(BLACKSTONE);
			this.gameBoard[9][7].setState(BLACKSTONE);
			this.gameBoard[9][6].setState(BLACKSTONE);
			this.gameBoard[9][5].setState(BLACKSTONE);
			this.gameBoard[9][4].setState(BLACKSTONE);
			this.gameBoard[10][9].setState(BLACKSTONE);
			this.gameBoard[11][9].setState(BLACKSTONE);
			this.gameBoard[12][9].setState(BLACKSTONE);
			this.gameBoard[13][9].setState(BLACKSTONE);
			this.gameBoard[14][9].setState(BLACKSTONE);
			
			
			
		

			
//			[6,0]
//			
//			[7,0]
//			
//			[8,0]
//			
//			[9,0]
//			
//			[10,0]
//			
//			[11,0]
//			
//			[12,0]
//			
//			[6,1]
//			
//			You clicked the square at [6,2]
//			
//			You clicked the square at [7,2]
//			
//			You clicked the square at [8,2]
//			
//			You clicked the square at [9,2]
//			
//			You clicked the square at [9,1]
//			

			
			
		}
		
	public PenteBoardSquare[] []getBoard() {
		return gameBoard;
		
	}


	
}
