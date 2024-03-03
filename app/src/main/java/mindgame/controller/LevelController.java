package mindgame.controller;

import mindgame.model.MemoryModel;
import mindgame.view.Levels;

public class LevelController {
    protected Levels levelgui;

    public LevelController() {

        levelgui = new Levels(this);
    }

    public void levelSelected(int rows, int cols, String level) {

        MemoryModel model = new MemoryModel(rows, cols, level);
        new MemoryController(model);
    }

}
