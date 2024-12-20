package org.firstinspires.ftc.teamcode.subsystems;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.StealthSubsystem;
//Servo value for original pos - 0.9289
//Servo value for clip pick up - 0.6661
//Servo value for maximum - 0.25
//Servo value for dumping - 0.7389
public class OutputLiftSubsystem extends StealthSubsystem {
    private static final String LEFT_BUCKET_LIFT_NAME = "leftbucketlift";
    public enum LiftState {
        INTAKE_READY_BUCKET,
        CLIP_GRAB,
        DUMP_BUCKET,
        CLIP_SCORE,
        MAX_POSITION;

        private static LiftState state = LiftState.INTAKE_READY_BUCKET;

        public static LiftState get() {
            return state;
        }

        public static void set(LiftState a) {
            state = a;
        }
    }

    private static final double BUCKET_CLIP_POSITION = 0.6661;
    private static final double BUCKET_DOWN_POSITION = 0.9289;
    private static final double BUCKET_DUMP_POSITION = 0.7389;
    private static final double BUCKET_MAX_POSITION = 0.25;

    private final Telemetry telemetryA;
    private final Servo leftBucketLift;
    public OutputLiftSubsystem(HardwareMap hardwareMap, Telemetry telemetry)
    {
        leftBucketLift = hardwareMap.get(Servo.class, LEFT_BUCKET_LIFT_NAME);
        telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    }

    private void setLeftPosition(double position){
        leftBucketLift.setPosition(position);
    }
    public double getLeftPosition() {
        return leftBucketLift.getPosition();
    }

    public boolean isDownPosition(){ return LiftState.get() == LiftState.INTAKE_READY_BUCKET;}

    public LiftState getState() {
       return LiftState.get();
    }


    @Override
    public void periodic(){
        telemetryA.addData("Arm Pos", leftBucketLift.getController().getServoPosition(leftBucketLift.getPortNumber()));
        telemetryA.addData("Arm", leftBucketLift.getPosition());
        telemetryA.addData("State", LiftState.get());
    }

    public void setClipGrab(){
        LiftState.set(LiftState.CLIP_GRAB);
        setLeftPosition(BUCKET_CLIP_POSITION);
    }

    public void setClipScore(){
        LiftState.set(LiftState.CLIP_SCORE);
        setLeftPosition(BUCKET_CLIP_POSITION);
    }
    public void setDown(){
        LiftState.set(LiftState.INTAKE_READY_BUCKET);
        setLeftPosition(BUCKET_DOWN_POSITION);
    }
    public void setDump(){
        LiftState.set(LiftState.DUMP_BUCKET);
        setLeftPosition(BUCKET_DUMP_POSITION);
    }
    public void setMax(){
        LiftState.set(LiftState.MAX_POSITION);
        setLeftPosition(BUCKET_MAX_POSITION);
    }

    public Command setClipScoreCmd(){
        return runOnce(this::setClipScore);
    }

    public Command setClipGrabCmd(){
        return runOnce(this::setClipGrab);
    }
    public Command setDownCmd(){
        return runOnce(this::setDown);
    }

    public Command setDumpCmd(){
        return runOnce(this::setDump);
    }
    public Command setMaxCmd() {
        return runOnce(this::setMax);
    }
}
