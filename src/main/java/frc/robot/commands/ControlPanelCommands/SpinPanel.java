package frc.robot.commands.ControlPanelCommands;

import frc.robot.subsystems.PanelControl;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * This command spins the control panel 3 times
 */
public class SpinPanel extends CommandBase 
{
    String lastColor;

    int greenCounter;

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final PanelControl panelControl;

    /**
     * Creates a new SpinPanel.
     *
     * @param subsystem The subsystem used by this command.
     */
    public SpinPanel(PanelControl panelControl) 
    {
        this.panelControl = panelControl;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(panelControl);
    }
    
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        panelControl.setWheelSpeed(0.5);
        System.out.println("We are spinning the control panel");
        lastColor = panelControl.getColor();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() 
    {
        String currentColor = panelControl.getColor();
        if (currentColor.equals("G") && !lastColor.equals("G"))
        {
            greenCounter ++;
            System.out.println("We have found the green on the control panel. The green");
        }
        lastColor = currentColor;
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
       panelControl.setWheelSpeed(0.0);
       System.out.printf("We have seen green 6 times , the color wheel has stopped spiining");
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
       return greenCounter > 6;
    }
}
