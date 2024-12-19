package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;
//leftbucketdump 0.85
//leftclip 0.074 //.11
//leftintakeready 0.48
//rightintakeready 0.46
//rightclip 0.94
//rightbucketdumb 0.09
public class OutputRotationSubsystem extends StealthSubsystem {
    private static final String LEFT_BUCKET_ROTATE_NAME = "leftbucketrotate";
    private static final double LEFT_INTAKE_READY_POSITION = 0.48;
    private static final double LEFT_CLIPS_POSITION = .11;
    private static final double LEFT_BUCKET_POSITION = 0.85;
    private static final String RIGHT_BUCKET_ROTATE_NAME = "rightbucketrotate";
    private static final double RIGHT_INTAKE_READY_POSITION = 0.46;
    private static final double RIGHT_CLIPS_POSITION = 0.81;
    private static final double RIGHT_BUCKET_POSITION = 0.09;

    private enum rotationState {
        BUCKET,
        INTAKE_READY,
        CLIPS
    }

    private rotationState state = rotationState.INTAKE_READY;

    private final Telemetry telemetryA;
    private final Servo leftOutputRotate;
    private final Servo rightOutputRotate;
    
    public OutputRotationSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        leftOutputRotate = hardwareMap.get(Servo.class, LEFT_BUCKET_ROTATE_NAME);
        rightOutputRotate = hardwareMap.get(Servo.class, RIGHT_BUCKET_ROTATE_NAME);
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public void setLeftPosition(double position){ leftOutputRotate.setPosition(position);}
    public double getLeftPosition() {return leftOutputRotate.getPosition();}
    public void setRightPosition(double position){ rightOutputRotate.setPosition(position);}
    public double getRightPosition() {return rightOutputRotate.getPosition();}
    @Override
    public void periodic(){
        telemetryA.addData("getPosition", getLeftPosition());
        telemetryA.addData("getPosition", getRightPosition());
    }

    public void setIntakeReady() {
        state = rotationState.INTAKE_READY;
        setLeftPosition(LEFT_INTAKE_READY_POSITION);
        setRightPosition(RIGHT_INTAKE_READY_POSITION);
    }

    public void setClip(){
        state = rotationState.CLIPS;
        setLeftPosition(LEFT_CLIPS_POSITION);
        setRightPosition(RIGHT_CLIPS_POSITION);
    }
    public void setBucket(){
        state = rotationState.BUCKET;
        setLeftPosition(LEFT_BUCKET_POSITION);
        setRightPosition(RIGHT_BUCKET_POSITION);
    }

    public void toggleClips() {
        switch (state) {
            case INTAKE_READY:
                state = rotationState.CLIPS;
                setClip();
                break;

            default:
                state = rotationState.INTAKE_READY;
                setIntakeReady();
        }
    }

    public void toggleBucket() {
        switch (state) {
            case INTAKE_READY:
                state = rotationState.BUCKET;
                setBucket();
                break;

            default:
                state = rotationState.INTAKE_READY;
                setIntakeReady();

        }
    }
}
