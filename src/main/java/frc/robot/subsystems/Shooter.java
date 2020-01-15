package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase 
{
    Talon shooterMotor1;
    Talon shooterMotor2;

    /**
     * Creates a new Shooter.
     */
    public Shooter() 
    {

    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }
}
