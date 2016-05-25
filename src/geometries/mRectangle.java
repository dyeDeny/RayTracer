package geometries;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import javax.imageio.ImageIO;

import utilities.Utility;

public class mRectangle extends Face{
	private Vector3 p0,p1,p2,p3;
	private BufferedImage texture;
	
	public mRectangle(Vector3 p1, Vector3 p2, Vector3 p3, 
			Vector3 p4){
		this.p0 = p1;
		this.p1 = p2;
		this.p2 = p3;
		this.p3 = p4;
	}
	
	public mRectangle(Vector3 p1, Vector3 p2, Vector3 p3, 
			Vector3 p4, String path){
		this.p0 = p1;
		this.p1 = p2;
		this.p2 = p3;
		this.p3 = p4;
		
		try{
		    URL                url; 
		    URLConnection      urlConn; 
//		    DataInputStream    dis;

//		    url = new URL(path);

		    // Note:  a more portable URL: 
		    //url = new URL(getCodeBase().toString() + "/test.jpg");

//		    urlConn = url.openConnection(); 
//		    urlConn.setDoInput(true); 
	//	    urlConn.setUseCaches(false);

//		    dis = new DataInputStream(new FileInputStream(path));
		    File f = new File(path);
		    texture=ImageIO.read(f);
//		    texture=ImageIO.read(url);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	}
	
	public Vector3 getNorm(){
		Vector3 v1 = Vector3.subtract(p0, p1);
		Vector3 v2 = Vector3.subtract(p0, p2);
		return Vector3.cross(v1, v2).unit();
	}
	
	public Vector3 getP0() {
		return p0;
	}


	public void setP0(Vector3 p0) {
		this.p0 = p0;
	}


	public Vector3 getP1() {
		return p1;
	}


	public void setP1(Vector3 p1) {
		this.p1 = p1;
	}


	public Vector3 getP2() {
		return p2;
	}


	public void setP2(Vector3 p2) {
		this.p2 = p2;
	}


	public Vector3 getP3() {
		return p3;
	}


	public void setP3(Vector3 p3) {
		this.p3 = p3;
	}


	public BufferedImage getTexture() {
		return texture;
	}


	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}


	public boolean encloses(Vector3 p){	

		Vector3 v1 = Vector3.subtract(p2, p1);
		Vector3 v2 = Vector3.subtract(p0, p1);
		Vector3 v3 = Vector3.subtract(p, p1);
		double x = Vector3.dot(v1, v3);
		double y = Vector3.dot(v2, v3);
		
		double t1 = (x/v1.norm());
		double t2 = (y/v2.norm());
		
		if(t1>0&&t1<v1.norm()&&t2>0&&t2<v2.norm()){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Color getColor(Vector3 p){
		if(encloses(p)){
			Vector3 v1 = Vector3.subtract(p0, p1);
			Vector3 v2 = Vector3.subtract(p0, p3);
			Vector3 v3 = Vector3.subtract(p0, p);
			double x = Vector3.dot(v1, v3)/v1.norm();
			double y = Vector3.dot(v2, v3)/v2.norm();
		
			double horizontalPercentage = x/v1.norm();
			double verticalPercentage = y/v2.norm();
		
			int u=(int)(horizontalPercentage*texture.getWidth());
			int v=(int)(verticalPercentage*texture.getHeight());
			int rgb=texture.getRGB(u,v);
			java.awt.Color color=new java.awt.Color(rgb,true);
			return color;
		}
		else{
			return null;
		}
	}


	@Override
	public double getIntersect(Ray ray){
		Vector3 origin = ray.getOrigin();
		double m1 = origin.getX();
		double m2 = origin.getY();
		double m3 = origin.getZ();
		double v1 = ray.getDirection().getX();
		double v2 = ray.getDirection().getY();
		double v3 = ray.getDirection().getZ();
		double n1 = this.getP0().getX();
		double n2 = this.getP0().getY();
		double n3 = this.getP0().getZ();
		double vp1 = this.getNorm().getX();
		double vp2 = this.getNorm().getY();
		double vp3 = this.getNorm().getZ();
		double t = (vp1* v1+ vp2* v2+ vp3* v3);
//		System.out.println(m1 + ", " + m2 + ", " + m3 + ", " + v1 + ", " + v2 + ", " + v3);
//		System.out.println(vp1 + ", " + vp2 + ", " + vp3 + ", " + n1 + ", " + n2 + ", " + n3);
		if(Utility.equal(t,0.0)){
//			System.out.println("1");
			return -1;
		}else{
//			System.out.println(t);
			t = ((n1 - m1)*vp1+(n2 - m2)*vp2+(n3 - m3)*vp3) / (vp1* v1+ vp2* v2+ vp3* v3);
//			System.out.println(t);
			if(isInRect(Vector3.add(origin,Vector3.multi(ray.getDirection(),t)))){
//				System.out.println("2");
				return t;
			}else{
//				System.out.println("3");
				return -1;
			}
		}
	}

	private boolean isInRect(Vector3 p) {
		Vector3 t1 = Vector3.cross(Vector3.subtract(p, p0),Vector3.subtract(p1, p0));
		Vector3 t2 = Vector3.cross(Vector3.subtract(p, p3),Vector3.subtract(p2, p3));
		Vector3 t3 = Vector3.cross(Vector3.subtract(p, p0),Vector3.subtract(p3, p0));
		Vector3 t4 = Vector3.cross(Vector3.subtract(p, p1),Vector3.subtract(p2, p1));
		
		return Utility.smaller(Vector3.dot(t1, t2),0) && Utility.smaller(Vector3.dot(t3, t4),0);
	}
	
	@Override
	public Vector3 getIntersectPoint(Ray r) {
		double t = this.getIntersect(r);
		if(Utility.bigger(t, 0)){
			return Vector3.add(r.getOrigin(), Vector3.multi(r.getDirection(), t));
		}
		return null;
	}
	
	@Override
	public Ray refractRay(Ray ray, double t) {
		return null;
	}
	
	public static void main(String args[]){
		Vector3 v0 = new Vector3(-100,-100,0);
		Vector3 v1 = new Vector3(100,-100,0);
		Vector3 v2 = new Vector3(100,100,0);
		Vector3 v3 = new Vector3(-100,100,0);
		mRectangle rec = new mRectangle(v0, v1, v2, v3);
		Vector3 orig = new Vector3(0,0,-100);
		Vector3 direction = new Vector3(0,100,200);
		Ray in = new Ray(orig, direction);
		double t = rec.getIntersect(in);
		System.out.println(t);
		Vector3 ine = rec.getIntersectPoint(in);
		System.out.println(ine);
//		Ray out = tr.refractRay(in, t);
//		System.out.println(out);
	}
}
