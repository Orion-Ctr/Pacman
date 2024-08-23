package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameBoard extends JFrame implements KeyListener {
    private List<Player> players;
    private List<Enemy> enemies;
    private List<Upgrade> upgrades;
    private List<Dot> dots;
    private JPanel gamePanel;
    private JScrollPane scrollPane;
    private int score;
    private int lives;
    private int time;
    private Timer gameTimer;
    private Random random;
    private Dimension boardSize;

    public GameBoard(String boardSize) {
        this.players = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.upgrades = new ArrayList<>();
        this.dots = new ArrayList<>();
        this.score = 0;
        this.lives = 3;
        this.time = 0;
        this.random = new Random();

        setTitle("Pacman Game Board - " + boardSize);
        setBoardSize(boardSize);
        setSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gamePanel = new AnimationPanel();
        gamePanel.setPreferredSize(this.boardSize);

        scrollPane = new JScrollPane(gamePanel);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        add(scrollPane);

        players.add(new Player(50, 50));
        enemies.add(new Enemy(100, 100, "red"));
        enemies.add(new Enemy(200, 200, "yellow"));

        populateDots();

        addKeyListener(this);
        setFocusable(true);

        gameTimer = new Timer(1000, e -> updateGame());
        gameTimer.start();

        pack();
        setVisible(true);
    }

    private void setBoardSize(String size) {
        switch (size) {
            case "Small":
                this.boardSize = new Dimension(800, 600);
                break;
            case "Medium":
                this.boardSize = new Dimension(1200, 800);
                break;
            case "Large":
                this.boardSize = new Dimension(1600, 1200);
                break;
            default:
                this.boardSize = new Dimension(800, 600);
                break;
        }
    }

    private void populateDots() {
        int smallDotCount = 0;
        int bigDotCount = 0;

        switch (this.boardSize.width) {
            case 800: // Small board
                smallDotCount = 100;
                bigDotCount = 10;
                break;
            case 1200: // Medium board
                smallDotCount = 250;
                bigDotCount = 25;
                break;
            case 1600: // Large board
                smallDotCount = 400;
                bigDotCount = 40;
                break;
        }

        for (int i = 0; i < smallDotCount; i++) {
            dots.add(new Dot(random.nextInt(this.boardSize.width), random.nextInt(this.boardSize.height), 1));
        }
        for (int i = 0; i < bigDotCount; i++) {
            dots.add(new Dot(random.nextInt(this.boardSize.width), random.nextInt(this.boardSize.height), 3));
        }
    }

    private void updateGame() {
        time++;
        if (random.nextInt(10) == 0 && upgrades.size() < 1) {
            String upgradeType = (random.nextInt(2) == 0) ? "Speed" : "Visibility";
            upgrades.add(new Upgrade(random.nextInt(this.boardSize.width), random.nextInt(this.boardSize.height), upgradeType));
        }

        for (Enemy enemy : enemies) {
            enemy.move(players.get(0).getX(), players.get(0).getY());
        }

        checkCollisions();
        gamePanel.repaint();
    }

    private void checkCollisions() {
        Player player = players.get(0);
        Rectangle playerBounds = new Rectangle(player.getX(), player.getY(), 30, 30);

        for (int i = 0; i < upgrades.size(); i++) {
            Upgrade upgrade = upgrades.get(i);
            Rectangle upgradeBounds = new Rectangle(upgrade.getX(), upgrade.getY(), 30, 30);
            if (playerBounds.intersects(upgradeBounds)) {
                applyUpgrade(upgrade.getType());
                upgrades.remove(i);
                i--;
            }
        }

        for (int i = 0; i < dots.size(); i++) {
            Dot dot = dots.get(i);
            int dotSize = dot.getValue() == 1 ? 5 : 10;
            Rectangle dotBounds = new Rectangle(dot.getX(), dot.getY(), dotSize, dotSize);
            if (playerBounds.intersects(dotBounds)) {
                score += dot.getValue();
                dots.remove(i);
                i--;
            }
        }

        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            Rectangle enemyBounds = new Rectangle(enemy.getX(), enemy.getY(), 30, 30);
            if (playerBounds.intersects(enemyBounds)) {
                lives--;
                if (lives <= 0) {
                    gameOver();
                }
            }
        }
    }

    private void applyUpgrade(String type) {
        if (type.equals("Speed")) {
            players.get(0).move(2, 2);
        } else if (type.equals("Visibility")) {
            for (Enemy enemy : enemies) {
                enemy.setVisible(false);
            }
            new Timer(5000, e -> {
                for (Enemy enemy : enemies) {
                    enemy.setVisible(true);
                }
            }).start();
        }
        score += 10;
    }

    private void gameOver() {
        gameTimer.stop();
        String playerName = JOptionPane.showInputDialog(this, "Game Over! Enter your name for the high scores:");
        if (playerName != null && !playerName.trim().isEmpty()) {
            List<String> highScores = HighScores.loadHighScores(playerName + "_highscores.txt");
            highScores.add(playerName + " - " + score);
            HighScores.saveHighScores(highScores, playerName + "_highscores.txt");
        }
        new MainMenu();
        dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player player = players.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (player.getY() > 0) player.move(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                if (player.getY() < boardSize.height - 30) player.move(0, 1);
                break;
            case KeyEvent.VK_LEFT:
                if (player.getX() > 0) player.move(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                if (player.getX() < boardSize.width - 30) player.move(1, 0);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    class AnimationPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);
            for (Player player : players) {
                player.draw(g);
            }
            for (Enemy enemy : enemies) {
                enemy.draw(g);
            }
            for (Upgrade upgrade : upgrades) {
                upgrade.draw(g);
            }
            for (Dot dot : dots) {
                dot.draw(g);
            }

            g.setColor(Color.WHITE);
            g.drawString("Score: " + score, 10, 10);
            g.drawString("Lives: " + lives, 10, 30);
            g.drawString("Time: " + time, 10, 50);
        }
    }
}
