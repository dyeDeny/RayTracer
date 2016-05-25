package geometries;
import java.awt.Color;

import utilities.Utility;

public class Triangle extends Face{
	private Vector3 p0,p1,p2;
	private double inIndex;
	private double outIndex;
	
	
	//Please add the points in counter clockwise order to define the outer and inner face;
	public Triangle(Vector3 v0, Vector3 v1, Vector3 v2, double n1, double n2){
		this.p0 = v0;
		this.p1 = v1;
		this.p2 = v2;
		this.inIndex = n1;
		this.outIndex = n2;
		this.setSurfaceType(Face.TRANSPARENT);
	}
	
	public Triangle(Vector3 v0, Vector3 v1, Vector3 v2) {
		this.p0 = v0;
		this.p1 = v1;
		this.p2 = v2;
		this.setSurfaceType(Face.DIFFUSE);
	}

	public Vector3 getNorm(){
		Vector3 v = Vector3.cross(Vector3.subtract(p1, p0), Vector3.subtract(p2, p1));
		return v.unit();
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

	public void setInIndex(double inIndex) {
		this.inIndex = inIndex;
	}

	public void setOutIndex(double outIndex) {
		this.outIndex = outIndex;
	}

	public double getInIndex(){
		return inIndex;
	}
	public double getOutIndex(){
		return outIndex;
	}
	
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
			if(isInTriangel(Vector3.add(origin,Vector3.multi(ray.getDirection(),t)))){
//				System.out.println("2");
				return t;
			}else{
//				System.out.println("3");
				return -1;
			}
		}
	}
	
	private boolean isInTriangel(Vector3 P) {
		Vector3 v0 = Vector3.subtract(p2, p0);
		Vector3 v1 = Vector3.subtract(p1, p0);
		Vector3 v2 = Vector3.subtract(P , p0);
		
		double dot00 = Vector3.dot(v0, v0);
		double dot01 = Vector3.dot(v0, v1);
		double dot02 = Vector3.dot(v0, v2);
		double dot11 = Vector3.dot(v1, v1);
		double dot12 = Vector3.dot(v1, v2);
		
		double inverDeno = 1 / (dot00 * dot11 - dot01 * dot01);
	    double u = (dot11 * dot02 - dot01 * dot12) * inverDeno;
	    
	    if (Utility.smaller(u,0) || Utility.bigger(u,1)) // if u out of range, return directly
	    {
	        return false ;
	    }

	    double v = (dot00 * dot12 - dot01 * dot02) * inverDeno ;
	    if (Utility.smaller(v,0) || Utility.bigger(v,1)) // if v out of range, return directly
	    {
	        return false ;
	    }

	    return Utility.smaller(u+v,1) || Utility.equal(v+u,1);
	}

	//���������� direction dot norm < 0;
	//If no refract ray, return null;
	public Ray refractRay(Ray ray, double t){
		if(Utility.smaller(t, 0)) return null;
		Vector3 origin = ray.getOrigin();
		Vector3 direction = ray.getDirection();
		Vector3 newOrig = Vector3.add(origin, Vector3.multi(direction, t));
		Vector3 outDirection;
		Vector3 norm = this.getNorm();
		if(Utility.smaller(Vector3.dot(direction, norm),0)){
			// From outer to inner
			direction = direction.unit();
			double cos_1 = -1 * Vector3.dot(direction,norm);
			double sin_1 = Math.sqrt(1-Math.pow(cos_1, 2));
			double sin_2 = sin_1 * outIndex / inIndex;
			double cos_2 = Math.sqrt(1-Math.pow(sin_2, 2));
//			System.out.println(cos_1 + ", " + sin_1 + ", " + cos_2 + ", " + sin_2);
			if(Utility.bigger(sin_2,1)) return null;
			Vector3 hor1 = Vector3.subtract(direction, Vector3.multi(norm, -1*cos_1));
			Vector3 hor2 = Vector3.multi(hor1, outIndex / inIndex);
			Vector3 ver2 = Vector3.multi(norm, -1*cos_2);
			outDirection = Vector3.add(hor2,ver2);
			
		}else if(Utility.bigger(Vector3.dot(direction, this.getNorm()),0)){
			//From inner to outer
			direction = direction.unit();
			double cos_1 = Vector3.dot(direction,norm);
			double sin_1 = Math.sqrt(1-Math.pow(cos_1, 2));
			double sin_2 = sin_1 * inIndex / outIndex;
			double cos_2 = Math.sqrt(1-Math.pow(sin_2, 2));
//			System.out.println("cos_1: " + cos_1 + ", sin_1: " + sin_1 + ", cos_2: " + cos_2 + ", sin_2: " + sin_2);
			if(Utility.bigger(sin_2,1)) return null;
//			System.out.println("dire:" + direction + ", norm:" + norm);
			Vector3 hor1 = Vector3.subtract(direction, Vector3.multi(norm, cos_1));
			Vector3 hor2 = Vector3.multi(hor1, inIndex / outIndex);
			Vector3 ver2 = Vector3.multi(norm, cos_2);
//			System.out.println(hor1 + ", " + hor2 + ", " + ver2);
			outDirection = Vector3.add(hor2,ver2);
		}else return null;
		
		Ray outRay = new Ray(newOrig, outDirection);
		return outRay;
	}


	@Override
	public Color getColor(Vector3 v) {
		return Color.black;
	}

	@Override
	public Vector3 getIntersectPoint(Ray r) {
		double t = this.getIntersect(r);
		if(Utility.bigger(t, 0)){
			return Vector3.add(r.getOrigin(), Vector3.multi(r.getDirection(), t));
		}
		return null;
	}
	
	
	public static void main(String args[]){
		Vector3 v0 = new Vector3(-100,-100,0);
		Vector3 v1 = new Vector3(100,-100,0);
		Vector3 v2 = new Vector3(0,200,0);
		Triangle tr = new Triangle(v0, v1, v2, 0.9, 1);
		Vector3 orig = new Vector3(0,0,-2);
		Vector3 direction = new Vector3(0,1,1);
		Ray in = new Ray(orig, direction);
		double t = tr.getIntersect(in);
		System.out.println(t);
		Vector3 inte = tr.getIntersectPoint(in);
		System.out.println(inte);
		Ray out = tr.refractRay(in, t);
		System.out.println(out);
	}
}
