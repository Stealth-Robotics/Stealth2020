package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase 
{
    // IMPORTANT : Speed Controller Group Does Not Respect Inversion. Do not use Speed Controller Group For Shooter
    private final CANSparkMax shooter1;
    private final CANSparkMax shooter2;

    private final CANEncoder shooterEncoder;

    protected final WPI_TalonSRX hood;

    private final PIDController shooterController;
    
    // protected final CANCoder hoodEncoder;
    private final PIDController hoodController;

    private boolean enabled;

    //PowerDistributionPanel PDP;

    /**
     * Creates a new Shooter.
     */
    public Shooter() 
    {
        shooter1 = new CANSparkMax(RobotMap.Shooter1, MotorType.kBrushless);
        shooter2 = new CANSparkMax(RobotMap.Shooter2, MotorType.kBrushless);

        shooter2.follow(shooter1, true);

        shooterEncoder = shooter1.getEncoder();

        shooterController = new PIDController(Constants.shooterkP, Constants.shooterkI, Constants.shooterkD);
        shooterController.setTolerance(100);

        hood = new WPI_TalonSRX(RobotMap.Hood);
        hood.setSelectedSensorPosition(0);

        // hoodEncoder = new CANCoder(RobotMap.hood);
        hoodController = new PIDController(Constants.hoodkP, Constants.hoodkI, Constants.hoodkD);
        hoodController.setTolerance(20);

        enabled = false;
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run

        hood.set(hoodController.calculate(hood.getSelectedSensorPosition(0)));
        /*System.out.println(hood.getSelectedSensorPosition(0));
        if (enabled)
        {
            shooter.set(-shooterController.calculate(shooterEncoder.getVelocity()));
        }
        else
        {
            shooter.set(0);
        }*/
        //System.out.println("Hood Setpoint: " + hoodController.getSetpoint());
        //System.out.println("Hood Current: " + hood.getSelectedSensorPosition(0));
        //System.out.println("Hood Power: " + hood.get());

        // TODO : Put Shooter On PID
        //shooter1.set(shooterController.calculate(shooterEncoder.));
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
     * Sets the speed of the shooter through Shooter PID
     * 
     * @param speed The speed to set
     */
    public void setShooterSpeed(double speed) 
    {
        shooterController.setSetpoint(speed);
    }

    /**
     * Sets the speed of the shooter directly
     * 
     * @param speed The speed to set
     */
    public void setShooterSpeedDirect(double speed) 
    {
        shooter1.set(speed);
    }

    public void setHoodSpeedDirect(double speed) 
    {
        hood.set(speed);
    }

    /**
     * Sets the angle to aim the shooter hood
     * 
     * @param angle The angle to set, based on the forward hood edge, in radians
     */
    public void setHoodPos(double angle)
    {
        hoodController.setSetpoint(((Constants.maxAngle) - angle) * Constants.ticksPerRadian);
        hoodController.reset();
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
}