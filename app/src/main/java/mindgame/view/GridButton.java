package mindgame.view;

import java.awt.*;
import javax.swing.*;

public class GridButton extends JButton {
    private int row;
    private int col;
    private int number;
    protected boolean matched;

    public GridButton(int row, int col, int number) {
        this.row = row;
        this.col = col;
        this.number = number;
        this.matched = false;

        setFont(new Font("Arial", Font.BOLD, 18)); // Set font to bold and adjust font size
        setForeground(Color.BLACK); // Set the text color to black
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNumber() {
        return number;
    }

    public void revealNumber() {
        setText(String.valueOf(number));
    }

    public void hideNumber() {
        setText("");
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void setNumber(int number) {
        this.number = number;
        setText(String.valueOf(number));
    }
}
