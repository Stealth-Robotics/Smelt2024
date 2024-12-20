package org.firstinspires.ftc.teamcode.commands.presets;

import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

import java.util.function.Supplier;

public class SpecimenGrabPreset extends OutputPresets {
    public SpecimenGrabPreset(
            OutputRotationSubsystem outputRotationSubsystem,
            OutputLiftSubsystem outputLiftSubsystem,
            LifterSubsystem lifterSubsystem,
            ClipsSubsystem clipsSubsystem,
            Supplier<OutputLiftSubsystem.LiftState> stateSupplier) {
        super(outputRotationSubsystem, outputLiftSubsystem, lifterSubsystem, clipsSubsystem);
        switch (stateSupplier.get()) {
            case INTAKE_READY_BUCKET:
                addCommands(
                        clips.setCloseCmd(),
                        outputLift.setClipGrabCmd(), // Move arm up first
                        new WaitCommand(ROTATE_DELAY), // wait for bucket to clear hubs
                        outputRotate.setClipCmd(), // rotate bucket
                        clips.setOpenCmd()); // open the claws
                break;

            case DUMP_BUCKET:
                addCommands(
                        lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                        clips.setCloseCmd(),
                        outputLift.setClipGrabCmd(),
                        outputRotate.setClipCmd(),
                        clips.setOpenCmd());
                break;

            case CLIP_SCORE:
                addCommands(
                        lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                        new WaitCommand(100),
                        outputLift.setClipGrabCmd(),
                        outputRotate.setClipCmd(),
                        new WaitCommand(100),
                        clips.setOpenCmd());
                break;
            default:
                addCommands(
                        clips.setOpenCmd());

        }
    }
}
