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

public class IntakeElbowSubsystem extends StealthSubsystem {
    private static final String INTAKE_ELBOW_LEFT_NAME = "leftintakeelbow";
    private static final String INTAKE_ELBOW_RIGHT_NAME = "rightintakeelbow";
    private static final double LEFT_INTAKE_DUMP_POS = 0.036;
    private static final double LEFT_INTAKE_PICKUP_POS = 0.816;
    private static final double LEFT_INTAKE_UP_POS = 0.49;
    private static final double RIGHT_INTAKE_DUMP_POS = 0.982;
    private static final double RIGHT_INTAKE_PICKUP_POS = 0.21;
    private static final double RIGHT_INTAKE_MIDDLE_POS = 0.5;
    private final Servo intakeElbowLeft;
    private final Servo intakeElbowRight;
    private boolean isStartPosition = false;

    public IntakeElbowSubsystem(HardwareMap hardwareMap){
        intakeElbowLeft = hardwareMap.get(Servo.class, INTAKE_ELBOW_LEFT_NAME);
        intakeElbowRight = hardwareMap.get(Servo.class, INTAKE_ELBOW_RIGHT_NAME);
    }

    @Override
    public void periodic() {
        if (isStartPosition) {
            setUp();
            isStartPosition = true;
        }
    }
    public void setDump()
    {
        intakeElbowLeft.setPosition(LEFT_INTAKE_DUMP_POS);
        intakeElbowRight.setPosition(RIGHT_INTAKE_DUMP_POS);
    }

    public void setPickup()
    {
        intakeElbowLeft.setPosition(LEFT_INTAKE_PICKUP_POS);
        intakeElbowRight.setPosition(RIGHT_INTAKE_PICKUP_POS);
    }

    public void setUp() {
        intakeElbowLeft.setPosition(LEFT_INTAKE_UP_POS);
        intakeElbowRight.setPosition(RIGHT_INTAKE_MIDDLE_POS);
    }

    public Command setDumpCmd(){
        return runOnce(this::setDump);
    }

    public Command setPickupCmd(){
        return runOnce(this::setPickup);
    }

    public Command setUpCmd() {
        return runOnce(this::setUp);
    }

}
