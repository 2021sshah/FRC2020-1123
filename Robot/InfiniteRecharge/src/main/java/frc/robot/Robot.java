/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.MecanumDriveTrain;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * This is a demo program showing how to use Mecanum control with the RobotDrive
 * class.
 */
public class Robot extends TimedRobot {
  private static final int kFrontLeftChannel = 11; // 11,12,13,14
  private static final int kRearLeftChannel = 12;
  private static final int kFrontRightChannel = 13;
  private static final int kRearRightChannel = 14;

  private static final int kJoystickChannel = 0;

  private MecanumDriveTrain m_robotDrive;
  private Joystick m_stick;

  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight"); 

  @Override
  public void robotInit() {
    WPI_TalonSRX frontLeft = new WPI_TalonSRX(kFrontLeftChannel);
    WPI_TalonSRX rearLeft = new WPI_TalonSRX(kRearLeftChannel);
    WPI_TalonSRX frontRight = new WPI_TalonSRX(kFrontRightChannel);
    WPI_TalonSRX rearRight = new WPI_TalonSRX(kRearRightChannel);

    // You may need to change or remove this to match your robot (for later).
    // frontLeft.setInverted(true);
    // rearLeft.setInverted(true);

    m_robotDrive = new MecanumDriveTrain(frontLeft, rearLeft, frontRight, rearRight);
    m_stick = new Joystick(kJoystickChannel);
  }

  @Override
  public void teleopPeriodic() {
    // Use the joystick X axis for lateral movement, Y axis for forward
    // movement, and Z axis for rotation.
    double ySpeed = m_stick.getX();
    double xSpeed = -m_stick.getY();
    double zSpeed = m_stick.getZ();
    double throttle = (1-m_stick.getThrottle())/2; // Limits Max Speed
    
    System.out.println("Yspeed" + ySpeed);
    System.out.println("Xspeed" + xSpeed);
    System.out.println("Throttle" + throttle);

    if(m_stick.getTrigger())
      m_robotDrive.swivelCartesian(ySpeed, xSpeed, zSpeed, throttle);
    else
      m_robotDrive.driveCartesian(ySpeed, xSpeed, zSpeed, throttle, 0.0);

    // LIMELIGHT CAMERA
    NetworkTableEntry tx = table.getEntry("tx"); 
    NetworkTableEntry ty = table.getEntry("ty"); 
    NetworkTableEntry ta = table.getEntry("ta");

    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
  }
}