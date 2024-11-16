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

       float OUTPUT_LIFT_POSITION = 0;


        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor");



        Servo LeftOutputLift = hardwareMap.servo.get("leftoutputlift");
        Servo RightOutputLift = hardwareMap.servo.get("rightoutputlift");
        Servo LeftOutputRotate = hardwareMap.servo.get("leftoutputlift");
        Servo RightOutputRotate = hardwareMap.servo.get("rightoutputrotate");




        DcMotor elevator = hardwareMap.dcMotor.get("elevator");
        DcMotor armextender = hardwareMap.dcMotor.get("armextender");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        elevator.setDirection(DcMotorSimple.Direction.REVERSE);




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

            LeftOutputRotate.setPosition(OUTPUT_LIFT_POSITION);
            RightOutputRotate.setPosition(OUTPUT_LIFT_POSITION + 0.171);
           

            elevator.setPower(ry);
            armextender.setPower(dx);


            if(gamepad1.a)
            {
                speedModifier = 0.5;
            }
            else if (!gamepad1.a)
            {
                speedModifier = 1;
            }

            //for dumping



        }











            telemetry.addData("Speed Modifier", speedModifier);

                // set to drop bucket contents 0
                //


                telemetry.update();
            }

        }
