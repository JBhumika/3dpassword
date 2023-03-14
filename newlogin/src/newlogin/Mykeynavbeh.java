package newlogin;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.applet.*;
import java.awt.*;
import java.sql.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.util.*;
//Connection conn=null;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.behaviors.keyboard.*;

import com.sun.j3d.loaders.Scene;

import com.sun.j3d.loaders.objectfile.ObjectFile;

import java.io.*;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;// add
import com.sun.j3d.utils.behaviors.mouse.*;// add 
import com.sun.j3d.utils.geometry.*;// add
import com.sun.j3d.utils.picking.*;// add
import com.sun.j3d.utils.picking.behaviors.*;// add 
import com.sun.j3d.utils.behaviors.interpolators.*;// add
import com.sun.j3d.utils.geometry.Text2D;
import com.sun.j3d.utils.geometry.Box;    // addextra
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Mykeynavbeh extends Applet implements MouseListener,KeyListener {
public int i=0;
 private SimpleUniverse universe = null;
 private Canvas3D canvas = null;
 private TransformGroup viewtrans = null;
 private Transform3D t3d_vt = new Transform3D();
 public String img[]=new String[13];
public int counter=0;
 // private TransformGroup tg = null;
 // private Transform3D t3d = null;
 private Transform3D t3dstep = new Transform3D();
 private Matrix4d matrix = new Matrix4d();
 // add 
 int getn()
 {
     return user;
 }
 private PickCanvas cnvs = null; 
 private String sensorID = null;
 private int sensorID_2 = 0;  //extra
private Vector3d trans = new Vector3d(); // (1) add zoomm

 private Text2D text2d_message;
private Text2D text2d_message1;  // added to comp
private static int user;

 public Mykeynavbeh(int n1) {
  setLayout(new BorderLayout());
  GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

  canvas = new Canvas3D(config);
  add("Center", canvas);
  universe = new SimpleUniverse(canvas);

  BranchGroup scene = createSceneGraph();
  universe.getViewingPlatform().setNominalViewingTransform();

  universe.getViewer().getView().setBackClipDistance(100.0);

  canvas.addKeyListener(this);
  canvas.addMouseListener(this);// add

  universe.addBranchGraph(scene);
  this.user=n1;
   Connection conn=null;
   
           
     
 
 }

 public BranchGroup createSceneGraph() {
  BranchGroup objRoot = new BranchGroup();

  BoundingSphere bounds = new BoundingSphere(new Point3d(), 10000.0);

  viewtrans = universe.getViewingPlatform().getViewPlatformTransform();

  KeyNavigatorBehavior keyNavBeh = new KeyNavigatorBehavior(viewtrans);
  keyNavBeh.setSchedulingBounds(bounds);
  PlatformGeometry platformGeom = new PlatformGeometry();
 platformGeom.addChild(keyNavBeh);
  universe.getViewingPlatform().setPlatformGeometry(platformGeom);

  Background background = new Background();
  background.setColor(0.0f, 0.0f, 0.0f);
  background.setApplicationBounds(bounds);
  objRoot.addChild(background);
  objRoot.addChild(createMuseum());
  //objRoot.addChild(createPicture(0.0, -0.17, -9.97, 0.0f, "paaword"));
  objRoot.addChild(createPicture(0.0, -0.17,-44.97, 0.0f, "model/Scream.obj"));
  //System.out.println("Enter the passward");
  objRoot.addChild(createPicture(-5.2, -0.17, -44.95, 0.0f,"model/Mona_Lisa.obj"));
  objRoot.addChild(createPicture(5.2, -0.15, -44.95, 0.0f, "model/Girl_Pearl_Earring.obj"));
  objRoot.addChild(createPicture(9.97, -0.14, -40.5, -1.57f, "model/CS.obj"));
  objRoot.addChild(createPicture(9.97, -0.14, -35.0, -1.57f, "model/hanuman.obj"));
  objRoot.addChild(createPicture(9.97, -0.17, -30.0, -1.57f, "model/Rideau.obj"));
  objRoot.addChild(createPicture(4.7, -0.17, -25.06, -3.14f, "model/Fifer.obj"));
  objRoot.addChild(createPicture(0.0, -0.17, -25.05, -3.14f, "model/Sunflowers.obj"));
  objRoot.addChild(createPicture(-5.0, -0.07, -25.05, -3.14f, "model/Dimanche.obj"));
  objRoot.addChild(createPicture(-12.83, -0.3, -43.0, 1.57f, "model/Woman_Parasol.obj"));
  objRoot.addChild(createPicture(-12.83, -0.3, -30.0, 1.57f, "model/Dance_Class.obj" ));
  objRoot.addChild(createPicture(-12.83, -0.3, -39.0, 1.57f, "model/Mill.obj"));
  objRoot.addChild(createPicture(-12.83, -0.2, -35.0, 1.57f, "model/Starry_Night.obj"));

objRoot.addChild(createMuseum()); //uncommented
  objRoot.addChild(CreatePicking());// add
    objRoot.addChild(createText2D_Message());
    objRoot.addChild(createText2D_Message1());//added to comp



  return objRoot;
 }

 public BranchGroup createMuseum() {

  BranchGroup objRoot = new BranchGroup();
  TransformGroup tg = new TransformGroup();
  Transform3D t3d = new Transform3D();

  tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

  t3d.setTranslation(new Vector3d(0.0,0.0,-35.0));
  // t3d.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, -1.2f));
  t3d.setScale(1.0);

  tg.setTransform(t3d);

  ObjectFile loader = new ObjectFile();
  Scene s = null;

  File file = new java.io.File("model/museum.obj");

  try {
   s = loader.load(file.toURI().toURL());
  } catch (Exception e) {
   System.err.println(e);
   System.exit(1);
  }

  tg.addChild(s.getSceneGroup());

  objRoot.addChild(tg);
  objRoot.addChild(createAmbientLight());

  objRoot.compile();

  return objRoot;

 }
public BranchGroup createPicture(double x, double y, double z, float r, String filename) {

  BranchGroup objRoot = new BranchGroup();
  TransformGroup tg = new TransformGroup();
  Transform3D t3d = new Transform3D();

  tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

  t3d.setTranslation(new Vector3d(x, y, z));
  t3d.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, r));
  t3d.setScale(0.017);

  tg.setTransform(t3d);

  ObjectFile loader = new ObjectFile();
  Scene s = null;

  File file = new java.io.File(filename);

  try {
   s = loader.load(file.toURI().toURL());
  } catch (Exception e) {
   System.err.println(e);
   System.exit(1);
  }

  tg.addChild(s.getSceneGroup());
  objRoot.addChild(tg);
  objRoot.addChild(createLight(-0.6f, -0.2f, -1.0f));
  objRoot.addChild(createLight(0.6f, 0.2f, 1.0f));

  objRoot.compile();

  return objRoot;

 }
// add  
 public BranchGroup CreatePicking()
 { 
     BranchGroup objRoot = new BranchGroup();
     cnvs = new PickCanvas(canvas, objRoot);
     TransformGroup tg = new TransformGroup();
     Transform3D t3d = new Transform3D();
     t3d.setTranslation(new Vector3d(0.1,-0.2, -34.87));
     t3d.setRotation(new AxisAngle4f(0.0f,0.0f, 0.0f, 0.0f));
     t3d.setScale(1.0); tg.setTransform(t3d); 
     tg.addChild(createText2D("",0.35, "right eye"));
   tg.addChild(createPrimitives()); // addextra
     objRoot.addChild(tg); objRoot.compile(); return objRoot; 
 } 
 

 // add 
 public BranchGroup createText2D(String eye, double x, String sensor) 
 { 
     BranchGroup objRoot = new BranchGroup(); 
     TransformGroup tg = new TransformGroup();
     Transform3D t3d = new Transform3D();
    
     t3d.setTranslation(new Vector3d(x, -0.46, -9.9601)); 
     t3d.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f)); 
     t3d.setScale(2.0); tg.setTransform(t3d); 
     Text2D text2d = new Text2D(eye, new Color3f(0.0f, 0.0f, 1.0f), "Helvetica", 8, Font.BOLD);// change 
     text2d.setCapability(TransformGroup.ALLOW_TRANSFORM_READ); 
     text2d.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
     text2d.setUserData(sensor); tg.addChild(text2d);
     objRoot.addChild(tg); 
     //objRoot.addChild(createLight()); 
     // comment out 
     objRoot.compile(); 
     return objRoot;
 }
 
 
 private BranchGroup createPrimitives() {  //extra

  BranchGroup objRoot = new BranchGroup();
  TransformGroup tg = new TransformGroup();
  TransformGroup tg1 = new TransformGroup();
// TransformGroup tg2 = new TransformGroup();
  TransformGroup tg2 = new TransformGroup();//a
  TransformGroup tg3 = new TransformGroup();//a
  TransformGroup tg4 = new TransformGroup();//a
  TransformGroup tg5 = new TransformGroup();//a
  TransformGroup tg6 = new TransformGroup();//a
  TransformGroup tg7 = new TransformGroup();//a
  TransformGroup tg8 = new TransformGroup();//a
  TransformGroup tg9 = new TransformGroup();//a
  TransformGroup tg10 = new TransformGroup();//a
  TransformGroup tg11 = new TransformGroup();//a
  TransformGroup tg12 = new TransformGroup();//a
  Transform3D t3d = new Transform3D();

  tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
  tg1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
  tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg4.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg5.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg6.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg7.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg8.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg9.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg10.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg11.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  tg12.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);//a
  
//t3d.setTranslation(new Vector3d(0.0, -0.17, -9.97));
  t3d.setTranslation(new Vector3d(-5.3, 0.04, -10.1));
  t3d.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 0.0f));
  t3d.setScale(2.13);
  Appearance app = new Appearance();
  Material material = new Material();
  material.setDiffuseColor(1.0f,0.25f,0.25f);
       app.setMaterial(material);
       
       
       Appearance app1 = new Appearance();
  Material material1 = new Material();
  material1.setDiffuseColor(0.0f,0.0f,0.0f);
       app1.setMaterial(material1);


  Box box = new Box(0.30f,-0.4f,-0.0001f,app);
  box.setUserData("1");
  tg.addChild(box);
  Transform3D t3d1 = new Transform3D();
 // Transform3D t3d2 = new Transform3D();
   Transform3D t3d2 = new Transform3D();//a
  Transform3D t3d3 = new Transform3D();//a
  Transform3D t3d4 = new Transform3D();//a
  Transform3D t3d5 = new Transform3D();//a
  Transform3D t3d6 = new Transform3D();//a
  Transform3D t3d7 = new Transform3D();//a
  Transform3D t3d8 = new Transform3D();//a
  Transform3D t3d9 = new Transform3D();//a
  Transform3D t3d10 = new Transform3D();//a
  Transform3D t3d11 = new Transform3D();//a
    Transform3D t3d12 = new Transform3D();//a
  t3d1.setTranslation(new Vector3d(5.1, 0.04, -10.1));
  t3d1.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 0.0f));
  t3d1.setScale(2.13);
 
 t3d2.setTranslation(new Vector3d(9.89, 0.049, 4.86));//a
  t3d2.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, -1.56f));//a
  t3d2.setScale(2.52);//a
  
  t3d10.setTranslation(new Vector3d(9.89, 0.041, -5.63)); //a   Cs
  t3d10.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, -1.57f));//a
  t3d10.setScale(2.15);//a
  
  t3d12.setTranslation(new Vector3d(9.89, 0.041, -0.14)); //a   hanuman
  t3d12.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, -1.57f));//a
  t3d12.setScale(2.14);//a
  
  t3d3.setTranslation(new Vector3d(4.6, 0.04, 9.83));//a
  t3d3.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, -3.14f));//a
  t3d3.setScale(2.13);//a

  t3d4.setTranslation(new Vector3d(-0.09, 0.04, 9.83));//a
  t3d4.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, -3.14f));//a sunflower
  t3d4.setScale(2.13);//a
  
  t3d5.setTranslation(new Vector3d(-5.0, 0.04, 9.83));//a
  t3d5.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, -3.14f));//a  Dimanche
  t3d5.setScale(2.13);//a
  
  t3d6.setTranslation(new Vector3d(-12.93, -0.1, 4.86));//a
  t3d6.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 1.57f));//a
  t3d6.setScale(2.13);//a
  
  t3d7.setTranslation(new Vector3d(-12.93, -0.0, -0.1));//a
  t3d7.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 1.57f));//a
  t3d7.setScale(2.13);//a
  
  t3d8.setTranslation(new Vector3d(-12.93, -0.1, -4.08));//a
  t3d8.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 1.57f));//a
  t3d8.setScale(2.13);//a
  
  t3d9.setTranslation(new Vector3d(-12.93, -0.1, -8.1));//a
  t3d9.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 1.57f));//a
  t3d9.setScale(2.13);//a
  
  t3d11.setTranslation(new Vector3d(0.3, -0.4, -10.0));//a
  t3d11.setRotation(new AxisAngle4f(0.0f, 1.0f, 0.0f, 0.0f));//a
  t3d11.setScale(2.13);//a
  
  tg.setTransform(t3d);
  tg1.setTransform(t3d1);
  tg2.setTransform(t3d2);//a
  tg3.setTransform(t3d3);//a
  tg4.setTransform(t3d4);//a
  tg5.setTransform(t3d5);//a
  tg6.setTransform(t3d6);//a
  tg7.setTransform(t3d7);//a
  tg8.setTransform(t3d8);//a
  tg9.setTransform(t3d9);//a
  tg10.setTransform(t3d10);//a
  tg11.setTransform(t3d11);//a
  tg12.setTransform(t3d12);//a

  
  Box box1 = new Box(0.30f,-0.4f,0.0001f,app);
  box1.setUserData("2");
  tg1.addChild(box1);
 // tg.addChild(box);
 Box box2 = new Box(0.27f,0.344f,0.00001f,app);//a
  box2.setUserData("3");//a
  tg2.addChild(box2);//a
  
  Box box3 = new Box(0.31f,0.4f,0.000001f,app);//a
  box3.setUserData("6");//a
  tg3.addChild(box3);//a
  
  Box box4 = new Box(0.31f,0.4f,0.000001f,app);//a
  box4.setUserData("7");//a
  tg4.addChild(box4);//a
  
  Box box5 = new Box(0.31f,0.41f,0.000001f,app);//a
  box5.setUserData("9");//a
  tg5.addChild(box5);//a
  
  Box box6 = new Box(0.31f,0.4f,0.000001f,app);//a
  box6.setUserData("10");//a
  tg6.addChild(box6);//a
  
  Box box7 = new Box(0.31f,0.4f,0.000001f,app);//a
  box7.setUserData("0");//a
  tg7.addChild(box7);//a
  
  Box box8 = new Box(0.30f,0.41f,0.000001f,app);//a
  box8.setUserData("8");//a
  tg8.addChild(box8);//a
  
  Box box9 = new Box(0.30f,0.41f,0.0000001f,app);//a
  box9.setUserData("11");//a
  tg9.addChild(box9);//a
  
  Box box10 = new Box(0.30f,0.405f,0.0000001f,app);//a
  box10.setUserData("4");//a
  tg10.addChild(box10);//a
  
  Box box12 = new Box(0.31f,0.405f,0.0000001f,app);//a
  box12.setUserData("5");
 //box12.set
//a
  //box12.setID(1);
  tg12.addChild(box12);
  Box box11 = new Box(0.04f,0.02f,0.0000001f,app1);//a
  box11.setUserData("13");//a
  tg11.addChild(box11);//a
  
  objRoot.addChild(tg);
  objRoot.addChild(tg1);
    objRoot.addChild(tg2);//a
  objRoot.addChild(tg3);//a
  objRoot.addChild(tg4);//a
  objRoot.addChild(tg5);//a
  objRoot.addChild(tg6);//a
  objRoot.addChild(tg7);//a
  objRoot.addChild(tg8);//a
  objRoot.addChild(tg9);//a
 objRoot.addChild(tg10);//a
 objRoot.addChild(tg11);//a
 objRoot.addChild(tg12);//a
  objRoot.compile();

  return objRoot;

 }
//added to cpmputer
  private BranchGroup createText2D_Message() {

  BranchGroup objRoot = new BranchGroup();
  TransformGroup tg = new TransformGroup();
  Transform3D t3d = new Transform3D();

  t3d.setTranslation(new Vector3d(-0.57, 0.09, -44.90));
  t3d.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f));
  t3d.setScale(1.0);
  tg.setTransform(t3d);

  text2d_message = new Text2D("", new Color3f(1.0f, 1.0f, 1.0f),
    "Helvetica", 37, Font.BOLD);

  tg.addChild(text2d_message);
  objRoot.addChild(tg);

  objRoot.compile();

  return objRoot;

 }
  private BranchGroup createText2D_Message1() {

  BranchGroup objRoot = new BranchGroup();
  TransformGroup tg = new TransformGroup();
  Transform3D t3d = new Transform3D();

  t3d.setTranslation(new Vector3d(-0.4,-0.1, -44.90));
  t3d.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f));
  t3d.setScale(1.0);
  tg.setTransform(t3d);

  text2d_message1 = new Text2D("", new Color3f(1.0f, 1.0f, 1.0f),
    "Helvetica", 37, Font.BOLD);

  tg.addChild(text2d_message1);
  objRoot.addChild(tg);

  objRoot.compile();

  return objRoot;

 }
  
 public Light createLight(float x, float y, float z) {
  DirectionalLight light = new DirectionalLight(true, new Color3f(0.2f, 0.2f, 0.2f), new Vector3f(x, y, z));

  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 10000.0));

  return light;
 }

 public Light createAmbientLight() {
  AmbientLight light = new AmbientLight(new Color3f(0.1f, 0.1f, 0.1f));

  light.setInfluencingBounds(new BoundingSphere(new Point3d(), 100.0));

  return light;  
 }

 public static void main(String[] args)  {
     //Mykeynavbeh d=new Mykeynavbeh();
  Mykeynavbeh applet = new Mykeynavbeh(user);
  Frame frame = new MainFrame(applet, 800, 600);
 }

 public void keyTyped(KeyEvent e) {
  char key = e.getKeyChar();

  if (key == 'd') {
     // t3d_vt.get(trans);
      //System.out.println("trans.z"+ trans.z);
   t3dstep.set(new Vector3d(0.0, 0.0, -0.3));
  viewtrans.getTransform(t3d_vt);
   t3d_vt.mul(t3dstep);
   viewtrans.setTransform(t3d_vt);
   
  }
  
  if (key == 'a') {

     
   t3dstep.rotY(Math.PI /4.2);
   viewtrans.getTransform(t3d_vt);
   t3d_vt.get(matrix);
   t3d_vt.setTranslation(new Vector3d(0.0, 0.0, 0.0));
   t3d_vt.mul(t3dstep);
   t3d_vt.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
   viewtrans.setTransform(t3d_vt);

  }
  if (key == 'x') {
      

       t3dstep.rotY(-Math.PI / 256);
       viewtrans.getTransform(t3d_vt);
       t3d_vt.get(matrix);
       t3d_vt.setTranslation(new Vector3d(0.0, 0.0, 0.0));
       t3d_vt.setRotation(new AxisAngle4f(0.0f,0.0f, 0.0f, 0.60f));
       t3d_vt.mul(t3dstep);
       t3d_vt.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
       viewtrans.setTransform(t3d_vt);
      
       
      

      }
   if (key == 's') {

         
       t3dstep.rotY(Math.PI /256);
       viewtrans.getTransform(t3d_vt);
       t3d_vt.get(matrix);
      t3d_vt.setTranslation(new Vector3d(0.0, 0.0, 0.0));
       t3d_vt.mul(t3dstep);
       t3d_vt.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
       viewtrans.setTransform(t3d_vt);

      }

  if (key == 'g') {
      

   t3dstep.rotY(-Math.PI / 4.2);
   viewtrans.getTransform(t3d_vt);
   t3d_vt.get(matrix);
   t3d_vt.setTranslation(new Vector3d(0.0, 0.0, 0.0));
   //t3d_vt.setRotation(new AxisAngle4f(0.0f,0.0f, 0.0f, 0.60f));
   t3d_vt.mul(t3dstep);
   t3d_vt.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
   viewtrans.setTransform(t3d_vt);

  }

   if (key == 'f') {
      

       t3dstep.rotY(-Math.PI / 256);
       viewtrans.getTransform(t3d_vt);
       t3d_vt.get(matrix);
       t3d_vt.setTranslation(new Vector3d(0.0, 0.0, 0.0));
       //t3d_vt.setRotation(new AxisAngle4f(0.0f,0.0f, 0.0f, 0.60f));
       t3d_vt.mul(t3dstep);
       t3d_vt.setTranslation(new Vector3d(matrix.m03, matrix.m13, matrix.m23));
       viewtrans.setTransform(t3d_vt);

      }
  // (3) change
  if (key == 'c') {

   //t3d_vt.get(trans);
   System.out.println("trans.z: " + trans.z);

   t3dstep.set(new Vector3d(0.0, 0.0, 0.3));
   viewtrans.getTransform(t3d_vt);
   t3d_vt.mul(t3dstep);
   viewtrans.setTransform(t3d_vt);

   t3d_vt.get(trans);
   //System.out.println("trans.z: " + trans.z);

   
  }
  
  
 }

 public void keyReleased(KeyEvent e) {
 }

 public void keyPressed(KeyEvent e) {
 }
// add
public void mousePressed(MouseEvent evt) 
{ 
    } 
 // changed
 public void mouseClicked(MouseEvent evt) {
 Connection conn=null;
 try{
      Class.forName("java.sql.Driver");
     String path="jdbc:mysql://localhost/password";
     String user="root";
     String pass="bhat";
    

     conn=DriverManager.getConnection(path,user,pass);
    //Statement str=conn.createStatement();
     //counter++;
 }
 catch(Exception e){
      JOptionPane.showMessageDialog(null,"Error found"+e);
 }
//    int counter=0;

     
  cnvs.setShapeLocation(evt.getX(), evt.getY());
  PickResult result = cnvs.pickClosest();
     
//  System.out.println(result);
  
  if (result != null) {
   Node node = result.getNode(PickResult.SHAPE3D);
    if (node.getUserData() != null){
   sensorID = (node.getUserData()).toString();
  //int i=node.getID();
   //password(i);

  // System.out.println(sensorID);
   if (sensorID == "right eye") {

    System.out.println("The right eye is clicked: " + sensorID);

   } else if (sensorID == "left eye") {

    System.out.println("The left eye is clicked: " + sensorID);
   } 
   
    }
    
    Node node_2 = result.getNode(PickResult.PRIMITIVE);
    if (!((sensorID == "right eye")||(sensorID == "left eye"))){
        
   int sensorID_2 = Integer.parseInt((node_2.getUserData()).toString());
   //String img[sensorID_2];
  //Connection conn=null; //System.out.println(sensorID_2);

  try
 {

     
     Class.forName("java.sql.Driver");
     String path="jdbc:mysql://localhost/password";
     String user="root";
     String pass="bhat";
     conn=DriverManager.getConnection(path,user,pass);
    //counter++;
         
    //System.out.println(""+i);
     
 }
catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
   }
 
   
   if (sensorID_2 == 1) {
 try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("1");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `1`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Mona_Lisa is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
   }  
 
    
    if (sensorID_2 == 2) {
   try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("2");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `2`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Girl_pearl_earing is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
   }  
 
    if (sensorID_2 == 3) {
     try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("3");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `3`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Dimanche is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
    }
  

     if (sensorID_2 == 4) {
    try
{
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("4");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `4`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("cs is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
  catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
     }
       if (sensorID_2 == 5) {
            try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("5");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `5`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("hanuman is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
       }
    
    
        if (sensorID_2 == 6) {
             try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("6");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `6`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Piper is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
        }
    
  
    
         if (sensorID_2 == 7) {
         try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("7");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `7`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println(" Sunflower is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
         }
  
    

             if (sensorID_2 == 8) {
                  try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("8");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `8`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Mill is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
             }
             
                 if (sensorID_2 == 9) {
       try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("9");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `9`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Ridaeu is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
                 }
 
                     if (sensorID_2 == 0) {
       try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("0");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `0`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Starry night is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
 
                     }
    
  
     if (sensorID_2 == 10) {
         try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("10");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `10`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("dance class is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
 
        
    }
                     
      if (sensorID_2 == 11) {
          try
 {
      
        String n=String.valueOf(this.user);
       PreparedStatement ps=conn.prepareStatement("select * from table1 where userid=?");
        ps.setString(1,n);
       ResultSet rs=ps.executeQuery();
       while(rs.next())
       {
       int no=rs.getInt("11");
       if(no==0)
       {
           counter++;
           String query="  update table1 set `11`=? where userid=? ";
 
  PreparedStatement pdt=conn.prepareStatement(query);
 String count=String.valueOf(counter);
 //str.setString(1,n);
  pdt.setString(1,count);
  pdt.setString(2,n);
           int n1=pdt.executeUpdate();        
  System.out.println("Woman parsol is clicked: " + sensorID_2);
  JOptionPane.showMessageDialog(null,"Image is clicked");
  text2d_message.setString("Click the button");
  text2d_message1.setString("to submit");
 
       }
       else
       {
           JOptionPane.showMessageDialog(null,"Image already selected");
       }
       }
      }
 
 catch(Exception e)
 {
     JOptionPane.showMessageDialog(null,"Error found"+e);
 }
 }
    
       if (sensorID_2 == 13) {
           
    System.out.println("button is clicked: " + sensorID_2);
    
JOptionPane.showMessageDialog(null,"Password set");
       setVisible(false);

Log1 r1=new Log1();
// ReRegister.reg.setText(this.tf.getText());
r1.show();
//text.setText("password set");

     
     //System.out.println("enter the password:");
     //Scanner input = new Scanner(System.in);
     //String pass = input.nextLine();
     //text2d_message.setString(pass);
       }
       }
    
    sensorID = null;

  

  
  if ((sensorID == null)||(sensorID_2 == 0)) {
   return;
  }
 
   }
 }
 
 public void mouseReleased(MouseEvent evt) {
 }

 public void mouseEntered(MouseEvent evt) {
 }

 public void mouseExited(MouseEvent evt) {
 }

   
}
 
/*void password(int n)
{
sum=sum*10+n;
]*/
