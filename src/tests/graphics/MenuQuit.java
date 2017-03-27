package tests.graphics;

import static org.junit.Assert.*;
import main.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import main.Constants;

public class MenuQuit {
	
	public Menu menu;
	public Window window;
	public GameState gamestate;
	public Main client;
	public Screen screen;

	
	@Before
	public void setUp(){
		window = new Window(800, 800);

	}

	@After
	public void tearDown() throws Exception {
		window.quit();
		
	}

	@Test
	public void BeginningMainMenu() {
		assertSame(Screen.MAIN_MENU, window.getScreen());
	}
	@Test
	public void GoneToGame()
	{
		window.setScreen(screen.GAME);
		assertSame(Screen.GAME, window.getScreen());
	}
	
	@Test
	public void GoneToSettings()
	{
		window.setScreen(screen.SETTINGS);
		assertSame(Screen.SETTINGS, window.getScreen());
	}
	
	@Test
	public void GoneToNotMenu()
	{
		window.setScreen(screen.GAME);
		assertNotEquals(Screen.MAIN_MENU, window.getScreen());
	}
	
	@Test
	public void GoneToLoading()
	{
		window.setScreen(Screen.LOADING);
		assertSame(Screen.LOADING, window.getScreen());
	}
	
	@Test
	public void Constants()
	{
		double[] vertices = new double[]{0,0,0,0,0,0,0,0,0,0,0,0};
		main.Rectangle reckie = new main.Rectangle();
	}

}
