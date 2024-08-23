package pacman;

import javax.swing.*;
import java.awt.*;

public class Dot {
    private int x, y;
    private int value;
    private Image dotImage;
    private static final int SMALL_DOT_SIZE = 5; // Size for small dots
    private static final int BIG_DOT_SIZE = 10; // Size for big dots

    public Dot(int startX, int startY, int value) {
        this.x = startX;
        this.y = startY;
        this.value = value;
        this.dotImage = loadImage(value);
    }

    private Image loadImage(int value) {
        switch (value) {
            case 1:
                return new ImageIcon(new ImageIcon("images/dot_small.jpg").getImage().getScaledInstance(SMALL_DOT_SIZE,
                        SMALL_DOT_SIZE, Image.SCALE_SMOOTH)).getImage();
            case 3:
                return new ImageIcon(new ImageIcon("images/dot_big.jpg").getImage().getScaledInstance(BIG_DOT_SIZE,
                        BIG_DOT_SIZE, Image.SCALE_SMOOTH)).getImage();
            default:
                return null;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(dotImage, x, y, null);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }
}
