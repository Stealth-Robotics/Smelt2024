package org.firstinspires.ftc.teamcode.subsystems;

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

    private  static final double INTAKE_POWER = 1;
    private  static final double OUTTAKE_POWER = -1;

    private static final double STOP_POWER = 0;

    private IntakeState state = IntakeState.STOPPED;

    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        intakesuck = hardwareMap.get(CRServo.class, INTAKE_NAME);

    }

    public void setPower(double speed){
        intakesuck.setPower(speed);
    }

    public double getPosition() {
        return intakesuck.getPower();
    }

    public void setIntake(){ setPower(INTAKE_POWER); }

    public void setOuttake(){
        setPower(OUTTAKE_POWER);
    }

    public void setStopIntake() {
        setPower(STOP_POWER);
    }

    public void toggleStateForward()
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

    public void toggleStateBackwards()
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
