package org.firstinspires.ftc.teamcode.commands.presets;

import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class OutputReadyPreset extends OutputPresets {
    public OutputReadyPreset(
            OutputRotationSubsystem outputRotationSubsystem,
            OutputLiftSubsystem outputLiftSubsystem,
            LifterSubsystem lifterSubsystem,
            ClipsSubsystem clipsSubsystem) {
        super(outputRotationSubsystem, outputLiftSubsystem, lifterSubsystem, clipsSubsystem);
        addCommands(
            lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
            clips.setCloseCmd(),
            outputRotate.setIntakeReadyCmd(),
            new WaitCommand(ROTATE_DELAY),
            outputLift.setDownCmd(),
            lifter.endSetPositionCommand(500));
    }
}
