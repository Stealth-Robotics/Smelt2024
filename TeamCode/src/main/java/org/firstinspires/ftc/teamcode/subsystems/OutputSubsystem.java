package org.firstinspires.ftc.teamcode.subsystems;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;
//Servo value for original pos - 0.9289
//Servo value for clip pick up - 0.6661
//Servo value for maximum - 0.25
//Servo value for dumping - 0.7389
public class OutputSubsystem extends StealthSubsystem {
    private static final String LEFT_BUCKET_LIFT_NAME = "leftbucketlift";

    private static final double BUCKET_CLIP_POSITION = 0.6661;
    private static final double BUCKET_DOWN_POSITION = 0.9289;
    private static final double BUCKET_DUMP_POSITION = 0.7389;
    private static final double BUCKET_MAX_POSITION = 0.25;

    private final Telemetry telemetryA;
    private final Servo leftBucketLift;
    public OutputSubsystem(HardwareMap hardwareMap, Telemetry telemetry)
    {
        leftBucketLift = hardwareMap.get(Servo.class, LEFT_BUCKET_LIFT_NAME);
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    }
    public void setPosition(double position){
        leftBucketLift.setPosition(position);
    }
    public double getPosition() {
        return leftBucketLift.getPosition();
    }

    @Override
    public void periodic(){
        telemetryA.addData("Arm Pos", leftBucketLift.getController().getServoPosition(leftBucketLift.getPortNumber()));
        telemetryA.addData("Arm", leftBucketLift.getPosition());
    }

    public void setClip(){setPosition(BUCKET_CLIP_POSITION);}
    public void setDown(){setPosition(BUCKET_DOWN_POSITION);}
    public void setDump(){setPosition(BUCKET_DUMP_POSITION);}
    public void setMax(){setPosition(BUCKET_MAX_POSITION);}
}
