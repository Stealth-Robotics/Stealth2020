
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase 
{
    private final SpeedControllerGroup intake;

    private final WPI_TalonSRX belt;

    private final Solenoid deployPistons;

    private final DigitalInput beamBreak1;

    public double fuelCount;
    public boolean breakTracker;

    //PowerDistributionPanel PDP;

    /**
     * Creates a new Intake.
     */
    public Intake() 
    {
        //this.PDP = PDP;

        WPI_TalonSRX mainIntake = new WPI_TalonSRX(RobotMap.intake);
        mainIntake.setInverted(true);
        intake = new SpeedControllerGroup(mainIntake, new WPI_TalonSRX(RobotMap.intakeHelper));
        belt = new WPI_TalonSRX(RobotMap.belt1);

        deployPistons = new Solenoid(RobotMap.PCM, RobotMap.intakeDeployPistons);

        beamBreak1 = new DigitalInput(RobotMap.beamBreak1);

        fuelCount = 0;
        breakTracker = false;
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
     * Sets the belt connected to the intake to 1
     */
    public void runBelt()
    {
        belt.set(1);
    }

    /**
     * Stops the belt connected to the belt
     */
    public void stopBelt()
    {
        belt.set(0);
    }

    /**
     * Sets the belt connected to the belt to -1
     */
    public void reverseBelt()
    {
        belt.set(-1);
    }

    /**
     * Toggles the deployment state of the intake
     */
    public void toggle()
    {
        deployPistons.set(!deployPistons.get());
    }

    /**
     * Gets the state of the beam break at the front of the belts
     * 
     * @return If the beam is broken
     */
    public boolean getBeamBreak1()
    {
        return !beamBreak1.get();
        // return false;
    }

    /*public void VoltageCheck() 
    {
        if(PDP.getCurrent(RobotMap.intakePDPChannel) > Constants.RedlineVoltageLimit)
        {
            intake.setVoltage(0);
		}
		
		if(PDP.getCurrent(RobotMap.belt3PDPChannel) > Constants.RedlineVoltageLimit)
		{
			belt.setVoltage(0);
		}   
    }*/
}
