package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class DistanceSensor extends SubsystemBase
{
    private final CANifier mCANIfier;
    double mReading = 0.0;
	double[][] mDutyCycleAndPeriods;

    public DistanceSensor()
    {
        mCANIfier = new CANifier(RobotMap.DistanceSensor);
        mDutyCycleAndPeriods = new double[][]
        {
            new double[]{0, 0}, new double[]{0, 0},
            new double[]{0, 0}, new double[]{0, 0}
        };
    }

    /**
     * Returns the distance from the distance sensor in MM
     * 
     * @return Distance in MM
     */
    public double getDistance()
    {
        mCANIfier.getPWMInput(CANifier.PWMChannel.PWMChannel0, mDutyCycleAndPeriods[0]);

        return mDutyCycleAndPeriods[0][0]-139.7-100;   
    }

    /**
     * Returns fused distance from limelight and distance sensor in MM
     * 
     * Accounts for if the distance sensor is being blocked by another robot.
     * 
     * @param limelight
     * @return Returns fused distance in MM
     */
    public double getFusedDistance(Limelight limelight)
    {
        double m_distance = getDistance();
        double lime_distance = limelight.getTargetDistance() * 1000;
        double tolarance = 100;

        double output = (m_distance + lime_distance) / 2;

        if(Math.abs(m_distance - lime_distance) > tolarance){
            output = lime_distance;
        }

        return output;
    }

    /**
     * Returns the delta from the LIDAR measured distance to the limelight estimated distance
     * 
     * @param limelight
     * @return Delta from LIDAR measured distance to limelight distance in MM
     */
    public double getLimeDistanceDelta(Limelight limelight)
    {
        return getDistance() - (limelight.getTargetDistance() * 1000);
    }

    public double getFilteredDistance()
    {
        mCANIfier.getPWMInput(CANifier.PWMChannel.PWMChannel0, mDutyCycleAndPeriods[0]);

        mReading = (mReading / 8) * 7 + (mDutyCycleAndPeriods[0][0] / 8);

        return mReading-139.7-100;
    }
}