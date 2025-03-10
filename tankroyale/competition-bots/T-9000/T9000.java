import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;
import java.awt.Color;

public class T9000 extends Bot {

    // Keeps track of when to trigger evasive movement
    int trigger;

    // Used for turning behavior when no target is scanned
    int turnDirection = 1; // clockwise (-1) or counterclockwise (1)

    // Main method to start the bot
    public static void main(String[] args) {
        new T9000().start();
    }

    // Constructor that loads the bot configuration
    T9000() {
        super(BotInfo.fromFile("T-9000.json"));
    }

    @Override
    public void run() {
        // Set a distinctive color scheme that mixes the styles from both bots
        setBodyColor(new Color(0xFF, 0x00, 0xFF)); // magenta
        setTurretColor(new Color(0x00, 0xFF, 0xFF)); // cyan
        setRadarColor(new Color(0xFF, 0xA5, 0x00)); // orange
        setBulletColor(new Color(0xFF, 0xFF, 0x00)); // yellow
        setScanColor(new Color(0x00, 0xFF, 0x00)); // lime

        // Initialize the energy trigger threshold for evasive moves.
        // Starts at 80 like in Target, then decreases by 20 with each event.
        trigger = 80;

        // Add custom event "trigger-hit" to check energy level drops.
        addCustomEvent(new Condition("trigger-hit") {
            @Override
            public boolean test() {
                return getEnergy() <= trigger;
            }
        });

        // Main loop: keep turning to scan for enemies.
        while (isRunning()) {
            // If no enemy is in sight, continuously turn to scan the field.
            turnLeft(5 * turnDirection);
        }
    }

    // When an enemy is scanned, use the tactics from RamFire:
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Turn to face the enemy
        turnToFaceTarget(e.getX(), e.getY());
        // Calculate distance and move toward the enemy plus a small overshoot for
        // ramming
        double distance = distanceTo(e.getX(), e.getY());
        forward(distance + 5);
        // Lock the radar on the target
        double radarTurn = bearingTo(e.getX(), e.getY());
        turnRadarLeft(normalizeBearing(radarTurn));
    }

    // Helper method to normalize the bearing to be within -180 to 180 degrees
    private double normalizeBearing(double angle) {
        while (angle > 180)
            angle -= 360;
        while (angle < -180)
            angle += 360;
        return angle;
    }

    // When colliding with an enemy, fire based on its energy level then try to ram
    // again.
    @Override
    public void onHitBot(HitBotEvent e) {
        // Turn to face the enemy bot
        turnToFaceTarget(e.getX(), e.getY());
        // Fire with power based on the enemy's remaining energy
        if (e.getEnergy() > 16) {
            fire(3);
        } else if (e.getEnergy() > 10) {
            fire(2);
        } else if (e.getEnergy() > 4) {
            fire(1);
        } else if (e.getEnergy() > 2) {
            fire(0.5);
        } else if (e.getEnergy() > 0.4) {
            fire(0.1);
        }
        // Continue ramming by moving forward
        forward(40);
    }

    // Custom event handling for energy drops. When the custom "trigger-hit" event
    // fires,
    // the robot will adjust its trigger and perform evasive maneuvers.
    @Override
    public void onCustomEvent(CustomEvent e) {
        if ("trigger-hit".equals(e.getCondition().getName())) {
            // Adjust the trigger so that this event is not repeatedly fired.
            trigger -= 20;
            // Log current energy to the console
            System.out.println("Energy low: " + (int) (getEnergy() + 0.5) + ". Initiating evasive maneuver.");
            // Evasive movement: turn and move forward
            turnLeft(65);
            forward(100);
        }
    }

    // Helper method to turn the robot to face a target at (x, y)
    private void turnToFaceTarget(double x, double y) {
        double bearing = bearingTo(x, y);
        // Update the default turn direction based on the bearing
        if (bearing >= 0) {
            turnDirection = 1;
        } else {
            turnDirection = -1;
        }
        turnLeft(bearing);
    }
}
