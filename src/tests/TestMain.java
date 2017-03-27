package tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tests.graphics.Circle;
import tests.graphics.MenuQuit;
import tests.graphics.Pair;

	@RunWith(Suite.class)

	@Suite.SuiteClasses({
	   Circle.class,
	   MenuQuit.class,
	   Pair.class
	})

	public class TestMain {   
	}  	

