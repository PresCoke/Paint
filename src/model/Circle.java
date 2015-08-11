package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Circle extends Shape {

	private java.awt.geom.Ellipse2D thisEllipse;
	private Color shapeColour;
	private boolean shapeSelected;
	
	public Circle(Point topLeft, Point bottomRight)
	{
		int height = (int) (bottomRight.getY() - topLeft.getY());
		int width = (int) (bottomRight.getX() - topLeft.getX());
		
		thisEllipse = new java.awt.geom.Ellipse2D.Double(topLeft.x, topLeft.y, width, height);
		
		this.shapeSelected = false;
	}
	
	public boolean add(Shape adding) {
		return false;
	}

	public void addColour(Color colourOfShape) {
		this.shapeColour = colourOfShape;
	}

	protected void addParent(Shape parent) {
		this.parentOfThis = parent;
	}

	public void deSelect() {
		this.shapeSelected = false;
	}

	public Graphics draw(Graphics g) {
		
		g.setColor(this.shapeColour);
		g.fillOval((int)thisEllipse.getX(), (int)thisEllipse.getY(), (int)thisEllipse.getWidth(), (int)thisEllipse.getHeight());
		if (this.shapeSelected)
		{
			g.setColor(super.selectColour);
			g.drawRect((int)thisEllipse.getX(), (int)thisEllipse.getY(), (int)thisEllipse.getWidth(), (int)thisEllipse.getHeight());
			//topLeft
			g.fillOval((int)thisEllipse.getX(), (int)thisEllipse.getY(), 5, 5);
			//topRight
			g.fillOval((int)((thisEllipse.getX() + thisEllipse.getWidth()) - 5), (int)thisEllipse.getY(), 5, 5);
			//BottomLeft
			g.fillOval((int)thisEllipse.getX(),(int)( (thisEllipse.getY() + thisEllipse.getHeight()) - 5), 5, 5);
			//BottomRight
			g.fillOval((int)((thisEllipse.getX() + thisEllipse.getWidth()) - 5), (int)((thisEllipse.getY() + thisEllipse.getHeight()) - 5), 5, 5);	
		}
		return g;
	}

	public Shape getChild(int num) {
		return null;
	}

	public Point2d getOppositeVertex(Point2d vertex) {
		Point2d temp = new Point2d(thisEllipse.getX(), thisEllipse.getY());
		//topLeft
		if (temp.distance(vertex)<5)
		{
			//bottomRight
			temp.X += thisEllipse.getWidth();
			temp.Y += thisEllipse.getHeight();
			return temp;
		}
		//topRight
		temp.X += thisEllipse.getWidth();
		if (temp.distance(vertex)<5)
		{
			//bottomLeft
			temp.X = thisEllipse.getX();
			temp.Y += thisEllipse.getHeight();
			return temp;
		}
		//bottomRight
		temp.Y += thisEllipse.getHeight();
		if (temp.distance(vertex)<5)
		{
			//topLeft
			temp.X = thisEllipse.getX();
			temp.Y = thisEllipse.getY();
			return temp;
		}
		//bottomLeft
		temp.X = thisEllipse.getX();
		if (temp.distance(vertex)<5)
		{
			//topRight
			temp.X += thisEllipse.getWidth();
			temp.Y = thisEllipse.getY();
			return temp;
		}
		return null;
	}

	public Shape getParent() {
		return this.parentOfThis;
	}

	public boolean isInside(Point2d click) {
		if (thisEllipse.contains(click))
		{
			System.out.println("Point is inside shape: "+thisEllipse.toString());
			return true;
		}
		else
		{
			System.out.println("Point is NOT inside shape: "+thisEllipse.toString());
			return false;
		}
	}

	public boolean isSelected() {
		return this.shapeSelected;
	}

	public boolean isVertex(Point2d click) {
		Point2d temp = new Point2d(thisEllipse.getX(), thisEllipse.getY());
		//topLeft
		if (temp.distance(click)<5)
			return true;
		//topRight
		temp.X += thisEllipse.getWidth();
		if (temp.distance(click)<5)
			return true;
		//bottomRight
		temp.Y += thisEllipse.getHeight();
		if (temp.distance(click)<5)
			return true;
		//bottomLeft
		temp.X = thisEllipse.getX();
		if (temp.distance(click)<5)
			return true;
		return false;
	}

	public void move(int initialX, int initialY, int newPosX, int newPosY) {
		int deltaX = initialX - newPosX;
		int deltaY = initialY - newPosY;
		
		thisEllipse.setFrame(thisEllipse.getX() - deltaX, thisEllipse.getY() - deltaY, thisEllipse.getWidth(), thisEllipse.getHeight());
	}

	public boolean remove(Shape removing) {
		return false;
	}

	public void resize(int chosenX, int chosenY, int x, int y) {
		
		thisEllipse.setFrame(chosenX, chosenY, x - chosenX, y - chosenY);
	}

	public void select() {
		this.shapeSelected = true;
	}

	@Override
	public Point2d[] biSect() {
		Point2d[] returnable = new Point2d[2];
		returnable[0] = new Point2d(thisEllipse.getX(), thisEllipse.getY());
		returnable[1] = new Point2d(thisEllipse.getX() + thisEllipse.getWidth(), thisEllipse.getY() + thisEllipse.getHeight());
		return returnable;
	}

	public Shape clone() {
		Point p1 = new Point((int)thisEllipse.getX(), (int)thisEllipse.getY());
		Point p2 = new Point((int)(thisEllipse.getX()+thisEllipse.getWidth()), (int)(thisEllipse.getY()+thisEllipse.getHeight()));
		model.Circle newCirc = new model.Circle(p1, p2);
		newCirc.addColour(this.shapeColour);
		return newCirc;
	}
	
	public boolean equals(Object compareTo)
	{
		if (compareTo != null && compareTo instanceof Circle)
		{
			Circle compare = (Circle) compareTo;
			if (compare.shapeColour == this.shapeColour && compare.thisEllipse.getX() == this.thisEllipse.getX() 
					&& compare.thisEllipse.getY() == this.thisEllipse.getY() && compare.thisEllipse.getWidth() == this.thisEllipse.getWidth()
					&& compare.thisEllipse.getHeight() == this.thisEllipse.getHeight())
				return true;
			else return false;
		}
		else return false;
	}
}
