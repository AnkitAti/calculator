import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** This class is responsible for the User interface of the Calculator.
 * It draws the various buttons and layout of the calculator.
 * It is also responsible for actions of the button.
 * @version 1.1.1.0
 * @author <a href = "ankmonuati@gmail.com">Ankit</a>
 * @see Draw()
 * @see getLayout()
 * @see getMenuBar()
 * 
 */

public class Draw
{
	JButton[] button;
	JTextField input;
	PostFix postFix;
	double result;
	boolean flag;
	/**
	 * @author Ankit
	 * 
	 * @return JPanel
	 * 
	 * @version 1.1.1.0
	 */
	public JPanel getLayout()
	{
		JPanel main = new JPanel();
		button = new JButton[20];
		button[0] = new JButton("+");
		button[1] = new JButton("-");
		button[2] = new JButton("*");
		button[3] = new JButton("/");
		button[4] = new JButton("(");
		button[5] = new JButton(")");
		button[6] = new JButton("=");
		button[7] = new JButton("Del");
		button[8] = new JButton("AC");
				JPanel buttonContainer1 = new JPanel(new GridLayout(1,4));
		for(int i=0; i<6; i++)
		{
			buttonContainer1.add(button[i]);
			button[i].addActionListener(new Operator());
		}
		JPanel buttonContainer2 = new JPanel(new GridLayout(1,4));
		for(int i=4; i<7; i++)
			buttonContainer2.add(button[i]);
		JPanel buttonContainer3 = new JPanel(new GridLayout(1,2));
		buttonContainer3.add(button[8]);
		button[6].addActionListener(new Calculate());
		button[7].addActionListener(new Extra());
		button[8].addActionListener(new Extra());
		input = new JTextField("0",20);
		input.addActionListener(new Result());
		input.setMargin(new Insets(2,2,2,2));
		input.setToolTipText("Enter your query here");
		input.setCaretPosition(1);
		JPanel number = new JPanel(new GridLayout(4,3));
		for(int i=0; i<9; i++)
		{
			button[10+i] = new JButton(Integer.toString(i+1));
			button[10+i].addActionListener(new Operator());
			number.add(button[10+i]);
		}
		button[19] = new JButton("0");
		button[19].addActionListener(new Operator());
		number.add(button[8]);
		number.add(button[19]);
		number.add(button[7]);
		main.add(BorderLayout.NORTH,input);
		main.add(buttonContainer1);
		main.add(buttonContainer2);
		main.add(buttonContainer3);
		main.add(number);
		return main;
	}
	
	/**
	 * @author Ankit
	 * 
	 * @return JMenuBar
	 * 
	 * @version  1.1.1.0
	 */
	public JMenuBar getMenuBar()
	{
		JMenu file = new JMenu("File     ");
		JMenuItem newF = new JMenuItem("New     ");
		file.add(newF);
		newF.setAccelerator(KeyStroke.getKeyStroke("ctrl + W"));
		newF.addActionListener(new Menu());
		JMenuItem clear = new JMenuItem("Clear    ");
		file.add(clear);
		clear.setAccelerator(KeyStroke.getKeyStroke("ctrl Z"));
		clear.addActionListener(new Menu());
		file.addSeparator();
		JCheckBoxMenuItem checkPrecision = new JCheckBoxMenuItem("Show answers in integers");
		checkPrecision.setSelected(true);
		flag = true;
		checkPrecision.addChangeListener(new Precision());
		file.add(checkPrecision);
		file.addSeparator();
		JMenuItem close = new JMenuItem("Close      ");
		file.add(close);
		close.addActionListener(new Menu());
		JMenuBar main = new JMenuBar();
		main.add(file);
		return main;
	}
	class Operator implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			int i = input.getCaretPosition();
			input.setText(input.getText().substring(0,i) + evt.getActionCommand() + input.getText().substring(i,input.getText().length()));
			input.requestFocus();
			input.setCaretPosition(i+1);
		}
	}
	class Calculate implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			try{
				postFix = new PostFix();
				String str = postFix.result(input.getText());
				result = Double.parseDouble(str);
				if(flag)
					str = Double.toString(result);
				else
					str = Integer.toString((int)result);
				input.setText(str);
				input.requestFocus();
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null,"Maths Error");
				input.requestFocus();
			}
		}
	}
	class Extra implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			switch(evt.getActionCommand())
			{
			case "Del":
				try{
					int i = input.getCaretPosition();
					input.setText(input.getText().substring(0,i-1)+input.getText().substring(i,input.getText().length()));
					input.requestFocus();
					input.setCaretPosition(i-1);
				}
				catch(StringIndexOutOfBoundsException e)
				{
					input.requestFocus();
				}
				break;
			case "AC":
				input.setText("0");
				input.requestFocus();
				break;
			default:
			}
		}
	}
	class Menu implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			String str = evt.getActionCommand().trim();
			switch(str)
			{
			case "New":
				Main m = new Main();
				m.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				m.setVisible(true);
				m.setResizable(false);
				break;
			case "Clear":
				input.setText("0");
				break;
			case "Close":
				System.exit(0);
				break;
			}
		}
	}
	class Precision implements ChangeListener
	{
		public void stateChanged(ChangeEvent evt) 
		{
			JCheckBoxMenuItem checkBox = (JCheckBoxMenuItem)evt.getSource();
			if(checkBox.isSelected())
				flag = true;
			else
				flag = false;
		}
	}
	class Result implements ActionListener
	{
		public void actionPerformed(ActionEvent evt) {
			int i = evt.getID();
			if(i == 1001)
			{
				try{
					postFix = new PostFix();
					String str = postFix.result(input.getText());
					result = Double.parseDouble(str);
					if(flag)
						str = Double.toString(result);
					else
						str = Integer.toString((int)result);
					input.setText(str);
					input.requestFocus();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,"Maths Error");
					input.requestFocus();
				}
			}
		}
	}
}
