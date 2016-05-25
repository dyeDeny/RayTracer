package geometries;

public class Ray extends Vector3{
	private Vector3 origin;
	private Vector3 direction;
	public Ray(Vector3 v1, Vector3 v2){
		this.origin = v1;
		this.direction = v2;
	}
	public Vector3 getOrigin() {
		return origin;
	}
	public void setOrigin(Vector3 origin) {
		this.origin = origin;
	}
	public Vector3 getDirection() {
		return direction;
	}
	public void setDirection(Vector3 direction) {
		this.direction = direction;
	}
	public String toString(){
		return "@"+ origin + "->" + direction;
	}
		
	
	
}
