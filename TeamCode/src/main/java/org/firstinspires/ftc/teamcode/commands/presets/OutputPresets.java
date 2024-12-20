package org.firstinspires.ftc.teamcode.commands.presets;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

public class OutputPresets extends SequentialCommandGroup {
    protected final OutputRotationSubsystem outputRotate;
    protected final OutputLiftSubsystem outputLift;
    protected final ClipsSubsystem clips;
    protected final LifterSubsystem lifter;

    protected static final long ROTATE_DELAY = 500;
    protected static final double LIFTER_SCORE_POSITION = .45;
    protected static final double LIFTER_DUMP_POSITION = .99;
    protected static final double LIFTER_INTAKE_POSITION = .001;

    public OutputPresets(
            OutputRotationSubsystem outputRotationSubsystem,
            OutputLiftSubsystem outputLiftSubsystem,
            LifterSubsystem lifterSubsystem,
            ClipsSubsystem clipsSubsystem) {
        addRequirements(outputRotationSubsystem, outputLiftSubsystem, lifterSubsystem, clipsSubsystem);
        this.outputRotate = outputRotationSubsystem;
        this.outputLift = outputLiftSubsystem;
        this.lifter = lifterSubsystem;
        this.clips = clipsSubsystem;
    }
}
