package org.firstinspires.ftc.teamcode.commands.presets;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWristSubsystem;

/**
 * Retracts the intake hopefully with specimen and delivers it to the bucket
 */
public class IntakeRetractPreset extends SequentialCommandGroup {
    public IntakeRetractPreset(ExtenderSubsystem extender, IntakeElbowSubsystem intakeElbow, IntakeWristSubsystem intakeWrist, IntakeSubsystem intake){

        addCommands(
                intake.setIntakeCmd(),
                intakeElbow.setMiddleCmd(),
                intakeWrist.setStartPositionCmd(),
                new WaitCommand(500),
                extender.setPositionCommand(.001, 2000),
                intakeElbow.setDumpCmd(),
                new WaitCommand(500),
                intake.setOuttakeCmd(),
                new WaitCommand(1000),
                intakeElbow.setUpCmd(),
                intake.setStopIntakeCmd());
    }
}
