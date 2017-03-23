package unused;

/**
 * Created by Pintilii on 17/03/2017.
 */
public class GameModes {

    private int platformsNumberEasyMode;
    private int platformsNumberNormalMode;
    private int platformsNumberHardMode;
    private double platformsSpeedEasyMode;
    private double platformsSpeedNormalMode;
    private double platformsSpeedHardMode;

    GameModes() {
        this.platformsNumberEasyMode = 30;
        this.platformsNumberNormalMode = 25;
        this.platformsNumberHardMode = 15;
        this.platformsSpeedEasyMode = 2;
        this.platformsSpeedNormalMode = 3;
        this.platformsSpeedHardMode = 4;
    }

    public int getEasyModeNrPlatforms(){
        return this.platformsNumberEasyMode;
    }
    public int getNormalModeNrPlatforms(){
        return this.platformsNumberNormalMode;
    }
    public int getHardModeNrPlatforms(){
        return this.platformsNumberHardMode;
    }
    public double getPlatformsSpeedEasyMode() {
        return this.platformsSpeedEasyMode;
    }
    public double getPlatformsSpeedNormalMode() {
        return this.platformsSpeedEasyMode;
    }
    public double getPlatformsSpeedHardMode() {
        return this.platformsSpeedEasyMode;
    }

}
