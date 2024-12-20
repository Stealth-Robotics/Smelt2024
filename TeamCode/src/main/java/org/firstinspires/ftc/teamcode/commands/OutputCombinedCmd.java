package org.firstinspires.ftc.teamcode.commands;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.CommandBase;
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

    protected OutputRotationSubsystem outputRotate;
    protected final OutputLiftSubsystem outputLift;
    protected final ClipsSubsystem clips;
    protected final LifterSubsystem lifter;

    protected static final long ROTATE_DELAY = 500;
    protected static final double LIFTER_SCORE_POSITION = .45;
    protected static final double LIFTER_DUMP_POSITION = .99;
    protected static final double LIFTER_INTAKE_POSITION = .001;

    private final BooleanSupplier grabSpecimen;
    private final BooleanSupplier placeSpecimen;
    private final BooleanSupplier dumpBucket;
    private final BooleanSupplier intakeReadyBucket;


    public OutputCombinedCmd(
            OutputRotationSubsystem outputRotationSubsystem,
            OutputLiftSubsystem outputLiftSubsystem,
            LifterSubsystem lifterSubsystem,
            ClipsSubsystem clipsSubsystem,
            BooleanSupplier grabSpecimen,
            BooleanSupplier dumpBucket,
            BooleanSupplier intakeReadyBucket,
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

        if (grabSpecimen.getAsBoolean()) {
            getSpecimenGrabCmd().schedule();
        }
        else if (placeSpecimen.getAsBoolean()) {
            getSpecimenPlaceCmd().schedule();
        }
        else if (dumpBucket.getAsBoolean()) {
            setDumpBucket().schedule();
        }
        else if (intakeReadyBucket.getAsBoolean()) {
            setIntakeReadyBucket().schedule();
        }

    }

    public SequentialCommandGroup getSpecimenGrabCmd() {
        SequentialCommandGroup cmd = new SequentialCommandGroup();
        switch (outputLift.getState()) {
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
                cmd.addCommands(
                        lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                        clips.setCloseCmd(),
                        outputLift.setClipGrabCmd(),
                        outputRotate.setClipCmd(),
                        clips.setOpenCmd());
                break;

            case CLIP_SCORE:
                cmd.addCommands(getScoreClipCmd());

            default:
                cmd.addCommands(
                        lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                        outputLift.setClipGrabCmd(),
                        outputRotate.setClipCmd(),
                        clips.setOpenCmd());

        }

        return cmd;
    }

    public SequentialCommandGroup setDumpBucket(){

        SequentialCommandGroup cmd = new SequentialCommandGroup();
        switch (outputLift.getState()) {
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

        if (outputLift.getState().equals(OutputLiftSubsystem.LiftState.CLIP_SCORE)) {
            return getScoreClipCmd();
        }

        return new SequentialCommandGroup(
                clips.setCloseCmd(),
                new WaitCommand(300),
                lifter.startSetPositionCommand(LIFTER_SCORE_POSITION),
                new WaitCommand(ROTATE_DELAY),
                outputLift.setClipScoreCmd(),
                outputRotate.setClipCmd());

    }

    public SequentialCommandGroup setIntakeReadyBucket() {

        return new SequentialCommandGroup(
                lifter.startSetPositionCommand(LIFTER_INTAKE_POSITION),
                clips.setCloseCmd(),
                outputRotate.setIntakeReadyCmd(),
                new WaitCommand(ROTATE_DELAY),
                outputLift.setDownCmd());
    }

    private SequentialCommandGroup getScoreClipCmd()
    {
        return new SequentialCommandGroup(
                lifter.startSetPositionCommand(LIFTER_SCORE_POSITION * 0.75),
                new WaitCommand(100),
                clips.setOpenCmd());
    }

}

