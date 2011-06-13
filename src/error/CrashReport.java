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
@SuppressWarnings("serial")
@Deprecated
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
			this.setTitle("FluxEngine has crashed.");  //Sets the title of the window.
			this.e = e;

			JPanel textAreas = new JPanel(new BorderLayout(5, 5));  //Sets up the area for the text to appear.
			JPanel buttons = new JPanel(new FlowLayout()); //Simple pane to hold the two buttons.
			JPanel root = (JPanel)this.getContentPane();
			root.setLayout(new BorderLayout());

			//Add both buttons to the buttons pane.
			buttons.add(repo);
			buttons.add(quit);

			//Set the information textbox to contain the default "we crashed" message.
			information.setText(message);
			information.setHorizontalTextPosition(JLabel.CENTER); //Also center it.

			Writer result = new StringWriter(); //Output the stack trace to a writer, so we can use it.
			PrintWriter printWriter = new PrintWriter(result);
			e.printStackTrace(printWriter);

			String stackTrack = result.toString(); //Save the stack trace as a String.

			error.setText(stackTrack); //Set the error textArea text to our stack trace.
			error.setEditable(false); //Don't let them edit it.



			//Add the textAreas to the JPanel, with information on top, the error in the middle, and room for comments on the bottom.
			textAreas.add(information, BorderLayout.NORTH);
			textAreas.add(error, BorderLayout.CENTER);
			textAreas.add(comment, BorderLayout.SOUTH);

			//Add the TextAreas to the top of the Frame.
			root.add(textAreas, BorderLayout.NORTH);
			root.add(buttons, BorderLayout.SOUTH); //Put the buttons at the bottom.
			root.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); //Put a nice 5px margin around it all.

			//Add ActionListeners to both buttons.
			repo.addActionListener(this);
			quit.addActionListener(this);
			
			//Clicking the 'X' kills the program.
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//Pack and show
			this.pack(); 
			this.setLocationRelativeTo(null); //Center it.
			this.setVisible(true);
		}
		catch(Exception easdfasdf)
		{
			quit(); //No "error in error" just quit. We give up at this point.
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) //When
	{
		Object o = e.getSource(); //Get the source of our action.

		if(o == quit) //Quit quites.
		{
			quit();
		}
		else if(o == repo) //Report, then quit.
		{
			//This is where the issue should be submitted.
			quit();
		}
	}

	private void quit()  //Because quit() is shorter than System.exit(1);
	{
		e.printStackTrace(); //Print the stack trace to the terminal (if any)
		System.exit(1); //Exit with error code of 1.
	}
}
