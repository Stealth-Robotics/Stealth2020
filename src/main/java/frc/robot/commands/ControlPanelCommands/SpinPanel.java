package frc.robot.commands.ControlPanelCommands;

import frc.robot.subsystems.PanelControl;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command spins the control panel 3 times
 */
public class SpinPanel extends CommandBase 
{

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final PanelControl panelControl;


    public SpinPanel(PanelControl panelControl) 
    {
        this.panelControl = panelControl;
    }
    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        panelControl.setWheelSpeed(0.5);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
       panelControl.setWheelSpeed(0.0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
       return false;
    }
}
