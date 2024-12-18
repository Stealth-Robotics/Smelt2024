package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

public class ClipsSubsystem extends StealthSubsystem {
    private static final String CLIP_NAME = "clips";
    private static final double CLIP_OPEN = 0.6;
    private static final double CLIP_CLOSE = 0;

    private final Telemetry telemetryA;
    private final CRServo clips;
    public ClipsSubsystem(HardwareMap hardwareMap, Telemetry telemetry)
    {
        clips = hardwareMap.get(CRServo.class, CLIP_NAME);
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    }
    public void setPower(double speed){
        clips.setPower(speed);
    }
    public double getPosition() {
        return clips.getPower();
    }
    @Override
    public void periodic()
    {
        telemetryA.addData("Clips Pos", clips.getController().getServoPosition(clips.getPortNumber()));
        telemetryA.addData("Clips", clips.getPower());
    }

    public void setOpen(){clips.setPower(CLIP_OPEN);}
    public void setClose(){clips.setPower(CLIP_CLOSE);}


}
