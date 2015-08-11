package model;
import java.awt.*;
import java.awt.geom.*;

import model.Shape;
import model.Point2d;

public class Line extends Shape {

	private class Line2d extends Line2D
	{

		private Point2d P1, P2;
		
		public Line2d (Point2d initialPt, Point2d finalPt)
		{
			P1 = initialPt;
			P2 = finalPt;
		}
		public Point2d getP1() {
			return P1;
		}

		public Point2d getP2() {
			return P2;
		}

		public double getX1() {
			return (P1.getX());
		}

		public double getX2() {
			return (P2.getX());
		}

		public double getY1() {
			return (P1.getY());
		}

		public double getY2() {
			return (P2.getY());
		}

		public void setLine(double x1, double y1, double x2, double y2) {
			System.out.println("Calling method: setLine");
			P1.setLocation(x1, y1);
			P2.setLocation(x2, y2);
		}

		public Rectangle2D getBounds2D() {
			System.out.println("Calling method: getBounds2D");
			return null;
		}
		
	}
	
	private Line2d thisLine;
	//private int X1, Y1, X2, Y2;
	private Color shapeColour;
	private boolean shapeSelected;
	
	public Line (Point2d initialPt, Point2d finalPt)
	{
		thisLine = new Line2d(initialPt, finalPt);
		
		/*Point2d newPoint = new Point2d(initialPt.getX()+1, initialPt.getY()+1);
		Point2d somethingPoint = new Point2d(finalPt.getX()+1, finalPt.getY()+1);
		thisLine1 = new Line2d(newPoint, somethingPoint);
		
		Point2d newNewPoint = new Point2d(initialPt.getX()-1, initialPt.getY()-1);
		Point2d newSomethingPoint = new Point2d(finalPt.getX()-2, finalPt.getY()-2);
		thisLine2 = new Line2d(newNewPoint, newSomethingPoint);
		*/
		shapeSelected = false;
	}
	
	public boolean add(Shape adding) {
		return false;
	}

	public Shape getChild(int num) {
		return null;
	}

	public Shape getParent() {
		return this.parentOfThis;
	}

	public void move(int initialX, int initialY, int newPosX, int newPosY) {
		int deltaX = initialX - newPosX;
		int deltaY = initialY - newPosY;
		
		thisLine.setLine(thisLine.getX1() - deltaX, thisLine.getY1() - deltaY, thisLine.getX2() - deltaX, thisLine.getY2() - deltaY);
	}

	public boolean remove(Shape adding) {
		return false;
	}

	public void resize(int chosenX, int chosenY, int newXPos, int newYPos) {
		thisLine.setLine(chosenX, chosenY, newXPos, newYPos);
	}

	public void addParent(Shape parent) {
		this.parentOfThis = parent;
	}

	public void addColour(Color colorOfShape) {
		this.shapeColour = colorOfShape;		
	}

	public Graphics draw(Graphics g) {
		
		if (shapeSelected == true)
		{
			g.setColor(super.selectColour);
			g.drawLine((int)thisLine.getX1(), (int)thisLine.getY1(), (int)thisLine.getX2(), (int)thisLine.getY2());
			g.fillOval((int)(thisLine.getX1()-2.5), (int) (thisLine.getY1()-2.5), 5, 5);
			g.fillOval((int)(thisLine.getX2()-2.5), (int) (thisLine.getY2()-2.5), 5, 5);
		}
		else if (shapeSelected == false)
		{
			g.setColor(this.shapeColour);
			g.drawLine((int)thisLine.getX1(), (int)thisLine.getY1(), (int)thisLine.getX2(), (int)thisLine.getY2());
		}
		return g;
	}
	
	public boolean isInside (Point2d isInside)
	{
		if (thisLine.ptLineDistSq(isInside)<5)
		{
			System.out.println("Point is inside shape: "+thisLine.toString());
			return true;
		}
		else
		{
			System.out.println("Point is not inside inside shape: "+thisLine.toString());
			return false;
		}
	}
	
	public boolean isSelected()
	{
		return shapeSelected;
	}
	public void select()
	{
		shapeSelected = true;
	}
	public void deSelect()
	{
		shapeSelected = false;
	}
	
	public boolean isVertex(Point2d isVertex)
	{
		if (thisLine.P1.distance(isVertex)<5) return true;
		else if (thisLine.P2.distance(isVertex)<5) return true;
		else return false;
	}
	
	public Point2d getOppositeVertex (Point2d vertex)
	{
		if (thisLine.P1.distance(vertex)<5)
			return thisLine.P2;
		else if (thisLine.P2.distance(vertex)<5)
			return thisLine.P1;
		else return null;
	}

	public Point2d[] biSect() {
		Point2d[] returnable = new Point2d[2];
		returnable[0] = thisLine.P1;
		returnable[1] = thisLine.P2;
		return returnable;
	}

	public Shape clone() {
		model.Line newLine = new model.Line((model.Point2d)this.thisLine.P1.clone(), (model.Point2d)this.thisLine.P2.clone());
		newLine.addColour(this.shapeColour);
		return newLine;
	}
	public boolean equals (Object compareTo)
	{
		if (compareTo != null && compareTo instanceof Line)
		{
			Line compare = (Line) compareTo;
			if ( compare.shapeColour == this.shapeColour && 
				 compare.thisLine.P1 == this.thisLine.P1 && 
				 compare.thisLine.P2 == this.thisLine.P2) return true;
			else return false;
		}
		else return false;
	}
}
