public class Main {

    private static final int windowHeight = 800;
    private static final int windowWidth = 800;


    private void play() {
        Window window = new Window(windowHeight, windowWidth);
        GameState game = new GameState(window);

        game.setUp();
        window.init(game);

        Menu.drawAll();
        while (!window.shouldClose()) {
            window.handleInput(game);
            game.updateLogic();
            game.updatePhysics();
            window.repaint(game);
        }
        window.end();
    }


    public static void main(String[] args) {
        Main main = new Main();
        main.play();
    }
}
