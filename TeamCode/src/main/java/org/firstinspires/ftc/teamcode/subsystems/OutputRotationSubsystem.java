package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

public class OutputRotationSubsystem extends StealthSubsystem {
    private static final String LEFT_BUCKET_ROTATE_NAME = "leftbucketrotate";
    private static final double INTAKE_READY_POSITION = 0; //??
    private static final double CLIPS_POSITION = 0.0; //??
    private static final double BUCKET_POSITION = 0; //??

    private enum rotationState {
        BUCKET,
        INTAKE_READY,
        CLIPS
    }

    private rotationState state = rotationState.INTAKE_READY;

    private final Telemetry telemetryA;
    private final Servo outputRotate;
    public OutputRotationSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        outputRotate = hardwareMap.get(Servo.class, LEFT_BUCKET_ROTATE_NAME);
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public void setPosition(double position){ outputRotate.setPosition(position);}
    public double getPosition() {return outputRotate.getPosition();}
    @Override
    public void periodic(){
        telemetryA.addData("Port position", outputRotate.getController().getServoPosition(outputRotate.getPortNumber()));
        telemetryA.addData("getPosition", getPosition());
    }
    public void setIntakeReady(){setPosition(INTAKE_READY_POSITION);}
    public void setClips(){setPosition(CLIPS_POSITION);}
    public void setBucket(){setPosition(BUCKET_POSITION);}

    public void toggleClips() {
        switch (state) {
            case INTAKE_READY:
                state = rotationState.CLIPS;
                setClips();
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
