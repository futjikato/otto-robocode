package de.haw;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Vince - a robot by (your name here)
 */
public class Vince extends AdvancedRobot
{
	int moveDirection = 1;
	int turnGunDirection = 1;
	
	/**
	 * run: Vince's default behavior
	 */
	public void run() {
		setColors(Color.green, Color.green, Color.green, Color.green, Color.green); // body,gun,radar
		
		setAdjustGunForRobotTurn(true);

		while(true) {
			setMaxVelocity(Rules.MAX_VELOCITY);
			setMaxTurnRate(Rules.MAX_TURN_RATE);
			setTurnGunRight(Rules.GUN_TURN_RATE);
			setBack(50 * moveDirection);
			setTurnRight(5);
			execute();
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		if (getGunHeat() < 2.8) {
			fire(getEnergy() > 50 ? 3 : 1.5);
			setBack(50 * moveDirection);
			execute();
		}
		
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

		if (Math.abs(bearingFromGun) <= 3) {
			turnGunRight(bearingFromGun);
			if (getGunHeat() == 0) {
				fire(getEnergy() > 50 ? 3 : 1.5);
				setBack(50 * moveDirection);
				execute();
			}
		} else {
			turnGunRight(bearingFromGun);
		}
		// Generates another scan event if we see a robot.
		// We only need to call this if the gun (and therefore radar)
		// are not turning.  Otherwise, scan is called automatically.
		if (bearingFromGun == 0) {
			scan();
		}
	}

	public void onHitByBullet(HitByBulletEvent e) {
		setBack(50 * moveDirection);
		setTurnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
		execute();
		scan();
	}
	
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		setTurnGunRight(turnGunAmt);
		fire(3);
		setBack(50 * moveDirection);
		execute();
	}
	
	public void onHitWall(HitWallEvent e) {
		moveDirection *= -1;
	}	
}
