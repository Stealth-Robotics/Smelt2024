package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
@Disabled
public class autonew extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration


        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor");
        DcMotor leftelle = hardwareMap.dcMotor.get("leftelle");
        DcMotor rightelle = hardwareMap.dcMotor.get("rightelle");
        Servo RightIntakeLift = hardwareMap.servo.get("rightintakelift");


        DcMotor leftarm = hardwareMap.dcMotor.get("leftarm");
        DcMotor rightarm = hardwareMap.dcMotor.get("rightarm");
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {
            frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            leftelle.setDirection(DcMotorSimple.Direction.REVERSE);
            rightarm.setDirection(DcMotorSimple.Direction.REVERSE);
            RightIntakeLift.setPosition(0.3457);

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
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(1);
            backLeftMotor.setPower(1);
            frontRightMotor.setPower(1);
            backRightMotor.setPower(1);
            leftelle.setPower(1);
            rightelle.setPower(1);
            sleep(300);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            leftelle.setPower(1);
            rightelle.setPower(1);
            sleep(1000);
            telemetry.addData("here", 0);
            telemetry.update();
            leftelle.setPower(0.1);
            rightelle.setPower(0.1);
            sleep(2000);
            telemetry.addData("here", 0);
            telemetry.update();
            leftelle.setPower(-1);
            rightelle.setPower(-1);
            sleep(3000);
            telemetry.addData("here", 0);
            telemetry.update();
            leftelle.setPower(0);
            rightelle.setPower(0);
            sleep(1000);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(-1);
            backLeftMotor.setPower(-1);
            frontRightMotor.setPower(-1);
            backRightMotor.setPower(-1);
            sleep(200);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(500);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            sleep(2500);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(500);
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(-0.5);
            sleep(800);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(500);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(-frontLeftPower);
            backLeftMotor.setPower(-backLeftPower);
            frontRightMotor.setPower(-frontRightPower);
            backRightMotor.setPower(-backRightPower);
            sleep(470);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(500);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(1);
            backLeftMotor.setPower(1);
            frontRightMotor.setPower(1);
            backRightMotor.setPower(1);
            sleep(650);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(300);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            sleep(250);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(100);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(-0.5);
            sleep(2200);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(500);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(1);
            backLeftMotor.setPower(1);
            frontRightMotor.setPower(1);
            backRightMotor.setPower(1);
            sleep(650);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(300);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            sleep(240);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(100);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(-0.5);
            sleep(2300);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(200);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(-frontLeftPower);
            backLeftMotor.setPower(-backLeftPower);
            frontRightMotor.setPower(-frontRightPower);
            backRightMotor.setPower(-backRightPower);
            sleep(100);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(200);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(1);
            backLeftMotor.setPower(1);
            frontRightMotor.setPower(1);
            backRightMotor.setPower(1);
            sleep(600);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(300);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            sleep(500);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(frontLeftPower*0.5);
            backLeftMotor.setPower(backLeftPower*0.5);
            frontRightMotor.setPower(frontRightPower*0.5);
            backRightMotor.setPower(backRightPower*0.5);
            sleep(1000);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(0);
            backLeftMotor.setPower(0);
            frontRightMotor.setPower(0);
            backRightMotor.setPower(0);
            sleep(300);
            telemetry.addData("here", 0);
            telemetry.update();
            frontLeftMotor.setPower(-0.5);
            backLeftMotor.setPower(-0.5);
            frontRightMotor.setPower(-0.5);
            backRightMotor.setPower(-0.5);
            sleep(2500);
            break;
        }
    }
}
