package pacman;

import javax.swing.*;
import java.awt.*;

public class Upgrade {
    private int x, y;
    private String type;
    private Image upgradeImage;
    private static final int UPGRADE_SIZE = 30;

    public Upgrade(int startX, int startY, String type) {
        this.x = startX;
        this.y = startY;
        this.type = type;
        this.upgradeImage = loadImage(type);
    }

    private Image loadImage(String type) {
        switch (type) {
            case "Speed":
                return new ImageIcon(new ImageIcon("images/upgrade_speed.jpg").getImage().getScaledInstance(UPGRADE_SIZE, UPGRADE_SIZE, Image.SCALE_SMOOTH)).getImage();
            case "Visibility":
                return new ImageIcon(new ImageIcon("images/upgrade_visibility.png").getImage().getScaledInstance(UPGRADE_SIZE, UPGRADE_SIZE, Image.SCALE_SMOOTH)).getImage();
            default:
                return null;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(upgradeImage, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getType() {
        return type;
    }
}

