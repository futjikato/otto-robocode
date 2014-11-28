package de.robotcode;
import robocode.*;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AndisRobot - a robot by (your name here)
 */
public class Motherbrain extends AdvancedRobot
{
    public static final int TURN_LEFT = 0;
    public static final int TURN_RIGHT = 1;

    public static final int MOVE_AHEAD = 1;
    public static final int MOVE_BACK = 1;

    private int turnDir = TURN_LEFT;

    private int moveDir = MOVE_BACK;

    private String target;

    /**
     * run: AndisRobot's default behavior
     */
    public void run() {
        setColors(Color.green, Color.green, Color.green);
        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while(true) {
            setTurnRadarLeft(360);
            if(moveDir == MOVE_BACK) {
                setBack(300);
            } else {
                setAhead(300);
            }
            execute();
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        if(target != null) {
            return;
        }
        target = e.getName();

        // Calculate exact location of the robot
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

        if(bearingFromGun > 0) {
            turnGunRight(Math.abs(bearingFromGun));
        } else {
            turnGunLeft(Math.abs(bearingFromGun));
        }

        if(getOthers() > 2) {
            fire(1);
        } else {
            fire(3);
        }

        target = null;
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        moveDir = (moveDir == 0) ? 1 : 0;
    }

    public void onHitRobot(HitRobotEvent event) {
        setTurnLeft(40);
        setBack(50);
        execute();
    }
}
