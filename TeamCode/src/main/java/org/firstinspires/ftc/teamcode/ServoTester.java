package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "!!Server Tester")
public class ServoTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        double armSetting = .5;
        int count = 0;
        boolean elevatorlock = false;
        //Servo IntakeLift = hardwareMap.servo.get("intakelift");

        Servo OutputLiftLeft = hardwareMap.servo.get("leftbucketlift");
        //Servo OutputLiftRight = hardwareMap.servo.get("outputliftright");
        //Servo OutputRotationRight = hardwareMap.servo.get("outputrotateright");

        //DcMotor elevator = hardwareMap.dcMotor.get("elevator");

        //CRServo Intake = hardwareMap.crservo.get("intake");


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



            telemetry.addData("count",count);
            count++;

            //lock the elevator (toggle)



           if(gamepad2.right_bumper)
           {
               armSetting += 0.0004;

                OutputLiftLeft.setPosition(armSetting);
//                OutputRotation.setPosition(armSetting);
            }

            if(gamepad2.left_bumper)
            {
                armSetting -= 0.0004;
                OutputLiftLeft.setPosition(armSetting);
//                OutputRotation.setPosition(armSetting);
            }
            telemetry.addData("servo value",OutputLiftLeft.getPosition());
                telemetry.update();
            }




        }
    }