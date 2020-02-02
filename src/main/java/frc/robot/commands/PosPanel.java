
package frc.robot.commands;

import frc.robot.subsystems.PanelControl;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command rotates the control panel to the desired location
 */
public class PosPanel extends CommandBase 
{
    String color;

    double detections;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final PanelControl panelControl;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public PosPanel(PanelControl panelControl) 
    {
        this.panelControl = panelControl;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(panelControl);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        panelControl.togglePosition();

        color = DriverStation.getInstance().getGameSpecificMessage();
        String currentColor = panelControl.getColor();
        if (currentColor.equals("R") && color.equals("Y")
                || currentColor.equals("Y") && color.equals("B")
                || currentColor.equals("B") && color.equals("G")
                || currentColor.equals("G") && color.equals("R"))
        {
            panelControl.setWheelSpeed(-1);
        }
        else
        {
            panelControl.setWheelSpeed(1);
        }
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        if (panelControl.getColor().equals(color))
        {
            detections++;
        }
        else
        {
            detections = 0;
        }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        panelControl.setWheelSpeed(0);
        panelControl.togglePosition();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return detections >= 10;
    }
}
