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

    public double getDistance()
    {
        mCANIfier.getPWMInput(CANifier.PWMChannel.PWMChannel0, mDutyCycleAndPeriods[0]);

        return mDutyCycleAndPeriods[0][0]-139.7-100;   
    }

    public double getFilteredDistance()
    {
        mCANIfier.getPWMInput(CANifier.PWMChannel.PWMChannel0, mDutyCycleAndPeriods[0]);

        mReading = (mReading / 8) * 7 + (mDutyCycleAndPeriods[0][0] / 8);

        return mReading-139.7-100;
    }
}