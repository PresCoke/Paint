package model;

import java.awt.*;

public class Rectangle extends Shape {

	java.awt.Rectangle thisRectangle;
	private Color shapeColour;
	private boolean shapeSelected;
	
	public Rectangle(Point topLeft, Point bottomRight)
	{
		int height = (int) (bottomRight.getY() - topLeft.getY());
		int width = (int) (bottomRight.getX() - topLeft.getX());
		thisRectangle = new java.awt.Rectangle(topLeft.x, topLeft.y, width, height);
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
		g.fillRect(thisRectangle.x, thisRectangle.y, thisRectangle.width, thisRectangle.height);
		if (this.shapeSelected)
		{
			g.setColor(super.selectColour);
			g.drawRect(thisRectangle.x, thisRectangle.y, thisRectangle.width, thisRectangle.height);
			//topLeft
			g.fillOval(thisRectangle.x, thisRectangle.y, 5, 5);
			//topRight
			g.fillOval((thisRectangle.x + thisRectangle.width) - 5, thisRectangle.y, 5, 5);
			//BottomLeft
			g.fillOval(thisRectangle.x, (thisRectangle.y + thisRectangle.height) - 5, 5, 5);
			//BottomRight
			g.fillOval((thisRectangle.x + thisRectangle.width) - 5, (thisRectangle.y + thisRectangle.height) - 5, 5, 5);	
		}
		return g;
	}

	public Shape getChild(int num) {
		return null;
	}

	public Point2d getOppositeVertex(Point2d vertex) {
		Point2d temp = new Point2d(thisRectangle.x, thisRectangle.y);
		//topLeft
		if (temp.distance(vertex)<5)
		{
			//bottomRight
			temp.X += thisRectangle.width;
			temp.Y += thisRectangle.height;
			return temp;
		}
		//topRight
		temp.X += thisRectangle.width;
		if (temp.distance(vertex)<5)
		{
			//bottomLeft
			temp.X = thisRectangle.x;
			temp.Y += thisRectangle.height;
			return temp;
		}
		//bottomRight
		temp.Y += thisRectangle.height;
		if (temp.distance(vertex)<5)
		{
			//topLeft
			temp.X = thisRectangle.x;
			temp.Y = thisRectangle.y;
			return temp;
		}
		//bottomLeft
		temp.X = thisRectangle.x;
		if (temp.distance(vertex)<5)
		{
			//topRight
			temp.X += thisRectangle.width;
			temp.Y = thisRectangle.y;
			return temp;
		}
		return null;
	}

	public Shape getParent() {
		return this.parentOfThis;
	}

	public boolean isInside(Point2d click) {
		if (thisRectangle.contains(click))
		{
			System.out.println("Point is inside shape: "+thisRectangle.toString());
			return true;
		}
		else
		{
			System.out.println("Point is NOT inside shape: "+thisRectangle.toString());
			return false;
		}
	}

	public boolean isSelected() {
		return this.shapeSelected;
	}

	public boolean isVertex(Point2d click) {
		Point2d temp = new Point2d(thisRectangle.x, thisRectangle.y);
		//topLeft
		if (temp.distance(click)<5)
			return true;
		//topRight
		temp.X += thisRectangle.width;
		if (temp.distance(click)<5)
			return true;
		//bottomRight
		temp.Y += thisRectangle.height;
		if (temp.distance(click)<5)
			return true;
		//bottomLeft
		temp.X = thisRectangle.x;
		if (temp.distance(click)<5)
			return true;
		return false;
	}

	public void move(int initialX, int initialY, int newPosX, int newPosY) {
		int deltaX = initialX - newPosX;
		int deltaY = initialY - newPosY;
		
		thisRectangle.x -= deltaX;
		thisRectangle.y -= deltaY;
	}

	public boolean remove(Shape removing) {
		return false;
	}

	public void resize(int chosenX, int chosenY, int x, int y) {
		thisRectangle.x=chosenX;
		thisRectangle.y=chosenY;
		thisRectangle.width = x - chosenX;
		thisRectangle.height = y - chosenY;
	}

	public void select() {
		this.shapeSelected = true;
	}

	@Override
	public Point2d[] biSect() {
		Point2d[] returnable = new Point2d[2];
		returnable[0] = new Point2d(thisRectangle.getX(), thisRectangle.getCenterY());
		returnable[1] = new Point2d(thisRectangle.getX() + thisRectangle.getWidth(), thisRectangle.getY() + thisRectangle.getHeight());
		return returnable;
	}

	@Override
	public Shape clone() {
		Point p1 = new Point(thisRectangle.x, thisRectangle.y);
		Point p2 = new Point(thisRectangle.x+thisRectangle.width, thisRectangle.y+thisRectangle.height);
		model.Rectangle newRect = new model.Rectangle(p1, p2);
		newRect.addColour(this.shapeColour);
		return newRect;
	}

	public boolean equals(Object compareTo)
	{
		if (compareTo != null && compareTo instanceof model.Rectangle)
		{
			model.Rectangle compare = (model.Rectangle) compareTo;
			if (compare.shapeColour == this.shapeColour && compare.thisRectangle.getX() == this.thisRectangle.getX()
					&& compare.thisRectangle.getY() == this.thisRectangle.getY() && compare.thisRectangle.getWidth() == this.thisRectangle.getWidth()
					&& compare.thisRectangle.getHeight() == this.thisRectangle.getHeight())
				return true;
			else return false;
		}
		else return false;
	}
}
