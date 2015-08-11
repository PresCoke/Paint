package model;
import java.awt.Color;
public abstract class Shape {
	
	protected final Color selectColour = Color.RED;
	
	protected Shape parentOfThis;
	/*Shape Components*/
	public abstract void move (int initialX, int initialY, int newPosX, int newPosY);
	public abstract void resize (int chosenX, int chosenY, int x, int y);
	public abstract void addColour (java.awt.Color colorOfShape);
	public abstract java.awt.Graphics draw (java.awt.Graphics g);
	public abstract boolean isInside (model.Point2d click);
	public abstract boolean isVertex (model.Point2d click);
	public abstract model.Point2d getOppositeVertex(model.Point2d vertex);
	public abstract model.Point2d[] biSect();
	public abstract boolean isSelected();
	public abstract void select();
	public abstract void deSelect();
	public abstract Shape clone();
	public abstract boolean equals(Object compareTo);
	
	/*Composite Methods*/
	public abstract boolean add(Shape adding);
	public abstract boolean remove(Shape removing);
	public abstract Shape getParent();
	protected abstract void addParent(Shape parent);
	public abstract Shape getChild(int num);
}
