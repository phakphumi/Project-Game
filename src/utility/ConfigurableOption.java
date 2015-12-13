package utility;


public class ConfigurableOption {
	public static final boolean debugGraphic = true;
	
	public static final int sleepTime = 15;
	public static final int northScreenHeight = 200;//200
	public static final int southScreenHeight = 360;//360
	public static final int screenWidth = 720;//720
	public static final int screenHeight = northScreenHeight+southScreenHeight;
	public static final int statusHeight = 40;

	public static boolean PAUSE = false;
	
	public static int xGateway1 = 260;
	public static int yGateway1 = 0;
	public static int xGateway2 = 450;
	public static int yGateway2 = 0;
	
	public static int xSpacebarTab = 3*screenWidth/10;
	public static int ySpacebarTab = 30;
	public static int spacebarTabHeight = 20;
	public static int gapWidth = 30;
	public static int tabDistance = 4*screenWidth/10;
	
	public static int wireFrameWidth = 300;
	public static int wireFrameHeight = 100;
	
	public static int stageNow = 0;
	public static int seedCoin = 0;
	public static int coinLimit = 5;
	public static int coinCounter = 0;
}
