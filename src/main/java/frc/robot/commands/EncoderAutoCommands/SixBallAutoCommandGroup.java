package frc.robot.commands.EncoderAutoCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DrivebaseCommands.AlignWithTarget;
import frc.robot.commands.EncoderAutoCommands.DriveBackwards;
import frc.robot.commands.EncoderAutoCommands.DriveForward;
import frc.robot.commands.EncoderAutoCommands.TurnToAngle;
import frc.robot.commands.IntakeCommands.IntakeFuel;
import frc.robot.commands.ShooterCommands.AimHood;
import frc.robot.commands.ShooterCommands.FireShooter;
import frc.robot.subsystems.Belts;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.*;
public class SixBallAutoCommandGroup extends SequentialCommandGroup {

  /**
   * Creates a new ComplexAuto.
   *
   * @param drive The drive subsystem this command will run on 
   * 
   */
  public SixBallAutoCommandGroup(DriveBase drive, Shooter shooter, Belts belt, Limelight limelight, Intake intake, DistanceSensor distanceSensor) {
    addCommands(
        // Drive forward the specified distance
        new AlignWithTarget(drive, limelight),
      new AimHood(shooter, distanceSensor),
       new FireShooter(shooter, belt).withTimeout(10),
       new TurnToAngle(0, drive),
       new DriveBackwards(1000, drive),
       //3 ball auto above
      
       new IntakeDown(intake),
       new DriveBackwards(1000, drive),
       new IntakeUp(intake),
       new DriveForward(1000, drive),
       new AlignWithTarget(drive, limelight),
       new AimHood(shooter, distanceSensor),
       new FireShooter(shooter, belt).withTimeout(10)
      // new TurnToAngle(0, drive)
    );
        
        
  }

}