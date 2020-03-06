package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.logging.Logger;

import frc.robot.RobotContainer;

public class CalibrateGyro extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final Logger logger = Logger.getLogger(this.getClass().getName());
  int Time = 0;

  public CalibrateGyro() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(RobotContainer.getInstance().Gyro);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.getInstance().Gyro.calibrateGyro();

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //logger.info("Increase Motor Speed was called");
    RobotContainer.getInstance().Gyro.calibrateGyro();
    Time++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Time>3){
      return true;
    }
    return false;
  }
}