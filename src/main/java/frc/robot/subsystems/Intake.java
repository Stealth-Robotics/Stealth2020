
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase 
{
    

    private final Solenoid deployPistons;

    public double fuelCount;
    public boolean breakTracker; 

    WPI_TalonSRX intakeHelper;
    WPI_TalonSRX mainIntake;

    /**
     * Creates a new Intake.
     */
    public Intake() 
    {
         mainIntake = new WPI_TalonSRX(RobotMap.Intake);
         mainIntake.setInverted(true);
         intakeHelper = new WPI_TalonSRX(RobotMap.IntakeHelper);
        deployPistons = new Solenoid(RobotMap.PCM, RobotMap.IntakeDeployPCMChannel);
    }

    /**
     * Sets the intake speed to 1
     */
    public void run()
    {
        mainIntake.set(0.9);
        intakeHelper.set(0.9);
    }

    /**
     * Stops the intake
     */
    public void stopIntake()
    {
        mainIntake.set(0);
        intakeHelper.set(0);
    }

    /**
     * Reverses the intake
     */
    public void reverse()
    {
        mainIntake.set(-0.5);
        intakeHelper.set(-0.5);
    }

    /**
     * Toggles the deployment state of the intake
     */
    public void toggle()
    {
        deployPistons.set(!deployPistons.get());
    }

    /**
     * Sets the deployment state of the intake
     */
    public void setDeployment(boolean state)
    {
        deployPistons.set(state);
    }

    /**
     * Runs the green intake helper wheel
     
     */
    public void runHelperWheel(double wheelSpeed)
    {
      intakeHelper.set(wheelSpeed);
    }

    public void stopHelperWheel()
    {
        intakeHelper.set(0);
    }
    
}
