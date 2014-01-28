import javax.swing.JFrame;

/**
 * This class simply is used to instantiate different things required for application.
 * @author ANKIT
 *
 * @version 1.1.1.0
 */
class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	public static void main(String[] args)
	{
		JFrame window = new Main();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setResizable(false);
	}
	Main()
	{
		super("Ankit's Calculator");
		Draw draw = new Draw();
		setContentPane(draw.getLayout());
		setJMenuBar(draw.getMenuBar());
		setSize(250,270);
	}
}
