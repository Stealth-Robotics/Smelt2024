package org.firstinspires.ftc.teamcode.commands.presets;

import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class SpecimenScorePreset extends OutputPresets {
    public SpecimenScorePreset(
            OutputRotationSubsystem outputRotationSubsystem,
            OutputLiftSubsystem outputLiftSubsystem,
            LifterSubsystem lifterSubsystem,
            ClipsSubsystem clipsSubsystem) {
        super(outputRotationSubsystem, outputLiftSubsystem, lifterSubsystem, clipsSubsystem);
        switch (outputLift.getState()) {
            case INTAKE_READY_BUCKET:
                addCommands(
                        clips.setCloseCmd(),
                        new WaitCommand(300),
                        lifter.startSetPositionCommand(LIFTER_SCORE_POSITION),
                        new WaitCommand(ROTATE_DELAY),
                        outputLift.setClipScoreCmd(),
                        outputRotate.setClipCmd());
                break;
        }
    }
}
