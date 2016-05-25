package gui;

import javax.swing.*;

import ray_tracing.RayTracer;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*; 

import geometries.*;

import java.io.*;
import java.awt.event.*;

class TMouseListener extends MouseAdapter{
	public void mouseClicked(MouseEvent event){
		System.out.printf("(%d,%d) clicked\n",
				event.getX(),event.getY());
	}
}
class RendererPanel extends JPanel{
	
	private RayTracer rayTracer;
	
	public RendererPanel(RayTracer rayTracer){
		super();
		this.rayTracer=rayTracer;
		addMouseListener(new TMouseListener());
		setBackground(java.awt.Color.gray);
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d=(Graphics2D)g;
		BufferedImage texture=rayTracer.getScreen();
		g2d.setPaint(new TexturePaint(texture,
				new Rectangle(texture.getWidth(),texture.getHeight())));
		g2d.fill(new Rectangle2D.Double(20,20,
				texture.getWidth(),texture.getHeight()));
	}
}

public class JRayTraceFrame extends JFrame {
	public final static int WIDTH=900;
	public final static int HEIGHT=900;
	//public final static String path="F:\\Joanne\\workspace\\Assignment5\\src\\test.jpg";
	private RayTracer rayTracer=null;
	private RendererPanel renderer;
	public static String path1 = "C:\\Users\\dye\\Desktop\\Ray-Cloak\\r2.png";
	public static String path2 = "C:\\Users\\dye\\Desktop\\Ray-Cloak\\b2.png";
	public static String path3 = "C:\\Users\\dye\\Desktop\\Ray-Cloak\\g2.png";
	
//	private Triangle me = null;
	
	public void initRayTracer(String path1, String path2, String path3){
		
		// setting up the view for the ray tracer
//		Vector3 eye=new Vector3(-120,0,0);
//		Vector3 lookat=new Vector3(-120 ,0,-100);
//		Vector3 up=new Vector3(0,1,0);
		int ab = 4;

		boolean cloak = true;
		boolean obj = true;
		
		double d = -6*ab;
		int t = 710;
//		Vector3 eye=new Vector3(0, 0, t);
//		Vector3 eye=new Vector3(-6*ab + t -1, 6*ab - t, d + t);
		Vector3 eye=new Vector3(0, 6*ab - t , 0 );
//		Vector3 eye=new Vector3(  t-1, 0 ,0 );
		
		Vector3 lookat=new Vector3( 0 , 0, 0);
		Vector3 up=new Vector3(0,1,1);
		rayTracer=new RayTracer(WIDTH,HEIGHT,eye,lookat,up);
		
		// setting up the objects
//		Vector3 p0=new Vector3(-50,40,-40);
//		Vector3 p1=new Vector3(-50,-40,-40);
//		Vector3 p2=new Vector3(50,-40,-40);
//		Vector3 p3=new Vector3(50,40,-40);
		Vector3 p0=new Vector3(-6*ab,-6*ab,d);
		Vector3 p1=new Vector3(6*ab,-6*ab,d);
		Vector3 p2=new Vector3(6*ab,6*ab,d);
		Vector3 p3=new Vector3(-6*ab,6*ab,d);
		
		Vector3 p4=new Vector3(-6*ab,-6*ab,d + 6*ab*2);
		Vector3 p5=new Vector3(-6*ab,6*ab,d + 6*ab*2);
		Vector3 p6=new Vector3(6*ab,6*ab, d + 6*ab*2);

		mRectangle rect1=
			new mRectangle(p0,p1,p2,p3,path1);
		rect1.setSurfaceType(Face.DIFFUSE);
		
		mRectangle rect2=
				new mRectangle(p4, p0,p3,p5,path2);
		rect2.setSurfaceType(Face.DIFFUSE);
		
		mRectangle rect3=
				new mRectangle(p3,p2,p6,p5,path3);
		rect3.setSurfaceType(Face.DIFFUSE);
		
		rayTracer.addFace(rect1);
		rayTracer.addFace(rect2);
		rayTracer.addFace(rect3);
		
		
//		double tx,ty,tz;
//		double dx,dy,dz;
//		tx = ty = -10;
//		tz = 5;
//		dx = dy = dz = 20;
//		double n = 3;
//		Vector3 v0 = new Vector3(tx,ty,tz);
//		Vector3 v1 = new Vector3(tx+dx,ty,tz);
//		Vector3 v2 = new Vector3(tx+dx,ty+dy,tz);
//		Vector3 v3 = new Vector3(tx,ty+dy,tz);
//		Vector3 v4 = new Vector3(tx,ty,tz+dz);
//		Vector3 v5 = new Vector3(tx+dx,ty,tz+dz);
//		Vector3 v6 = new Vector3(tx+dx,ty+dy,tz+dz);
//		Vector3 v7 = new Vector3(tx,ty+dy,tz+dz);
//
//		Triangle tr0 = new Triangle(v0,v1,v2,1,n);
//		Triangle tr1 = new Triangle(v0,v2,v3,1,n);
//		Triangle tr2 = new Triangle(v0,v5,v1,1,n);
//		Triangle tr3 = new Triangle(v0,v4,v5,1,n);
//		Triangle tr4 = new Triangle(v0,v3,v7,1,n);
//		Triangle tr5 = new Triangle(v0,v7,v4,1,n);
//		Triangle tr6 = new Triangle(v6,v2,v1,1,n);
//		Triangle tr7 = new Triangle(v6,v1,v5,1,n);
//		Triangle tr8 = new Triangle(v6,v7,v3,1,n);
//		Triangle tr9 = new Triangle(v6,v3,v2,1,n);
//		Triangle tr10 = new Triangle(v6,v4,v7,1,n);
//		Triangle tr11 = new Triangle(v6,v5,v4,1,n);
		
//		rayTracer.addFace(tr0);
//		rayTracer.addFace(tr1);
//		rayTracer.addFace(tr2);
//		rayTracer.addFace(tr3);
//		rayTracer.addFace(tr4);
//		rayTracer.addFace(tr5);
//		rayTracer.addFace(tr6);
//		rayTracer.addFace(tr7);
//		rayTracer.addFace(tr8);
//		rayTracer.addFace(tr9);
//		rayTracer.addFace(tr10);
//		rayTracer.addFace(tr11);


//		rayTracer.setLightSource(new Vector3(0,4,4));
		double scale = 10;
		double a = 0.276 * scale;
//     hiding object		
		Vector3 v0 = new Vector3(-a,  -a, a);
		Vector3 v1 = new Vector3(a,  -a, a);
		Vector3 v2 = new Vector3(a,  a, a);
		Vector3 v3 = new Vector3(-a,  a, a);
		Vector3 v4 = new Vector3(-a,  -a, -a);
		Vector3 v5 = new Vector3(a,  -a, -a);
		Vector3 v6 = new Vector3(a,  a, -a);
		Vector3 v7 = new Vector3(-a,  a, -a);

		Vector3 v8 = new Vector3(0,  0, scale);
		Vector3 v9 = new Vector3(-scale,  0, 0);
		Vector3 v10 = new Vector3(0,  0, -scale);
		Vector3 v11 = new Vector3(scale,  0, 0);
		Vector3 v12 = new Vector3(0,  -scale, 0);
		Vector3 v13 = new Vector3(0, scale,  0);
		
		Triangle tr0 = new Triangle(v0,v1,v8);
		Triangle tr1 = new Triangle(v2,v1,v8);
		Triangle tr2 = new Triangle(v2,v3,v8);
		Triangle tr3 = new Triangle(v3,v1,v8);
		Triangle tr4 = new Triangle(v0,v3,v9);
		Triangle tr5 = new Triangle(v3,v7,v9);
		Triangle tr6 = new Triangle(v7,v4,v9);
		Triangle tr7 = new Triangle(v4,v0,v9);
		Triangle tr8 = new Triangle(v4,v5,v10);
		Triangle tr9 = new Triangle(v5,v6,v10);
		Triangle tr10 = new Triangle(v6,v7,v10);
		Triangle tr11 = new Triangle(v7,v4,v10);
		Triangle tr12 = new Triangle(v1,v2,v11);
		Triangle tr13 = new Triangle(v2,v6,v11);
		Triangle tr14 = new Triangle(v6,v5,v11);
		Triangle tr15 = new Triangle(v5,v1,v11);
		Triangle tr16 = new Triangle(v0,v1,v12);
		Triangle tr17 = new Triangle(v1,v5,v12);
		Triangle tr18 = new Triangle(v5,v4,v12);
		Triangle tr19 = new Triangle(v4,v0,v12);
		Triangle tr20 = new Triangle(v2,v3,v13);
		Triangle tr21 = new Triangle(v3,v7,v13);
		Triangle tr22 = new Triangle(v7,v6,v13);
		Triangle tr23 = new Triangle(v6,v2,v13);


	
	if(obj){
		rayTracer.addFace(tr0);
		rayTracer.addFace(tr1);
		rayTracer.addFace(tr2);
		rayTracer.addFace(tr3);
		rayTracer.addFace(tr4);
		rayTracer.addFace(tr5);
		rayTracer.addFace(tr6);
		rayTracer.addFace(tr7);
		rayTracer.addFace(tr8);
		rayTracer.addFace(tr9);
		rayTracer.addFace(tr10);
		rayTracer.addFace(tr11);
		rayTracer.addFace(tr12);
		rayTracer.addFace(tr13);
		rayTracer.addFace(tr14);
		rayTracer.addFace(tr15);
		rayTracer.addFace(tr16);
		rayTracer.addFace(tr17);
		rayTracer.addFace(tr18);
		rayTracer.addFace(tr19);
		rayTracer.addFace(tr20);
		rayTracer.addFace(tr21);
		rayTracer.addFace(tr22);
		rayTracer.addFace(tr23);
	}

if(cloak){
//   galss1
		double n1 = 1.900;
		double n2 = 1.333; // water;
		double n3 = 1.621; // glass2
		
//		double n1 = 1.0, n2  = 1.0, n3 = 1.0;

	
		//point 0, 1
		Triangle gl0 = new Triangle(v0,v8,v12,n2,n1);
		Triangle gl1 = new Triangle(v8,v1,v12,n2,n1);
		
		
		//point 0,3
		Triangle gl2 = new Triangle(v0,v9,v8,n2,n1);
		Triangle gl3 = new Triangle(v9,v3,v8,n2,n1);
		
		
		
		//point 2,3
		Triangle gl4 = new Triangle(v3,v13,v8,n2,n1);
		Triangle gl5 = new Triangle(v13,v2,v8,n2,n1);
		
		
	
		//point 1,2
		Triangle gl6 = new Triangle(v2,v11,v8,n2,n1);
		Triangle gl7 = new Triangle(v11,v1,v8,n2,n1);
		
		
	
		//point 3,7
		Triangle gl8 = new Triangle(v3,v9,v13,n2,n1);
		Triangle gl9 = new Triangle(v9,v7,v13,n2,n1);
		
		
	
		//point 0,4
		Triangle gl10 = new Triangle(v0,v12,v9,n2,n1);
		Triangle gl11 = new Triangle(v12,v4,v9,n2,n1);
		
		
	
		//point 1,5
		Triangle gl12 = new Triangle(v1,v11,v12,n2,n1);
		Triangle gl13 = new Triangle(v11,v5,v12,n2,n1);
		
		
	
		//point 2,6
		Triangle gl14 = new Triangle(v2,v13,v11,n2,n1);
		Triangle gl15 = new Triangle(v13,v6,v11,n2,n1);
		
		
	
		//point 4,5
		Triangle gl16 = new Triangle(v5,v10,v12,n2,n1);
		Triangle gl17 = new Triangle(v10,v4,v12,n2,n1);
		
		
	
		//point 5,6
		Triangle gl18 = new Triangle(v5,v11,v10,n2,n1);
		Triangle gl19 = new Triangle(v11,v6,v10,n2,n1);
		
		
	
		//point 6,7
		Triangle gl20 = new Triangle(v6,v13,v10,n2,n1);
		Triangle gl21 = new Triangle(v13,v7,v10,n2,n1);
		
		
	
		//point 7,4
		Triangle gl22 = new Triangle(v7,v9,v10,n2,n1);
		Triangle gl23 = new Triangle(v9,v4,v10,n2,n1);
		
		rayTracer.addFace(gl0);
		rayTracer.addFace(gl1);
		
		rayTracer.addFace(gl2);
		rayTracer.addFace(gl3);
		
		rayTracer.addFace(gl4);
		rayTracer.addFace(gl5);
		
		rayTracer.addFace(gl6);
		rayTracer.addFace(gl7);
		
		rayTracer.addFace(gl8);
		rayTracer.addFace(gl9);
		
		rayTracer.addFace(gl10);
		rayTracer.addFace(gl11);
		
		rayTracer.addFace(gl12);
		rayTracer.addFace(gl13);
		
		rayTracer.addFace(gl14);
		rayTracer.addFace(gl15);
		
		rayTracer.addFace(gl16);
		rayTracer.addFace(gl17);
		
		rayTracer.addFace(gl18);
		rayTracer.addFace(gl19);
		
		rayTracer.addFace(gl20);
		rayTracer.addFace(gl21);
		
		rayTracer.addFace(gl22);
		rayTracer.addFace(gl23);

		
//    water face
//		double n2 = 1.33;
		Triangle gf0 = new Triangle(v8,v11,v12,n3,n2);
		Triangle gf1 = new Triangle(v8,v13,v11,n3,n2);
		Triangle gf2 = new Triangle(v8,v9,v13,n3,n2);
		Triangle gf3 = new Triangle(v8,v12,v9,n3,n2);
		Triangle gf4 = new Triangle(v10,v11,v13,n3,n2);
		Triangle gf5 = new Triangle(v10,v13,v9,n3,n2);
		Triangle gf6 = new Triangle(v10,v9,v12,n3,n2);
		Triangle gf7 = new Triangle(v10,v12,v11,n3,n2);
		
		rayTracer.addFace(gf0);
		rayTracer.addFace(gf1);
		rayTracer.addFace(gf2);
		rayTracer.addFace(gf3);
		rayTracer.addFace(gf4);
		rayTracer.addFace(gf5);
		rayTracer.addFace(gf6);
		rayTracer.addFace(gf7);
		
// outGlass
		double nb = n2;  //background
		
		Vector3 vs0 = new Vector3(-scale, -scale, -scale);
		Vector3 vs1 = new Vector3(-scale, scale, -scale);
		Vector3 vs2 = new Vector3(scale, scale, -scale);
		Vector3 vs3 = new Vector3(scale, -scale, -scale);
		Vector3 vs4 = new Vector3(-scale, -scale, scale);
		Vector3 vs5 = new Vector3(-scale, scale, scale);
		Vector3 vs6 = new Vector3(scale, scale, scale);
		Vector3 vs7 = new Vector3(scale, -scale, scale);
	// Face 0,1,2,3	
		Triangle tf0 = new Triangle(vs0, vs1, vs2,nb,n3);
		Triangle tf1 = new Triangle(vs0, vs2, vs3,nb,n3);
	// face 0,4,5,1
		Triangle tf2 = new Triangle(vs0, vs4, vs5,nb,n3);
		Triangle tf3 = new Triangle(vs0, vs5, vs1,nb,n3);
	// face 0,3,7,4
		Triangle tf4 = new Triangle(vs0, vs3, vs7,nb,n3);
		Triangle tf5 = new Triangle(vs0, vs7, vs4,nb,n3);
	// face 6,5,4,7
		Triangle tf6 = new Triangle(vs6, vs5, vs4, nb, n3);
		Triangle tf7 = new Triangle(vs6, vs4, vs7, nb, n3);
	// face 6,2,1,5
		Triangle tf8 = new Triangle(vs6, vs2, vs1, nb,n3);
		Triangle tf9 = new Triangle(vs6, vs1, vs5, nb,n3);
	// face 6,7,3,2
		Triangle tf10 = new Triangle(vs6, vs7, vs3,nb,n3);
		Triangle tf11 = new Triangle(vs6, vs3, vs2,nb,n3);
	
		rayTracer.addFace(tf0);
		rayTracer.addFace(tf1);
		rayTracer.addFace(tf2);
		rayTracer.addFace(tf3);
		rayTracer.addFace(tf4);
		rayTracer.addFace(tf5);
		rayTracer.addFace(tf6);
		rayTracer.addFace(tf7);
		rayTracer.addFace(tf8);
		rayTracer.addFace(tf9);
		rayTracer.addFace(tf10);
		rayTracer.addFace(tf11);
	}		
		
		
		rayTracer.update();
	}
	
	public void initComponent(){
		renderer=new RendererPanel(rayTracer);
		add(renderer,BorderLayout.CENTER);
		
		JPanel control=new JPanel();
		control.setLayout(new FlowLayout());
//		ButtonGroup radioGroup=new ButtonGroup();
//		JRadioButton reflection=new JRadioButton("Reflection", true);
//		JRadioButton refraction=new JRadioButton("Refraction",false);
//		radioGroup.add(reflection);
//		radioGroup.add(refraction);
		
//		control.add(reflection);
//		control.add(refraction);
		
//		reflection.addItemListener(new RadioButtonHandler(reflectiveSphere));
//		refraction.addItemListener(new RadioButtonHandler(refractiveSphere));
		
//		add(control,BorderLayout.SOUTH);
	}
	
	public JRayTraceFrame(String title){
		super(title);
		initRayTracer(path1, path2, path3);
		initComponent();
	}
	

	
	public static void main(String args[]){
		//path = args[0];

		JRayTraceFrame frame=new JRayTraceFrame("Ray Tracing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH+50, HEIGHT+70);
		frame.setVisible(true);
	}
}

