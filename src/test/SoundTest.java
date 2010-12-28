package test;

import sound.Sound;

public class SoundTest {

	public static void main(String args[])
	{
		Sound sound = new Sound("/resources/test.ogg");
		
		sound.play();
		long x = System.currentTimeMillis();
		while(System.currentTimeMillis()-x<5000){}
		sound.pause();
		sound.pause();
		sound.pause();
		x = System.currentTimeMillis();
		while(System.currentTimeMillis()-x<5000){}
		sound.play();
		x = System.currentTimeMillis();
		while(System.currentTimeMillis()-x<5000){}
		sound.stop();
		sound.stop();
		sound.stop();
		x = System.currentTimeMillis();
		while(System.currentTimeMillis()-x<5000){}
		sound.play();
		sound.play();
		sound.play();
		x = System.currentTimeMillis();
		while(System.currentTimeMillis()-x<15000){}
		sound.stop();
	}
}
