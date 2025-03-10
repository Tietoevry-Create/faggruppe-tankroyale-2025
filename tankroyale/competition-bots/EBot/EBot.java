import java.util.Date;

import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class EBot extends Bot
{
    private boolean spinRight = false;
    private boolean spotted = false;
    private long lastSpotTime = new Date().getTime();
    // The main method starts our bot
    public static void main(String[] args) {
        new EBot().start();
    }

    // Constructor, which loads the bot config file
    EBot() {
        super(BotInfo.fromFile("EBot.json"));
    }

    @Override
    public void run() {
        lastSpotTime = new Date().getTime();
        var maxWaitTime = 80;
        spotted = false;

        while (isRunning()) {
            rescan();
            var spotDelta = new Date().getTime() - lastSpotTime;
            if (spotted && spotDelta > maxWaitTime) {
                spinRight = !spinRight;
                spotted = false;
            }
            
            if (spotted) {
                fire(1);
            }

            var turnSpeed = 1 + 10 * Math.min(1, spotDelta / (maxWaitTime * 10));
            if (spinRight) turnGunRight(turnSpeed);
            else turnGunLeft(turnSpeed);
            if (!spotted && spotDelta > maxWaitTime * 5 && Math.random() < 0.01) {
                turnRight(90);
                turnGunLeft(90);
                if (Math.random() < 0.5) forward(100);
                else back(100);
            }
        }
    }

    @Override
    public void onScannedBot(ScannedBotEvent e) {
        lastSpotTime = new Date().getTime();
        spotted = true;
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

