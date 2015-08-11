package test;
import java.awt.Graphics;

import model.*;
import view.*;
import controller.*;

public class TestWith {

	private static PaintController applicationController;
	private static ViewWithCanvas applicationWindow;
	
	public static void main (String args[])
	{
		applicationWindow = new ViewWithCanvas();
		applicationWindow.initializeComponents();
		applicationWindow.showWindow();
		
		applicationController = new PaintController(applicationWindow);
	}
}
