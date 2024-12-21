package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

import java.util.function.BooleanSupplier;

/**
 * Controls the bucket rotation, lift harms, clips, and lifter
 */
public class OutputCombinedCmd extends CommandBase {

    private enum OutputState
    {
        OUTPUT_START,
        INTAKE_READY_BUCKET,
        CLIP_GRAB,
        DUMP_BUCKET,
        CLIP_SCORE,
        CLIP_SCORED,
        MAX_POSITION,
        UNKNOWN_POSITION;
    }
    protected OutputRotationSubsystem outputRotate;
    protected final OutputLiftSubsystem outputLift;
    protected final ClipsSubsystem clips;
    protected final LifterSubsystem lifter;

    protected static final long ROTATE_DELAY = 500;
    protected static final double LIFTER_SCORE_POSITION = .55;
    protected static final double LIFTER_DUMP_POSITION = .99;
    protected static final double LIFTER_INTAKE_POSITION = .001;

    private final BooleanSupplier grabSpecimen;
    private final BooleanSupplier placeSpecimen;
    private final BooleanSupplier dumpBucket;
    private final BooleanSupplier intakeReadyBucket;

    private OutputState state = OutputState.UNKNOWN_POSITION;

    public OutputCombinedCmd(
            OutputRotationSubsystem outputRotationSubsystem,
            OutputLiftSubsystem outputLiftSubsystem,
            LifterSubsystem lifterSubsystem,
            ClipsSubsystem clipsSubsystem,
            BooleanSupplier intakeReadyBucket,
            BooleanSupplier dumpBucket,
            BooleanSupplier grabSpecimen,
            BooleanSupplier placeSpecimen) {

        addRequirements(outputLiftSubsystem);

        this.outputRotate = outputRotationSubsystem;
        this.outputLift = outputLiftSubsystem;
        this.lifter = lifterSubsystem;
        this.clips = clipsSubsystem;

        this.grabSpecimen = grabSpecimen;
        this.placeSpecimen = placeSpecimen;
        this.dumpBucket = dumpBucket;
        this.intakeReadyBucket = intakeReadyBucket;
    }


    @Override
    public void execute() {
        telemetry.addData("OutputCombinedCmd State", outputLift.getState());
        if (state.equals(OutputState.UNKNOWN_POSITION))
        {
            setIntakeStart().schedule();
            state = OutputState.OUTPUT_START;
        }
        if (grabSpecimen.getAsBoolean()) {
            getSpecimenGrabCmd().schedule();
            state = OutputState.CLIP_GRAB;
        }
        else if (placeSpecimen.getAsBoolean()) {
            getSpecimenPlaceCmd().schedule();
            state = OutputState.CLIP_SCORE;
        }
        else if (dumpBucket.getAsBoolean()) {
            setDumpBucket().schedule();
            state = OutputState.DUMP_BUCKET;
        }
        else if (intakeReadyBucket.getAsBoolean()) {
            setIntakeReadyBucket().schedule();
            state = OutputState.INTAKE_READY_BUCKET;
        }

    }

    public SequentialCommandGroup getSpecimenGrabCmd() {
        SequentialCommandGroup cmd = new SequentialCommandGroup();
        switch (state) {
            case INTAKE_READY_BUCKET:
                if (lifter.getPosition() > 10) {
                    cmd.addCommands(lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION));
                }
                cmd.addCommands(
                        clips.setCloseCmd(),
                        outputLift.setClipGrabCmd(), // Move arm up first
                        new WaitCommand(ROTATE_DELAY), // wait for bucket to clear hubs
                        outputRotate.setClipCmd(), // rotate bucket
                        clips.setOpenCmd()); // open the claws
                break;

            case DUMP_BUCKET:
                cmd.addCommands(unDumpBucket());
                cmd.addCommands(
                        lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                        clips.setCloseCmd(),
                        outputLift.setClipGrabCmd(),
                        outputRotate.setClipCmd(),
                        clips.setOpenCmd());
                break;

            case CLIP_SCORE:
                cmd.addCommands(setScoreSpecimenCmd());

            default:
                cmd.addCommands(
                        lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                        outputLift.setClipGrabCmd(),
                        outputRotate.setClipCmd(),
                        clips.setOpenCmd());

        }

        return cmd;
    }

    public SequentialCommandGroup unDumpBucket() {
        return new SequentialCommandGroup(
                outputRotate.setIntakeReadyCmd(),
                new WaitCommand(100),
                outputLift.setDownCmd(),
                new WaitCommand(100));
    }

    public SequentialCommandGroup setDumpBucket(){

        SequentialCommandGroup cmd = new SequentialCommandGroup();
        switch (state) {
            case INTAKE_READY_BUCKET:
                cmd.addCommands(
                        clips.setCloseCmd(),
                        outputLift.setDumpCmd(),
                        new WaitCommand(ROTATE_DELAY),
                        outputRotate.setBucketCmd());
                break;
            case CLIP_SCORE:
                cmd.addCommands(getSpecimenPlaceCmd(),
                        new WaitCommand(100));
            default:
                cmd.addCommands(
                        clips.setCloseCmd(),
                        outputLift.setDumpCmd(),
                        outputRotate.setBucketCmd());
        }

        return cmd;
    }

    public SequentialCommandGroup getSpecimenPlaceCmd() {

        SequentialCommandGroup cmd = new SequentialCommandGroup();
        switch (state){
            case CLIP_SCORE:
                cmd.addCommands(setScoreSpecimenCmd());
                break;
            case DUMP_BUCKET:
                cmd.addCommands(unDumpBucket());
            case CLIP_GRAB:
            case INTAKE_READY_BUCKET:
            default:
                cmd.addCommands(clips.setCloseCmd(),
                        new WaitCommand(300),
                        lifter.startSetPositionCommand(LIFTER_SCORE_POSITION),
                        new WaitCommand(ROTATE_DELAY),
                        outputLift.setClipScoreCmd(),
                        outputRotate.setScorePoseCmd());

        }

        return cmd;
    }

    public ParallelCommandGroup setIntakeStart() {
        return new ParallelCommandGroup(
                lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                clips.setCloseCmd(),
                new WaitCommand(ROTATE_DELAY),
                outputLift.setDownCmd());
    }
    public SequentialCommandGroup setIntakeReadyBucket() {
        SequentialCommandGroup cmd = new SequentialCommandGroup();
        switch (state) {
            case CLIP_SCORE:
                cmd.addCommands(setScoreSpecimenCmd());
                break;
            case DUMP_BUCKET:
                cmd.addCommands(unDumpBucket());
            case CLIP_GRAB:
            case INTAKE_READY_BUCKET:
            default:
                cmd.addCommands(
                        lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                        clips.setCloseCmd(),
                        outputRotate.setIntakeReadyCmd(),
                        new WaitCommand(ROTATE_DELAY),
                        outputLift.setDownCmd());
        }

        return cmd;
    }

    private SequentialCommandGroup setScoreSpecimenCmd()
    {
        state = OutputState.CLIP_SCORED;
        return new SequentialCommandGroup(
                        outputRotate.setScorePoseCmd(),
                        lifter.startSetPositionCommand(LIFTER_SCORE_POSITION * .5),
                        new WaitCommand(100),
                        clips.setOpenCmd());
    }

}

