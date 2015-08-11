package controller;

public class ResizeShapeCommand extends PaintCommands {

	private java.awt.Point originalMovePos, staticPoint, movedPoint;
	private model.Shape selectedShape;
	public ResizeShapeCommand(java.awt.Point original, java.awt.Point staticP, java.awt.Point chosen)
	{
		originalMovePos = original;
		staticPoint = staticP;
		movedPoint = chosen;
		selectedShape = (model.Shape) PaintController.getSelectedShape().clone();
	}
	public void Execute()
	{
		PaintController.resizeSelected(staticPoint, movedPoint, selectedShape);
	}

	public void UnExecute()
	{
		PaintController.resizeSelected(staticPoint, originalMovePos, selectedShape);
	}

}
