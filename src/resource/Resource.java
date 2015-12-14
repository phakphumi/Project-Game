package resource;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JOptionPane;

import render.AnimationManager;
import render.ImageReader;

public class Resource {
	private static HashMap<String,AnimationManager> rs = new HashMap<>();
	private static HashMap<String,AudioClip> audio = new HashMap<>();

	public static final Font standardFont = new Font("Tahoma",Font.BOLD,30);
	
	public AnimationManager read(String url,int setX,int setY,int setCharWidth,int setCharHeight,int mode) throws FethResourceException{
		return new AnimationManager(ImageReader.get(url,mode),setX,setY,setCharWidth,setCharHeight,mode);
	}
	public AnimationManager read(String url,int setX,int setY,int setCharWidth,int setCharHeight) throws FethResourceException{
		return new AnimationManager(
				ImageReader.get(url)
				,setX
				,setY
				,setCharWidth
				,setCharHeight
				,AnimationManager.DONOTTHING
			);
	}
	public AnimationManager read(String url,int mode) throws FethResourceException{
		return new AnimationManager(ImageReader.get(url,mode),0,0,0,0,mode);
	}
	public AnimationManager read(String url) throws FethResourceException{
		return new AnimationManager(ImageReader.get(url),0,0,0,0,AnimationManager.DONOTTHING);
	}
	public AudioClip audioRead(String url){
		return Applet.newAudioClip(Resource.class.getClassLoader().getResource(url));
	}
	
	public Resource(){
		try {
			audio.put("birdSound", audioRead("res/sound/bird.wav"));
			audio.put("thebeat", audioRead("res/sound/thebeat.wav"));
			audio.put("doorbell", audioRead("res/sound/doorbell2.wav"));
			audio.put("zombiedeath", audioRead("res/sound/zombiedeath.wav"));
			audio.put("gamebgm", audioRead("res/sound/05. Plants vs. Zombies Original Soundtrack - Loonboon.wav"));
			audio.put("introbgm", audioRead("res/sound/02. Plants vs. Zombies Original Soundtrack - Crazy Dave (Intro Theme).wav"));
			audio.put("pausebgm", audioRead("res/sound/02. Plants vs. Zombies Original Soundtrack - Crazy Dave (Intro Theme).wav"));
			audio.put("gameoverbgm", audioRead("res/sound/02. Plants vs. Zombies Original Soundtrack - Crazy Dave (Intro Theme).wav"));
			audio.put("winningbgm", audioRead("res/sound/02. Plants vs. Zombies Original Soundtrack - Crazy Dave (Intro Theme).wav"));
			
			rs.put("batman-walking", read("res/character/batman-walking.gif",45,90,23,68));
			rs.put("batman-standing", read("res/character/batman-standing.gif",45,90,23,68));
			rs.put("boy", read("res/character/boy.gif",45,90,35,80));
			
			rs.put("zombie-ballon", read("res/character/zombie-ballon.gif",48,150,90,95,AnimationManager.FLIP));
			rs.put("zombie-helmet", read("res/character/zombie-helmet.gif",40,100,71,85,AnimationManager.FLIP));
			rs.put("zombie-imps", read("res/character/zombie-imps.gif",50,100,85,150,AnimationManager.FLIP));
			rs.put("zombie-moonwalk", read("res/character/zombie-moonwalk.gif",45,100,90,100,AnimationManager.FLIP));
			rs.put("zombie-running", read("res/character/zombie-running.gif",50,96,50,150,AnimationManager.FLIP));
			
			rs.put("batman-intro", read("res/bg/batman-intro.gif",AnimationManager.BufferOPTIMIZED));
			rs.put("batman-pic", read("res/bg/batman-pic.png"));
			rs.put("BMDP", read("res/bg/BMDP.gif",AnimationManager.BufferOPTIMIZED));
			rs.put("town", read("res/bg/town.jpg"));
			rs.put("city", read("res/bg/city.png"));
	
			rs.put("button1", read("res/etc/button1.60.png"));
			rs.put("button2", read("res/etc/button2.2.png"));
			rs.put("batman-icon", read("res/etc/batman-icon.png"));
	
	//		rs.put("test", read("res/character/zombie-walking.6.png")); //use for sprite PNG
		} catch (FethResourceException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), 
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public static AnimationManager get(String key) {
		if(rs.containsKey(key)) {
			return rs.get(key);
		}
		throw new GetResourceException(GetResourceException.ANIMATION, key);
	}
	
	public static BufferedImage getImage(String key) {
		if(rs.containsKey(key)) {
			return rs.get(key).getCurrentBufferedImage();
		}
		throw new RuntimeException("Character Key is incorrect : " + key);
	}
	
	public static BufferedImage getImage(String key,int index) {
		if(rs.containsKey(key)) {
			return rs.get(key).getCurrentBufferedImage(index);
		}
		throw new RuntimeException("Character Key is incorrect : " + key);
	}
	
	public static AudioClip getAudio(String key) {
		if(audio.containsKey(key)) {
			return audio.get(key);
		}
		throw new RuntimeException("Audio Key is incorrect : " + key);
	}
}