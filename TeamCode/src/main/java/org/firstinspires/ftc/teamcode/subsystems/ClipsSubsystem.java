package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

public class ClipsSubsystem extends StealthSubsystem {
    private static final String CLIP_NAME = "clips";
    private static final double CLIP_OPEN = 0.6;
    private static final double CLIP_CLOSE = 0;

    private static Boolean toggleClosed = false;


    private final CRServo clips;
    public ClipsSubsystem(HardwareMap hardwareMap)
    {
        clips = hardwareMap.get(CRServo.class, CLIP_NAME);
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
        telemetry.addData("Clips Pos", clips.getController().getServoPosition(clips.getPortNumber()));
        telemetry.addData("Clips", clips.getPower());
    }

    public void setOpen(){
        toggleClosed = false;
        clips.setPower(CLIP_OPEN);}
    public void setClose(){
        toggleClosed = true;
        clips.setPower(CLIP_CLOSE);
    }

    public void toggle(){
        if(toggleClosed){
            setOpen();
        }else{
            setClose();
        }
    }

    public Command setOpenCmd(){
        return runOnce(this::setOpen);
    }
    public Command setCloseCmd(){
        return runOnce(this::setClose);
    }

    public Command toggleCmd(){
        return runOnce(this::toggle);
    }

}
