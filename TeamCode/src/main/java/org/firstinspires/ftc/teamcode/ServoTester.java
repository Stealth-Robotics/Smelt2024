package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        float armSetting = 0;
        int count = 0;
        boolean elevatorlock = false;
        Servo IntakeLift = hardwareMap.servo.get("intakelift");

        Servo OutputLift = hardwareMap.servo.get("outputlift");
        Servo OutputRotation = hardwareMap.servo.get("outputrotate");

        DcMotor elevator = hardwareMap.dcMotor.get("elevator");

        CRServo Intake = hardwareMap.crservo.get("intake");


        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {

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

            //Rotation Pos:
            //Clips: 0
            //top bucket ready: 0.4336
            //top bucket dump: 0.184
            //down position: 0.9706

            //lift pos:
            //Down position: 1
            //clip pickup position: 0.8246
            //bucket position: 0.6275
            // clip score pos: 0.9183

            telemetry.addData("count",count);
            count++;

            //lock the elevator (toggle)
            if(gamepad2.a)
            {
                elevatorlock = true;

            }
            if(gamepad2.b)
            {

                elevatorlock = false;
            }

            //move and lock elevator
            if (elevatorlock)
            {
                elevator.setPower(0.1);

            } else if (gamepad2.dpad_up)
            {
                elevator.setPower(0.3);

            } else if (gamepad2.dpad_down)
            {
                elevator.setPower(-0.3);


            }




           if(gamepad2.right_bumper)
            {
                armSetting += 0.00004;
                OutputLift.setPosition(armSetting);
//                OutputRotation.setPosition(armSetting);
            }

            if(gamepad2.left_bumper)
            {
                armSetting -= 0.00004;
                OutputLift.setPosition(armSetting);
//                OutputRotation.setPosition(armSetting);
            }
            telemetry.addData("servo value",armSetting);
                telemetry.update();
            }




        }
    }