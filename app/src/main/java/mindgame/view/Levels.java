package mindgame.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import mindgame.controller.LevelController;

public class Levels {
    protected LevelController controller;

    public Levels(LevelController controller) {
        this.controller = controller;
        JFrame frame = new JFrame("Memory Game 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(135, 206, 235));
        panel.setLayout(new GridLayout(4, 1, 20, 20));
        panel.setPreferredSize(new Dimension(400, 400));
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        JLabel label = new JLabel("Choose the Level");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JButton button1 = new JButton("Easy Mode");
        button1.setFocusable(false);
        button1.setBackground(Color.ORANGE);
        button1.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        controller.levelSelected(3, 3, button1.getText());
                    }
                });

        JButton button2 = new JButton("Medium Mode");
        button2.setFocusable(false);
        button2.setBackground(Color.ORANGE);
        button2.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        controller.levelSelected(4, 4, button2.getText());
                    }
                });

        JButton button3 = new JButton("Hard Mode");
        button3.setFocusable(false);
        button3.setBackground(Color.ORANGE);
        button3.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        controller.levelSelected(5, 5, button3.getText());
                    }
                });

        panel.add(label);
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

}
