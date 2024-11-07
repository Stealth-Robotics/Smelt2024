package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class PushScoreAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration


        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor");

        Servo IntakeLift = hardwareMap.servo.get("intakelift");
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {

            IntakeLift.setPosition(0.9);
            double y = 0;
            double x = 1.1;
            double rx = 0;
            //movement
            //double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            //double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            //double rx = gamepad1.right_trigger - gamepad1.left_trigger;


            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = -(y + x + rx) / denominator;
            double backLeftPower = -(y - x + rx) / denominator;
            double frontRightPower = -(y - x - rx) / denominator;
            double backRightPower = -(y + x - rx) / denominator;
        }
    }
}
