package de.robotcode;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.MoveCompleteCondition;
import robocode.ScannedRobotEvent;

public class PanosRobot extends AdvancedRobot {
	double turning = 0.0;
	double speed = 40000.0;
	
	public void run() {
		while (true) {
			updateMoving();
			waitFor(new MoveCompleteCondition(this));
		}
	}
	
	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		turning = 0.0;
		updateMoving();
		fire(1);
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		// TODO Auto-generated method stub
		if (speed<0) {
			speed*=-1;
		} else {
			turning = Math.random()*45+event.getBearing();			
		}
		updateMoving();
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		// TODO Auto-generated method stub
		speed+=5000;
	}
	 @Override
	public void onHitRobot(HitRobotEvent event) {
			turning = -event.getBearing();

		 fire(3);
	}
	
	private void updateMoving() {
		if (speed>=0) {
			setAhead(speed);
		} else {
			setBack(-speed);
		}
		
		if (turning>=0) {
			setTurnLeft(turning);
		} else {
			setTurnRight(-turning);
		}
	}
}
