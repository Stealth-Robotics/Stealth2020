package frc.robot.commands.AutoCommands.AutoPaths;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoCommands.DriveForInches;
import frc.robot.commands.AutoCommands.TurnToAngle;
import frc.robot.commands.MultiSubsystemCommands.ScoreFuel;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
public class SixBallAutoCommandGroup extends SequentialCommandGroup {

  /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on 
   * 
   */
  public SixBallAutoCommandGroup(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, Intake intake, DistanceSensor distanceSensor) {
    addCommands
    (
        // Drive forward the specified distance
        //3 ball auto
      new ScoreFuel(driveBase, shooter, belts, limelight, distanceSensor),
      new TurnToAngle(0, driveBase),
      new DriveForInches(-5, driveBase)//,
      //comment out for 6 ball auto, TODO: TEST
      /*
      new RunCommand(() -> intake.toggle()),
      new DriveForInches(-5, driveBase),
      new RunCommand(() -> intake.toggle()),
      new DriveForInches(5, driveBase),
      new ScoreFuel(driveBase, shooter, belts, limelight, distanceSensor),
      new TurnToAngle(0, driveBase)
      */
    );
  }
}