package tests;

import static org.junit.Assert.*;
import main.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class MenuQuit {
	
	public Menu menu;
	public Window window;
	public GameState gamestate;
	public Main client;
	public Screen screen = Screen.MAIN_MENU;

	
	@Before
	public void setUp(){
		//menu = mock(Menu.class);
		window = mock(Window.class);
		when(window.getScreen()).thenReturn(Screen.MAIN_MENU);
		//gamestate = mock(GameState.class);
		//client = mock(Main.class);
		//screen = Screen.MAIN_MENU;
	}

	@After
	public void tearDown() throws Exception {
		window.quit();
		
	}

	@Test
	public void test() {
		//-0.2 -0.8
		//window.handleMouseClick(gamestate, client, -0.2, -0.8);
		//verify(window, times(1)).quit();
		//startGame(OpponentType.HUMAN)
		assertSame(Screen.MAIN_MENU, window.getScreen());
	}

}
