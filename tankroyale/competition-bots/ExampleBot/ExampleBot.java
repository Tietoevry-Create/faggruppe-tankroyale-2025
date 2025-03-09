import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class ExampleBot extends Bot {

    // The main method starts our bot
    public static void main(String[] args) {
        new ExampleBot().start();
    }

    // Constructor, which loads the bot config file
    ExampleBot() {
        super(BotInfo.fromFile("ExampleBot.json"));
    }

    @Override
    public void run() {
        // Do initial setup for each round

        while (isRunning()) {
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
    }

    // todo: additional handlers exist.. see the docs!
}

