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
    public static final int MOVE_AHEAD = 1;
    public static final int MOVE_BACK = 1;

    private int moveDir = MOVE_BACK;

    private String target;
    private double targetDistance;

    /**
     * run: AndisRobot's default behavior
     */
    public void run() {
        setColors(Color.green, Color.green, Color.green);
        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while(true) {
            if(target == null) {
                movemovemove();
            }
        }
    }

    public void movemovemove() {
        setTurnRadarLeft(360);
        if(moveDir == MOVE_BACK) {
            setBack(300);
        } else {
            setAhead(300);
        }
        setTurnLeft(180);
        execute();
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        if(target != null && getGunHeat() > 0) {
            if(e.getDistance() >= targetDistance) {
                return;
            }
        }
        target = e.getName();
        targetDistance = e.getDistance();

        // Calculate exact location of the robot
        double absoluteBearing = getHeading() + e.getBearing();
        double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
        
        if(bearingFromGun > 0) {
            turnGunRight(Math.abs(bearingFromGun));
        } else {
            turnGunLeft(Math.abs(bearingFromGun));
        }

        if(getOthers() > 2) {
            if(getEnergy() < 50) {
                fire(1);
            } else {
                fire(3);
            }
        } else {
            if(getEnergy() < 50) {
                fire(1);
            } else {
                fire(3);
            }
        }

        target = null;
    }

    public void onHitWall(HitWallEvent e) {
        moveDir = (moveDir == 0) ? 1 : 0;
    }

    public void onHitRobot(HitRobotEvent event) {
        moveDir = (moveDir == 0) ? 1 : 0;
    }
}
