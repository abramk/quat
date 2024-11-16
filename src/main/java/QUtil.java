import org.apache.commons.math3.complex.Quaternion;

public class QUtil {

  public static Quaternion create(double angle, Quaternion q) {
    double theta = Math.toRadians(angle/2.0);
    double c = Math.cos(theta);
    double s = Math.sin(theta);
    return new Quaternion(c, s * q.getQ1(), s * q.getQ2(), s * q.getQ3()).normalize();
  }

  public static Quaternion rotate(Quaternion p, Quaternion q) {
    return q.multiply(p).multiply(q.getInverse()).normalize();
  }
}
