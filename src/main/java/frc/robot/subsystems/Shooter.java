package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase 
{
    private final SpeedControllerGroup shooter;
    private final Talon hood;
    
    private final Encoder hoodEncoder;
    PIDController hoodController;

    /**
     * Creates a new Shooter.
     */
    public Shooter() 
    {
        shooter = new SpeedControllerGroup(new Talon(RobotMap.shooter1), new Talon(RobotMap.shooter2));
        hood = new Talon(RobotMap.hood);

        hoodEncoder = new Encoder(RobotMap.hoodEncoderPorts[0], RobotMap.hoodEncoderPorts[1]);
        hoodController = new PIDController(Constants.hoodkP, Constants.hoodkI, Constants.hoodkD);
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
        hood.set(hoodController.calculate(hoodEncoder.get()));
    }

    public void run(double speed)
    {
        shooter.set(speed);
    }

    public void setHoodPos(double angle)
    {
        hoodController.setSetpoint(angle * Constants.ticksPerDegree);
    }
}
