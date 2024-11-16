import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javafx.geometry.Point2D;

public class Airplane implements KeyListener {
  private static final double EPSILON = 0.0001;
  private static final DecimalFormat df = new DecimalFormat("0.00");

  private Quaternion nose = new Quaternion(0, 1, 0, 0);
  private Quaternion wing = new Quaternion(0, 0, 1, 0);
  private Quaternion normal = new Quaternion(0, 0, 0, 1);
  
  public Airplane roll(double angle) {
    rotateAircraft(QUtil.create(angle, nose));
    return this;
  }
  
  public Airplane pitch(double angle) {
    rotateAircraft(QUtil.create(-angle, wing));
    return this;
  }
  
  public Airplane yaw(double angle) {
    rotateAircraft(QUtil.create(angle, normal));
    return this;
  }
  
  private void rotateAircraft(Quaternion rotator) {
    nose = QUtil.rotate(nose, rotator);
    wing = QUtil.rotate(wing, rotator);
    normal = QUtil.rotate(normal, rotator);
  }
  
  public Orientation getOrientation() {
    Orientation o = new Orientation();
    o.roll = angle(new Point2D(wing.getQ2(), wing.getQ3()), new Point2D(1, 0));
    o.pitch = -angle(new Point2D(normal.getQ1(), normal.getQ3()), new Point2D(0, 1));
//    o.pitch = angle(new Vector3D(normal.getQ1(), normal.getQ2(), normal.getQ3()), new Vector3D(0, 0, 1));
    if (Math.abs(o.pitch - 90.0) < EPSILON) {
      o.roll = 0;
      o.yaw = angle(new Point2D(normal.getQ1(), normal.getQ2()), new Point2D(-1, 0));
    } else if (Math.abs(o.pitch + 90.0) < EPSILON) {
      o.roll = 0;
      o.yaw = angle(new Point2D(normal.getQ1(), normal.getQ2()), new Point2D(-1, 0));
    } else {
      o.yaw = angle(new Point2D(nose.getQ1(), nose.getQ2()), new Point2D(1, 0));
    }
    return o;
  }

  public double angle(Point2D a, Point2D b) {
    if (Math.abs(a.getX()) < EPSILON && Math.abs(a.getY()) < EPSILON) {
      return Double.NaN;
    }
    double dot = a.getX()*b.getX()+a.getY()*b.getY();
    double det = a.getX()*b.getY()-a.getY()*b.getX();
    return Math.toDegrees(Math.atan2(det, dot));
  }

  public double angle(Vector3D a, Vector3D b) {
    return Math.toDegrees(Math.atan2(a.crossProduct(b).getNorm(), a.dotProduct(b)));
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
    case 37:
      roll(-5.0);
      break;
    case 40:
      pitch(5.0);
      break;
    case 39:
      roll(5.0);
      break;
    case 38:
      pitch(-5.0);
      break;
    case 74:
      yaw(-5.0);
      break;
    case 75:
      yaw(5.0);
      break;
    }
    getOrientation().print();
    System.out.println(this);
  }

  public class Orientation {
    double roll, yaw, pitch;

    public void print() {
      System.out.println(
          "r = " + df.format(trim(roll)) + ", p = " + df.format(trim(pitch)) + ", y = " + df.format(trim(yaw)));
    }
  }

  public double trim(double d) {
    return Math.round(d * 100.0)/100.0;
  }

  private String getQStr(Quaternion q) {
    return "[" + df.format(trim(q.getQ0())) + ", " + df.format(trim(q.getQ1())) + ", " +
        df.format(trim(q.getQ2())) + ", " + df.format(trim(q.getQ3())) + "]";
  }

  @Override
  public String toString() {
    return getQStr(nose) + " " + getQStr(wing) + " " + getQStr(normal);
  }
}
