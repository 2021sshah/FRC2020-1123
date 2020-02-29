package frc.robot;

import java.util.Map;
import java.util.logging.Logger;
import frc.robot.RobotContainer;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;

import frc.robot.commands.*;

public class DashboardControlSystem {

  private static NetworkTableEntry maxSpeed;

  public static void initialize() {
    Logger logger = Logger.getLogger(frc.robot.DashboardControlSystem.class.getName());

    // TODO: Add controls for autonomous mode
    // ShuffleboardTab Autonomous = Shuffleboard.getTab("Autonomous Tab");

    ShuffleboardTab teleopTab = Shuffleboard.getTab("Teleop");

    ShuffleboardLayout motorControl = teleopTab.getLayout("Motor Control", BuiltInLayouts.kList)
      .withPosition(0, 0).withSize(2, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));

    motorControl.add("Spin Motors", new SpinShooterMotorsCommand());
    motorControl.add("Shooter Motors Start", new StartShooterMotorsCommand());
    motorControl.add("Shooter Motors Stop", new StopShooterMotorsCommand());
    
    ShuffleboardLayout motorSpeed = teleopTab.getLayout("Motor Speed", BuiltInLayouts.kList)
      .withPosition(2, 0).withSize(2, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));

    motorSpeed.add("Increase Shooter Motor Speed 50", new IncreaseShooterMotorSpeed50());
    motorSpeed.add("Increase Shooter Motor Speed 100", new IncreaseShooterMotorSpeed100());
    motorSpeed.add("Decrease Shooter Motor Speed 50", new DecreaseShooterMotorSpeed50());
    motorSpeed.add("Decrease Shooter Motor Speed 100", new DecreaseShooterMotorSpeed100());
    maxSpeed = motorSpeed.add("Speed Slider", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("min", 0, "max", 1))
      .getEntry();

    //RobotContainer.getInstance().shooter.setSpeed(motorSpeed.SpeedSlider.get );

    ShuffleboardLayout ramControl = teleopTab.getLayout("Ball Ram", BuiltInLayouts.kList)
      .withPosition(4, 0).withSize(2, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));
    RobotContainer robotContainer = RobotContainer.getInstance();

    ramControl.add("Shoot", new ShooterShootCommand());
    ramControl.add("Load", new ShooterLoadCommand());

    ShuffleboardTab endgameTab = Shuffleboard.getTab("Endgame");
    endgameTab.add("Deploy hook", false);
    endgameTab.add("Winch left", false);
    endgameTab.add("Winch right", false);

    // TODO: Add controls for end game climb
    // ShuffleboardTab EndGame = Shuffleboard.getTab("Endgame");
  }
  public static double getSliderSpeed() {
    double shooterSpeed = maxSpeed.getDouble(0.0);
    SmartDashboard.putNumber("Logging Shooter Speed: ", shooterSpeed);
    return shooterSpeed;
  }
}