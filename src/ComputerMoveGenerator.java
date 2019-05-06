import java.util.ArrayList;
import java.util.Collections;

public class ComputerMoveGenerator {
	
	
	public static final int OFFENSE =1;
	public static final int DEFENSE =-1;
	public static final int ONE_IN_ROW_DEF =1;
	public static final int TWO_IN_ROW_DEF = 2;
	public static final int TWO_IN_ROW_OPEN = 3;
	public static final int TWO_IN_ROW_CAP = 4;
	
	
	PenteGameBoard myGame;
	int myStone;
	
	
	
	ArrayList<CMObject> oMoves = new ArrayList <CMObject>();
	ArrayList<CMObject> dMoves = new ArrayList <CMObject>();
	
	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor) {
		
		myStone = stoneColor;
		myGame = gb;
		
		System.out.println("Computer is playing as player" + myStone);
	}
	
	
	public void sortDefPriorities(){
		
		//Comparator<CMObject> compareByPriority = (CMObject o1, CMObject o2) ->
		//o1.getPriorityInt().compareTo( o2.getPriorityInt() );
		
		Collections.sort(dMoves);
		
	}
	
	public int [] getComputerMove() {
		int [] newMove = new int[2];
		newMove[0] = -1;
		newMove[1] = -1;
		
		dMoves.clear();
		dMoves.clear();
				
		
		findDefMoves();
		sortDefPriorities();
		
		System.out.println("First Def Move:" + dMoves.get(0));
		System.out.println("Last D3ef Move:" + dMoves.get(dMoves.size()-1));

		findOffMoves();
		
		if(dMoves.size() > 0) {
			//int whichOne = (int)(Math.random() * dMoves.size());
			CMObject ourMove = dMoves.get(0);
			newMove[0] = ourMove.getRow();
			newMove[1] = ourMove.getCol();
		
			
		} else {
			newMove = generateRandomMove();
		}
		
		
		
		
		newMove = generateRandomMove();
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	//	sleepForAMove();
		
		return newMove;
		
	}
	
	public void printPriorities() {
		
		for(CMObject m: dMoves) {
			System.out.println(m);
		}
	}
	
	
	public void findDefMoves() {
		
		for(int row= 0 ; row< PenteGameBoard.NUM_SQUARES_SIDE; row++) {
			for(int col = 0; col < PenteGameBoard.NUM_SQUARES_SIDE; col++) {
				if(myGame.getBoard()[row][col].getState() == myStone * -1) {
					findOneDef(row,col);
					findTwoDef(row,col);
				}
			}
				
		}
		
		//findOneDef();
		//findTwoDef();
		//findThreeDef();
		//findTFourDef();
		
		
		
		
	}
	
	public void findOneDef(int r, int c) {
		
		
		for(int rL =-1;rL<=1;rL++) {
			for(int uD = -1;uD<= 1;uD++) {
				try {
					
					if(myGame.getBoard()[r + rL][c +uD].getState() == PenteGameBoard.EMPTY) {
						CMObject newMove = new CMObject();
						newMove.setRow(r +rL);
						newMove.setCol(c +uD);
						newMove.setPriority(ONE_IN_ROW_DEF);
						newMove.setMoveType(DEFENSE);
						dMoves.add(newMove);
					}
				
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
				
					}
				}
			
			}
	}
	
	
		public boolean isOnBoard(int r, int c) {
			
			boolean isOn = false;
			
			if(r>0 && r<PenteGameBoard.NUM_SQUARES_SIDE) {
				if(c>0 && c<PenteGameBoard.NUM_SQUARES_SIDE) {
					isOn = true;
				}
			}
			
			return isOn;
		}
		
		
		public void setDefMove(int r, int c, int p) {
			CMObject newMove = new CMObject();
			newMove.setRow(r);
			newMove.setCol(c);
			newMove.setPriority(p);
			newMove.setMoveType(DEFENSE);
			dMoves.add(newMove);
		}
	
	
		public void findTwoDef(int r, int c) {
		
		
		for(int rL =-1;rL<=1;rL++) {
			for(int uD = -1;uD<= 1;uD++) {
				try {
					
					if(myGame.getBoard()[r + rL][c +uD].getState() == myStone *-1) {
						if(myGame.getBoard()[r + rL][c +uD*2].getState() == PenteGameBoard.EMPTY) {
							
							
						// r-rl , s-ud is open
						if(isOnBoard(r-rL, c-uD) == false) {
							setDefMove(
									r +(rL *2), 
									c +(uD *2), 
									this.TWO_IN_ROW_DEF	);
						
			
						} else if(
							myGame.getBoard()[r - rL][c -uD].getState() == myStone){
								
							}
						}
			
						
							
						// if r-rl, c-ud is is 
							
							
						//if r-rl is the wall
							
						
							
							
							
							
							
						
				
						
						
					}
				
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
				
					}
				}
			
			}
	}
	
	public void findOffMoves() {
		
		
		
		
	}
	
	
	public int [] generateRandomMove() {
		int [] move = new int[2]; 
		
		boolean done = false;
		
		int newR, newC;
		
		do {
			newR = (int)(Math.random()* PenteGameBoard.NUM_SQUARES_SIDE );
			newC = (int)(Math.random()* PenteGameBoard.NUM_SQUARES_SIDE );
			
			if(myGame.getBoard()[newR][newC].getState() == PenteGameBoard.EMPTY) {
				done = true;
				move[0]=newR;
				move[1]=newC;

				
			}
			
			
		}while (!done);
		
		
		return move;
		
	}
		
		
		public void sleepForAMove() throws InterruptedException{
			Thread currThread = Thread.currentThread();
			
			Thread.sleep(PenteGameBoard.SLEEP_TIME);
		
		
		
		
		
	}

}
