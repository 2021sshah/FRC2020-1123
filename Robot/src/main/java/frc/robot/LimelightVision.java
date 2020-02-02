package frc.robot;

import java.util.logging.Logger;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightVision extends SubsystemBase {
    public LimelightVision() { } // Static Methods

    public static void checkValues() {
        Double xval = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
        Double yval = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
        Double area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0.0);

        SmartDashboard.putNumber("Xval", xval);
        SmartDashboard.putNumber("Yval", yval);
        SmartDashboard.putNumber("Area", area);
    }

    public static void getArea() { // Not Really Necessary
        Double area = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0.0);
        SmartDashboard.putNumber("Area", area);
    }

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