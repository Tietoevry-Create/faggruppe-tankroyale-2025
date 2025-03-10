import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;
import java.awt.Color;
import java.util.Random;

public class TomasBot extends Bot {

    int dist = 50; // Distance to move when we're hit, forward or back
    int turnDirection = 1; // clockwise (-1) or counterclockwise (1)
    boolean movingForward;
    int enemies; // Number of enemy bots in the game
    static int corner = randomCorner(); // Which corner we are currently using. Set to random corner
    boolean stopWhenSeeEnemy = false; // See goCorner()

    // The main method starts our bot
    public static void main(String[] args) {
        new TomasBot().start();
    }

    // Constructor, which loads the bot config file
    TomasBot() {
        super(BotInfo.fromFile("TomasBot.json"));
    }

    @Override
    public void run() {
        // Do initial setup for each round
        setBodyColor(Color.green);
        setTurretColor(Color.black);
        setRadarColor(Color.red);
        setBulletColor(Color.blue);
        setScanColor(Color.blue);

        enemies = getEnemyCount();

        goCorner();

        // Initialize gun turn speed to 3
        int gunIncrement = 3;

        // Spin gun back and forth
        // Loop while as long as the bot is running
        while (isRunning()) {
            // Tell the game we will want to move ahead 40000 -- some large number
            setForward(40000);
            movingForward = true;
            // Tell the game we will want to turn right 90
            setTurnRight(90);
            // At this point, we have indicated to the game that *when we do something*,
            // we will want to move ahead and turn right. That's what "set" means.
            // It is important to realize we have not done anything yet!
            // In order to actually move, we'll want to call a method that takes real time, such as
            // waitFor.
            // waitFor actually starts the action -- we start moving and turning.
            // It will not return until we have finished turning.
            waitFor(new TurnCompleteCondition(this));
            // Note: We are still moving ahead now, but the turn is complete.
            // Now we'll turn the other way...
            setTurnLeft(180);
            // ... and wait for the turn to finish ...
            waitFor(new TurnCompleteCondition(this));
            // ... then the other way ...
            setTurnRight(180);
            // ... and wait for that turn to finish.
            waitFor(new TurnCompleteCondition(this));
            // then back to the top to do it all again.
        }
    }

    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Scanned an enemy bot
        var distance = distanceTo(e.getX(), e.getY());
        if (distance < 50 && getEnergy() > 50) {
            fire(3);
        } else {
            // Otherwise, only fire 1
            fire(1);
        }
        // Rescan
        rescan();
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Turn perpendicular to the bullet direction
        turnLeft(normalizeRelativeAngle(90 - (getDirection() - e.getBullet().getDirection())));

        // Move forward or backward depending if the distance is positive or negative
        forward(dist);
        dist *= -1; // Change distance, meaning forward or backward direction

        // Rescan
        rescan();
    }

    @Override
    public void onHitBot(HitBotEvent e) {
        turnToFaceTarget(e.getX(), e.getY());

        // Determine a shot that won't kill the bot...
        // We want to ram him instead for bonus points
        if (e.getEnergy() > 16) {
            fire(3);
        } else if (e.getEnergy() > 10) {
            fire(2);
        } else if (e.getEnergy() > 4) {
            fire(1);
        } else if (e.getEnergy() > 2) {
            fire(.5);
        } else if (e.getEnergy() > .4) {
            fire(.1);
        }
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        // Hit a wall
    }

    // Returns a random corner (0, 90, 180, 270)
    private static int randomCorner() {
        return 90 * new Random().nextInt(4); // Random number is between 0-3
    }

    private void goCorner() {
        // We don't want to stop when we're just turning...
        stopWhenSeeEnemy = false;
        // Turn to face the wall towards our desired corner
        turnLeft(calcBearing(corner));
        // Ok, now we don't want to crash into any bot in our way...
        stopWhenSeeEnemy = true;
        // Move to that wall
        forward(5000);
        // Turn to face the corner
        turnRight(90);
        // Move to the corner
        forward(5000);
        // Turn gun to starting point
        turnGunRight(90);
    }

    // Condition that is triggered when the turning is complete
    public static class TurnCompleteCondition extends Condition {

        private final IBot bot;

        public TurnCompleteCondition(IBot bot) {
            this.bot = bot;
        }

        @Override
        public boolean test() {
            // turn is complete when the remainder of the turn is zero
            return bot.getTurnRemaining() == 0;
        }
    }

    private void turnToFaceTarget(double x, double y) {
        var bearing = bearingTo(x, y);
        if (bearing >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        turnLeft(bearing);
    }
}

