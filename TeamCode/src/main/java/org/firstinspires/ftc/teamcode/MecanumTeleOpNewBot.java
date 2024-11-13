package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class MecanumTeleOpNewBot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        float elevatorpos = 0;
        float armSetting = 0;
        double speedModifier = 1;
        float jimArmSetting  = 0;
        boolean intaketoggle = false;

        int outputcycler = 0;
        // 0 base position
        //1 first step of bucket
        //2 second step of bucket
        // 3 first step of specimen
        // 4 second step of specimen

        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor");

        DcMotor leftelle = hardwareMap.dcMotor.get("leftelle");
        DcMotor rightelle = hardwareMap.dcMotor.get("rightelle");

        DcMotor leftarm = hardwareMap.dcMotor.get("leftarm");
        DcMotor rightarm = hardwareMap.dcMotor.get("rightarm");

        //Servo IntakeLift = hardwareMap.servo.get("intakelift");

        //Servo OutputLift = hardwareMap.servo.get("outputlift");
        //Servo OutputRotation = hardwareMap.servo.get("outputrotate");

        //CRServo Intake = hardwareMap.crservo.get("intake");

        //DcMotor elevator = hardwareMap.dcMotor.get("elevator");
        //DcMotor armextender = hardwareMap.dcMotor.get("armextender");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //elevator.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {



            //movement
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double zx = gamepad1.right_trigger - gamepad1.left_trigger;

            //elevator
            float ry = gamepad1.right_stick_y;
            leftelle.setPower(ry);
            rightelle.setPower(ry);
            leftelle.setDirection(DcMotorSimple.Direction.REVERSE);

            //arm extender
            float rx = gamepad1.right_stick_x;
            leftarm.setPower(rx);
            rightarm.setPower(rx);
            rightarm.setDirection(DcMotorSimple.Direction.REVERSE);
            //intake
            double intakepower = 0;
            if (gamepad1.right_bumper){intakepower=-.5;}
            if (gamepad1.left_bumper){intakepower=.5;}
            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(zx), 1);
            double frontLeftPower = ((y + x + zx) / denominator) * speedModifier;
            double backLeftPower = ((y - x + zx) / denominator) * speedModifier;
            double frontRightPower = ((y - x - zx) / denominator) * speedModifier;
            double backRightPower = ((y + x - zx) / denominator) * speedModifier;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            /*Intake.setPower(intakepower);

            elevator.setPower(ry);
            armextender.setPower(rx);

            //setting position to dump the intake
            if (gamepad1.a)
            {
                speedModifier = 0.5;


            } else
            {
                speedModifier = 1;


            }

            if (intaketoggle == false && gamepad1.y)
            {
                intaketoggle = true;
                IntakeLift.setPosition(0.9);


            } else if (intaketoggle == true && gamepad1.y)
            {
                intaketoggle = false;
                IntakeLift.setPosition(0.75);
            }


            if(gamepad1.dpad_right && outputcycler<4)
            {
                outputcycler++;

            } else if (gamepad1.dpad_left && outputcycler>0)
            {

                outputcycler--;

            }

            if(outputcycler == 0)
            {
                OutputLift.setPosition(0.35);
                OutputRotation.setPosition(0.9);
            } else if(outputcycler == 1)
            {
                OutputLift.setPosition(0.3247);
                OutputRotation.setPosition(0.9);

            } else if (outputcycler == 2)
            {
                OutputLift.setPosition(0.2957);
                OutputRotation.setPosition(0.5);

            } else if (outputcycler == 3)
            {
                OutputRotation.setPosition(0);
                OutputLift.setPosition(0.3259);

            } else if (outputcycler == 4)
            {

                OutputRotation.setPosition(0.9);
                OutputRotation.setPosition(0);
            }


            if (gamepad1.dpad_up)
            {
                OutputRotation.setPosition(0.2);


            }
            if(gamepad1.dpad_down)
            {
                outputcycler = 0;

            }




            // is is so that the block doesn't fall off while going up - click dpad left while going up on outputlift


            if (gamepad2.dpad_left) {
                OutputRotation.setPosition(0.5);

                //this is so that it doesn't destroy the robot when it goes down. make sure to press dpad right
            } else if (gamepad2.dpad_right) {
                OutputRotation.setPosition(0.9);

            }
             */
            //for dumping



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

          /* if(gamepad2.right_bumper)
            {
                jimArmSetting += 0.00008;
                OutputLift.setPosition(jimArmSetting);
//                OutputRotation.setPosition(armSetting);
            }

            if(gamepad2.left_bumper)
            {
                jimArmSetting -= 0.00008;
                OutputLift.setPosition(jimArmSetting);
//                OutputRotation.setPosition(armSetting);
            }
            telemetry.addData("jims servo value",jimArmSetting);
            telemetry.update();
            }*/

            // down 0.1438
            // up 0.8821

        }
    }}
