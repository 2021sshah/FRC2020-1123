/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableInstance;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.MecanumDriveTrain;
import frc.robot.LimelightVision;
import com.ctre.phoenix.motorcontrol.can.*;

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
  private JoystickButton button2;

  private AHRS navX = new AHRS(SPI.Port.kMXP); // Controls all Sensors
  private final Logger logger = Logger.getLogger(this.getClass().getName());

  @Override
  public void robotInit() {
    logger.info("Team 1123 initializing");
    CommandScheduler.getInstance().cancelAll();
    LiveWindow.disableAllTelemetry();

    WPI_TalonSRX frontLeft = new WPI_TalonSRX(kFrontLeftChannel);
    WPI_TalonSRX rearLeft = new WPI_TalonSRX(kRearLeftChannel);
    WPI_TalonSRX frontRight = new WPI_TalonSRX(kFrontRightChannel);
    WPI_TalonSRX rearRight = new WPI_TalonSRX(kRearRightChannel);

    // You may need to change or remove this to match your robot (for later).
    // frontLeft.setInverted(true);
    // rearLeft.setInverted(true);

    m_robotDrive = new MecanumDriveTrain(frontLeft, rearLeft, frontRight, rearRight);
    m_stick = new Joystick(kJoystickChannel);
    button2 = new JoystickButton(m_stick, 2);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    logger.info("Robot initializing disabled mode");
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void teleopInit() {
    logger.info("The robot is initializing teleop mode");
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
    // Use the joystick X axis for lateral movement, Y axis for forward
    // movement, and Z axis for rotation.
    double ySpeed = m_stick.getX();
    double xSpeed = -m_stick.getY();
    double zSpeed = m_stick.getZ();
    double throttle = (1-m_stick.getThrottle())/2; // Limits Max Speed

    if (m_stick.getTrigger())
      m_robotDrive.swivelCartesian(ySpeed, xSpeed, zSpeed, throttle);
    else
      m_robotDrive.driveCartesian(ySpeed, xSpeed, zSpeed, throttle, 0.0);

    LimelightVision.checkValues();
    button2.whenPressed(new InstantCommand(LimelightVision::switchLedState, new LimelightVision()));

    SmartDashboard.putNumber("Gyro Angle", navX.getAngle()); // Gyro Testing
    SmartDashboard.putNumber("X Accel", navX.getRawAccelX()*100); // Accel Testing
    SmartDashboard.putNumber("Y Accel", navX.getRawAccelY()*100);
    SmartDashboard.putNumber("Z Accel", navX.getRawAccelZ()*100); 
  }
}