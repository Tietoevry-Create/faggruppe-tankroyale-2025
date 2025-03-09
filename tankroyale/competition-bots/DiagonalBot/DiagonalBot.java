import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class DiagonalBot extends Bot {

    // The main method starts our bot
    public static void main(String[] args) {
        new DiagonalBot().start();
    }

    // Constructor, which loads the bot config file
    DiagonalBot() {
        super(BotInfo.fromFile("DiagonalBot.json"));
    }

    // Called when a new round is started -> initialize and do some movement
    @Override
    public void run() {

        turnRight(getDirection() - 0);
        forward(getArenaWidth());

        // Repeat while the bot is running
        while (isRunning()) {
//            forward(399);
//            forward(100);
//            turnGunRight(360);
//            back(100);
//            turnGunRight(360);
        }
    }

    // We saw another bot
    @Override
    public void onScannedBot(ScannedBotEvent e) {
    }

    // We were hit by a bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {

        // Calculate the bearing to the direction of the bullet
     //   double bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
    //    turnLeft(90 - bearing);
    }
}

