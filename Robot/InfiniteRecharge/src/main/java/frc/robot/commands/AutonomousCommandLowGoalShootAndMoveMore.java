/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.MecanumDriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
// import jdk.javadoc.internal.tool.Start;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import java.util.logging.Logger;

/**
 * An example command that uses an example subsystem.
 */
public class AutonomousCommandLowGoalShootAndMoveMore extends CommandBase {
  private MecanumDriveSubsystem m_subsystemDrive;
  private LowerGoalShootForCommandAutonomous shootLow = new LowerGoalShootForCommandAutonomous();
  private final Logger logger = Logger.getLogger(this.getClass().getName());
  double StartTime;
  double StartTime2;
  double StartTime3;
  boolean InitShoot = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutonomousCommandLowGoalShootAndMoveMore(MecanumDriveSubsystem subsystem1) {
    m_subsystemDrive = subsystem1;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem1);
    addRequirements(RobotContainer.getInstance().Gyro);
    StartTime = Timer.getFPGATimestamp();
    
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // StartTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Timer.getFPGATimestamp()-StartTime < 1.5 && InitShoot == false){
      InitShoot = true;
      shootLow.initialize();
    }
    if((Timer.getFPGATimestamp()-StartTime)<4){
      // logger.info("in autonomous command");
      m_subsystemDrive.driveCartesian(0, 1, 0, 0.25);
    }
    else if(shootLow.isFinished() == false){
      shootLow.execute();
      RobotContainer.getInstance().Gyro.calibrateGyro();
      StartTime2 = Timer.getFPGATimestamp(); 
    }
    else if(Timer.getFPGATimestamp()-StartTime2<2){
      m_subsystemDrive.FieldCartesian(0, -1, 0, 0.5, RobotContainer.getInstance().Gyro.getAngle());
      shootLow.end(true);
    }
    else if(Timer.getFPGATimestamp()-StartTime3 < 3){
      m_subsystemDrive.FieldCartesian(0, -1, 0, 0.5, RobotContainer.getInstance().Gyro.getAngle());
    }
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      m_subsystemDrive.drivePolar(0,0,0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((Timer.getFPGATimestamp()-StartTime)>14){
      logger.info("Ending Autonomous move forward");
      return true;
    }
    return false;
  }
}