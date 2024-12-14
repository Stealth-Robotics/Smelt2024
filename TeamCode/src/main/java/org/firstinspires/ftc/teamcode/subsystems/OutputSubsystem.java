package org.firstinspires.ftc.teamcode.subsystems;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

public class OutputSubsystem extends StealthSubsystem {
    private static final String OUTPUTLIFT_NAME = "leftbucketlift";
    private final Telemetry telemetryA;
    private final Servo outputlift;
    public OutputSubsystem(HardwareMap hardwareMap, Telemetry telemetry)
    {
        outputlift = hardwareMap.get(Servo.class, OUTPUTLIFT_NAME);
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    }
    public void setPosition(double position){
        outputlift.setPosition(position);
    }
    public double getPosition() {
        return outputlift.getPosition();
    }
    @Override
    public void periodic(){
        telemetryA.addData("Arm Pos", outputlift.getController().getServoPosition(outputlift.getPortNumber()));
        telemetryA.addData("Arm", outputlift.getPosition());
    }
}
