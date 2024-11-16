import javax.swing.JTextField;
import javax.swing.JFrame;

public class Sim {

  public static void main(String[] args) throws Exception {
    Airplane airplane = new Airplane();
    airplane.getOrientation();
//    System.out.println(airplane);

    JTextField textField = new JTextField();
    textField.addKeyListener(airplane);
    JFrame jframe = new JFrame();
    jframe.add(textField);
    jframe.setSize(400, 350);
    jframe.setVisible(true);
  }


}
