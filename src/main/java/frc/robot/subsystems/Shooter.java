package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.StopWatch;

public class Shooter extends SubsystemBase 
{
    private final SpeedControllerGroup shooter;
    protected final WPI_TalonSRX hood;

    private final SpeedControllerGroup belt;

    private final CANCoder shooterEncoder;
    private final PIDController shooterController;
    
    protected final CANCoder hoodEncoder;
    private final PIDController hoodController;

    private final AnalogInput beamBreak2;
    private final AnalogInput beamBreak3;

    private boolean enabled;

    /**
     * Creates a new Shooter.
     */
    public Shooter() 
    {
        shooter = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.shooter1), new WPI_TalonSRX(RobotMap.shooter2));
        hood = new WPI_TalonSRX(RobotMap.hood);
        belt = new SpeedControllerGroup(new WPI_TalonSRX(RobotMap.belt1), new WPI_TalonSRX(RobotMap.belt2));

        shooterEncoder = new CANCoder(RobotMap.shooter1);
        shooterController = new PIDController(Constants.shooterkP, Constants.shooterkI, Constants.shooterkD);
        shooterController.setTolerance(100);

        hoodEncoder = new CANCoder(RobotMap.hood);
        hoodController = new PIDController(Constants.hoodkP, Constants.hoodkI, Constants.hoodkD);
        hoodController.setTolerance(50);

        beamBreak2 = new AnalogInput(RobotMap.beamBreak2);
        beamBreak3 = new AnalogInput(RobotMap.beamBreak3);

        enabled = false;
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
        hood.set(hoodController.calculate(hoodEncoder.getPosition()));
        if (enabled)
        {
            shooter.set(shooterController.calculate(shooterEncoder.getVelocity()));
        }
        else
        {
            shooter.set(0);
        }
    }

    public void runBelt()
    {
        belt.set(1);
    }

    public void stopBelt()
    {
        belt.set(0);
    }

    public void reverseBelt()
    {
        belt.set(-1);
    }

    public void setShooterSpeed(final double speed) 
    {
        shooterController.setSetpoint(speed);
    }

    public void setHoodPos(final double angle)
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

    public void initializePosition()
    {
        double previousEncoderPosition = hoodEncoder.getPosition();
        

        
        hood.set(-0.1);
        StopWatch timer = new StopWatch(1000);
        StopWatch  timerShort = new StopWatch(50);

        while(!timer.isExpired())
        {
            while(!timerShort.isExpired());
            if(previousEncoderPosition == hoodEncoder.getPosition())
            {
                break;
            }
                    
        }

        hood.set(0);
        hoodEncoder.setPosition(0);
    }

    //TODO find out how beam break voltage actually works

    public boolean getBeamBreak2()
    {
        return beamBreak2.getVoltage() < 1; 
    }

    public boolean getBeamBreak3()
    {
        return beamBreak3.getVoltage() < 1;
    }
}
