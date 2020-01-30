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

    private final SpeedControllerGroup belts;

    private final Encoder shooterEncoder;
    private final PIDController shooterController;
    
    private final Encoder hoodEncoder;
    private final PIDController hoodController;

    private boolean enabled;

    /**
     * Creates a new Shooter.
     */
    public Shooter() 
    {
        shooter = new SpeedControllerGroup(new Talon(RobotMap.shooter1), new Talon(RobotMap.shooter2));
        hood = new Talon(RobotMap.hood);
        belts = new SpeedControllerGroup(new Talon(RobotMap.belt1), new Talon(RobotMap.belt2), new Talon(RobotMap.belt3));

        shooterEncoder = new Encoder(RobotMap.shooterEncoderPorts[0], RobotMap.shooterEncoderPorts[1]);
        shooterController = new PIDController(Constants.shooterkP, Constants.shooterkI, Constants.shooterkD);
        shooterController.setTolerance(100);

        hoodEncoder = new Encoder(RobotMap.hoodEncoderPorts[0], RobotMap.hoodEncoderPorts[1]);
        hoodController = new PIDController(Constants.hoodkP, Constants.hoodkI, Constants.hoodkD);
        hoodController.setTolerance(50);

        enabled = false;
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
        hood.set(hoodController.calculate(hoodEncoder.get()));
        if (enabled)
        {
            shooter.set(shooterController.calculate(shooterEncoder.getRate()));
        }
        else
        {
            shooter.set(0);
        }
    }

    public void runBelts(double speed)
    {
        belts.set(speed);
    }

    public void setShooterSpeed(double speed)
    {
        shooterController.setSetpoint(speed);
    }

    public void setHoodPos(double angle)
    {
        hoodController.setSetpoint(angle * Constants.ticksPerDegree);
    }

    public boolean shooterAtSpeed()
    {
        return shooterController.atSetpoint();
    }

    public boolean hoodAimed()
    {
        return hoodController.atSetpoint();
    }

    public void enable()
    {
        shooterController.reset();
        enabled = true;
    }

    public void disable()
    {
        enabled = false;
    }
}
