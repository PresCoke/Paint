package controller;
import model.*;

public class NewRectangleCommand extends PaintCommands {

	private Rectangle newRect;
	public NewRectangleCommand(java.awt.Point topLeft, java.awt.Point bottomRight, java.awt.Color colour)
	{
		newRect = new Rectangle(topLeft, bottomRight);
		newRect.addColour(colour);
	}
	public void Execute() {
		PaintController.addShapesToCanvas(newRect);
		newRect = (Rectangle) newRect.clone();
	}

	public void UnExecute() {
		PaintController.removeShapeFromCanvas(newRect);
	}
}
