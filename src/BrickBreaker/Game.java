package BrickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int startingScore =0;
    private int totalBricks = 21;
    private Timer timer;
    private int delay = 10;
    private int playerX = 310;
    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator mapGenerator;

    public Game(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        mapGenerator = new MapGenerator(3,7);
        timer.start();

    }

    public void paint(Graphics graphics) {
        // background

        graphics.setColor(Color.BLACK);
        graphics.fillRect(1,1,700,592);

        // borders

        graphics.setColor(Color.YELLOW);
        graphics.fillRect(0,0,3, 592);
        graphics.fillRect(0,0,698, 3);
        graphics.fillRect(697,0,3, 592);

        //scores
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString(""+startingScore, 590,30);

        // paddle
        graphics.setColor(Color.GREEN);
        graphics.fillRect(playerX,550,100,8);


        //drawingmap
        mapGenerator.draw((Graphics2D) graphics);

        // ball
        graphics.setColor(Color.ORANGE);
        graphics.fillOval(ballPositionX,ballPositionY,20,20);

        if (totalBricks == 0){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("You Won!! Scores are : "+startingScore, 150,250);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to restart: ", 200,350);
        }

        if (ballPositionY > 570){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            graphics.setColor(Color.RED);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("Game OVER, Scores are : "+startingScore, 100,250);

            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter to restart: ", 200,350);
        }



        graphics.dispose();
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        moveBall();
        brick();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            if (playerX >= 600) {
                playerX = 600;
            }else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            if (playerX <=10) {
                playerX = 10;
            }else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if (!play){
                play=true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballXdir = -1;
                ballYdir = -2;
                playerX = 310;
                startingScore =0;
                totalBricks = 21;
                mapGenerator = new MapGenerator(3,7);
                repaint();

            }
        }

    }

    public void moveRight() {
        play = true;
        playerX+= 20;
    }

    public void moveLeft() {
        play = true;
        playerX-= 20;
    }

    private void moveBall(){
        if (play){
            detectIntersectionOfBallAndPaddle();
            ballPositionX+= ballXdir;
            ballPositionY += ballYdir;
            if (ballPositionX<0){
                ballXdir = -ballXdir;
            }
            if (ballPositionY<0){
                ballYdir = -ballYdir;
            }
            if (ballPositionX>670){
                ballXdir = -ballXdir;
            }
        }
    }

    private void detectIntersectionOfBallAndPaddle() {
        if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX,550,100,8))){
            ballYdir = -ballYdir;
        }
    }

    private void brick(){
       A: for (int i=0; i <mapGenerator.map.length; i++){
            for (int j=0;j<mapGenerator.map[0].length; j++){
                if (mapGenerator.map[i][j]>0){
                    int brickX = j*mapGenerator.brickWidth + 80;
                    int brickY = i*mapGenerator.brickHeight + 50;
                    int brickWidth = mapGenerator.brickWidth;
                    int brickHeight = mapGenerator.brickHeight;

                    Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY,20,20);
                    Rectangle brickRect = rect;

                    if (ballRect.intersects(brickRect)){
                        mapGenerator.setBrickValue(0,i,j);
                        totalBricks--;
                        startingScore+= 5;

                        if (ballPositionX + 19 <= brickRect.x || ballPositionX +1 >= brickRect.x+brickRect.width){
                            ballXdir = -ballXdir;
                        }else {
                            ballYdir = -ballYdir;
                        }
                        break A;
                    }
                }
            }
        }
    }

}
