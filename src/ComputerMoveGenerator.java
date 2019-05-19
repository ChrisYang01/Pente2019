import java.util.ArrayList;
import java.util.Collections;

public class ComputerMoveGenerator {
	
	
	public static final int OFFENSE =1;
	public static final int DEFENSE =-1;
	public static final int ONE_IN_ROW_DEF =1;
	public static final int TWO_IN_ROW_DEF = 2;
	
	public static final int ONE_IN_ROW_OFF =1;
	public static final int TWO_IN_ROW_OFF =4;
	
	public static final int TWO_IN_ROW_CAP =10;
	public static final int TWO_IN_ROW_DEF_CAP =0;
	
	public static final int THREE_IN_ROW_DEF =15;
	public static final int FOUR_IN_ROW_DEF =100;
	
	public static final int THREE_IN_ROW_OFF =13;
	public static final int FOUR_IN_ROW_OFF =150;
	
	
	
	
	PenteGameBoard myGame;
	
	int myStone;
	
	
	
	ArrayList<CMObject> allMoves = new ArrayList <CMObject>();
	//ArrayList<CMObject> dMoves = new ArrayList <CMObject>();
	
	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor) {
		
		myStone = stoneColor;
		myGame = gb;
		
		System.out.println("Computer is playing as player" + myStone);
	}
	
	
	public void sortPriorities(){
		
		//Comparator<CMObject> compareByPriority = (CMObject o1, CMObject o2) ->
		//o1.getPriorityInt().compareTo( o2.getPriorityInt());
		
		Collections.sort(allMoves);
		
	}
	
	public int [] getComputerMove() {
		int [] newMove = new int[2];
		newMove[0] = -1;
		newMove[1] = -1;
		
		
		
		allMoves.clear();
	
				
		
		findDefMoves();
		findOffMoves();
		sortPriorities();
		
	

		if(allMoves.size() > 0) {
			
			CMObject ourMove;
			
			if(allMoves.get(0).getPriority() <= this.ONE_IN_ROW_DEF) {
				ourMove = allMoves.get((int)(Math.random()* allMoves.size()));
			} else {
				ourMove = allMoves.get(0);
			}
			
			newMove[0] = ourMove.getRow();
			newMove[1] = ourMove.getCol();
			
			if(myGame.darkSquareProblem(newMove[0],newMove[1]) == true ){
				System.out.println("You have a problem");
			}
			
		} else {
			
			if(myStone == PenteGameBoard.BLACKSTONE && myGame.getdarkStoneMove2Taken() == false) {
				System.out.println("In getComputerMove(), myStone is dark and there is DSProblem ");
				
				int newBStoneProbRow = -1;
				int newBStoneProbCol = -1;
				int innerSafeSquareSideLen = PenteGameBoard.INNER_END - PenteGameBoard.INNER_START +1;
				
				while (myGame.getdarkStoneMove2Taken() == false) {
					newBStoneProbRow = (int)(Math.random() * (innerSafeSquareSideLen +2))
							+ (innerSafeSquareSideLen +1);
					newBStoneProbCol = (int)(Math.random() * (innerSafeSquareSideLen +2))
							+ (innerSafeSquareSideLen +1);
					
					myGame.darkSquareProblem(newBStoneProbRow, newBStoneProbCol);
				}
				
				newMove[0] = newBStoneProbRow;
				newMove[1] = newBStoneProbCol;
				
				
			} else {
				System.out.println("I'm just generating random moves");
				newMove = generateRandomMove();
			}
			
		}
		
			
			//System.out.println("First Def Move:" + dMoves.get(0));
			//System.out.println("Last Def Move:" + dMoves.get(dMoves.size()-1));
			
			//int whichOne = (int)(Math.random() * dMoves.size());
			
			//CMObject ourMove = dMoves.get(0);
			
			//newMove[0] = ourMove.getRow();
			//newMove[1] = ourMove.getCol();
		
		
		
		
		//newMove = generateRandomMove();
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		return newMove;
		
	}
	
	public void printPriorities() {
		
		for(CMObject m: allMoves) {
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
						allMoves.add(newMove);
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
		
		public void setMoves(int r, int c, int p, int type) {
			System.out.println(" in set move the dark stone move is" + myGame.getdarkStoneMove2Taken());
			
			if(myStone == PenteGameBoard.BLACKSTONE && myGame.getdarkStoneMove2Taken() == false) {
				
				if(myGame.darkSquareProblemComputerMoveList(r, c)  == false) {
					CMObject newMove = new CMObject();
					newMove.setRow(r);
					newMove.setCol(c);
					newMove.setPriority(p);
					newMove.setMoveType(type);
					allMoves.add(newMove);
				} else {
					System.out.println("There is a dark square problem: ");
					System.out.println("\tmove at ["+r+", " + c+ "] is being thrown out");
				}
			
		} else {
			
			CMObject newMove = new CMObject();
			newMove.setRow(r);
			newMove.setCol(c);
			newMove.setPriority(p);
			newMove.setMoveType(type);
			allMoves.add(newMove);
		}
			
	}
		

		
		
		
		
		
		public void setOffMove(int r, int c, int p) {
			CMObject newMove = new CMObject();
			newMove.setRow(r);
			newMove.setCol(c);
			newMove.setPriority(p);
			newMove.setMoveType(OFFENSE);
			allMoves.add(newMove);
		}
	
	
		public void findTwoDef(int r, int c) {
		
		
			for(int rL =-1;rL<=1;rL++) {
				for(int uD = -1;uD<= 1;uD++) {
					try {
					
					if(myGame.getBoard()[r + rL][c +uD].getState() == myStone *-1 &&
						myGame.getBoard()[r + rL*2][c +uD*2].getState() == PenteGameBoard.EMPTY) {
							
							
						// r-rl , s-ud is open
						if(isOnBoard(r-rL, c-uD) == false) {
							setMoves(
									r +(rL *2), 
									c +(uD *2), 
									TWO_IN_ROW_DEF,DEFENSE	);
						
			
						} else if(
								
							myGame.getBoard()[r - rL][c -uD].getState() == PenteGameBoard.EMPTY
							){
						setMoves(
									r +(rL *2), 
									c +(uD *2),
									this.TWO_IN_ROW_DEF,DEFENSE);
							
								
							}else if(
									myGame.getBoard()[r - rL][c -uD].getState() == myStone
									) {
								setMoves(
										r +(rL *2), 
										c +(uD *2),
										this.TWO_IN_ROW_CAP,DEFENSE);
										
							
							
					
			
						
							
						// if r-rl, c-ud is is 
							
							
						//if r-rl is the wall
							
						
							
						}	
							
							
							
						
				
						
						
					}
				
					
				} catch (ArrayIndexOutOfBoundsException e) {
					//System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
				
					}
				}
			
			}
	}
		
	public void findThreeDef(int r, int c) {	
		
		
		
		for(int rL =-1;rL<=1;rL++) {
			for(int uD = -1;uD<= 1;uD++) {
				try {
					
					if(myGame.getBoard()[r + rL][c +uD].getState() == myStone *-1) {
						if(myGame.getBoard()[r + rL][c +uD*2].getState() == PenteGameBoard.EMPTY) {
							if(myGame.getBoard()[r + rL*3][c +uD*3].getState() == PenteGameBoard.EMPTY) {
						}
							
							
						// r-rl , s-ud is open
						if(isOnBoard(r-rL, c-uD) == false) {
							setMoves(
									r +(rL *3), 
									c +(uD *3), 
									THREE_IN_ROW_DEF,DEFENSE);

						} else if(
								
							myGame.getBoard()[r - rL][c -uD].getState() == PenteGameBoard.EMPTY
							){
							setMoves(
									r +(rL *3), 
									c +(uD *3),
									this.THREE_IN_ROW_DEF,DEFENSE);
							
								
							}else if(
									myGame.getBoard()[r - rL][c -uD].getState() == myStone
									) {
								setMoves(
										r +(rL *3), 
										c +(uD *3),
										this.THREE_IN_ROW_DEF,DEFENSE);
								
							}
						
						
						
						// if r-rl, c-ud is is 
							
							
						//if r-rl is the wall
							
						
							
						}	
							
						
						
					}
				
					
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
				
					}
				}
			
			}
	}			
			
		
//	// new part	
//	public void findFourDef(int r, int c) {	
//		
//		
//		
//		for(int rL =-1;rL<=1;rL++) {
//			
//			for(int uD = -1;uD<= 1;uD++) {
//				
//				try {
//					
//					if(myGame.getBoard()[r + rL][c +uD].getState() == myStone *-1 &&
//						myGame.getBoard()[r + rL*2][c +uD*2].getState() == myStone*-1 &&
//						myGame.getBoard()[r + rL*3][c +uD*3].getState() == myStone*-1 &&
//						myGame.getBoard()[r + rL*4][c +uD*4].getState() == PenteGameBoard.EMPTY) {
//						
//							
//							
//						
//						if(isOnBoard(r-rL, c-uD) == false) {
//							setDefMove(
//									r +(rL *4), 
//									c +(uD *4), 
//									FOUR_IN_ROW_DEF);
//
//						} else if(
//								
//							myGame.getBoard()[r - rL][c -uD].getState() == myStone
//							){
//						setDefMove(
//									r +(rL *4), 
//									c +(uD *4),
//									FOUR_IN_ROW_DEF);
//							
//							
//						
//						
//						
//						// if r-rl, c-ud is is 
//							
//							
//						//if r-rl is the wall
//							
//						
//							
//						}	
//							
//						
//						
//					}
//				
//					
//				} catch (ArrayIndexOutOfBoundsException e) {
//					System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
//				
//					}
//				}
//			
//			}
//	}		
//		
//		
//	
	
	
	public void findOffMoves() {
		
		for(int row = 0; row < PenteGameBoard.NUM_SQUARES_SIDE;row++) {
			
			for(int col = 0; col < PenteGameBoard.NUM_SQUARES_SIDE;col++) {
				
				if(myGame.getBoard()[row][col].getState()== myStone) {
					
					findOneOff(row,col);
					findTwoOff(row,col);
					findThreeOff(row,col);
					//findFourOff(row,col);
				}
			}
			
		}
	}
		
	public void findOneOff(int r, int c) {	
			
			
			for(int rL =-1;rL<=1;rL++) {
				
			
				for(int uD = -1;uD<= 1;uD++) {
					
					try {
						
						if(myGame.getBoard()[r + rL][c +uD].getState() == PenteGameBoard.EMPTY) {
							if(myGame.getBoard()[r + rL][c +uD].getState() == myStone * -1) {
								
								setOffMove(r+ rL, c + uD, TWO_IN_ROW_DEF_CAP);
							} else {
								setOffMove(r+rL,c+uD,ONE_IN_ROW_OFF);
							}
							
						}
						
					} catch (ArrayIndexOutOfBoundsException e) {
						System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
						return;
					}
					
				}
				
			}
								
							
		
		
	}
		
	public void findTwoOff(int r, int c) {	
			
			
			
			for(int rL =-1;rL<=1;rL++) {
				
			
				for(int uD = -1;uD<= 1;uD++) {
					
					try {
						
						if(myGame.getBoard()[r + rL][c +uD].getState() == myStone && myGame.getBoard()[r+rL*2][c+uD*2].getState()==PenteGameBoard.EMPTY) {
							if(isOnBoard(r - rL,c-uD)== false) {
								
								setOffMove(r+ rL, c + uD, TWO_IN_ROW_DEF_CAP);
							} else {
								setOffMove(r+rL,c+uD,ONE_IN_ROW_OFF);
							}
							
						}
						
					} catch (ArrayIndexOutOfBoundsException e) {
						//System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
						return;
					}
					
				}
				
			}
								
							
		
		
	}
	
	public void findThreeOff(int r, int c) {	
		
		
		
		for(int rL =-1;rL<=1;rL++) {
			
		
			for(int uD = -1;uD<= 1;uD++) {
				
				try {
					
					if(myGame.getBoard()[r + rL][c +uD].getState() == myStone && 
						myGame.getBoard()[r+rL*2][c+uD*2].getState()== myStone &&
						myGame.getBoard()[r+rL*3][c+uD*3].getState()==PenteGameBoard.EMPTY) {
					
						if(isOnBoard(r - rL,c-uD)== false) {
							
							setOffMove(r+ rL*3, c + uD*3, THREE_IN_ROW_OFF);
							
						} else if (myGame.getBoard()[r- rL][c-uD].getState()==myStone ) {
							
							setOffMove(r+ rL*3, c + uD*3, THREE_IN_ROW_OFF);
						
						} else if (myGame.getBoard()[r- rL][c-uD].getState()==PenteGameBoard.EMPTY ) {
							setOffMove(r+ rL*3, c + uD*3, FOUR_IN_ROW_OFF -1);
						}
							
						
					}
					
				} catch (ArrayIndexOutOfBoundsException e) {
					//System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
					return;
				}
				
			}
			
		}
			
	}
		
//		public void findFourOff(int r, int c) {	
//			
//			
//			
//			for(int rL =-1;rL<=1;rL++) {
//				
//			
//				for(int uD = -1;uD<= 1;uD++) {
//					
//					try {
//						
//						if(myGame.getBoard()[r + rL][c +uD].getState() == myStone && 
//							myGame.getBoard()[r+rL*2][c+uD*2].getState()== myStone &&
//							myGame.getBoard()[r+rL*3][c+uD*3].getState()== myStone &&
//							myGame.getBoard()[r+rL*4][c+uD*4].getState()==PenteGameBoard.EMPTY) {
//							
//						
//						
//						if(isOnBoard(r - rL,c-uD)== false) {
//								
//								setOffMove(r+ rL*4, c + uD*4, FOUR_IN_ROW_OFF);
//								
//							} else if (myGame.getBoard()[r- rL][c-uD].getState()==myStone ) {
//								
//								setOffMove(r+ rL*4, c + uD*4, FOUR_IN_ROW_OFF);
//							
//							} else if (myGame.getBoard()[r- rL][c-uD].getState()==PenteGameBoard.EMPTY ) {
//								setOffMove(r+ rL*4, c + uD*4, FOUR_IN_ROW_OFF -1);
//							}
//								
//					 		
//						}
//						
//					} catch (ArrayIndexOutOfBoundsException e) {
//						//System.out.println("Off the board in findOneDef at [ " + r+ "," + c +"]");
//						return;
//					}
//					
//				}
//				
//			}
//		
//		
//							
//						
//	
//	
//}
	
	
	
	
	public void findDefCase(int r, int c) {
	
		for(int rL =-1;rL<=1;rL++) {
			for(int uD = -1;uD<= 1;uD++) {
				try {
				
				if(myGame.getBoard()[r + rL][c +uD].getState() == myStone *-1 &&
					myGame.getBoard()[r + rL*2][c +uD*2].getState() == PenteGameBoard.EMPTY &&
					myGame.getBoard()[r + rL*3][c +uD*3].getState() == myStone *-1) {
					
				if(isOnBoard(r-rL,c- uD) == false) {
					setMoves(r-rL *2, c +uD*2, THREE_IN_ROW_DEF, DEFENSE);
					
				} else if (myGame.getBoard()[r-rL][c- uD].getState() == myStone) {
					setMoves(r-rL *2, c +uD*2, THREE_IN_ROW_DEF, DEFENSE);
				} else if (myGame.getBoard()[r-rL][c- uD].getState() == PenteGameBoard.EMPTY) {
					setMoves(r-rL *2, c +uD*2, FOUR_IN_ROW_DEF -1, DEFENSE);
				
				}
				
							
						
			}
				
				
				
			if(myGame.getBoard()[r + rL][c +uD].getState() == myStone *-1 &&
					myGame.getBoard()[r + rL*2][c +uD*2].getState() == myStone *-1 &&
					myGame.getBoard()[r + rL*3][c +uD*3].getState() == PenteGameBoard.EMPTY &&
							myGame.getBoard()[r + rL*3][c +uD*3].getState() == myStone *-1){
				setMoves(r +rL * 3, c+ uD * 3, FOUR_IN_ROW_DEF, DEFENSE);
			}
			
			if(myGame.getBoard()[r + rL][c +uD].getState() == myStone *-1 &&
					myGame.getBoard()[r + rL*2][c +uD*2].getState() == PenteGameBoard.EMPTY &&
					myGame.getBoard()[r + rL*3][c +uD*3].getState() == myStone *-1 &&
							myGame.getBoard()[r + rL*3][c +uD*3].getState() == myStone *-1){
				setMoves(r +rL * 2, c+ uD * 2, FOUR_IN_ROW_DEF, DEFENSE);
			}
			
				} catch(ArrayIndexOutOfBoundsException e) {
					
				}
				
			}
			
		}
		
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
