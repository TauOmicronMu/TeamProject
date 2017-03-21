package main;

/**
 * Created by Pintilii on 17/03/2017.
 */
public class GameModes {

    private static final int platformsNumberEasyMode = 20;
    private static final int platformsNumberNormalMode = 22;
    private static final int platformsNumberHardMode = 15;
    private static final double platformsSpeedEasyMode = Constants.PLATFORM_START_DY - 3;
    private static final double platformsSpeedNormalMode = Constants.PLATFORM_START_DY;
    private static final double platformsSpeedHardMode = Constants.PLATFORM_START_DY + 10;
    private int identity;
    private static final int platformsWidthEasyMode = 140;
    private static final int platformsWidthNormalMode = 120;
    private static final int platformsWidthHardMode = 100;
    private static final int itemsNumberEasyMode = 6;
    private static final int itemsNumberNormalMode = 5;
    private static final int itemsNumberHardMode = 3;


    public static int getNrPlatforms(int mode){
        switch(mode){
            case 1: return platformsNumberEasyMode;
            case 2: return platformsNumberNormalMode;
            default: return platformsNumberHardMode;
        }
    }
    public static double getPlatformsSpeed(int mode) {
        switch(mode){
            case 1: return platformsSpeedEasyMode;
            case 2: return platformsSpeedNormalMode;
            default: return platformsSpeedHardMode;
        }
    }
    public static int getPlatformsWidth(int mode) {
        switch(mode){
            case 1: return platformsWidthEasyMode;
            case 2: return platformsWidthNormalMode;
            default: return platformsWidthHardMode;
        }
    }
    public static int getNrItems(int mode){
        switch(mode){
            case 1: return itemsNumberEasyMode;
            case 2: return itemsNumberNormalMode;
            default: return itemsNumberHardMode;
        }
    }
}
