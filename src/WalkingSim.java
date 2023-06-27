import java.util.Random;
import java.io.File;
import processing.core.PImage;

public class WalkingSim{
    private static Random randGen;
    private static int bgColor;
    private static PImage[] frames;
    private static Walker[] walkers;


    public static void main(String[] args){
        Utility.runApplication();

    }
    /**
     * This method is used to setup the application
     */
    public static void setup(){
        randGen = new Random();
        bgColor = randGen.nextInt();
        frames = new PImage[Walker.NUM_FRAMES];
        for(int i = 0; i < Walker.NUM_FRAMES; i++){
            frames[i] = Utility.loadImage("images"+File.separator+"walk-"+i+".png");
            System.out.println("images"+File.separator+"walk-"+i+".png");
        }
        //Initialize the walkers array
        walkers = new Walker[8];
        walkers[0] = new Walker();
        Walker ref = walkers[0];
        // initialize the first walker
        // random number of walkers
        int count = randGen.nextInt(walkers.length-1)+1;
        //initialize all the workers
        for(int i = 0; i < count; i++){
            walkers[i] = new Walker(randGen.nextInt(Utility.width()), randGen.nextInt(Utility.height()));
        }
        
        


        
    }
    
    /**
     * This method is used to draw the window and the current state of its contents to the screen
     */
    public static void draw(){
        //System.out.println("Some output from draw");
        Utility.background(bgColor);
        // Utility.image(frames[0], walkers[0].getPositionX(), walkers[0].getPositionY());
        // check if the walker is out of size(non-null walkers)
        int i = 0;
        while (i < walkers.length && walkers[i] != null){
            Utility.image(frames[walkers[i].getCurrentFrame()], walkers[i].getPositionX(), walkers[i].getPositionY());
            if (walkers[i].isWalking()){
                walkers[i].update();
                walkers[i].setPositionX(walkers[i].getPositionX()+3);
            }

            if (walkers[i].getPositionX() / 800 >= 1 && walkers[i].getPositionY() % 800 >= 0){
                walkers[i].setPositionX(0);
            }

        i++;
        }


    }
    /**
     * This method returns true is the mouse if over any part of one of the walkers.
     * The method uses width height and position of the walker to determine if the mouse is over it.
     * 
     * @param walker
     * @return boolean
     */
    public static boolean isMouseOver(Walker walker){
       
        float top_left_X = walker.getPositionX() - (frames[0].width/2);
        float top_left_Y = walker.getPositionY() - (frames[0].height/2);
        float bottom_right_X = walker.getPositionX() + (frames[0].width/2);
        float bottom_right_Y = walker.getPositionY() + (frames[0].height/2);

        if(Utility.mouseX() > top_left_X && Utility.mouseX() < bottom_right_X && Utility.mouseY() > top_left_Y && Utility.mouseY() < bottom_right_Y){
            
            return true;
        }
        return false;

    }
    

    /**
     * This method is called whenever the mouse is pressed
     * 
     * 
     */
    public static void mousePressed(){
        // check if the mouse is over the walker
        int i = 0;
        // check i is not out of bound and the walker is not null
        while(i< walkers.length && walkers[i] != null){
            if(isMouseOver(walkers[i])){
                // System.out.println("Mouse is over a walker!");
                walkers[i].setWalking(true);
                break;
            }
            i++;
        }
        
    }
    /**
     * This method is called whenever a key is pressed
     * when a or A is introduced, a new walker is added if the array is not full yet
     * when s or S is introduced, all the walkers stop
     * 
     * @param key
     */
    public static void keyPressed(char key) {
        int i = 0;
        if (key == 'a' || key == 'A') {
            while (i < walkers.length){
                if (walkers[i] == null){
                    walkers[i] = new Walker(randGen.nextInt(Utility.width()), randGen.nextInt(Utility.height()));
                    break;
                }
                i++;
            }
        }
        if (key == 's' || key == 'S') {
            for (int j = 0; j < walkers.length; j++){
                if (walkers[j] != null){
                    walkers[j].setWalking(false);
                }       
            }
        }
    }


    


}

