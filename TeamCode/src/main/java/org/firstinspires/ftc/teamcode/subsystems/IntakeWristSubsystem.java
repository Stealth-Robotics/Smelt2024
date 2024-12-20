package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.stealthrobotics.library.StealthSubsystem;

public class IntakeWristSubsystem extends StealthSubsystem{
    private static final String INTAKE_WRIST_NAME = "intakewrist";
    private static final double START_POSITION = 0;
    private static final double EXTEND_POSITION = .2;
    private final Servo intakeWrist;

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
        intakeWrist.setPosition(START_POSITION);
    }

    public Command setStartPositionCmd(){
        return runOnce(this::setStartPosition);
    }
}
