package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DistanceSensor extends SubsystemBase
{
    private final CANifier mCANIfier = new CANifier(7);
    double mReading = 0.0;
	double[][] mDutyCycleAndPeriods;

    public DistanceSensor()
    {
        mDutyCycleAndPeriods = new double[][]{new double[]{0, 0}, new double[]{0, 0},
                                                     new double[]{0, 0}, new double[]{0, 0}};
    }

    public double getDistance()
    {
        mCANIfier.getPWMInput(CANifier.PWMChannel.PWMChannel0, mDutyCycleAndPeriods[0]);

        mReading = (mReading/8)*7 + (mDutyCycleAndPeriods[0][0]/8);

        return mReading;
    }

    @Override
    public void periodic()
    {
        //System.out.println("Distance :" + getDistance());
    }
}