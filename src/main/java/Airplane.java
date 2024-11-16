import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import org.apache.commons.math3.complex.Quaternion;

public class Airplane implements KeyListener {
  private static final double EPSILON = 0.0001;
  private static final DecimalFormat df = new DecimalFormat("0.00");

  private Quaternion roll = new Quaternion(0, 1, 0, 0);
  private Quaternion pitch = new Quaternion(0, 0, 1, 0);
  private Quaternion yaw = new Quaternion(0, 0, 0, 1);
  private Quaternion craft = new Quaternion(0, 1, 0, 0);

  public Airplane roll(double angle) {
    Quaternion q = QUtil.create(angle, roll);
    craft = craft.multiply(q);
    return this;
  }
  
  public Airplane pitch(double angle) {
    Quaternion q = QUtil.create(-angle, pitch);
    craft = craft.multiply(q);
    return this;
  }
  
  public Airplane yaw(double angle) {
    Quaternion q = QUtil.create(angle, yaw);
    craft = craft.multiply(q);
    return this;
  }
  
  private Quaternion rotateAircraft() {
    return craft;
  }
  
  public void getOrientation() {
    Quaternion o = rotateAircraft();
    System.out.println("quaternion: " + o);

    double w = o.getQ0();
    double x = o.getQ1();
    double y = o.getQ2();
    double z = o.getQ3();
    double yaw = Math.toDegrees(Math.atan2(2.0*(w*z + x*y), w*w+x*x-y*y-z*z));
    double pitch = Math.toDegrees(Math.asin(-2.0*(x*z - w*y)));
    double roll = Math.toDegrees(Math.atan2(2.0*(z*y + w*x), w*w - x*x - y*y + z*z));
    System.out.println("y p r: " + yaw + ", " + pitch + ", " + roll);
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
    getOrientation();
//    System.out.println(this);
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
    return "";
  }
}
