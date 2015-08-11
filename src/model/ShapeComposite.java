package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class ShapeComposite extends Shape {

	private LinkedList<Shape> listOShapes;
	
	public ShapeComposite()
	{
		listOShapes = new LinkedList<Shape>();
	}
	
	public boolean add(Shape adding) {
		try {
			adding.addParent(this);
			listOShapes.add(adding);	
		} catch (Exception e){
			System.out.print("Exception in boolean:ShapeComposite.add(Shape) ");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected void addParent(Shape parent) {
		this.parentOfThis = parent;
	}

	public Shape getChild(int num) {
		if (num <= listOShapes.size())
		{
			try{
				Shape wantedShape = listOShapes.get(num);
				return wantedShape;
			}catch (Exception e){
				System.out.print("Exception in Shape:ShapeComposite.getChild(Shape) ");
				e.printStackTrace();
				return null;
			}
		}
		else return null;
	}

	public Shape getParent() {
		return this.parentOfThis;
	}

	public void move(int initialX, int initialY, int newPosX, int newPosY) {
		try{
		for (int i=0; i<listOShapes.size(); i++)
		{
			listOShapes.get(i).move(initialX, initialY, newPosX, newPosY);
		}
		}catch(Exception e){
			System.out.print("Exception in void:ShapeComposite.move(int, int, int, int) ");
			e.printStackTrace();
		}
	}

	public boolean remove(Shape removing) {
		try{
			if (listOShapes.contains(removing) )
			{
				if (listOShapes.remove(removing)) return true;
				else return false;
			}
			else return false;
		}catch (Exception e){
			System.out.print("Exception in boolean:ShapeComposite.remove(Shape) ");
			e.printStackTrace();
			return false;
		}
	}

	public void resize(int chosenX, int chosenY, int x, int y) {
		try{
			for (int i=0; i<listOShapes.size(); i++)
			{
				listOShapes.get(i).resize(chosenX, chosenY, x, y);
			}
			}catch(Exception e){
				System.out.print("Exception in void:ShapeComposite.resize(int, int, int, int) ");
				e.printStackTrace();
			}
	}

	public void addColour(Color colorOfShape) {
		try{
			for (int i=0; i<listOShapes.size(); i++)
			{
				listOShapes.get(i).addColour(colorOfShape);
			}
			}catch(Exception e){
				System.out.print("Exception in void:ShapeComposite.addColour(Color) ");
				e.printStackTrace();
			}
	}

	public Graphics draw(Graphics g) {
		try{
			for (int i=0; i<listOShapes.size(); i++)
			{
				listOShapes.get(i).draw(g);
			}
			}catch(Exception e){
				System.out.print("Exception in void:ShapeComposite.draw(Graphics) ");
				e.printStackTrace();
				return null;
			}
			return g;
	}
	
	public void select()
	{
		try{
			for (int i=0; i<listOShapes.size(); i++)
			{
				listOShapes.get(i).select();
			}
			}catch(Exception e){
				System.out.print("Exception in void:ShapeComposite.draw(Graphics) ");
				e.printStackTrace();
			}
	}
	public void deSelect() {
		try{
			for (int i=0; i<listOShapes.size(); i++)
			{
				listOShapes.get(i).deSelect();
			}
			}catch(Exception e){
				System.out.print("Exception in void:ShapeComposite.draw(Graphics) ");
				e.printStackTrace();
			}
	}
	
	public boolean isSelected()
	{
		//because if one shape is selected in composite... ALL ARE!!
		return listOShapes.getFirst().isSelected();
	}
	
	public boolean isInside(Point2d click) {
		try{
			//boolean oneSelected = false;
			for (int i=0; i<listOShapes.size(); i++)
			{
				if (listOShapes.get(i).isInside(click))
				{
					//oneSelected = true;
					return true;
				}
			}
			return false;
			}catch(Exception e){
				System.out.print("Exception in void:ShapeComposite.draw(Graphics) ");
				e.printStackTrace();
				return false;
			}
	}
	public boolean isVertex(Point2d click)
	{
		return false;
	}
	
	public Point2d getOppositeVertex (Point2d vertex)
	{
		return null;
	}

	public int getNumElements()
	{
		return (this.listOShapes.size());
	}
	
	public Point2d[] biSect() {
		
		return null;
	}

	@Override
	public Shape clone() {
		ShapeComposite newSC = new ShapeComposite();
		for (int i = 0; i<listOShapes.size(); i++)
		{
			newSC.add(listOShapes.get(i).clone());
		}
		return newSC;
	}
	
	public boolean equals (Object compareTo)
	{
		if (compareTo != null && compareTo instanceof ShapeComposite)
		{
			ShapeComposite compare = (ShapeComposite) compareTo;
			if (compare.getNumElements() == this.getNumElements())
			{
				for (int i = 0; i<listOShapes.size(); i++)
				{
					if (!compare.getChild(i).equals(this.getChild(i)))
						return false;
				}
				return true;
			}
			else return false;
		}
		else return false;
	}
}
