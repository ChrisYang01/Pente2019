import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class PenteBoardSquare {
	
	private int xLoc,yLoc;
	private int sWidth,sHeight;
	
	private int sState;
	
	private Color sColor;
	private Color lColor;
	private Color bColor;
	private Color innerC;
	
	private Color darkStoneColor = new Color(4,9,84);
	private Color darkStoneTop  = new Color (63,39,138);
	private Color darkStoneHighlight = new Color(188,199,255);
	private Color shadowGrey = new Color(158,143,99);
	private Color lightStoneColor = new Color(255,255,240);
	private Color lightStoneTop  = new Color (250,250,250);
	
	boolean isInner = false;
	
	
	public PenteBoardSquare(int x, int y, int w, int h) {
		
		xLoc = x;
		yLoc = y;
		sWidth = w;
		sHeight = h;
		
		
		
		sColor = new Color(249, 218, 124);
		lColor = new Color(83,85,89);
		bColor = new Color (240, 240, 190);
		innerC = new Color(255, 238, 134);
		
		sState = PenteGameBoard.EMPTY;
		
		
	}
	
	public void setInner() {
		
		isInner = true;
	}
	
	
	public void drawMe(java.awt.Graphics g) {
		
		if (isInner == true) {
			g.setColor(innerC);
		} else {
			g.setColor(sColor);
		}
		
		g.fillRect(xLoc, yLoc, sWidth, sHeight);
		
		
		//border color 
		
		g.setColor(bColor);
		g.drawRect(xLoc, yLoc, sWidth, sHeight);
		
		
		if(sState != PenteGameBoard.EMPTY) {
			g.setColor(shadowGrey);
			g.fillOval(xLoc, yLoc + 6, sWidth-8, sHeight-8);
		}
		
		
	
		g.setColor(lColor);
		
		//g.setColor(bColor);  
		//g.drawRect(xLoc, yLoc, sWidth, sHeight);
	
		//g.setColor(lColor);
		
		//Horizontal
		
		g.drawLine(xLoc, yLoc + sHeight/2,xLoc +sWidth, yLoc+sHeight/2);
		g.drawLine(xLoc + sWidth/2, yLoc, xLoc+sWidth/2,yLoc+sHeight);
	
		if(sState == PenteGameBoard.BLACKSTONE) {
			g.setColor(darkStoneColor);
			g.fillOval(xLoc +4 , yLoc +4, sWidth-8, sHeight-8);
			
			g.setColor(darkStoneTop);
			g.fillOval(xLoc, yLoc, sWidth-12, sHeight-10);
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(3));
			
			
			
			g.setColor(darkStoneHighlight);
			
			g2.setStroke(new BasicStroke(1));
			
			g2.drawArc(xLoc+(int)(sWidth*0.45),
					yLoc + 10,
					(int)(sWidth * 0.30),
					
					(int)(sHeight * 0.35),
					0,
					90
					);
			
			
		}
		
		if(sState == PenteGameBoard.WHITESTONE) {
			g.setColor(lightStoneColor);
			g.fillOval(xLoc +4, yLoc +4, sWidth-8, sHeight-8);
			
			g.setColor(lightStoneTop);
			g.fillOval(xLoc +8, yLoc +6, sWidth-12,sHeight-10);
		}
	
	
	
	}
	
	public void setState(int newState) {
		if (newState < -1 || newState >1) {
			System.out.println(newState + "is an illegal. State must be between -1 and 1");
		} else {
			sState = newState;
		}
		 
	}
	
	
	

}
