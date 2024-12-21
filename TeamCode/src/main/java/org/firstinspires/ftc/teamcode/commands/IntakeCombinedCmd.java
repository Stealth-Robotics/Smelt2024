package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.presets.IntakeDeployPreset;
import org.firstinspires.ftc.teamcode.commands.presets.IntakeRetractPreset;
import org.firstinspires.ftc.teamcode.commands.presets.OutputReadyPreset;
import org.firstinspires.ftc.teamcode.subsystems.ClipsSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeElbowSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeWristSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LifterSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputLiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.OutputRotationSubsystem;

import java.util.function.BooleanSupplier;

public class IntakeCombinedCmd extends CommandBase {
    private final IntakeElbowSubsystem intakeElbow;
    private final IntakeWristSubsystem intakeWrist;
    private final IntakeSubsystem intake;
    private final ExtenderSubsystem extender;
    private final OutputRotationSubsystem outputRotation;
    private final LifterSubsystem lifter;
    private final ClipsSubsystem clips;
    private final OutputLiftSubsystem outputLift;

    private final BooleanSupplier deployIntake;
    private final BooleanSupplier retractIntake;

    private boolean initState = false;

    private boolean wristExtended = false;
    private long lastSystemTime = 0;
    private static final long EXTEND_TIME = 1000;

    public IntakeCombinedCmd(
            IntakeElbowSubsystem intakeElbow,
            IntakeWristSubsystem intakeWrist,
            ExtenderSubsystem extender,
            IntakeSubsystem intake,
            OutputRotationSubsystem outputRotation,
            LifterSubsystem lifter,
            ClipsSubsystem clips,
            OutputLiftSubsystem outputLift,
            BooleanSupplier deployIntake,
            BooleanSupplier retractIntake) {
        this.clips = clips;
        this.outputLift = outputLift;
        this.deployIntake = deployIntake;
        this.retractIntake = retractIntake;
        addRequirements(intakeElbow);
        this.intakeElbow = intakeElbow;
        this.intakeWrist = intakeWrist;
        this.intake = intake;
        this.outputRotation = outputRotation;
        this.lifter = lifter;
        this.extender = extender;
    }

    @Override
    public void execute(){
        if (!initState) {
         clips.setClose();
         intakeElbow.setMiddlePose();
         intakeWrist.setStartPosition();
         initState = true;
        }

        if(deployIntake.getAsBoolean()){
            if (intakeElbow.getState() == IntakeElbowSubsystem.ElbowState.PICKUP_POSITION)
            {
                setElbowMiddle().schedule();
            }else {
                deployIntake().schedule();
            }
        }
        else if(retractIntake.getAsBoolean()) {
            retractIntake().schedule();
        }
//        else if (intakeElbow.getState().equals(IntakeElbowSubsystem.ElbowState.PICKUP_POSITION)) {
//            long currentTime = System.currentTimeMillis();
//            if (currentTime > lastSystemTime + EXTEND_TIME) {
//                intakeWrist.setToggleCmd().schedule();
//                lastSystemTime = currentTime;
//            }
        //}

    }

    private ParallelCommandGroup setElbowMiddle()
    {
        return new ParallelCommandGroup(
                intakeElbow.setMiddleCmd()
        );
    }

    private ParallelCommandGroup deployIntake(){
        ParallelCommandGroup cmd = new ParallelCommandGroup();
        cmd.addCommands(
                new IntakeDeployPreset(extender, intakeElbow, intakeWrist, intake));
//        if (outputLift.getState() != OutputLiftSubsystem.LiftState.INTAKE_READY_BUCKET ||
//            lifter.getPosition() > 20) {
//            cmd.addCommands(new OutputReadyPreset(outputRotation, outputLift, lifter, clips));
//        }
        return cmd;
    }

    private SequentialCommandGroup retractIntake(){
        SequentialCommandGroup cmd =  new SequentialCommandGroup();
        if (outputLift.getState() != OutputLiftSubsystem.LiftState.INTAKE_READY_BUCKET ||
                lifter.getPosition() > 20) {

            cmd.addCommands(new OutputReadyPreset(outputRotation, outputLift, lifter, clips));
        }

        cmd.addCommands(new IntakeRetractPreset(extender, intakeElbow, intakeWrist, intake));

        return cmd;
    }

}
