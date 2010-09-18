package error;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Creates a Crash Report whenever an Exception is raised.  This allows the user
 * to give a comment and add the issue to the issue tracker.
 * @author tbutram
 *
 */
public class CrashReport extends JFrame implements ActionListener 
{
	private String message = "<html><p align=\"center\">We are sorry but the Fluxware Game Engine has crashed.<br>" +
	"If you want you can send an error report, with a nice comment,<br>or you may" +
	" quit your game, which will, sadly,<br>will cause you to lose any unsaved information.</p></html>";

	private JButton repo = new JButton("Report");
	private JButton quit = new JButton("Quit");

	private JLabel information = new JLabel(message);
	private JTextArea comment = new JTextArea("Please enter any additional information.", 5, 30);
	private JTextArea error = new JTextArea();

	private Exception e = null;

	/**
	 * Creates a Dialog box that informs the user of the FluxEngine that it has crashed.
	 * The exception that caused it to fail should be passed in.
	 * @param e - The Exception that caused the failure.
	 */
	public CrashReport(Exception e)
	{
		try
		{
			this.setTitle("FluxEngine has crashed.");
			this.e = e;

			JPanel textAreas = new JPanel(new BorderLayout(5, 5));
			JPanel buttons = new JPanel(new FlowLayout());
			JPanel root = (JPanel)this.getContentPane();
			root.setLayout(new BorderLayout());

			buttons.add(repo);
			buttons.add(quit);

			information.setText(message);
			information.setHorizontalTextPosition(JLabel.CENTER);

			Writer result = new StringWriter();
			PrintWriter printWriter = new PrintWriter(result);
			e.printStackTrace(printWriter);

			String stackTrack = result.toString();

			error.setText(stackTrack);
			error.setEditable(false);



			textAreas.add(information, BorderLayout.NORTH);
			textAreas.add(error, BorderLayout.CENTER);
			textAreas.add(comment, BorderLayout.SOUTH);

			root.add(textAreas, BorderLayout.NORTH);
			root.add(buttons, BorderLayout.SOUTH);
			root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

			repo.addActionListener(this);
			quit.addActionListener(this);
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.pack();
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
		catch(Exception easdfasdf)
		{
			quit();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();

		if(o == quit)
		{
			quit();
		}
		else if(o == repo)
		{
			SubmitIssue.sendIssue(comment.getText(), this.e);
			quit();
		}
	}

	private void quit()  //Because quit() is shorter than System.exit(1);
	{
		e.printStackTrace();
		System.exit(1);
	}
}
