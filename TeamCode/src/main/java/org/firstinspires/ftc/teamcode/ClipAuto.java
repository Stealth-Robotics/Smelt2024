package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class ClipAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration


        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor");

        DcMotor elevator = hardwareMap.dcMotor.get("elevator");

        Servo IntakeLift = hardwareMap.servo.get("intakelift");
        Servo OutputLift = hardwareMap.servo.get("outputlift");
        Servo OutputRotation = hardwareMap.servo.get("outputrotate");



        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        final float OUTPUT_LIFT_DOWN = 1;
        final float OUTPUT_LIFT_CLIP_PICKUP = 0.8246f;
        final float OUTPUT_LIFT_CLIP_SCORE = 0.9183f;
        final float OUTPUT_LIFT_BUCKET = 0.6275f;


        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {


            double y = 0;
            double x = 1.1;
            double rx = 0;
            //movement



            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
           /* double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = -(y + x + rx) / denominator;
            double backLeftPower = -(y - x + rx) / denominator;
            double frontRightPower = -(y - x - rx) / denominator;
            double backRightPower = -(y + x - rx) / denominator; */

            elevator.setPower(0.5);
            sleep(3000);
            elevator.setPower(0.1);
            OutputRotation.setPosition(0);
            OutputLift.setPosition(OUTPUT_LIFT_CLIP_SCORE);
            sleep(2000);
            elevator.setPower(-1);
            sleep(300);
            OutputRotation.setPosition(0.9);
            sleep(1000);
            OutputLift.setPosition(OUTPUT_LIFT_DOWN);
            sleep(3000);
            break;


        }
    }
}