
package frc.robot.subsystems;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PanelControl extends SubsystemBase 
{
    Talon wheelMotor;
    Talon wheelMotor2; 
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

    public void setWheelSpeed(double speed)
    {
        wheelMotor.set(speed);
    }

    /**
     * Gets the current color of the control panel
     * 
     * @return The color of control panel
     */
    public String getColor()
    {
        double red = colorSensor.getRed();
        double blue = colorSensor.getBlue();
        double green = colorSensor.getGreen();

        if (red > 100)
        {
            return "B";
        }
        else if (green > 100)
        {
            return "Y";
        }
        else if (blue > 100)
        {
            return "R";
        }
        else
        {
            return "G";
        }
    }
}
