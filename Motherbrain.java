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
    private int turnDir = 0;

    private long lastHitBy = 0;

    /**
     * run: AndisRobot's default behavior
     */
    public void run() {
        setColors(Color.green, Color.green, Color.green);

        while(true) {
            if(getGunHeat() == 0) {
                if(turnDir == 0) {
                    turnLeft(10);
                } else {
                    turnLeft(10);
                }
            } else {
                back(10);
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

        if(e.getDistance() > 300) {
            ahead(20);
        } else {
            if (Math.abs(bearingFromGun) <= 2) {
                if (getEnergy() < 50) {
                    fire(1);
                } else {
                    fire(3);
                }
            } else {
                if (bearingFromGun < 0) {
                    turnLeft(1);
                } else {
                    turnRight(1);
                }
            }
        }
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        ahead(10);
        turnDir = (turnDir == 0) ? 1 : 0;
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        turnLeft(90);
    }

    public void onHitRobot(HitRobotEvent event) {
        turnRight(90);
        back(100);
    }
}
