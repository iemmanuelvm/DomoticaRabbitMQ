import	javax.swing.JFrame;	
public class Principal
{
    public static void main(String args[])
    {
    	InterfazUsuario obj = new InterfazUsuario();
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.setSize(1700, 1000);
		obj.setVisible(true);
		obj.setLayout(null);
		obj.setResizable(false);
		obj.setLocationRelativeTo(null);
    }
}
