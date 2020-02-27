/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.BeltsCommands;

import frc.robot.subsystems.Belts;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class ResetBelts extends CommandBase 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final Belts belts;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ResetBelts(Belts belts) 
    {
        this.belts = belts;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(belts);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        belts.reverseAllBelts();
    }

    @Override
    public void execute()
    {
        if (belts.getBeamBreak1())
        {
            belts.stopBelt1();
        }
        if (belts.getBeamBreak2())
        {
            belts.stopBelt2();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return belts.getBeamBreak1() && belts.getBeamBreak2();
    }
}
