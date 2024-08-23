package pacman;

import javax.swing.*;
import java.awt.*;

public class Player {
    private int x, y;
    private int speed;
    private Image currentImage;
    private Image[] playerImages;
    private static final int PLAYER_SIZE = 30; // Adjust this size as needed
    private int animationCounter;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.speed = 2; // Adjust speed as needed
        this.playerImages = loadImages();
        this.currentImage = playerImages[0];
        this.animationCounter = 0;
    }

    private Image[] loadImages() {
        return new Image[]{
            new ImageIcon(new ImageIcon("images/player_right_closed.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage(),
            new ImageIcon(new ImageIcon("images/player_right_open.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage(),
            new ImageIcon(new ImageIcon("images/player_left_closed.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage(),
            new ImageIcon(new ImageIcon("images/player_left_open.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage(),
            new ImageIcon(new ImageIcon("images/player_up_closed.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage(),
            new ImageIcon(new ImageIcon("images/player_up_open.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage(),
            new ImageIcon(new ImageIcon("images/player_down_closed.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage(),
            new ImageIcon(new ImageIcon("images/player_down_open.png").getImage().getScaledInstance(PLAYER_SIZE, PLAYER_SIZE, Image.SCALE_SMOOTH)).getImage()
        };
    }

    public void move(int dx, int dy) {
        x += dx * speed;
        y += dy * speed;
        animationCounter++;
        
        if (dx > 0) {
            currentImage = playerImages[(animationCounter % 2 == 0) ? 0 : 1]; // right
        } else if (dx < 0) {
            currentImage = playerImages[(animationCounter % 2 == 0) ? 2 : 3]; // left
        } else if (dy > 0) {
            currentImage = playerImages[(animationCounter % 2 == 0) ? 6 : 7]; // down
        } else if (dy < 0) {
            currentImage = playerImages[(animationCounter % 2 == 0) ? 4 : 5]; // up
        }
    }

    public void draw(Graphics g) {
        g.drawImage(currentImage, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
