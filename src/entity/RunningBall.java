package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import render.IRenderable;
import render.RenderableHolder;
import utility.TimeToCounter;
import utility.ConfigurableOption;
import utility.RandomUtility;

public class RunningBall implements IRenderable{
	protected int x, y;
	protected int xTab, yTab;
	protected int diameter;
	protected int tabDistance;
	protected int direction;
	protected boolean destroyed;
	private int shuffleDirectionDelay;
	
	public RunningBall(int x, int y, int tabDistance){
		this.diameter = ConfigurableOption.spacebarTabHeight;
		this.x = this.xTab = x;
		this.y = this.yTab = y;
		this.tabDistance = tabDistance;
		this.direction = 1;
		this.destroyed = false;
		this.shuffleDirectionDelay = 0;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.ORANGE);
		g2d.fillOval(x, y, diameter, diameter);
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void update() {
		if(destroyed){
			RenderableHolder.getInstance().getSouthRenderableList().remove(this);
		}
		
		if(x>=ConfigurableOption.tabDistance+ConfigurableOption.xSpacebarTab-20){
			this.direction = -1;
		}else if (x<=ConfigurableOption.xSpacebarTab){
			this.direction = 1;
		}else if(ConfigurableOption.stageNow == 2 && shuffleDirectionDelay >= TimeToCounter.getCounter(300)){
			shuffleDirectionDelay = 0;
			if(RandomUtility.random(1, 2)==1){
				this.direction = -1;
			}else{
				this.direction = 1;
			}
		}

		this.x += 3*this.direction;
		shuffleDirectionDelay++;
	}
	public int getDiameter(){
		return diameter;
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
	public int getZ() {
		return 10;
	}

	@Override
	public boolean isDestroyed() {
		return destroyed;
	}

	@Override
	public void setDestroying(boolean destroyed) {
		this.destroyed = destroyed;
	}

	@Override
	public void updateAnimation() {
		// TODO Auto-generated method stub
		
	}


}
