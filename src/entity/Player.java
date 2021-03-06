package entity;

import java.applet.AudioClip;
import java.awt.*;

import com.sun.swing.internal.plaf.synth.resources.synth;

import Main.ScreenManager;
import render.AnimationManager;
import render.IRenderable;
import render.RenderAnimationHelper;
import render.RenderableHolder;
import resource.Resource;
import utility.ConfigurableOption;
import utility.Debugger;
import utility.TimeToCounter;

public class Player implements IRenderable {
	public static Object playerLocker = new Object();
	protected int x;
	protected int y;
	protected int charWidth,charHeight;
	protected boolean destroyed;
	protected boolean destroying;
	private int deadCounter;
	public AnimationManager animation;
	public AnimationManager animationWalking;
	public AnimationManager animationStanding;
	private boolean walking;
	private boolean visible;
	private int threadCounter;

	public Player() {
		this.x = -40;
		this.y = ConfigurableOption.northScreenHeight;
		this.destroyed = false;
		this.visible = true;
		this.deadCounter = 0;
		this.threadCounter = 0;
		this.destroyed = false;
		this.destroying = false;
		
		animationWalking = Resource.get("batman-walking");
		animationStanding = Resource.get("batman-standing");
		setWalking(true);

		this.charHeight = ConfigurableOption.characterHeight;
		this.charWidth = animation.getCharWidthByHeight(this.charHeight);
		
		creatThread();
	}

	public void setWalking(boolean walking){
		if(!(this.walking ^ walking)) return;
		this.walking = walking;
		if(walking){
			animation = animationWalking;
		}else{
			animation = animationStanding;
		}
		animation.loop();
	}

	public void update() {
		if(ConfigurableOption.stageNow == 0 && x < ConfigurableOption.xGateway1-35){
			this.x+=2;
			setWalking(true);
		}else if(ConfigurableOption.stageNow == 1 && x < ConfigurableOption.xGateway1+30){
			this.x+=2;
			setWalking(true);
		}else if(ConfigurableOption.stageNow == 2 && x <ConfigurableOption.xGateway2 -35){
			this.x+=2;
			setWalking(true);
		}else if(ConfigurableOption.stageNow == 3 && x < ConfigurableOption.xGateway2 + 70){
			this.x+=2;
			setWalking(true);
		}else if(ConfigurableOption.stageNow >= ConfigurableOption.ENDSTAGE && x < ConfigurableOption.screenWidth + charWidth){
			this.x+=2;
			setWalking(true);
		}else if(ConfigurableOption.stageNow >=  ConfigurableOption.ENDSTAGE){
			ScreenManager.changeScreen(ScreenManager.WINNINGSCREEN);
		}else{
			setWalking(false);
		}
	}
	
	public boolean collideWith(Zombie zombie){
		return Math.hypot(this.x-zombie.x, this.y-zombie.y) < this.charWidth/2 + zombie.getCharWidth()/2;
	}
	
	public boolean isDestroyed(){
		return this.destroyed;
	}

	@Override
	public int getZ() {
		return 100;
	}

	@Override
	public synchronized void draw(Graphics2D g2d) {
		if(!destroying){
			RenderAnimationHelper.draw(g2d, animation, x, y,0, charHeight);
		}else{
			if(deadCounter == 0){
				ScreenManager.bgm.stop();
				Resource.getAudio("dead").play();
				deadCounter = 150;
			}
			
			if(deadCounter%25<12)
				RenderAnimationHelper.draw(g2d, animation, x, y,0, charHeight);
			
			if(--deadCounter == 0){
				destroyed = true;
			}
		}
	}
	
	public void zombieIsComming(){
		threadCounterReset();
		synchronized (playerLocker) {
			playerLocker.notifyAll();
		}
	}
	
	
	public int getThreadCounter() {
		synchronized (playerLocker) {
			return threadCounter;
		}
	}

    public void threadCounterIncrement() {
		synchronized (playerLocker) {
			threadCounter++;
		}
    }

    public void threadCounterDecrement() {
		synchronized (playerLocker) {
			threadCounter--;
		}
    }
    public void threadCounterReset() {
		synchronized (playerLocker) {
	    	threadCounter = 0;
		}
    }

	public void creatThread(){
		new Thread(new Runnable() {
			public void run() {
				long startTime = System.currentTimeMillis();
				long endTime = System.currentTimeMillis();
				AudioClip zombie = Resource.getAudio("zombie");
				synchronized (playerLocker) {
					try {
						playerLocker.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				while(true){
					try {
						Thread.sleep(utility.ConfigurableOption.sleepTime);
					} catch (InterruptedException e) {}

					endTime = System.currentTimeMillis();
					if(getThreadCounter()==1){
						if(endTime - startTime > 4000){
							zombie.play();
							startTime = System.currentTimeMillis();
						}	
					}else if(getThreadCounter()==TimeToCounter.getCounter(75)){
						animation.flip(AnimationManager.FlipToUnUsual);
					}else if(getThreadCounter()==TimeToCounter.getCounter(500)){
						animation.flip(AnimationManager.FlipToUsual);
						synchronized (playerLocker) {
							try {
								playerLocker.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					threadCounterIncrement();
				}
			}
		}).start();
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	@Override
	public void setDestroying(boolean destroyed) {
		this.destroying = destroyed;
	}

	public boolean isDestroying() {
		return this.destroying;
	}

	@Override
	public void updateAnimation() {
			animation.update();
	}
}
