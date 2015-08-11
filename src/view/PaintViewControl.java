package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.*;


public class PaintViewControl {

	private ViewWithCanvas referenceToView;
	private boolean lineDraw;
	private boolean rectDraw;
	private boolean circDraw;
	//private boolean polyDraw;
	
	private boolean noDraw;
	private boolean moveShape;
	private boolean reSizeShape;
	
	private Point initialPoint;
	private Point finalPoint;
	private Point workPoint;
	
	private Point topLeft;
	private Point bottomRight;
	
	private Color colour;
	
	Point[] bisect;
	
	public PaintViewControl(ViewWithCanvas view)
	{
		referenceToView = view;
		colour = Color.BLACK;
		lineDraw = false;
		rectDraw = false;
		circDraw = false;
		//polyDraw = false;
		noDraw = true;
		
		initialPoint = null;
		finalPoint = null;
		workPoint = null;
		
		topLeft = null;
		bottomRight = null;
	
	}
	public void finishMouseEvent() {
    	if (noDraw == true)
    	{
    		if (reSizeShape == true)
    		{
    			String shapeType = PaintController.getSelectedShapeType();
    			if (shapeType == "Line")
    			{
    				ResizeShapeCommand resizeShapeC = new ResizeShapeCommand(finalPoint, initialPoint, workPoint);
    				System.out.println("Called ResizeShapeCommand on Line. Original: " + initialPoint.toString() + " to: "+finalPoint.toString()
    					+" Now: " + initialPoint.toString() + " to " + workPoint.toString());
    				resizeShapeC.Execute();
    			}
    			else if (shapeType == "Rectangle")
    			{
    				ResizeShapeCommand resizeShapeC = new ResizeShapeCommand(finalPoint, bottomRight, topLeft);
    				System.out.println("Called ResizeShapeCommand on Rectangle. Original: " + initialPoint.toString() + " to: "+finalPoint.toString()
        					+" Now: " + topLeft.toString() + " to " + bottomRight.toString());
    				resizeShapeC.Execute();
    				topLeft = null;
    				bottomRight = null;
    			}
    			else if (shapeType == "Circle")
    			{
    				ResizeShapeCommand resizeShapeC = new ResizeShapeCommand(finalPoint, bottomRight, topLeft);
    				System.out.println("Called ResizeShapeCommand on Circle. Original: " + initialPoint.toString() + " to: "+finalPoint.toString()
        					+" Now: " + topLeft.toString() + " to " + bottomRight.toString());
    				resizeShapeC.Execute();
    				topLeft = null;
    				bottomRight = null;
    			}
    			initialPoint = null;
    			workPoint = null;
    			finalPoint = null;
    			reSizeShape = false;
    		}
    		else if (moveShape == true)
    		{
    			MoveShapeCommand moveShapeC = new MoveShapeCommand(initialPoint, finalPoint);
    			System.out.println("Called MoveLineCommand. From: " + initialPoint.toString() + " to: "+finalPoint.toString());
    			moveShapeC.Execute();
    			initialPoint = null;
    			finalPoint = null;
    			moveShape = false;
    		}
    	}
    	else if (lineDraw == true)
    	{
    		NewLineCommand makeNewLine = new NewLineCommand(initialPoint, finalPoint, this.colour);
    		System.out.println("Called NewLineCommand. From: " + initialPoint.toString() + " to: "+finalPoint.toString());
    		makeNewLine.Execute();
    		initialPoint = null;
    		finalPoint = null;
    	}
    	else if (rectDraw == true)
    	{
    		NewRectangleCommand makeNewRect = new NewRectangleCommand(topLeft, bottomRight, this.colour);
    		System.out.println("Called NewRectangleCommand. From: " + topLeft.toString() + " to: " + bottomRight.toString());
    		makeNewRect.Execute();
    		
    		initialPoint = null;
    		finalPoint = null;
    		topLeft = null;
    		bottomRight = null;
    	}
    	else if (circDraw == true)
    	{
    		NewCircleCommand makeNewCirc = new NewCircleCommand(topLeft, bottomRight, this.colour);
    		System.out.println("Called NewCircleCommand. From: " + topLeft.toString() + " to: " + bottomRight.toString());
    		makeNewCirc.Execute();
    		
    		initialPoint = null;
    		finalPoint = null;
    		topLeft = null;
    		bottomRight = null;
    	}
    	/*else if (polyDraw == true)
    	{
    		
    	}*/
    	else
    	{
    		
    	}
	}

	protected void drawThings(MouseEvent me)
    {
		Graphics drawWith = this.referenceToView.getGraphics();
    	if (noDraw == true)
    	{
    		if (moveShape == true)
    		{
    			   			
    		}
    		else if (reSizeShape == true)
    		{
        		String shapetype = PaintController.getSelectedShapeType();
        		if (shapetype == "Line")
        		{
        			drawWith.setColor(referenceToView.getBackgroundColour());
        			drawWith.drawLine(initialPoint.x, initialPoint.y, workPoint.x, workPoint.y);
        			workPoint = me.getPoint();
            		drawWith.setColor(Color.RED);
        			drawWith.drawLine(initialPoint.x, initialPoint.y, workPoint.x, workPoint.y);
        		}
        		else if (shapetype == "Rectangle")
        		{
        			if (topLeft !=null && bottomRight != null)
            		{
        				drawWith.setColor(referenceToView.getBackgroundColour());
            			drawWith.setColor(referenceToView.getBackgroundColour());
            			drawWith.fillRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
            		}
            		
            		workPoint = me.getPoint();
            		
            		if (initialPoint.x < workPoint.x && initialPoint.y <= workPoint.y)
            		{
            			topLeft = (Point) initialPoint.clone();
            			bottomRight = (Point) workPoint.clone();
            		}
            		else if (initialPoint.x <= workPoint.x && initialPoint.y > workPoint.y)
            		{
            			topLeft.x = initialPoint.x;
            			topLeft.y = workPoint.y;
            			
            			bottomRight.x = workPoint.x;
            			bottomRight.y = initialPoint.y;
            		}
            		else if (initialPoint.x > workPoint.x && initialPoint.y >= workPoint.y)
            		{
            			topLeft = (Point) workPoint.clone();
            			bottomRight = (Point) initialPoint.clone();
            		}
            		else if (initialPoint.x >= workPoint.x && initialPoint.y < workPoint.y)
            		{
            			topLeft.x = workPoint.x;
            			topLeft.y = initialPoint.y;
            			
            			bottomRight.x = initialPoint.x;
            			bottomRight.y = workPoint.y;
            		}
            		
            		drawWith.setColor(Color.RED);
            		drawWith.fillRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
        		}
        		else if (shapetype == "Circle")
        		{
        			if (topLeft !=null && bottomRight != null)
            		{
        				drawWith.setColor(referenceToView.getBackgroundColour());
            			drawWith.setColor(referenceToView.getBackgroundColour());
            			drawWith.fillOval(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
            		}
            		
            		workPoint = me.getPoint();
            		
            		if (initialPoint.x < workPoint.x && initialPoint.y <= workPoint.y)
            		{
            			topLeft = (Point) initialPoint.clone();
            			bottomRight = (Point) workPoint.clone();
            		}
            		else if (initialPoint.x <= workPoint.x && initialPoint.y > workPoint.y)
            		{
            			topLeft.x = initialPoint.x;
            			topLeft.y = workPoint.y;
            			
            			bottomRight.x = workPoint.x;
            			bottomRight.y = initialPoint.y;
            		}
            		else if (initialPoint.x > workPoint.x && initialPoint.y >= workPoint.y)
            		{
            			topLeft = (Point) workPoint.clone();
            			bottomRight = (Point) initialPoint.clone();
            		}
            		else if (initialPoint.x >= workPoint.x && initialPoint.y < workPoint.y)
            		{
            			topLeft.x = workPoint.x;
            			topLeft.y = initialPoint.y;
            			
            			bottomRight.x = initialPoint.x;
            			bottomRight.y = workPoint.y;
            		}
            		
            		drawWith.setColor(Color.RED);
            		drawWith.fillOval(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
        		}
    		}
    	}
    	else if (lineDraw == true)
    	{
    		if (finalPoint != null)
    		{
    			drawWith.setColor(referenceToView.getBackgroundColour());
    			drawWith.drawLine(initialPoint.x, initialPoint.y, finalPoint.x, finalPoint.y);
    		}
    		finalPoint = me.getPoint();
    		drawWith.setColor(Color.RED);
    		drawWith.drawLine(initialPoint.x, initialPoint.y, finalPoint.x, finalPoint.y);
    	}
    	else if (rectDraw == true)
    	{
    		if (finalPoint != null && topLeft !=null && bottomRight != null)
    		{
    			drawWith.setColor(referenceToView.getBackgroundColour());
    			drawWith.fillRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    		}
    		
    		finalPoint = me.getPoint();
    		
    		if (initialPoint.x < finalPoint.x && initialPoint.y <= finalPoint.y)
    		{
    			topLeft = (Point) initialPoint.clone();
    			bottomRight = (Point) finalPoint.clone();
    		}
    		else if (initialPoint.x <= finalPoint.x && initialPoint.y > finalPoint.y)
    		{
    			topLeft.x = initialPoint.x;
    			topLeft.y = finalPoint.y;
    			
    			bottomRight.x = finalPoint.x;
    			bottomRight.y = initialPoint.y;
    		}
    		else if (initialPoint.x > finalPoint.x && initialPoint.y >= finalPoint.y)
    		{
    			topLeft = (Point) finalPoint.clone();
    			bottomRight = (Point) initialPoint.clone();
    		}
    		else if (initialPoint.x >= finalPoint.x && initialPoint.y < finalPoint.y)
    		{
    			topLeft.x = finalPoint.x;
    			topLeft.y = initialPoint.y;
    			
    			bottomRight.x = initialPoint.x;
    			bottomRight.y = finalPoint.y;
    		}
    		
    		drawWith.setColor(Color.RED);
    		drawWith.fillRect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    	}
    	else if (circDraw == true)
    	{
    		if (finalPoint != null && topLeft !=null && bottomRight != null)
    		{
    			drawWith.setColor(referenceToView.getBackgroundColour());
    			drawWith.fillOval(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    		}
    		
    		finalPoint = me.getPoint();
    		
    		if (initialPoint.x < finalPoint.x && initialPoint.y <= finalPoint.y)
    		{
    			topLeft = (Point) initialPoint.clone();
    			bottomRight = (Point) finalPoint.clone();
    		}
    		else if (initialPoint.x <= finalPoint.x && initialPoint.y > finalPoint.y)
    		{
    			topLeft.x = initialPoint.x;
    			topLeft.y = finalPoint.y;
    			
    			bottomRight.x = finalPoint.x;
    			bottomRight.y = initialPoint.y;
    		}
    		else if (initialPoint.x > finalPoint.x && initialPoint.y >= finalPoint.y)
    		{
    			topLeft = (Point) finalPoint.clone();
    			bottomRight = (Point) initialPoint.clone();
    		}
    		else if (initialPoint.x >= finalPoint.x && initialPoint.y < finalPoint.y)
    		{
    			topLeft.x = finalPoint.x;
    			topLeft.y = initialPoint.y;
    			
    			bottomRight.x = initialPoint.x;
    			bottomRight.y = finalPoint.y;
    		}
    		
    		drawWith.setColor(Color.RED);
    		drawWith.fillOval(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y);
    	}
    	/*else if (polyDraw == true)
    	{
    		
    	}*/
    	else
    	{
    		
    	}
    }
	public MouseListener getMouseListener()
	{
		return (new MouseListener() {
    		public void mouseClicked(MouseEvent me)
    		{
    		}
    		public void mouseEntered(MouseEvent me)
    		{
    			//me.consume();
    		}
			public void mouseExited(MouseEvent me)
			{
				//me.consume();
			}
			public void mousePressed(MouseEvent me) 
			{
				initialPoint = me.getPoint();
				if (topLeft == null)
					topLeft = new Point();
				if (bottomRight == null)
					bottomRight = new Point();
				if (noDraw==true)
				{
					model.Point2d p = new model.Point2d(initialPoint.x, initialPoint.y);
					if (me.isShiftDown())
					{
						if (PaintController.clickIsInsideAddShapeToSelected(p))
						{
							
						}
					}
					else if (PaintController.clickIsVertex(p))
					{
						reSizeShape = true;
						moveShape = false;
						
						finalPoint = initialPoint;
						workPoint = (Point) finalPoint.clone();
						initialPoint = PaintController.getOtherVertex(p);
					}
					else if (PaintController.clickInsideShape(p))
					{
						reSizeShape = false;
						moveShape = true;
					}
					else PaintController.deSelectAll();
				}
			}
			public void mouseReleased(MouseEvent me)
			{
				finalPoint = me.getPoint();
				if (finalPoint.getX() != initialPoint.getX() || finalPoint.getY() != initialPoint.getY())
				{
					finishMouseEvent();
				}
				initialPoint = null;
			}
    	});
	}
	public MouseMotionListener getMouseMotionListener()
	{
		return(new MouseMotionListener() {
			public void mouseDragged(MouseEvent arg0) {
				drawThings(arg0);
			}
			public void mouseMoved(MouseEvent arg0) {
				
			}
		});
	}
	public void setLineDraw()
	{
		if (lineDraw == false) {
            lineDraw = true;
            noDraw = false;
            rectDraw = false;
            circDraw = false;
            PaintController.deSelectAll();
            //polyDraw = false;
        }
        else if (lineDraw == true)
        {
        	lineDraw = false;
        }
	}
	public void setRectDraw()
	{
		if (rectDraw == false) {
            lineDraw = false;
            noDraw = false;
            rectDraw = true;
            circDraw = false;
            PaintController.deSelectAll();
            //polyDraw = false;
        }
        else if (rectDraw == true)
        {
        	rectDraw = false;
        }
	}
	public void setCircDraw()
	{
		if (circDraw == false) {
            lineDraw = false;
            noDraw = false;
            rectDraw = false;
            circDraw = true;
            PaintController.deSelectAll();
            //polyDraw = false;
        }
        else if (circDraw == true)
        {
        	circDraw = false;
        }
	}
	public void setNoDraw()
	{
		if (noDraw == false) {
            lineDraw = false;
            noDraw = true;
            rectDraw = false;
            circDraw = false;
            //polyDraw = false;
        }
        else if (noDraw == true)
        {
        	noDraw = false;
        }
	}
	public void changeColour(Color c)
	{
		this.colour = c;
		PaintController.setSelectedColour(this.colour);
	}
	public KeyListener getKeyListener()
	{
		return (new KeyListener(){
			public void keyPressed(KeyEvent ke) {
				if (ke.isControlDown())
				{
					if (ke.getKeyChar() == 'c')
					{
						
					}
					else if (ke.getKeyChar() == 'v')
					{
						
					}
				}
				else if (ke.getKeyChar() == 'g')
				{
					if (PaintController.getSelectedShapeType() == "Composite")
					{
						GroupCommand groupObjects = new GroupCommand(PaintController.getGroup());
						groupObjects.Execute();
					}
				}
				else if (ke.getKeyChar() == 'u')
				{
					if (PaintController.getSelectedShapeType() == "Composite")
					{
						GroupCommand groupObjects = new GroupCommand(PaintController.getGroup());
						groupObjects.UnExecute();
					}
				}
			}

			public void keyReleased(KeyEvent ke) {
			}

			public void keyTyped(KeyEvent ke) {
			}
		});
	}
}
