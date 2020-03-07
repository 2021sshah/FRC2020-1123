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
  boolean Step2 = false;
  boolean Step3 = false;
  boolean Step4 = false;
  boolean Step5 = false;

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
      if(shoothigh.isFinished() == true){
        shoothigh.end(true);
        Step1 = false;
        Step2 = true;
        StartTime = Timer.getFPGATimestamp();
      }
    }
    if(Step2 == true){
      m_subsystemDrive.driveCartesian(1, 0, 0.25, 0.75); // Slams Right into Wall
      if(Timer.getFPGATimestamp()-StartTime > 1){
        Step2 = false;
        Step3 = true;
        StartTime2 = Timer.getFPGATimestamp();
      }
    }
    if(Step3 == true){
      m_subsystemDrive.driveCartesian(1, 0, 0, 0.5); // Into Wall, Calibrate
      if(Timer.getFPGATimestamp()-StartTime2 > 2 ){
        m_subsystemDrive.driveCartesian(0, 0, 0, 0);
        Step3 = false;
        Step4 = true;
        calibrateGyro.initialize();
        Intake.initialize();
        StartTime3 = Timer.getFPGATimestamp();
      }
    }
    if(Step4 == true){
      m_subsystemDrive.FieldCartesian(-1, 0, 0, 0.8, RobotContainer.getInstance().Gyro.getAngle()); // Back off wall
      if(Timer.getFPGATimestamp()-StartTime3>0.4){
        m_subsystemDrive.driveCartesian(0, 0, 0, 0);
        Step4 = false;
        Step5 = true;
        StartTime4 = Timer.getFPGATimestamp();
      }
    }
    if(Step5 == true){
      Intake.execute(); // Go for Kessel Run Balls
      m_subsystemDrive.FieldCartesian(0, -5, 0, 0.45, RobotContainer.getInstance().Gyro.getAngle());
      if(Timer.getFPGATimestamp()-StartTime4 > 3){ // We're DONE!!
        Step1 = false;
        Step2 = false;
        Step3 = false;
        Step4 = false;
        Step5 = false;
        m_subsystemDrive.driveCartesian(0,0,0,0);
      }
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