package mindgame.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MemoryModelTest {

    private MemoryModel model;

    @Before
    public void setUp() {
        model = new MemoryModel(4, 4, "Test Level");
    }

    @Test
    public void testInitialization() {
        assertNotNull(model);
        assertEquals(4, model.getRows());
        assertEquals(4, model.getCols());
        assertEquals("Test Level", model.getLevel());
        assertEquals(0, Integer.parseInt(model.getScore()));
        assertEquals(0, Integer.parseInt(model.getWrongAttempts()));
        assertEquals(0, model.successfulGrids());
    }

    @Test
    public void testMakeGuessCorrect() {
        // Test a correct guess
        int initialScore = Integer.parseInt(model.getScore());
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(model.getGridNumber(0,0) == model.getGridNumber(i,j)){
                    model.makeGuess(i,j);
                }
            }
        }
        assertEquals(initialScore + 2, Integer.parseInt(model.getScore()));
        assertEquals(0, model.successfulGrids());
        assertFalse(model.isGameOver());
    }

    @Test
    public void testMakeGuessIncorrect() {
        MemoryModel model = new MemoryModel(4, 4, "Test Level");
        int initialScore = Integer.parseInt(model.getScore());
        int initialWrongAttempts=Integer.parseInt(model.getWrongAttempts());
        model.makeGuess(0,0);
        int num=model.getGridNumber(0,0);
        for(int i=1;i<3;i++){
            if(num!=model.getGridNumber(i,0)){
                model.makeGuess(i,0);
                break;
            }
        }
        assertEquals(initialScore, Integer.parseInt(model.getScore()));
        assertEquals(initialWrongAttempts + 1, Integer.parseInt(model.getWrongAttempts()));
        assertFalse(model.isGameOver());
    }

    @Test
    public void testGenerateNewGrid() {
        MemoryModel model1 = new MemoryModel(4, 4, "Test Level");
        model1.generateNewGrid();
        assertEquals(1, model1.successfulGrids());
        assertFalse(model1.isGameOver());
        assertEquals(0, Integer.parseInt(model1.getScore()));
    }

    @Test
    public void testMaxWrongAttempts() {
        MemoryModel model=new MemoryModel(4, 4, "test");
        model.setmaxWrongAttempts();
        assertEquals(model.getRows() - 3, model.getMaxWrongAttempts());
        assertFalse(model.isGameOver());
        model.makeGuess(0, 0);
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(model.getGridNumber(0,0)!=model.getGridNumber(i,j)){
                    model.makeGuess(i, j);
                    break;
                }

            }

        }
        assertTrue(model.isGameOver());
        assertTrue(Integer.parseInt(model.getWrongAttempts())>model.getMaxWrongAttempts());
    
        
    }

    @Test
    public void testGridComplete(){
        MemoryModel model3=new MemoryModel(4, 4, "easy");
        int[][] grid1=model3.getGridNumbers();
        int[][] grid2=model3.getGridNumbers();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(!model3.isOpened(i, j)){
                    for(int k=0;k<4;k++){
                        for(int l=0;l<4;l++){
                            if(grid1[i][j]==grid2[k][l] && (i!=k || j!=l)){
                                model3.makeGuess(i, j);
                                model3.makeGuess(k, l);

                                assertTrue(model3.isOpened(i,j));
                                assertTrue(model3.isOpened(k,l));
                            }
                            
                        }
                    }

                }

            }
        }
        if(model3.newGrid()==model3.getLen()){
            System.out.println("all matches in previous grid are successfully opened");
            model3.generateNewGrid();
        }

        assertEquals(1,model3.successfulGrids());
    }
        

}

