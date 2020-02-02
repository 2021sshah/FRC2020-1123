package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.logging.Logger;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimelightCommand extends CommandBase {
    public LimelightCommand() { }
    
    public static void switchLedState() {
        Logger logger = Logger.getLogger(LimelightVision.class.getName());
        logger.info("Changing LED Status");
        Number ledState = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").getNumber(1);
        logger.info("Current LED state" + ledState.intValue());
        if (ledState.intValue() == 1) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        }
        else if (ledState.intValue() == 3) {
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
        }
    }
}