package controller;

import java.awt.*;
import java.util.*;

import model.Point2d;
import model.Shape;
import model.ShapeComposite;
import view.ViewWithCanvas;

public class PaintController {

	private static LinkedList<Shape> shapesOnCanvas;
	private static ViewWithCanvas application;
	private static Shape selected;
	private static ShapeComposite primitiveGroup;
	private static int numShapesSelected;
	
	public PaintController(ViewWithCanvas reference)
	{
		primitiveGroup = new ShapeComposite();
		shapesOnCanvas = new LinkedList<Shape>();
		application = reference;
		selected = null;
	}
	public static void addShapesToCanvas(Shape adding)
	{
		shapesOnCanvas.add(adding);
		
		Graphics g = application.getGraphics();
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()) - 1; i>=0; i--)
		{
			shapesOnCanvas.get(i).draw(g);
		}
	}
	
	public static void removeShapeFromCanvas(Shape remove)
	{
		shapesOnCanvas.remove(remove);
		
		Graphics g = application.getGraphics();
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()-1); i>=0; i--)
		{
			shapesOnCanvas.get(i).draw(g);
		}
	}
	public static void deSelectAll()
	{
		numShapesSelected = 0;
		Shape temp;
		Graphics g = application.getGraphics();
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()-1); i>=0; i--)
		{
			temp = shapesOnCanvas.get(i);
			temp.deSelect();
			temp.draw(g);
		}
		while(primitiveGroup.getNumElements()>0)
		{
			temp = primitiveGroup.getChild(0);
			primitiveGroup.remove(temp);
		}
	}
	public static boolean clickInsideShape(model.Point2d clickOrigin)
	{
		Graphics g = application.getGraphics();
		Shape temp;
		boolean somethingSelected = false;
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()-1); i>=0; i--)
		{
			temp = shapesOnCanvas.get(i);
			if(temp.isInside(clickOrigin))
			{
				somethingSelected = true;
				if (!temp.isSelected())
				{
					numShapesSelected=1;
					selected = temp;
					selected.select();
				}
				break;
			}
		}
		for (int i=(shapesOnCanvas.size() - 1); i>=0; i--)
		{
			temp = shapesOnCanvas.get(i);
			if (temp != selected)
				temp.deSelect();
			temp.draw(g);
		}
		System.out.println("Number of Shapes Selected is: "+numShapesSelected);
		return somethingSelected;
		
	}
	public static boolean clickIsVertex(model.Point2d clickOrigin)
	{
		Graphics g = application.getGraphics();
		Shape temp;

		boolean wasVertex = false;
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()-1); i>=0; i--)
		{
			temp = shapesOnCanvas.get(i);
			if(temp.isVertex(clickOrigin))
			{
				wasVertex = true;
				if (!temp.isSelected())
				{
					numShapesSelected=1;
					selected = temp;
					selected.select();
				}
				System.out.println(clickOrigin.toString()+" was a vertex in: "+selected.toString());
				break;
			}
		}
		for (int i=(shapesOnCanvas.size() - 1); i>=0; i--)
		{
			temp = shapesOnCanvas.get(i);
			if (temp != selected)
				temp.deSelect();
			temp.draw(g);
		}
		System.out.println("Number of Shapes Selected is: "+numShapesSelected);
		return wasVertex;
	}

	public static void moveSelectedShape(model.Point2d moveTo, model.Point2d moveFrom, Shape chosenShapeToMove)
	{
		Shape temp;
		for (int i =0; i<shapesOnCanvas.size(); i++)
		{
			temp = shapesOnCanvas.get(i);
			if (temp.equals(chosenShapeToMove))
				temp.move((int)moveFrom.getX(), (int)moveFrom.getY(), (int)moveTo.getX(), (int)moveTo.getY());
		}
		Graphics g = application.getGraphics();
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()-1); i>=0; i--)
			shapesOnCanvas.get(i).draw(g);
	}
	
	public static void resizeSelected(Point staticPoint, Point movingPoint, Shape chosenShapeToResize)
	{
		Shape temp;
		for (int i =0; i<shapesOnCanvas.size(); i++)
		{
			temp = shapesOnCanvas.get(i);
			if (temp.equals(chosenShapeToResize))
				temp.resize((int) movingPoint.getX(), (int) movingPoint.getY(), (int) staticPoint.getX(), (int) staticPoint.getY());
		}
		Graphics g = application.getGraphics();
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()-1); i>=0; i--)
			shapesOnCanvas.get(i).draw(g);
	}
	
	public static Point getOtherVertex(model.Point2d p)
	{
		if (selected.isVertex(p))
		{
			try{
			model.Point2d temp = selected.getOppositeVertex(p);
			return (new Point((int) temp.getX(), (int) temp.getY()));
			}catch(NullPointerException npe){
				return null;
			}
		}
		else return null;
	}
	
	public static String getSelectedShapeType()
	{
		String temp = selected.toString();
		if (temp.contains("model.Line"))
			return ("Line");
		else if (temp.contains("model.Rectangle"))
			return ("Rectangle");
		else if (temp.contains("model.Circle"))
			return ("Circle");
		else if (temp.contains("model.ShapeComposite"))
			return ("Composite");
		else return ("Undefined");
	}
	
	public static void setSelectedColour(Color c)
	{
		if (selected!=null)
			selected.addColour(c);
	}
	public static boolean clickIsInsideAddShapeToSelected(model.Point2d clickOrigin)
	{
		Graphics g = application.getGraphics();
		Shape temp;
		boolean somethingSelected = false;
		application.clearCanvas();
		for (int i = (shapesOnCanvas.size()-1); i>=0; i--)
		{
			temp = shapesOnCanvas.get(i);
			if(temp.isInside(clickOrigin))
			{
				numShapesSelected++;
				somethingSelected = true;
				if (!temp.isSelected())
				{
					primitiveGroup.add(temp);
					selected = primitiveGroup;
					selected.select();
				}
				temp.draw(g);
			}
		}
		System.out.println("Number of Shapes Selected is: "+numShapesSelected);
		return somethingSelected;
	}
	public static ShapeComposite getGroup()
	{
		return primitiveGroup;
	}
	public static void commitGroup()
	{
		for (int i=0; i<primitiveGroup.getNumElements(); i++)
		{
			shapesOnCanvas.remove(primitiveGroup.getChild(i));
		}
		shapesOnCanvas.add(primitiveGroup.clone());
		Shape temp;
		while(primitiveGroup.getNumElements()>0)
		{
			temp = primitiveGroup.getChild(0);
			primitiveGroup.remove(temp);
		}
	}
	public static void disassembleGroup(ShapeComposite group)
	{
		while (group.getNumElements()!=0)
		{
			shapesOnCanvas.add(group.getChild(0));
		}
	}
	public static Shape getSelectedShape()
	{
		return selected;
	}
}
