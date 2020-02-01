
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase 
{
    private final WPI_TalonSRX intake;

    /**
     * Creates a new Intake.
     */
    public Intake() 
    {
        intake = new WPI_TalonSRX(RobotMap.intake);
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    public void runIntake()
    {
        intake.set(1);
    }

    public void stopIntake()
    {
        intake.set(0);
    }
}
