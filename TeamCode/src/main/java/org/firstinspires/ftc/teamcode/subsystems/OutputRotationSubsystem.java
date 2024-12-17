package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

public class OutputRotationSubsystem extends StealthSubsystem {
    private static final String OUTPUTROTATE_NAME = "leftbucketrotate";
    private final Telemetry telemetryA;
    private final Servo outputrotate;
    public OutputRotationSubsystem(HardwareMap hardwareMap, Telemetry telemetry){
        outputrotate = hardwareMap.get(Servo.class, OUTPUTROTATE_NAME);
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    public void setPosition(double position){outputrotate.setPosition(position);}
    public double getPosition() {return outputrotate.getPosition();}
    @Override
    public void periodic(){
        telemetryA.addData("rotation pos", outputrotate.getController().getServoPosition(outputrotate.getPortNumber()));
        telemetryA.addData("Spinny", outputrotate.getPosition());
    }

}
