package tests.simple;

import java.io.IOException;

import sound.Sound;

public class SimpleSound
{
	public static void main(String args[])
	{
		try
		{
			Sound s = new Sound("/tests/resources/sounds/boo.wav");
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
