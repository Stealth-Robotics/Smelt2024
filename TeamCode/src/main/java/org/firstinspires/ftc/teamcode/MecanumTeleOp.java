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
        float bucketmodifier = 1f;
        boolean elevatorlock = false;
        float ry = 0;

       float OUTPUT_LIFT_ROTATION = 0;

        /*DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor"); */



        Servo LeftOutputLift = hardwareMap.servo.get("leftoutputlift");
        Servo RightOutputLift = hardwareMap.servo.get("rightoutputlift");
        Servo LeftOutputRotate = hardwareMap.servo.get("leftoutputrotate");
        Servo RightOutputRotate = hardwareMap.servo.get("rightoutputrotate");
        Servo RightIntakeLift = hardwareMap.servo.get("rightintakelift");
        Servo LeftIntakeLift = hardwareMap.servo.get("leftintakelift");

        CRServo intake = hardwareMap.crservo.get("intake");




       // DcMotor elevator = hardwareMap.dcMotor.get("elevator");
      //  DcMotor armextender = hardwareMap.dcMotor.get("armextender");

        //frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
       // elevator.setDirection(DcMotorSimple.Direction.REVERSE);




        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {

            // Declare our motors
            // Make sure your ID's match your configuration
            float elevatorpos = 0;

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

            RightIntakeLift.setPosition(0.3457);

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
                double rzx = gamepad2.right_trigger - gamepad2.left_trigger;
                    ry = gamepad2.right_stick_y;


                leftelle.setPower(ry);
                rightelle.setPower(ry);
                leftelle.setDirection(DcMotorSimple.Direction.REVERSE);

                //arm extender
                float rx = gamepad2.right_stick_x;
                leftarm.setPower(rx);
                rightarm.setPower(rx);
                rightarm.setDirection(DcMotorSimple.Direction.REVERSE);
                //intak
                //
                

                if(gamepad2.right_bumper)
                {
                    bucketmodifier = 0.5f;
                }
                else if(gamepad2.left_bumper)
                {
                    bucketmodifier = 0.25f;
                }
               else
                {
                    bucketmodifier = 1;
                }
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
                intake.setPower(rzx * bucketmodifier);

                LeftIntakeLift.setPosition(1);





          //  elevator.setPower(ry);
           // armextender.setPower(dx);


            if(gamepad1.a&&speedModifier == 1)
            {
                speedModifier = 0.5;
            }
            else if (gamepad1.a&&speedModifier == 0.5)
            {
                speedModifier = 1;
            }
            //dumping
            if(gamepad2.a)
            {
                LeftOutputLift.setPosition(0.6817);
                //RightOutputLift.setPosition(0.5032);
                //LeftOutputRotate.setPosition(0.2153);
            }
            //clips
            if(gamepad2.dpad_up)
            {
                LeftOutputLift.setPosition(0.5733);
            }
            //start position
            if(gamepad2.dpad_down)
            {
                LeftOutputLift.setPosition(0.1456);
                //RightOutputLift.setPosition(0.0343);
                //LeftOutputRotate.setPosition(0.7158);
            }
            if(gamepad2.dpad_left)
            {
                LeftOutputRotate.setPosition(0.4773);
            }
            if(gamepad2.right_bumper)
            {
                LeftOutputLift.setPosition(0.9194);
                //RightOutputLift.setPosition(0.5032);
                //LeftOutputRotate.setPosition(0.8);
            }

            if(intaketoggle == true)
            {
                RightIntakeLift.setPosition(0.3025);
            } else if(intaketoggle == false)
            {
                RightIntakeLift.setPosition(0.73);
            }



            if(gamepad2.b && intaketoggle == false)
            {
               intaketoggle = true;
            } else if (gamepad2.b && intaketoggle == true)
            {
                intaketoggle = false;
            }










        }






            telemetry.addData("Intake Toggle", intaketoggle);

            // set to drop bucket contents 0
            //


            telemetry.update();




            telemetry.addData("Speed Modifier", speedModifier);

                // set to drop bucket contents 0
                //


                telemetry.update();
            }

        }
}


