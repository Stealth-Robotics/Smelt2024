package org.firstinspires.ftc.teamcode;

import android.icu.util.Output;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        float armSetting = 0;
        double speedModifier = 1;

        final float OUTPUT_LIFT_DOWN = 1;
        final float OUTPUT_LIFT_CLIP_PICKUP = 0.8246f;
        final float OUTPUT_LIFT_CLIP_SCORE = 0.9183f;
        final float OUTPUT_LIFT_BUCKET = 0.6275f;


        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor");

        Servo IntakeLift = hardwareMap.servo.get("intakelift");

        Servo OutputLift = hardwareMap.servo.get("outputlift");
        Servo OutputRotation = hardwareMap.servo.get("outputrotate");

        CRServo Intake = hardwareMap.crservo.get("intake");

        DcMotor elevator = hardwareMap.dcMotor.get("elevator");
        DcMotor armextender = hardwareMap.dcMotor.get("armextender");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator.setDirection(DcMotorSimple.Direction.REVERSE);

        IntakeLift.setPosition(0.55);
        OutputRotation.setPosition(0.9706);
        OutputLift.setPosition(OUTPUT_LIFT_DOWN);

        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {

            //movement
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_trigger - gamepad1.left_trigger;

            //elevator
            float ry = gamepad2.right_stick_y;
            //arm extender
            float dx = gamepad2.left_stick_x;

            //intake
            double intakepower = 0.25 * (gamepad2.right_trigger - gamepad2.left_trigger);

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = ((y + x + rx) / denominator) * speedModifier;
            double backLeftPower = ((y - x + rx) / denominator) * speedModifier;
            double frontRightPower = ((y - x - rx) / denominator) * speedModifier;
            double backRightPower = ((y + x - rx) / denominator) * speedModifier;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            Intake.setPower(intakepower);

            elevator.setPower(ry);
            armextender.setPower(dx);

            //setting position to dump the intake
             if (gamepad2.a) {

                IntakeLift.setPosition(0.9);
                //setting the passive positon for the intake thing - so that it doesn't drag
            } else if (gamepad2.b) {
                IntakeLift.setPosition(0.55);
            }
            //this is going up
            if (gamepad2.x) {
                OutputRotation.setPosition(0.9706);

                telemetry.addData("Arm Down", 0);
            }
            if(gamepad2.y)
            {
               OutputLift.setPosition(OUTPUT_LIFT_DOWN);



            }

            if(gamepad1.a)
            {
                speedModifier = 0.5;
            }
            else if (!gamepad1.a)
            {
                speedModifier = 1;
            }

            //for dumping


            if (gamepad2.dpad_up)
            {
                OutputLift.setPosition(OUTPUT_LIFT_CLIP_PICKUP);
                OutputRotation.setPosition(0);


            }
            if (gamepad2.dpad_down)
            {
                OutputLift.setPosition(OUTPUT_LIFT_BUCKET);
                OutputRotation.setPosition(0.4336);

            }
            if(gamepad2.dpad_left)
            {
                OutputRotation.setPosition(0.112);
            }
            if(gamepad2.dpad_right)
            {
                OutputLift.setPosition(OUTPUT_LIFT_CLIP_SCORE);


            }

        }











            telemetry.addData("Speed Modifier", speedModifier);

                // set to drop bucket contents 0
                //


                telemetry.update();
            }

        }

