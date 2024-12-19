package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "!!Server Tester")
public class ServoTester extends LinearOpMode {
    private static final String LEFT_BUCKET_ROTATE_NAME = "leftbucketrotate";
    private static final double LEFT_INTAKE_READY_POSITION = 0.48;
    private static final double LEFT_CLIPS_POSITION = .11;
    private static final double LEFT_BUCKET_POSITION = 0.85;
    private static final String RIGHT_BUCKET_ROTATE_NAME = "rightbucketrotate";
    private static final double RIGHT_INTAKE_READY_POSITION = 0.46;
    private static final double RIGHT_CLIPS_POSITION = 0.81;
    private static final double RIGHT_BUCKET_POSITION = 0.09;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        double rightVal = RIGHT_CLIPS_POSITION;
        double leftVal = LEFT_CLIPS_POSITION;
        double incAmount = 0.0004;
        int count = 0;
        boolean elevatorlock = false;
        //Servo IntakeLift = hardwareMap.servo.get("intakelift");

        Servo rightServo = hardwareMap.servo.get("rightbucketrotate");
        Servo leftServo = hardwareMap.servo.get("leftbucketrotate");
        Servo outputlift = hardwareMap.servo.get("leftbucketlift");
        outputlift.setPosition( 0.6661);

        rightServo.setPosition(rightVal);
        leftServo.setPosition(leftVal);

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


            if (gamepad2.a)
            {
                leftVal += incAmount;
                leftServo.setPosition(leftVal);
            }
            if(gamepad2.b)
            {
                leftVal -= incAmount;
                leftServo.setPosition(leftVal);
            }


           if(gamepad2.right_bumper)
           {
               rightVal += incAmount;

                rightServo.setPosition(rightVal);
//                OutputRotation.setPosition(armSetting);
            }

            if(gamepad2.left_bumper)
            {
                rightVal -= incAmount;
                rightServo.setPosition(rightVal);
//                OutputRotation.setPosition(armSetting);
            }

            telemetry.addData("Right Servo",rightServo.getPosition());
            telemetry.addData( "Left Servo", leftServo.getPosition());
            telemetry.update();
            }




        }
    }