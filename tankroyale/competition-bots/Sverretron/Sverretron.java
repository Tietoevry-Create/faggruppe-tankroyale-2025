import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class Sverretron extends Bot {


    // The main method starts our bot
    public static void main(String[] args) {
        new Sverretron().start();
    }


    // Constructor, which loads the bot config file
    Sverretron() {
        super(BotInfo.fromFile("Sverretron.json"));
    }

    @Override
    public void run() {
        // Do initial setup for each round

        while (isRunning()) {

            for (int i = 0; i < 4; i++) {
                forward(4);
                turnRight(90);
                
                fire(1);
                
                
            }


            // Repeat while the bot is running
            // should move tank, handle the gun and radar
        }
    }



    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Scanned an enemy bot
        
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Hit by a bullet
        
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        // Hit a wall
        turnRight(90);
        forward(4); 
    }

}