package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;
import frc.util.StopWatch;

public class Shooter extends SubsystemBase 
{
    private final SpeedControllerGroup shooter;

    private final CANSparkMax shooter1;
    private final CANSparkMax shooter2;

    private final CANEncoder shooterEncoder;

    protected final WPI_TalonSRX hood;

    private final WPI_TalonSRX belt;

    private final PIDController shooterController;
    
    // protected final CANCoder hoodEncoder;
    private final PIDController hoodController;

    private final DigitalInput beamBreak2;
    private final DigitalInput beamBreak3;

    private boolean enabled;

    //PowerDistributionPanel PDP;

    /**
     * Creates a new Shooter.
     */
    public Shooter() 
    {
        shooter1 = new CANSparkMax(RobotMap.Shooter1, MotorType.kBrushless);
        shooter2 = new CANSparkMax(RobotMap.Shooter2, MotorType.kBrushless);
        shooter2.setInverted(true);

        shooter = new SpeedControllerGroup(shooter1, shooter2);

        shooterEncoder = shooter1.getEncoder();

        shooterController = new PIDController(Constants.shooterkP, Constants.shooterkI, Constants.shooterkD);
        shooterController.setTolerance(100);

        hood = new WPI_TalonSRX(RobotMap.Hood);
        
        belt = new WPI_TalonSRX(RobotMap.Belt2);

        // hoodEncoder = new CANCoder(RobotMap.hood);
        hoodController = new PIDController(Constants.hoodkP, Constants.hoodkI, Constants.hoodkD);
        hoodController.setTolerance(3);

        beamBreak2 = new DigitalInput(RobotMap.BeamBreak2);
        beamBreak3 = new DigitalInput(RobotMap.BeamBreak3);

        enabled = false;

        //TODO: Remove after testing and uncomment periodic code
        shooter.set(-1);
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run

        /*
        hood.set(hoodController.calculate(hood.getSelectedSensorPosition(0)));
        System.out.println(hood.getSelectedSensorPosition(0));

        if (enabled)
        {
            shooter.set(-shooterController.calculate(shooterEncoder.getVelocity()));
        }
        else
        {
            shooter.set(0);
        }
        */
    }

    /**
     * Checks if the shooter is enabled (spinning)
     * 
     * @return Returns boolean true if enabled
     */
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * Runs the belt connected to the shooter in the forward direction
     */
    public void runBelt()
    {
        belt.set(1);
    }

    /**
     * Stops the belt connected to the shooter
     */
    public void stopBelt()
    {
        belt.set(0);
    }

    /**
     * Reverses the belt connected to the shooter
     */
    public void reverseBelt()
    {
        belt.set(-1);
    }

    /**
     * Sets the speed of the shooter
     * 
     * @param speed The speed to set
     */
    public void setShooterSpeed(double speed) 
    {
        shooterController.setSetpoint(-speed);
    }

    /**
     * Sets the angle to aim the shooter hood
     * 
     * @param angle The angle to set, based on the forward hood edge
     */
    public void setHoodPos(double angle)
    {
        System.out.println("Target: " + (Math.PI / 4 - angle) * Constants.ticksPerRadian);
        hoodController.setSetpoint((Math.PI / 4 - angle) * Constants.ticksPerRadian);
    }

    /**
     * Checks if the shooter is at the desired speed
     * 
     * @return if the shooter is at the desired speed
     */
    public boolean shooterAtSpeed()
    {
        return shooterController.atSetpoint();
    }

    /**
     * Checks if the hood is at the desired angle
     * 
     * @return if the hood is at the desired angle
     */
    public boolean hoodAimed()
    {
        return hoodController.atSetpoint();
    }

    /**
     * Tells the shooter to run
     */
    public void enable()
    {
        shooterController.reset();
        enabled = true;
    }
    
    /**
     * Tells the shooter to stop
     */
    public void disable()
    {
        enabled = false;
    }


    //TODO: Rework InitHoodPosition
    /**
     * Allows the shooter to zero itself at the start of the match
     */
    /*
    public void initializePosition()
    {
        double previousEncoderPosition = hood.getSelectedSensorPosition(0);
        
        hood.set(-0.1);
        StopWatch timer = new StopWatch(1000);
        StopWatch  timerShort = new StopWatch(50);

        while(!timer.isExpired())
        {
            while(!timerShort.isExpired());
            // if(previousEncoderPosition == hoodEncoder.getPosition())
            if(previousEncoderPosition == hood.getSelectedSensorPosition(0))
            {
                break;
            }
                    
        }

        hood.set(0);
        hood.getSelectedSensorPosition(0);
    }*/

    /**
     * Gets if the beam break at the bottom of the shooter belt is triggered
     * 
     * @return If triggered
     */
    public boolean getBeamBreak2()
    {
        return !beamBreak2.get(); //true == no break
    }

    /**
     * Gets if the beam break at the top of the shooter belt is triggered
     * 
     * @return If triggered
     */
    public boolean getBeamBreak3()
    {
        return !beamBreak3.get();
    }

    /*public void VoltageCheck()
    {
        if(PDP.getCurrent(RobotMap.shooter1PDPChannel) > Constants.NEOVoltageLimit
        || PDP.getCurrent(RobotMap.shooter2PDPChannel) > Constants.NEOVoltageLimit)
        {
            shooter.setVoltage(0);
        }

        if(PDP.getCurrent(RobotMap.hoodPDPChannel) > Constants.Neverest60VoltageLimit)
        {
            hood.setVoltage(0);
        }

        if(PDP.getCurrent(RobotMap.belt1PDPChannel) > Constants.RedlineVoltageLimit
        ||PDP.getCurrent(RobotMap.belt2PDPChannel) > Constants.RedlineVoltageLimit)
        {
            belt.setVoltage(0);
        }
    }*/
}
