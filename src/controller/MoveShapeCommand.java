package controller;

public class MoveShapeCommand extends PaintCommands {

	java.awt.Point original, next;
	model.Shape selectedShape;
	
	public MoveShapeCommand(java.awt.Point initialPoint, java.awt.Point finalPoint)
	{
		original = initialPoint;
		next = finalPoint;
		selectedShape = (model.Shape) PaintController.getSelectedShape().clone();
	}
	public void Execute()
	{
		PaintController.moveSelectedShape(new model.Point2d(next.x, next.y), new model.Point2d(original.x, original.y), selectedShape);
	}

	@Override
	public void UnExecute() {
		PaintController.moveSelectedShape(new model.Point2d(original.x, original.y), new model.Point2d(next.x, next.y), selectedShape);
	}

}
