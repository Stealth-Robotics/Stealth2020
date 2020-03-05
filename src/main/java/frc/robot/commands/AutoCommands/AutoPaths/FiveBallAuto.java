package frc.robot.commands.AutoCommands.AutoPaths;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoCommands.DriveForTicks;
import frc.robot.commands.MultiSubsystemCommands.ScoreFuel;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class FiveBallAuto extends SequentialCommandGroup {

  DriveBase driveBase;
  Shooter shooter;
  Belts belts;
  Limelight limelight;
  Intake intake;
  DistanceSensor distanceSensor;

  /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on
   * 
   */

  public FiveBallAuto(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, Intake intake,
      DistanceSensor distanceSensor) 
  {
    this.driveBase = driveBase;
    this.shooter = shooter;
    this.belts = belts;
    this.limelight = limelight;
    this.intake = intake;
    this.distanceSensor = distanceSensor;
  }
  
  @Override
  public void initialize()
  {
    addCommands
    (
      // Drive forward the specified distance
      new ScoreFuel(driveBase, shooter, belts, limelight, distanceSensor, intake),
      new RunCommand(() -> intake.toggle()),
      new DriveForTicks(-5, driveBase),
      new DriveForTicks(5, driveBase),
      new ScoreFuel(driveBase, shooter, belts, limelight, distanceSensor, intake)
    );
  }
}