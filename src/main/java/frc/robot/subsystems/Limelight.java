package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Limelight extends SubsystemBase 
{
    NetworkTable limelightTableEntry;

    public Limelight()
    {
        limelightTableEntry = NetworkTableInstance.getDefault().getTable("limelight");

        intializeLimelight();
    }

    @Override
    public void periodic()
    {
        //System.out.println(getTargetDistance() * 100 / 2.54 / 12);
    }

    /**
     * Intializes Limelight LED Mode & Camera Mode
     */
    public void intializeLimelight()
    {
        SetLedMode(3);
        SetCamMode(0);
    }

    /**
     * Checks if Limelight has a valid target
     * 
     * @return if the Limelight has a valid target
     */
    public boolean hasValidTarget()
    {
        return limelightTableEntry.getEntry("tv").getDouble(0) == 1;
    }

    /**
     * Returns Horizontal Offset Of The Target From The Crosshair
     * 
     * @return If the limelight has a valid target, it will return the horizontal offset of the target from the crosshair. If the limelight doesn't have a valid target, it will return NaN.
     */
    public double getTargetHorizontalOffset()
    {
        return hasValidTarget() ? limelightTableEntry.getEntry("tx").getDouble(0) : Double.NaN;
    }

    /**
     * The angle between the camera and the target
     * 
     * @return The angle, in radians
     */
    public double getTargetVerticalOffset()
    {
        return hasValidTarget() ? limelightTableEntry.getEntry("ty").getDouble(0) * Math.PI / 180 : Double.NaN;
    }

    public double getTargetArea()
    {
        return hasValidTarget() ? limelightTableEntry.getEntry("ta").getDouble(0) : Double.NaN;
    }

    /**
     * Gets the distance from the shooter to the power port
     * 
     * @return The distance, in meters
     */
    public double getTargetDistance()
    {
        return (Constants.crosshairHeight - Constants.cameraHeight) / Math.tan(Constants.cameraAngle + getTargetVerticalOffset()) ;
    }

    public void GetCamMode(double defaultValue)
    {
        limelightTableEntry.getEntry("camMode").getDouble(defaultValue);
    }

    public void GetLedMode(double defaultValue)
    {
        limelightTableEntry.getEntry("ledMode").getDouble(defaultValue);
    }

    public void SetCamMode(double camMode)
    {
        limelightTableEntry.getEntry("camMode").setNumber(camMode);
    }

    public void SetLedMode(double ledMode)
    {
        limelightTableEntry.getEntry("ledMode").setNumber(ledMode);
    }
}