package mindgame.view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import mindgame.GameObserver;
import mindgame.controller.LevelController;
import mindgame.controller.MemoryController;
import mindgame.model.MemoryModel;

public class GameGUI implements GameObserver {

    private JFrame frame;
    private JPanel mainpanel;
    private JPanel gridpanel;
    private JPanel scorepanel;
    private MemoryModel model;
    private MemoryController controller;
    private GridButton[][] gridButtons;
    private JLabel score, wrongAttempts, highScore;
    private JButton startButton;
    private JLabel titleLabel;
    private JLabel completedGrids;

    public GameGUI(MemoryController controller, MemoryModel model) {
        this.controller = controller;
        this.model = model;
        this.model.register(this);
        this.gridButtons = new GridButton[model.getRows()][model.getCols()];
        this.frame = new JFrame("2D Memory Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainpanel = new JPanel(new BorderLayout());

        this.gridpanel = new JPanel();
        gridpanel.setBackground(new Color(135, 206, 235));
        gridpanel.setLayout(new GridLayout(model.getRows(), model.getCols(), 10, 10));
        gridpanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50)); // Remove top margin

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 0, 50)); // Remove top and bottom margins
        titlePanel.setBackground(new Color(135, 206, 235));

        titleLabel = new JLabel(model.getLevel());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set font to bold and change font size
        titlePanel.add(titleLabel, BorderLayout.NORTH);

        this.completedGrids = new JLabel("Successfully Completed Grids: " + model.successfulGrids());
        completedGrids.setHorizontalAlignment(SwingConstants.CENTER);
        completedGrids.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel titleTextPanel = new JPanel(new GridLayout(2, 1));
        titleTextPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50)); // Adjust top margin
        titleTextPanel.setBackground(new Color(135, 206, 235));
        titleTextPanel.add(completedGrids);

        JLabel maxWrongGuessesLabel = new JLabel("Max Wrong Guesses Allowed: " + model.getMaxWrongAttempts());
        maxWrongGuessesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        maxWrongGuessesLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Modify font size or style as needed
        titleTextPanel.add(maxWrongGuessesLabel);
        titlePanel.add(titleTextPanel, BorderLayout.SOUTH);

        this.startButton = new JButton("START GAME");
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                // Show numbers on the grid
                showNumbersOnGrid();
                showTimer();
            }
        });

        JPanel startButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startButtonPanel.add(startButton);
        startButtonPanel.setBackground(new Color(135, 206, 235));
        startButtonPanel.setPreferredSize(new Dimension(150, 60)); // Adjust the width and height as needed

        titlePanel.add(startButtonPanel, BorderLayout.CENTER);

        this.scorepanel = new JPanel(new GridLayout(2, 2, 10, 10));
        scorepanel.setBackground(new Color(135, 206, 235));
        scorepanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 50));
        this.wrongAttempts = new JLabel("Wrong Attempts: 0");
        wrongAttempts.setFont(new Font("Arial", Font.BOLD, 16));
        this.score = new JLabel("Score: 0");
        score.setFont(new Font("Arial", Font.BOLD, 16));
        JButton restart = new JButton("Restart");
        highScore = new JLabel("High Score :" + model.getHighScore());
        highScore.setFont(new Font("Arial", Font.BOLD, 16));

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame, "Do you want to restart the game?", "Restart Game",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_NO_OPTION) {
                    if (frame != null) {
                        frame.dispose();
                    }
                    new LevelController();
                }
            }
        });

        scorepanel.add(wrongAttempts);
        scorepanel.add(score);
        scorepanel.add(highScore);
        scorepanel.add(restart);

        int index = 0;
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                int gridNumber = model.getGridNumber(i, j);
                GridButton gridbutton = new GridButton(i, j, gridNumber);
                gridbutton.setFocusable(false);
                gridbutton.setPreferredSize(new Dimension(100, 100));

                int number = gridNumber;
                gridbutton.setText(String.valueOf(number));

                gridbutton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(gridbutton);
                    }
                });
                gridButtons[i][j] = gridbutton;
                gridpanel.add(gridbutton);

                index++;
                if (index == model.getLen()) {
                    break;
                }
            }

        }
        hideNumbersOnGrid();
        disableAllButtons();

        mainpanel.add(titlePanel, BorderLayout.NORTH);
        mainpanel.add(gridpanel, BorderLayout.CENTER); // Adjust the component placement
        mainpanel.add(scorepanel, BorderLayout.SOUTH);

        frame.add(mainpanel);
        frame.pack();
        frame.setVisible(true);
    }

    private void handleButtonClick(GridButton clickedButton) {
        this.controller.userClicked(clickedButton.getRow(), clickedButton.getCol());
    }

    private void showNumbersOnGrid() {
        for (Component component : gridpanel.getComponents()) {
            if (component instanceof GridButton) {
                ((GridButton) component).revealNumber();
            }
        }
    }

    private void hideNumbersOnGrid() {
        for (Component component : gridpanel.getComponents()) {
            if (component instanceof GridButton) {
                ((GridButton) component).hideNumber();
            }
        }
    }

    private void disableAllButtons() {
        for (Component component : gridpanel.getComponents()) {
            if (component instanceof GridButton) {
                ((GridButton) component).setEnabled(false);
            }
        }
    }

    private void enableAllButtons() {
        for (Component component : gridpanel.getComponents()) {
            if (component instanceof GridButton) {
                ((GridButton) component).setEnabled(true);
            }
        }
    }

    @Override
    public void update() {
        int index = 0;
        if (model.isGameOver()) {
            titleLabel.setText(model.getLevel() + " : GAME OVER");
            disableAllButtons();
            int currentScore = Integer.parseInt(model.getScore());
            int currentHighScore = Integer.parseInt(model.getHighScore());

            // Compare the current score with the high score
            if (currentScore < currentHighScore) {
                // Show a dialog box with the current score
                JOptionPane.showMessageDialog(frame, "Your Score is: " + currentScore, "GAME OVER",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Update the high score in the model and show a dialog box for new high score
                model.updateHighScore();
                JOptionPane.showMessageDialog(frame, "New High Score: " + currentScore, "GAME OVER",
                        JOptionPane.INFORMATION_MESSAGE);
                highScore.setText("High Score: " + model.getHighScore()); // Update the displayed high score
            }
        }

        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                if (model.isOpened(i, j)) {
                    gridButtons[i][j].revealNumber();
                    gridButtons[i][j].setEnabled(false);

                }
                index++;
                if (index == model.getLen()) {
                    break;
                }
            }
        }
        wrongAttempts.setText("Wrong Attempts: " + model.getWrongAttempts());
        score.setText("Score: " + model.getScore());
        highScore.setText("High Score :" + model.getHighScore());

        if (model.newGrid() == model.getLen()) {
            model.generateNewGrid();
            completedGrids.setText("Successfully Completed Grids: " + model.successfulGrids());
            int index1 = 0;
            for (int i = 0; i < model.getRows(); i++) {
                for (int j = 0; j < model.getCols(); j++) {
                    int newGridNumber = model.getGridNumber(i, j);
                    gridButtons[i][j].setNumber(newGridNumber);

                    index1++;
                    if (index1 == model.getLen()) {
                        break;
                    }

                }
            }
            showNumbersOnGrid();

            showTimer();

        }

    }

    public void showTimer() {
        Timer showTimer = new Timer(1000 * model.getOpenTime(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hideNumbersOnGrid();
                enableAllButtons();
            }
        });
        showTimer.setRepeats(false);
        showTimer.start();

    }

}
