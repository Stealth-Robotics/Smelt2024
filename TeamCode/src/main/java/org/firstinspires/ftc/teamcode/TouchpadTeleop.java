package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled
public class TouchpadTeleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        float armSetting = 0;
        double speedModifier = 1;


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


        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {



            //movement
            double y = gamepad1.touchpad_finger_1_y; // Remember, Y stick value is reversed
            double x = gamepad1.touchpad_finger_1_x; // Counteract imperfect strafing
            double rx = gamepad1.touchpad_finger_2_x;

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
                OutputRotation.setPosition(0.9);

                telemetry.addData("Arm Down", 0);
            }
            if(gamepad2.y)
            {
               OutputLift.setPosition(0.35);

            //eurvbleyivgbaelyrigfayeifduiahelfukhalerv

            }

            if(gamepad1.a)
            {
                speedModifier = 0.5;
            }
            else if (!gamepad1.a)
            {
                speedModifier = 1;



            }


            // is is so that the block doesn't fall off while going up - click dpad left while going up on outputlift

            /*
            if (gamepad2.dpad_left) {
                OutputRotation.setPosition(0.5);

                //this is so that it doesn't destroy the robot when it goes down. make sure to press dpad right
            } else if (gamepad2.dpad_right) {
                OutputRotation.setPosition(0.9);

            }
             */
            //for dumping
            if (gamepad2.dpad_right) {

                OutputRotation.setPosition(0.5);

            } else if (gamepad2.dpad_left)
            {

                OutputRotation.setPosition(0.2);
            }
            if (gamepad2.dpad_up) {
                OutputRotation.setPosition(0);
                OutputLift.setPosition(0.3259);

            }else if (gamepad2.dpad_down) {
                OutputLift.setPosition(0.2957);
                OutputRotation.setPosition(0.5);


            }

            if(gamepad2.options)
            {
                OutputLift.setPosition(0.3247);


            }

            telemetry.addData("Speed Modifier", speedModifier);

                // set to drop bucket contents 0
                //

                /////////////////////////////////////////////////////////////////
                ///
                ///   Jim's Servo testing code for finding values of the servos.
                ///   comment out which servo you want to find the value of
                ///   The trigger buttons move the servo
                //    The servo value is displayed on the driver station
                ///
                ///
                ///   Comment this code block out for game time
                ///
                /////////////////////////////////////////////////////////////////

           /*if(gamepad2.right_bumper)
            {
                armSetting += 0.00008;
                OutputLift.setPosition(armSetting);
//                OutputRotation.setPosition(armSetting);
                telemetry.addData("servo value",armSetting);
            }

            if(gamepad2.left_bumper)
            {
                armSetting -= 0.00008;
                OutputLift.setPosition(armSetting);
//                OutputRotation.setPosition(armSetting);
                telemetry.addData("servo value",armSetting);
            }*/

                telemetry.update();
            }

        }
    }