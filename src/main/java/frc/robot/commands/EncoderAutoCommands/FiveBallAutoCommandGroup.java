package frc.robot.commands.EncoderAutoCommands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.IntakeCommands.IntakeFuel;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.DistanceSensor;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
public class FiveBallAutoCommandGroup extends SequentialCommandGroup {

  /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on
   * 
   */
  public FiveBallAutoCommandGroup(DriveBase drive, Shooter shooter, Belts belt, Limelight limelight, Intake intake, DistanceSensor distanceSensor) {
    addCommands(
        // Drive forward the specified distance
        new AlignWithTarget(drive, limelight),
        new AimHood(shooter, distanceSensor),
       new FireShooter(shooter, belt),
       new IntakeFuel(intake),
       new DriveBackwards(1000, drive),
       new DriveForward(1000, drive),
       new AlignWithTarget(drive, limelight),
       new AimHood(shooter, distanceSensor),
       new FireShooter(shooter, belt)



    );
        
        
  }

}