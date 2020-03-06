package frc.robot.commands.AutoCommands.AutoPaths;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

// import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.AutoCommands.DriveForTicks;
import frc.robot.commands.AutoCommands.TurnToAngle;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class SixBallAuto extends SequentialCommandGroup {

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

  public SixBallAuto(DriveBase driveBase, Shooter shooter, Belts belts, Limelight limelight, Intake intake,
      DistanceSensor distanceSensor) {
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
        // First Shot

        new TurnToAngle(-15, driveBase),
        new RunCommand(() -> limelight.SetLedMode(3)).withTimeout(0.5),
        new AlignWithTarget(driveBase, limelight, distanceSensor), new AimHood(shooter, distanceSensor, false),
        new RunCommand(() -> shooter.setShooterSpeedDirect(0.85)).withTimeout(3),
        new FireShooter(shooter, belts).withTimeout(5),
        new RunCommand(() -> shooter.setHoodPos(Constants.maxAngle)).withTimeout(0),

        // Gather Three Balls

        new TurnToAngle(0, driveBase));
        new ParallelDeadlineGroup(
            new DriveForTicks(1500, driveBase),
            new RunCommand(() -> intake.run(), intake),
        new WaitCommand(2),
        new DriveForTicks(-1700, driveBase));

        // Second Shot

        // new TurnToAngle(-15, driveBase),
        // new RunCommand(() -> limelight.SetLedMode(3)).withTimeout(0.5),
        // new AlignWithTarget(driveBase, limelight, distanceSensor), new AimHood(shooter, distanceSensor, false),
        // new RunCommand(() -> shooter.setShooterSpeedDirect(0.85)).withTimeout(3),
        // new FireShooter(shooter, belts).withTimeout(5),
        // new RunCommand(() -> shooter.setHoodPos(Constants.maxAngle)).withTimeout(0)
    //);
  }
}