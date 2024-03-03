package mindgame.controller;

import mindgame.ControllerInterface;
import mindgame.model.MemoryModel;
import mindgame.view.GameGUI;

public class MemoryController implements ControllerInterface {
    protected MemoryModel model;
    protected GameGUI medium;

    public MemoryController(MemoryModel model) {
        this.model = model;
        this.medium = new GameGUI(this, model);
    }

    public void userClicked(int row, int col) {
        model.makeGuess(row, col);
        model.notifyObservers();
    }

    public void restartGame() {
        model.setSuccesfulGrids();
        model.generateNewGrid();
        model.setWrongAttempts();
        model.setScore();

    }

}
