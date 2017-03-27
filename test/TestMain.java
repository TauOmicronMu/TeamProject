import graphics.MenuQuit;
import graphics.TestCircle;
import graphics.TestPair;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   TestCircle.class,
   MenuQuit.class,
   TestPair.class
})

public class TestMain {
}

