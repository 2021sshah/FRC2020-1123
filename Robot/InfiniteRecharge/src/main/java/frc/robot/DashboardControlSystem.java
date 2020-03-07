package frc.robot;

import java.util.Map;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.subsystems.LimelightSubsystem;


import frc.robot.commands.*;

public class DashboardControlSystem {

  private static NetworkTableEntry maxSpeed;
  private static NetworkTableEntry autoNum;

  private static NetworkTableEntry teleTime;
  private static NetworkTableEntry endTime;

  private static ShuffleboardTab teleopTab;
  private static ShuffleboardTab endgameTab;

  public static void initialize() {
    Logger logger = Logger.getLogger(frc.robot.DashboardControlSystem.class.getName());

    // TODO: Add controls for autonomous mode
    // ShuffleboardTab Autonomous = Shuffleboard.getTab("Autonomous Tab");
    // Autonomous.add("Autonomous 1", new SetAutonomous1());
    // Autonomous.add("Autonomous 2", new SetAutonomous2());
    // Autonomous.add("Autonomous 3", new SetAutonomous3());
    // autoNum = Autonomous.add("Auto", "low")
    // .withWidget(BuiltInWidgets.kNumberBar)
    // .getEntry();

    teleopTab = Shuffleboard.getTab("Teleop");
    // teleopTab.add("Limelight", new LimelightCommand(new LimelightSubsystem()));
    // teleopTab.add("Intake Extend", new ExtendIntakePiston());
    // teleopTab.add("Intake Retract", new RetractIntakePiston());
    // teleopTab.add("Calibrate Gyro", new CalibrateGyro());
    ShuffleboardLayout motorControl = teleopTab.getLayout("Motor Control", BuiltInLayouts.kList)
      .withPosition(0, 0).withSize(2, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));

    motorControl.add("Spin Motors", new SpinShooterMotorsCommand());
    motorControl.add("Shooter Motors Start", new StartShooterMotorsCommand());
    motorControl.add("Shooter Motors Stop", new StopShooterMotorsCommand());
    
    ShuffleboardLayout motorSpeed = teleopTab.getLayout("Motor Speed", BuiltInLayouts.kList)
      .withPosition(2, 0).withSize(2, 3)
      .withProperties(Map.of("Label Position", "HIDDEN"));

    // motorSpeed.add("Increase Shooter Motor Speed 50", new IncreaseShooterMotorSpeed50());
    motorSpeed.add("Increase Shooter Motor Speed 100", new IncreaseShooterMotorSpeed100());
    motorSpeed.add("Increase Shooter Motor Speed 500", new IncreaseShooterMotorSpeed500());
    motorSpeed.add("Increase Shooter Motor Speed 1000", new IncreaseShooterMotorSpeed1000());
    // motorSpeed.add("Decrease Shooter Motor Speed 50", new DecreaseShooterMotorSpeed50());
    motorSpeed.add("Decrease Shooter Motor Speed 100", new DecreaseShooterMotorSpeed100());
    motorSpeed.add("Decrease Shooter Motor Speed 500", new DecreaseShooterMotorSpeed500());
    motorSpeed.add("Decrease Shooter Motor Speed 1000", new DecreaseShooterMotorSpeed1000());
    motorSpeed.add("High Goal Speed Set", new SetShooterMotorSpeedHighGoal());

    // maxSpeed = motorSpeed.add("Speed Slider", 1)
    //   .withWidget(BuiltInWidgets.kNumberSlider)
    //   .withProperties(Map.of("min", 0, "max", 1))
    //   .getEntry();

    ShuffleboardLayout ramControl = teleopTab.getLayout("Shooter Stuffz", BuiltInLayouts.kList)
      .withPosition(4, 0).withSize(2, 3)
      .withProperties(Map.of("Label Position", "HIDDEN"));

    ramControl.add("Shoot", new ShootOneBallCommand());
    ramControl.add("Load", new ShooterLoadCommand());
    ramControl.add("Shoot Lower Goal All", new LowerGoalShootCommand());
    ramControl.add("Shoot Lower Goal Shoot 10 Balls", new LowerGoalShootALotCommand());
    ramControl.add("Shoot High Goal All", new ShootCommand());
    ramControl.add("Line Up on goal", new LineUpOnGoal());
    ramControl.add("Rotate to Goal", new RotateToGoal());
    ramControl.add("Shoot High And Rotate to goal", new ShootHighAndAimOnGoal());

    ShuffleboardLayout Misc = teleopTab.getLayout("Misc", BuiltInLayouts.kList)
      .withPosition(6, 0).withSize(2, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));

    Misc.add("Limelight", new LimelightCommand(new LimelightSubsystem()));
    Misc.add("Intake Extend", new ExtendIntakePiston());
    Misc.add("Intake Retract", new RetractIntakePiston());
    Misc.add("Calibrate Gyro", new CalibrateGyro());

    teleTime = teleopTab.add("Time Left", 135)
      .withWidget(BuiltInWidgets.kDial)
      .withProperties(Map.of("min", 0, "max", 135))
      .withPosition(0, 2).withSize(2, 1)
      .getEntry();

    endgameTab = Shuffleboard.getTab("Endgame");
    // endgameTab.add("Deploy hook", false);
    // endgameTab.add("Winch left", false);
    // endgameTab.add("Winch right", false);
    // endgameTab.add("Start Climber Motors Together Up" , new StartClimberMotorsTogetherUpCommand());
    // endgameTab.add("Stop Climber Motors Together" , new StopClimberMotorsTogetherCommand());
    // endgameTab.add("Start Climber Motors Together Down" , new StartClimberMotorsTogetherDownCommand());
    // endgameTab.add("Start Climber Motors Together Down Slow" , new StartClimberMotorsTogetherDownSlowCommand());
    // endgameTab.add("Start Left Climber Up" , new StartClimberMotorAUpCommand());
    // endgameTab.add("Stop Left Climber" , new StopClimberMotorACommand());
    // endgameTab.add("Start Left Climber Down" , new StartClimberMotorADownCommand());
    // endgameTab.add("Start Right Climber Up" , new StartClimberMotorBUpCommand());
    // endgameTab.add("Stop Right Climber" , new StopClimberMotorBCommand());
    // endgameTab.add("Start Right Climber Down" , new StartClimberMotorBDownCommand());
    // endgameTab.add("Release the Winch", new ReleaseWinchCommand());
    // endgameTab.add("Engage the Winch", new EngageWinchCommand());
    // endgameTab.add("Go Right Slowly", new ClimbingMovingRight());
    // endgameTab.add("Go Left Slowly", new ClimbingMovingLeft());
    // endgameTab.add("Stop Drivetrain", new ClimbingMovingStop());
    ShuffleboardLayout BothMotors = endgameTab.getLayout("BothMotors", BuiltInLayouts.kList)
      .withPosition(0, 0).withSize(3, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));
    BothMotors.add("Start Climber Motors Together Up" , new StartClimberMotorsTogetherUpCommand());
    BothMotors.add("Stop Climber Motors Together" , new StopClimberMotorsTogetherCommand());
    BothMotors.add("Start Climber Motors Together Down" , new StartClimberMotorsTogetherDownCommand());

    ShuffleboardLayout LeftMotor = endgameTab.getLayout("LeftMotor", BuiltInLayouts.kList)
      .withPosition(3, 0).withSize(2, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));
    LeftMotor.add("Start Left Climber Up" , new StartClimberMotorAUpCommand());
    LeftMotor.add("Stop Left Climber" , new StopClimberMotorACommand());
    LeftMotor.add("Start Left Climber Down" , new StartClimberMotorADownCommand());

    ShuffleboardLayout RightMotor = endgameTab.getLayout("RightMotor", BuiltInLayouts.kList)
      .withPosition(5, 0).withSize(2, 2)
      .withProperties(Map.of("Label Position", "HIDDEN"));
    RightMotor.add("Start Right Climber Up" , new StartClimberMotorBUpCommand());
    RightMotor.add("Stop Right Climber" , new StopClimberMotorBCommand());
    RightMotor.add("Start Right Climber Down" , new StartClimberMotorBDownCommand());

    ShuffleboardLayout Winches = endgameTab.getLayout("Winches", BuiltInLayouts.kList)
      .withPosition(7, 0).withSize(2, 1)
      .withProperties(Map.of("Label Position", "HIDDEN"));
    Winches.add("Release the Winch", new ReleaseWinchCommand());
    Winches.add("Engage the Winch", new EngageWinchCommand());

    ShuffleboardLayout Driving = endgameTab.getLayout("Driving", BuiltInLayouts.kList)
    .withPosition(7, 1).withSize(2, 2)
    .withProperties(Map.of("Label Position", "HIDDEN"));
    Driving.add("Go Right Slowly", new ClimbingMovingRight());
    Driving.add("Go Left Slowly", new ClimbingMovingLeft());
    Driving.add("Stop Drivetrain", new ClimbingMovingStop());

    endTime = endgameTab.add("Time Left", 135)
      .withWidget(BuiltInWidgets.kDial)
      .withProperties(Map.of("min", 0, "max", 135))
      .withPosition(0, 2).withSize(2, 1)
      .getEntry();

    // TODO: Add controls for end game climb
    // ShuffleboardTab EndGame = Shuffleboard.getTab("Endgame");
    // EndGame.add("Start Climber Motors Together" , new StartClimberMotorsTogetherCommand());
    // EndGame.add("Stop Climber Motors Together" , new StopClimberMotorsTogetherCommand());
    // EndGame.add("Start Left Climber" , new StartClimberMotorACommand());
    // EndGame.add("Stop Left Climber" , new StopClimberMotorACommand());
    // EndGame.add("Start Right Climber" , new StartClimberMotorBCommand());
    // EndGame.add("Stop Right Climber" , new StopClimberMotorBCommand());

    
  }
  public static double getSliderSpeed() {
    double shooterSpeed = maxSpeed.getDouble(0.0);
    SmartDashboard.putNumber("Logging Shooter Speed: ", shooterSpeed);
    return shooterSpeed;
  }

  public static String getAutoStr() {
    String autoStr = autoNum.getString("high");
    SmartDashboard.putString("AutoNum", autoStr);
    return autoStr;
  }

  public static void PutIsBox(boolean boo){
    SmartDashboard.putBoolean("Limelight Box", boo);
  }

  public static void putTimeRemaining(double time){
    teleTime.forceSetDouble(time);
    endTime.forceSetDouble(time);
  }
}