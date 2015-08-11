package controller;
import model.*;

public class NewLineCommand extends PaintCommands {

	/*State Info*/
	private Line lineAddedToCanvas;
	public NewLineCommand(java.awt.Point init, java.awt.Point fin, java.awt.Color colour)
	{
		Point2d initialPt = new Point2d(init.getX(), init.getY());
		Point2d finalPt = new Point2d(fin.getX(), fin.getY());
		lineAddedToCanvas = new Line(initialPt, finalPt);
		lineAddedToCanvas.addColour(colour);
	}
	public void Execute()
	{
		PaintController.addShapesToCanvas(lineAddedToCanvas);
		lineAddedToCanvas = (Line) lineAddedToCanvas.clone();
	}

	public void UnExecute()
	{
		PaintController.removeShapeFromCanvas(lineAddedToCanvas);
	}

}
