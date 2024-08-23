package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame {
    private JComboBox<String> boardSizeSelector;

    public Game() {
        setTitle("Pacman Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] boardSizes = { "Small", "Medium", "Large", "Extra Large", "Huge" };
        boardSizeSelector = new JComboBox<>(boardSizes);

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSize = (String) boardSizeSelector.getSelectedItem();
                new GameBoard(selectedSize);
                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Select Board Size:"));
        panel.add(boardSizeSelector);
        panel.add(startGameButton);

        add(panel);
        setVisible(true);
    }
}
