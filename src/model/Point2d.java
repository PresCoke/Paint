package model;
import java.awt.geom.*;

public class Point2d extends Point2D{

	double X, Y;
	
	public Point2d(double x, double y)
	{
		X = x;
		Y = y;
	}
	
	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}

	public void setLocation(double x, double y) {
		X = x;
		Y = y;
	}

}
