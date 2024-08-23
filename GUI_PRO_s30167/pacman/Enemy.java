package pacman;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    private int x, y;
    private String color;
    private Image enemyImage;
    private static final int ENEMY_SIZE = 30;
    private boolean visible;
    private int speed; // Add a speed attribute

    public Enemy(int startX, int startY, String color) {
        this.x = startX;
        this.y = startY;
        this.color = color;
        this.visible = true; // Enemies are visible by default
        this.enemyImage = loadImage(color);
        this.speed = 5; // Set the speed of the enemy equal to the player's speed
    }

    private Image loadImage(String color) {
        switch (color) {
            case "red":
                return new ImageIcon(new ImageIcon("images/enemy_red.png").getImage().getScaledInstance(ENEMY_SIZE, ENEMY_SIZE, Image.SCALE_SMOOTH)).getImage();
            case "yellow":
                return new ImageIcon(new ImageIcon("images/enemy_yellow.png").getImage().getScaledInstance(ENEMY_SIZE, ENEMY_SIZE, Image.SCALE_SMOOTH)).getImage();
            default:
                return null;
        }
    }

    public void move(int playerX, int playerY) {
        if (x < playerX) {
            x += speed; // Move by speed units
        } else if (x > playerX) {
            x -= speed;
        }
        if (y < playerY) {
            y += speed;
        } else if (y > playerY) {
            y -= speed;
        }
    }

    public void draw(Graphics g) {
        if (visible) {
            g.drawImage(enemyImage, x, y, null);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }
}
