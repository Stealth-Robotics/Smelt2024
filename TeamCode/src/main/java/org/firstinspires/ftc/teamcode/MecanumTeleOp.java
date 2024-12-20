package org.firstinspires.ftc.teamcode;

import android.icu.util.Output;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
@Disabled
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        double speedModifier = 1;


        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleftmotor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleftmotor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontrightmotor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backrightmotor");

        Servo IntakeWrist = hardwareMap.servo.get("intakewrist");
        CRServo IntakeSuck = hardwareMap.crservo.get("intakesuck");

        Servo leftIntakeElbow = hardwareMap.servo.get("leftintakeelbow");
        Servo rightIntakeElbow = hardwareMap.servo.get("rightintakeelbow");

        Servo leftOutputLift = hardwareMap.servo.get("leftoutputlift");
        Servo rightOutputLift = hardwareMap.servo.get("rightoutputlift");
        Servo leftOutputRotate = hardwareMap.servo.get("leftoutputrotate");
        Servo rightOutputRotate = hardwareMap.servo.get("rightoutputrotate");

        Servo Clips = hardwareMap.servo.get("clips");

        DcMotor RightElle = hardwareMap.dcMotor.get("rightelle");
        DcMotor LeftElle = hardwareMap.dcMotor.get("leftelle");

        DcMotor RightArm = hardwareMap.dcMotor.get("rightarm");
        DcMotor LeftArm = hardwareMap.dcMotor.get("leftarm");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;
        while (opModeIsActive()) {

            //movement
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_trigger - gamepad1.left_trigger;
            double intakesuckpower = gamepad2.left_trigger - gamepad2.right_trigger;
            //elevator
            float ry = gamepad2.right_stick_y;
            //arm extender
            float dx = gamepad2.left_stick_x;

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
            IntakeSuck.setPower(intakesuckpower);
            if (gamepad1.a) {
                speedModifier = 0.5;
            } else if (!gamepad1.a) {
                speedModifier = 1;
            }
            telemetry.addData("Speed Modifier", speedModifier);
            telemetry.update();
            /////////////////////////////////////////////////
            //   leftOutputLift start position - 0.15
            //   Clips start position - 0.775
            //   Clips hold position - 0.3983
            //
            //
            //
            //
            //
            //
            //
            //
            //////////////////////////////////////////////////
        }

    }

}

