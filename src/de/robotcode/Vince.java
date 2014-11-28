package de.haw;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * Vince - a robot by (your name here)
 */
public class Vince extends Robot
{
	int moveDirection = 1;
	int turnGunDirection = 1;
	
	/**
	 * run: Vince's default behavior
	 */
	public void run() {
		setColors(Color.green, Color.green, Color.green, Color.green, Color.green); // body,gun,radar

		// Robot main loop
		while(true) {
			turnGunRight(40 * turnGunDirection);
		}
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		double absoluteBearing = getHeading() + e.getBearing();
		double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
		
		fire(3);

		/**if (getGunHeat() == 0) {
			fire(e.getDistance() < 100 && getEnergy() > 50 ? 3 : 3);
		}*/
		
		back(60 * moveDirection);
	}

	public void onBulletHitBullet(BulletHitBulletEvent e) {
		if (getEnergy() <= 20) {
			back(30 * moveDirection);
		}
	}

	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
		back(50 * moveDirection);
		scan();
	}
	
	public void onHitRobot(HitRobotEvent e) {
		double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

		turnGunRight(turnGunAmt);
		fire(3);
		back(30 * moveDirection);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		moveDirection *= -1;
	}	
}
