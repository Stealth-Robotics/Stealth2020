package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
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
    protected final WPI_TalonSRX hood;

    private final WPI_TalonSRX belt;

    private final CANCoder shooterEncoder;
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
        //this.PDP = PDP;

        // shooter = new SpeedControllerGroup(new PWMSparkMax(RobotMap.shooter1), new PWMSparkMax(RobotMap.shooter2));
        shooter = new SpeedControllerGroup(new CANSparkMax(RobotMap.shooter1, MotorType.kBrushless), new CANSparkMax(RobotMap.shooter2, MotorType.kBrushless));
        hood = new WPI_TalonSRX(RobotMap.hood);
        belt = new WPI_TalonSRX(RobotMap.belt2);

        shooterEncoder = new CANCoder(RobotMap.shooter1);
        shooterController = new PIDController(Constants.shooterkP, Constants.shooterkI, Constants.shooterkD);
        shooterController.setTolerance(100);

        // hoodEncoder = new CANCoder(RobotMap.hood);
        hoodController = new PIDController(Constants.hoodkP, Constants.hoodkI, Constants.hoodkD);
        hoodController.setTolerance(3);

        beamBreak2 = new DigitalInput(RobotMap.beamBreak2);
        beamBreak3 = new DigitalInput(RobotMap.beamBreak3);

        enabled = false;

        shooter.set(-1); //TODO remove this 
    }

    @Override
    public void periodic() 
    {
        
        //VoltageCheck();

        // This method will be called once per scheduler run
        // hood.set(hoodController.calculate(hoodEncoder.getPosition()));
        // System.out.println(hoodEncoder.getPosition());
        /*hood.set(hoodController.calculate(hood.getSelectedSensorPosition(0)));
        System.out.println(hood.getSelectedSensorPosition(0));
        if (enabled)
        {
            shooter.set(-shooterController.calculate(shooterEncoder.getVelocity()));
        }
        else
        {
            shooter.set(0);
        }*/
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

    /**
     * Allows the shooter to zero itself at the start of the match
     */
    public void initializePosition()
    {
        // double previousEncoderPosition = hoodEncoder.getPosition();
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
    }

    //TODO find out how beam break voltage actually works

    /**
     * Gets if the beam break at the bottom of the shooter belt is triggered
     * 
     * @return If triggered
     */
    public boolean getBeamBreak2()
    {
        return !beamBreak2.get(); //true == no break
        // return false;
    }

    /**
     * Gets if the beam break at the top of the shooter belt is triggered
     * 
     * @return If triggered
     */
    public boolean getBeamBreak3()
    {
        return !beamBreak3.get();
        // return false;
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
