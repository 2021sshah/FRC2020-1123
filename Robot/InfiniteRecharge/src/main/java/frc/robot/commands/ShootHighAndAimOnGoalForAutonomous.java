package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import java.util.logging.Logger;

import frc.robot.RobotContainer;
import frc.robot.commands.RotateToGoal;


/**
 * An example command that uses an example subsystem.
 */
public class ShootHighAndAimOnGoalForAutonomous extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })

  private final Logger logger = Logger.getLogger(this.getClass().getName());
  int time = 0;
  int TimeSinceLastShot = 0;
  RotateToGoal Aim = new RotateToGoal();

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShootHighAndAimOnGoalForAutonomous() {
    addRequirements(RobotContainer.getInstance().shooter);
    addRequirements(RobotContainer.getInstance().intakeSubsystem);
    addRequirements(RobotContainer.getInstance().Gyro);
    // Use addRequirements() here to declare subsystem dependencies.
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    logger.info("got to motor Activate");
    RobotContainer.getInstance().shooter.SpinMotor(7400);
    RobotContainer.getInstance().intakeSubsystem.IntakeSlowHigh();
    RobotContainer.getInstance().shooter.ResetNumberOfBallsFired();
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").forceSetNumber(3);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    time++;
    RobotContainer.getInstance().shooter.SpinMotor(7200);
    if(Aim.isFinished() == false){
      Aim.execute();
    }
    if(RobotContainer.getInstance().Limelight.ifBox() == false || Aim.isFinished() == true){
      if(time>50){
        RobotContainer.getInstance().shooter.FireBallAndRetractHigh();
      }  
    }
  }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    logger.info("got to High Goal Shoot Stop");
    RobotContainer.getInstance().shooter.Stop();
    RobotContainer.getInstance().shooter.LoadBall();
    RobotContainer.getInstance().intakeSubsystem.Stop();
    RobotContainer.getInstance().shooter.ResetNumberOfBallsFired();
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").forceSetNumber(1);
    time = 0;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(RobotContainer.getInstance().shooter.getNumberOfBallsFired() >= 4){
      return true;
    }
    return false;
  }
}