package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Pacman Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel titleLabel = new JLabel("Pacman Game", SwingConstants.CENTER);
        panel.add(titleLabel);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            String[] options = {"Small", "Medium", "Large"};
            String size = (String) JOptionPane.showInputDialog(this, "Select board size:", "Board Size",
                    JOptionPane.PLAIN_MESSAGE, null, options, "Small");
            if (size != null) {
                new GameBoard(size);
                dispose();
            }
        });
        panel.add(startButton);

        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.addActionListener(e -> {
            // Display high scores
            String playerName = JOptionPane.showInputDialog(this, "Enter your name to view high scores:");
            if (playerName != null && !playerName.trim().isEmpty()) {
                List<String> highScores = HighScores.loadHighScores(playerName + "_highscores.txt");
                if (highScores.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No high scores found for " + playerName, "High Scores", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, String.join("\n", highScores), "High Scores", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        panel.add(highScoresButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
