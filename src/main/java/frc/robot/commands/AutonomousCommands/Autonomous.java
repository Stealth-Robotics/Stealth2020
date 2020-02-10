/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.AutonomousCommands;

import frc.robot.commands.MultiSubsystemCommands.ScoreFuel;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * An example command that uses an example subsystem.
 */
public class Autonomous extends SequentialCommandGroup 
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DriveBase driveBase;
    private final Shooter shooter;
    private final Limelight limelight;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public Autonomous(DriveBase driveBase, Shooter shooter, Limelight limelight) 
    {
        this.driveBase = driveBase;
        this.shooter = shooter;
        this.limelight = limelight;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(driveBase, shooter, limelight);

    }
    public void execute()
    {

   
        
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() 
    {
        addCommands(new ScoreFuel(driveBase, shooter, limelight));
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) 
    {
        
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() 
    {
        return false;
    }
}
