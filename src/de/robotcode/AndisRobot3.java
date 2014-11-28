package de.robotcode;

import robocode.*;

import java.awt.*;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * AndisRobot - a robot by (your name here)
 */
public class AndisRobot3 extends AdvancedRobot
{
    boolean forward = true;
    /**
     * run: AndisRobot's default behavior
     */
    public void run() {
        // Initialization of the robot should be put here
        setColors(Color.green, Color.green, Color.green, Color.green, Color.green); // body,gun,radar

        setInterruptible(true);
        // Robot main loop
        while(true) {

            setMaxVelocity(Rules.MAX_VELOCITY);

            // Replace the next 4 lines with any behavior you would like
            setTurnGunLeft(360);
            execute();

            if (forward) {
                ahead(20.0);
            }else {
                back(20.0);
            }
        }
    }

    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        // Replace the next line with any behavior you would like
        if (forward) {
            setAhead(20.0);
        }else {
            setBack(20.0);
        }
        if (e.getEnergy() < 40) {
            setFire(1.1);
            setFire(.7);
        }else{
            setFire(1.9);
            setFire(1.1);
        }
        execute();
    }

    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        setTurnLeft(45.0);
        if (forward) {
            setAhead(20.0);
        }else{
            setBack(20.0);
        }

        execute();
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        this.forward = !forward;
        if (forward) {
            setAhead(20.0);
        }else{
            setBack(20.0);
        }
        execute();
    }

    public void onHitRobot(HitRobotEvent event) {
        setTurnLeft(15);
        execute();
        back(30);
    }
}
