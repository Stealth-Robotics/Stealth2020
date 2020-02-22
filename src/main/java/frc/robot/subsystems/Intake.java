
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase 
{
    private final SpeedControllerGroup intake;

    private final Solenoid deployPistons;

    public double fuelCount;
    public boolean breakTracker;

    //PowerDistributionPanel PDP;

    /**
     * Creates a new Intake.
     */
    public Intake() 
    {
        //this.PDP = PDP;

        WPI_TalonSRX mainIntake = new WPI_TalonSRX(RobotMap.Intake);
        mainIntake.setInverted(true);
        intake = new SpeedControllerGroup(mainIntake, new WPI_TalonSRX(RobotMap.IntakeHelper));

        deployPistons = new Solenoid(RobotMap.PCM, RobotMap.IntakeDeployPCMChannel);
    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }

    /**
     * Sets the intake speed to 1
     */
    public void run()
    {
        intake.set(0.7);
    }

    /**
     * Stops the intake
     */
    public void stopIntake()
    {
        intake.set(0);
    }

    /**
     * Toggles the deployment state of the intake
     */
    public void toggle()
    {
        deployPistons.set(!deployPistons.get());
    }
}
