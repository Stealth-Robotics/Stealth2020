
package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PanelControl extends SubsystemBase 
{
    Talon wheelMotor;
    ColorSensorV3 colorSensor;

    /**
     * Creates a new ColorWheel.
     */
    public PanelControl() 
    {

    }

    @Override
    public void periodic() 
    {
        // This method will be called once per scheduler run
    }
}
