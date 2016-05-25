package ray_tracing;

import geometries.Face;
import geometries.Ray;
import geometries.Vector3;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;




import utilities.Utility;

public class RayTracer {
	private BufferedImage screen=null;
	// the objects in the scene
	private ArrayList<Face> faces=new ArrayList<Face>();
	// this application only consider single light source
//	private Vector3 lightSource;
	// eye and world coordinate
	private CoordinateSystem eyeCoordinateSystem = null;
	// the screen
	private Vector3 pixels[][]=null;
	
	
	
	public RayTracer(int width, int height, Vector3 eye, 
			Vector3 lookat, Vector3 up){
		setUpView(width,height,eye,lookat,up);
	}
	
	public void addFace(Face f){
		faces.add(f);
	}
	
	
	private void setBackground(int rgb){
		for(int i=0;i<screen.getWidth();i++)
			for(int j=0;j<screen.getHeight();j++){
				screen.setRGB(i,j,rgb);
			}
	}
	
	/*
	 * This method updates the screen. It calls rayTrace().
	 */
	public void update(){
		for(int i=0;i<screen.getWidth();i++)
			for(int j=0;j<screen.getHeight();j++){
				
				/* insert your code here */
					// create the ray that begins at the eye and
					// points to pixel (i,j)
				Vector3 eye = eyeCoordinateSystem.getEye();
				Vector3 x = pixels[i][j];
//				Ray r = new Ray (eye, Vector3.subtract(x,eye)); 
				Ray r = new Ray (x, new Vector3(0,1,0)); 
//				Ray r = new Ray (eye, x); 
				Color color= RayTrace(r);
				screen.setRGB(i,j,color.getRGB());
			}
	}
	public BufferedImage getScreen() {
		return screen;
	}


	private Color RayTrace(Ray r){
		Face f = nearestFace(r);
		if(f == null) return new Color(0,0,0);
		else {
			if(f.getSurfaceType() == Face.DIFFUSE){
				Vector3 p = f.getIntersectPoint(r);
				return f.getColor(p);
			}else{
				double t = f.getIntersect(r);
				Ray out = f.refractRay(r, t);
				if(out == null) return new Color(0,0,0);
				else return RayTrace(out);
			}
		}
	}
	
	public Face nearestFace(Ray r){
		double minT = 1e+7;
		Face minFace = null;
		Iterator<Face> it = faces.iterator();
		while(it.hasNext()){
			Face f = it.next();
			double t = f.getIntersect(r);
			if(Utility.bigger(t, 0) && Utility.bigger(minT, t)){
				minT = t;
				minFace = f;
			}
		}
		return minFace;
	}


	private void setUpView(int width, int height, Vector3 eye, Vector3 lookat,
			Vector3 up) {
		// width and height are the dimensions of the  screen
		// width # of columns height is # of rows
		if(screen==null || screen.getHeight()!=height || screen.getWidth()!=width){
			// this change only happens when the 
			// image size is changed
			screen=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			pixels=new Vector3[width][height];
		}
		eyeCoordinateSystem=new CoordinateSystem(eye, lookat, up);
		
		// initialize the pixel positions in the eye reference basis,
		// the screen is (-2,-2) by (2,2)
		// then convert the pixel's coordinates in the eye reference basis to 
		// their world coordinates
		int sz = 4+1;
		double top = 6 * sz, bottom = -6 * sz, right = 6 * sz, left = -6 * sz;
//		double top = 70, bottom = -70, right = 120, left = -120;
		double viewPlane = -120; // from the eye point
		     // the center of the screen 
		     // is at the opposite of w axis 
		
		/* insert your code here */
		double pixelSizeX = (right - left)/width;
		double pixelSizeY = (top - bottom)/height;
			
		for (int i = 0; i<width; i++){
			for (int j = 0; j<height; j++){
//				Vector3 temp = new Vector3((top - j*pixelSizeY),(left + i*pixelSizeX), viewPlane);
				Vector3 temp = new Vector3((left + i*pixelSizeX),(bottom + j*pixelSizeY), viewPlane);
				temp = eyeCoordinateSystem.convertToWorld(temp);
				pixels[i][j] = temp;
			}
		}
	}
}
