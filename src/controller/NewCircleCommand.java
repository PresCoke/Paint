package controller;

import model.Circle;

public class NewCircleCommand extends PaintCommands {

	private Circle newCirc;
	public NewCircleCommand(java.awt.Point topLeft, java.awt.Point bottomRight, java.awt.Color colour)
	{
		newCirc = new Circle(topLeft, bottomRight);
		newCirc.addColour(colour);
	}
	public void Execute() {
		PaintController.addShapesToCanvas(newCirc);
		newCirc = (Circle) newCirc.clone();
	}

	public void UnExecute() {
		PaintController.removeShapeFromCanvas(newCirc);
	}
}

