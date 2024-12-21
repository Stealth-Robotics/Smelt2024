package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.stealthrobotics.library.StealthSubsystem;

//leftintakedump pos: 0.036
//leftintakepickup pos: 0.816
// leftintake up pos: 0.49
// rightintake dump pos: 0.982
//rightintakepickup pos: 0.21
//rightintake middle pos: 0.5

// UP OUt of way
// Left .38 Right .80

public class IntakeElbowSubsystem extends StealthSubsystem {

    public enum ElbowState {
        DUMP_POSITION,
        UP_POSITION,
        MIDDLE_POSITION,
        PICKUP_POSITION,
        UNKNOWN_POSITION
    }

    private static final String INTAKE_ELBOW_LEFT_NAME = "leftintakeelbow";
    private static final String INTAKE_ELBOW_RIGHT_NAME = "rightintakeelbow";

    private static final double RIGHT_INTAKE_DUMP_POS = 0.982;
    private static final double LEFT_INTAKE_DUMP_POS = 0.036;

    private static final double RIGHT_INTAKE_MIDDLE_POS = 0.5;
    private static final double LEFT_INTAKE_MIDDLE_POS = 0.49;

    private static final double RIGHT_INTAKE_PICKUP_POS = .1;
    private static final double LEFT_INTAKE_PICKUP_POS = .98;

    private static final double RIGHT_INTAKE_UP_POS = 0.73;
    private static final double LEFT_INTAKE_UP_POS = 0.45;

    private final Servo intakeElbowLeft;
    private final Servo intakeElbowRight;
    private boolean isStartPosition = false;

    private ElbowState state = ElbowState.UNKNOWN_POSITION;

    public IntakeElbowSubsystem(HardwareMap hardwareMap){
        intakeElbowLeft = hardwareMap.get(Servo.class, INTAKE_ELBOW_LEFT_NAME);
        intakeElbowRight = hardwareMap.get(Servo.class, INTAKE_ELBOW_RIGHT_NAME);
    }

    @Override
    public void periodic() {
        if (isStartPosition) {
            setUpPose();
            isStartPosition = true;
        }
    }
    public void setDumpPose()
    {
        state = ElbowState.DUMP_POSITION;
        intakeElbowLeft.setPosition(LEFT_INTAKE_DUMP_POS);
        intakeElbowRight.setPosition(RIGHT_INTAKE_DUMP_POS);
    }

    public ElbowState getState() {
        return state;
    }

    public void setPickupPose()
    {
        state = ElbowState.PICKUP_POSITION;
        intakeElbowLeft.setPosition(LEFT_INTAKE_PICKUP_POS);
        intakeElbowRight.setPosition(RIGHT_INTAKE_PICKUP_POS);
    }

    public void setMiddlePose() {
        state = ElbowState.MIDDLE_POSITION;
        intakeElbowLeft.setPosition(LEFT_INTAKE_MIDDLE_POS);
        intakeElbowRight.setPosition(RIGHT_INTAKE_MIDDLE_POS);
    }

    public void setUpPose() {
        state = ElbowState.UP_POSITION;
        intakeElbowLeft.setPosition(LEFT_INTAKE_UP_POS);
        intakeElbowRight.setPosition(RIGHT_INTAKE_UP_POS);
    }

    public Command setDumpCmd(){
        return runOnce(this::setDumpPose);
    }

    public Command setPickupCmd(){
        return runOnce(this::setPickupPose);
    }

    public Command setMiddleCmd() {
        return runOnce(this::setMiddlePose);
    }

    public Command setUpCmd() {
        return runOnce(this::setUpPose);
    }



}
