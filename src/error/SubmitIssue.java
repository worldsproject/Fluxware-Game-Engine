package error;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;

import com.google.gdata.client.projecthosting.ProjectHostingService;
import com.google.gdata.data.HtmlTextConstruct;
import com.google.gdata.data.Person;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.projecthosting.IssuesEntry;
import com.google.gdata.data.projecthosting.Label;
import com.google.gdata.data.projecthosting.Owner;
import com.google.gdata.data.projecthosting.SendEmail;
import com.google.gdata.data.projecthosting.Status;
import com.google.gdata.data.projecthosting.Username;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class SubmitIssue 
{
	public static void sendIssue(String comment, Exception except)
	{
		ProjectHostingService service = new ProjectHostingService("fluxware");
		String username = "fluxware.bot@gmail.com";
		
		ProjectHostingClient client = null;
		try 
		{
			client  = new ProjectHostingClient(service, "fluxware", username, "cu9sp4Zt3kt9");
		}
		catch (AuthenticationException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Person author = new Person();
	    author.setName(username);

	    Owner owner = new Owner();
	    owner.setUsername(new Username(username));

	    IssuesEntry entry = new IssuesEntry();
	    entry.getAuthors().add(author);

	    
	    Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
	    except.printStackTrace(printWriter);
	    
	    String stackTrack = result.toString();


	    
	    entry.setOwner(owner);
	    entry.setContent(new HtmlTextConstruct("Stack Trace:<br/>" + stackTrack + "<br /><br />Cause:<br/>" + except.getCause() + "<br/><br />Message:<br />" + except.getMessage() + "<br /><br />User Comment:<br />" + comment));
	    entry.setTitle(new PlainTextConstruct(except.toString()));
	    entry.setStatus(new Status("New"));
	    entry.addLabel(new Label("Priority-High"));
	    entry.addLabel(new Label("GameEngine"));
	    entry.addLabel(new Label("Type-Defect"));
	    entry.addLabel(new Label("Bot-Added"));
	    entry.setSendEmail(new SendEmail("False"));
	    
	    try 
	    {
			client.insertIssue(entry);
		}
	    catch (Exception e) 
	    {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
