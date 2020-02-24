package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Belts extends SubsystemBase
{
    private final WPI_TalonSRX belt1;
    private final WPI_TalonSRX belt2;

    private final DigitalInput beamBreak1;
    private final DigitalInput beamBreak2;
    private final DigitalInput beamBreak3;

    private int ballCount;

    private NetworkTableEntry ballCountNet;
    
    public Belts()
    {
        beamBreak1 = new DigitalInput(RobotMap.BeamBreak1);
        beamBreak2 = new DigitalInput(RobotMap.BeamBreak2);
        beamBreak3 = new DigitalInput(RobotMap.BeamBreak3);

        belt1 = new WPI_TalonSRX(RobotMap.Belt1);
        belt2 = new WPI_TalonSRX(RobotMap.Belt2);

        ballCount = 0;
        
        

        //Get the default instance of NetworkTables that was created automatically
        //when your program starts
        NetworkTableInstance inst = NetworkTableInstance.getDefault();

        //Get the table within that instance that contains the data. There can
        //be as many tables as you like and exist to make it easier to organize
        //your data. In this case, it's a table called datatable.
        NetworkTable table = inst.getTable("datatable");

        //Get the entries within that table that correspond to the X and Y values
        //for some operation in your program.
        ballCountNet = table.getEntry("BallCount");
    }

    @Override
    public void periodic()
    {
        ballCountNet.setDouble(getBallCount());
    }

    public void runBelt1()
    {
        belt1.set(0.8);
    }

    public void runBelt2()
    {
        belt2.set(0.8);
    }

    public void runAllBelts()
    {
        belt1.set(0.8);
        belt2.set(0.8);
    }

    public void stopBelt1()
    {
        belt1.set(0);
    }

    public void stopBelt2()
    {
        belt2.set(0);
    }

    public void stopAllBelts()
    {
        belt2.set(0);
        belt1.set(0);
    }

    public void reverseBelt1()
    {
        belt1.set(-0.75);
    }

    public void reverseBelt2()
    {
        belt2.set(-0.8);
    }

    public void reverseAllBelts()
    {
        belt1.set(-0.8);
        belt2.set(-0.8);
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

    /**
     * Gets the state of the beam break at the bottom of second belt
     * 
     * @return If the beam is broken
     */
    public boolean getBeamBreak2()
    {
        return !beamBreak2.get();
        // return false;
    }

    /**
     * Gets the state of the beam break right before the shooter
     * 
     * @return If the beam is broken
     */
    public boolean getBeamBreak3()
    {
        return !beamBreak3.get();
        // return false;
    }

    /**
     * Adds one to the ball count
     */
    public void addBall()
    {
        ballCount++;
    }

    /**
     * Removes one from ball count
     */
    public void removeBall()
    {
        ballCount--;
    }

    /**
     * Resets the ball count to 0
     */
    public void resetBallCount()
    {
        ballCount = 0;
    }

    /**
     * returns the current ball count
     * 
     * @return returns the current ball count as an int
     */
    public int getBallCount()
    {
        return ballCount;
    }
}