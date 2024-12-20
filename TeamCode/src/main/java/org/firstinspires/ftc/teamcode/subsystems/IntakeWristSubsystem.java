package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.stealthrobotics.library.StealthSubsystem;

public class IntakeWristSubsystem extends StealthSubsystem{
    public enum WristState {
        START_POSITION,
        EXTEND_POSITION,
        UNKNOWN_POSITION
    }

    private static final String INTAKE_WRIST_NAME = "intakewrist";
    private static final double START_POSITION = 0;
    private static final double EXTEND_POSITION = .1;
    private final Servo intakeWrist;

    private WristState state = WristState.UNKNOWN_POSITION;

    private boolean isStartPosition = false;
    public IntakeWristSubsystem (HardwareMap hardwareMap){
        intakeWrist = hardwareMap.get(Servo.class, INTAKE_WRIST_NAME);
    }

    @Override
    public void periodic() {
        if (isStartPosition) {
            setStartPosition();
            isStartPosition = true;
        }
    }

    public double getPosition(){
        return intakeWrist.getPosition();
    }

    public void setStartPosition(){
        state = WristState.START_POSITION;
        intakeWrist.setPosition(START_POSITION);
    }

    public void setExtendPosition(){
        state = WristState.EXTEND_POSITION;
        intakeWrist.setPosition(EXTEND_POSITION);
    }

    public WristState getState(){
        return state;
    }

    public void setTogglePosition(){
        if (state == WristState.START_POSITION) {
            setExtendPosition();
        } else {
            setStartPosition();
        }
    }

    public Command setToggleCmd(){
        return runOnce(this::setTogglePosition);
    }

    public Command setExtendCmd(){
        return runOnce(this::setExtendPosition);
    }


    public Command setStartPositionCmd(){
        return runOnce(this::setStartPosition);
    }
}
