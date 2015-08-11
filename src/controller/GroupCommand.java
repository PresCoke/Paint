package controller;

import model.ShapeComposite;

public class GroupCommand extends PaintCommands {

	model.ShapeComposite committedGroup;
	public GroupCommand(model.ShapeComposite group)
	{
		committedGroup = (ShapeComposite) group.clone();
	}
	public void Execute() {
		PaintController.commitGroup();
	}
	
	public void UnExecute() {
		PaintController.disassembleGroup(committedGroup);
	}

}
