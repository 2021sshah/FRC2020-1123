/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.GyroSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.MecanumDriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.logging.Logger;

/**
 * An example command that uses an example subsystem.
 */
public class AutonomousShootAndPickUpBalls extends CommandBase {
  private final MecanumDriveSubsystem m_subsystemDrive;
  private ShootHighAndAimOnGoalForAutonomous shoothigh = new ShootHighAndAimOnGoalForAutonomous();
  private CalibrateGyro calibrateGyro = new CalibrateGyro();
  private IntakeCommand Intake = new IntakeCommand();
  private final Logger logger = Logger.getLogger(this.getClass().getName());
  double StartTime = 0;
  double StartTime2 = 0;
  double StartTime3 = 0;
  double StartTime4 = 0;
  boolean Step1 = true;
  boolean Step1Canceled = false;
  boolean Step2 = false;
  boolean Step2Canceled = true;
  boolean Step3 = false;
  boolean Step3Canceled = true;
  boolean Step4 = false;
  boolean Step4Canceled = true;
  boolean Step5 = false;
  boolean Step5Canceled = true;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutonomousShootAndPickUpBalls(MecanumDriveSubsystem subsystem1) {
    m_subsystemDrive = subsystem1;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem1);   
    addRequirements(RobotContainer.getInstance().Gyro); 
    addRequirements(RobotContainer.getInstance().intakeSubsystem); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double newSpeed = 6900;
    RobotContainer.getInstance().shooter.setSpeed(newSpeed);
    shoothigh.initialize();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Step1 == true){
      shoothigh.execute();
    }
    if(shoothigh.isFinished() == true && Step1Canceled == false){
      shoothigh.end(true);
      Step1 = false;
      Step2 = true;
      Step3 = false;
      StartTime = Timer.getFPGATimestamp();
      Step1Canceled = true;
      Step2Canceled = false;
    }
    if(Step2 == true){
      m_subsystemDrive.driveCartesian(1, 0, 0.25, 0.75);
    }
    if(Timer.getFPGATimestamp()-StartTime > 1 && Step2Canceled == false){
      Step1 = false;
      Step2 = false;
      Step3 = true;
      Step4 = false;
      StartTime2 = Timer.getFPGATimestamp();
      Step2Canceled = true;
      Step3Canceled = false;
    }

     if(Step3 == true){
      m_subsystemDrive.driveCartesian(1, 0, 0, 0.5);
    }
    if(Timer.getFPGATimestamp()-StartTime2 > 2 && Step3Canceled == false){
      m_subsystemDrive.driveCartesian(0, 0, 0, 0);
      Step1 = false;
      Step2 = false;
      Step3 = false;
      Step4 = true;
      calibrateGyro.initialize();
      Intake.initialize();
      StartTime3 = Timer.getFPGATimestamp();
      Step3Canceled = true;
      Step4Canceled = false;
    }

    if(Step4 == true){
      m_subsystemDrive.FieldCartesian(-1, 0, 0, 0.8, RobotContainer.getInstance().Gyro.getAngle());
    }
    if(Timer.getFPGATimestamp()-StartTime3>0.4 && Step4Canceled == false){
      m_subsystemDrive.driveCartesian(0, 0, 0, 0);
      Step4Canceled = true;
      Step5Canceled = false;
      Step5 = true;
      StartTime4 = Timer.getFPGATimestamp();
    }
    if(Step5 == true){
      Intake.execute();
      m_subsystemDrive.FieldCartesian(0, -5, 0, 0.45, RobotContainer.getInstance().Gyro.getAngle());
      StartTime4 = Timer.getFPGATimestamp();
    }
    if(Timer.getFPGATimestamp()-StartTime4 > 3 && Step5Canceled == false){
      Step1 = false;
      Step2 = false;
      Step3 = false;
      Step4 = false;
      Step5 = false;
      m_subsystemDrive.driveCartesian(0,0,0,0);
    }
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystemDrive.driveCartesian(0,0,0,0);
    shoothigh.end(true);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((Timer.getFPGATimestamp()-StartTime)>15){
      logger.info("Ending Autonomous move forward");
      return true;
    }
    return false;
  }
}