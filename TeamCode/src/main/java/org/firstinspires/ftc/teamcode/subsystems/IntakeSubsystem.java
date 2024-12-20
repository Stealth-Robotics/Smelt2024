package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

public class IntakeSubsystem extends StealthSubsystem {

    private enum IntakeState {
        STOPPED,
        INTAKING,
        OUTTAKING
    }

    private static final String INTAKE_NAME = "intakesuck";
    private final CRServo intakesuck;

    private  static final double INTAKE_POWER = -1;
    private  static final double OUTTAKE_POWER = 1;

    private static final double STOP_POWER = 0;

    private IntakeState state = IntakeState.STOPPED;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakesuck = hardwareMap.get(CRServo.class, INTAKE_NAME);

    }

    public void setPower(double speed){
        intakesuck.setPower(speed);
    }

    public double getPosition() {
        return intakesuck.getPower();
    }

    public void setIntake(){
        state = IntakeState.INTAKING;
        setPower(INTAKE_POWER);
    }

    public void setOuttake(){
        state = IntakeState.OUTTAKING;
        setPower(OUTTAKE_POWER);
    }

    public void setStopIntake() {
        state = IntakeState.STOPPED;
        setPower(STOP_POWER);
    }

    public Command setIntakeCmd(){
        return runOnce(this::setIntake);
    }

    public Command setOuttakeCmd(){
        return runOnce(this::setOuttake);
    }

    public Command setStopIntakeCmd(){
        return runOnce(this::setStopIntake);
    }

    public void toggleStateForward()
    {
        switch (state) {
            case STOPPED:
                setIntake();
                break;
            case INTAKING:
                setOuttake();
                break;
            default:
                setStopIntake();
        }
    }

    public void toggleStateBackwards()
    {
        switch (state) {
            case STOPPED:
                setOuttake();
                break;
            case OUTTAKING:
                setIntake();
                break;
            default:
                setStopIntake();
        }
    }
}
