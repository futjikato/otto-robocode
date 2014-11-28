package de.robotcode;
import robocode.*;
import java.awt.Color;
import static robocode.util.Utils.normalRelativeAngleDegrees;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AndisRobot - a robot by (your name here)
 */
public class Motherbrain extends Robot
{
    public static final int TURN_LEFT = 0;
    public static final int TURN_RIGHT = 1;

    public static final int MOVE_AHEAD = 1;
    public static final int MOVE_BACK = 1;

    private int turnDir = TURN_LEFT;

    private int moveDir = MOVE_BACK;

    /**
     * run: AndisRobot's default behavior
     */
    public void run() {
        setColors(Color.green, Color.green, Color.green);
        setAdjustGunForRobotTurn(true);
        while(true) {
            if(getGunHeat() == 0) {
                if(turnDir == TURN_LEFT) {
                    turnGunLeft(10);
                } else {
                    turnGunRight(10);
                }
            } else {
                if(moveDir == MOVE_BACK) {
                    back(10);
                } else {
                    ahead(10);
                }
            }
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        // Calculate exact location of the robot
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

        if(e.getDistance() > 600) {
            if(moveDir == MOVE_BACK) {
                back(10);
            } else {
                ahead(10);
            }
        } else {
            if (Math.abs(bearingFromGun) <= 2) {
                fire(3);
            } else {
                if (bearingFromGun < 0) {
                    turnGunLeft(1);
                } else {
                    turnGunRight(1);
                }
            }
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate exact location of the robot
        double absoluteBearing = getHeading() + e.getBearing();
        if(Math.abs(absoluteBearing) <= 3) {
            turnLeft(10);
        }

        if(moveDir == MOVE_BACK) {
            back(10);
        } else {
            ahead(10);
        }
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        moveDir = (moveDir == 0) ? 1 : 0;
    }

    public void onHitRobot(HitRobotEvent event) {
        turnRight(90);
        back(100);
    }
}
