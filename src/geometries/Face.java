package geometries;

import java.awt.Color;

public abstract class Face {
	public final static int DIFFUSE=1;
	public final static int SPECULAR=2;
	public final static int TRANSPARENT=4;
	
	private int surfaceType; // DIFFUSE, SPECULAR, TRANSPARENT
	
	

	public void setSurfaceType(int surfaceType) {
		this.surfaceType = surfaceType;
	}

	public int getSurfaceType() {
		return surfaceType;
	}
	
	public abstract double getIntersect(Ray r);
	public abstract Color getColor(Vector3 v);
	public abstract Vector3  getIntersectPoint(Ray r);
	public abstract Ray refractRay(Ray ray, double t);
}
