package BrickBreaker;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame gameFrame = new JFrame();
        Game game = new Game();
        gameFrame.setBounds(10,10,700,600);
        gameFrame.setTitle("Brick Breaker");
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(game);
    }
}
