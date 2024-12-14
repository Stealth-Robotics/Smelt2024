package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeSuckCommand extends CommandBase {
    private enum IntakeState {
        STOPPED,
        INTAKING,
        OUTTAKING
    }
    private final IntakeSubsystem intake;
    private IntakeState state = IntakeState.STOPPED;

    public IntakeSuckCommand(IntakeSubsystem intake, Telemetry telemetry){
        this.intake = intake;
        addRequirements(intake);
    }
    @Override
    public void execute() {

    }
    public void setIntake(){
        intake.setPower(1);
    }

    public void setOuttake(){
        intake.setPower(-1);
    }

    public void setStopIntake() {
        intake.setPower(0);
    }

    public void toggleState()
    {
        switch (state) {
            case STOPPED:
                state = IntakeState.INTAKING;
                setIntake();
                break;
            case INTAKING:
                state = IntakeState.OUTTAKING;
                setOuttake();
                break;
            default:
                state = IntakeState.STOPPED;
                setStopIntake();
        }
    }
    public void toggleStatebackwards()
    {
        switch (state) {
            case STOPPED:
                state = IntakeState.OUTTAKING;
                setIntake();
                break;
            case OUTTAKING:
                state = IntakeState.INTAKING;
                setOuttake();
                break;
            default:
                state = IntakeState.STOPPED;
                setStopIntake();
        }
    }
}

