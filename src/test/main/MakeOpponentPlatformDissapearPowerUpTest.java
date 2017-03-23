package main;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MakeOpponentPlatformDissapearPowerUpTest {



    GameState gameState;
    @Before
    public void setUp() throws Exception {
        gameState = new GameState(1600,800);
    }
    @Test
    public void performAction() throws Exception {
        if(AudioEngine.isClient)return;
    }

}