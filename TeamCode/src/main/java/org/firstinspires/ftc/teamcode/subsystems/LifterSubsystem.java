package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.stealthrobotics.library.StealthSubsystem;

/**
 * Example usage of a single DC motor with PIDF control.
 */
@Config
public class LifterSubsystem extends StealthSubsystem {

    public static final double HOLD_POWER = 0.1;
    private static final String LEFT_ELEVATOR = "leftelle";
    private static final String RIGHT_ELEVATOR = "rightelle";
    private static final double KP = 0.006;
    private static final double KI = 0.00;
    private static final double KD = 0.0;
    private static final double KF = 0.00;
    private static final double TOLERANCE = 10.0;
    private static final double MAX_SPEED = 1;
    private static final double MAX_HEIGHT = 4367;
    private final PIDFController pidf = new PIDFController(KP, KI, KD, KF);
    private final MotorGroup motors;
    private final MotorEx leftElevator;
    private final MotorEx rightElevator;
    private final Telemetry telemetryA;
    private Boolean usingPidf = false;

    public LifterSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {

        this.telemetryA = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        leftElevator = new MotorEx(hardwareMap, LEFT_ELEVATOR);
        rightElevator = new MotorEx(hardwareMap, RIGHT_ELEVATOR);
        rightElevator.setInverted(true);

        motors = new MotorGroup(leftElevator, rightElevator);
        motors.setRunMode(Motor.RunMode.RawPower);
        motors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        pidf.setTolerance(TOLERANCE);
        resetEncoder();
    }

    /**
     * Called by the scheduler if registered and running
     */
    @Override
    public void periodic() {
        if (usingPidf) {
            double power = pidf.calculate(getPosition());
            motors.set(power * -MAX_SPEED);

//            if (pidf.atSetPoint()) {
//                motors.set(HOLD_POWER);
//                usingPidf = false;
//            }
        }

        telemetryA.addData("Left Position:", leftElevator.getCurrentPosition());
        telemetryA.addData("Right Position:", rightElevator.getCurrentPosition());
        telemetryA.addData("usingPidf:", usingPidf);
    }

    /**
     * Starts the motor moving but does not wait for it to reach the desired position
     * @param position % of the arm range
     * @return A Command that can be run
     */
    public Command startSetPositionCommand(double position) {
        return this.runOnce(()-> setPosition(position));
    }

    /**
     * After calling setPosition this can be called to wait for the position to be reached
     * @param timeout waiting time before giving up on the desired position
     * @return command to wait for the position to be reached
     */
    public Command endSetPositionCommand(long timeout) {
        long endTime = System.currentTimeMillis() + timeout;
        return new WaitUntilCommand(()-> (!usingPidf || System.currentTimeMillis() >= endTime));
    }

    /**
     * Sets the power of the lift motor
     * @param power power to set
     */
    public void setPower(double power) {
        motors.set(power);
    }

    /**
     * Sets the position of the lift motor in % of max range
     * 1 = full up .5 = half up. should be used with limit switches
     * @param position position in % of max range
     */
    public void setPosition(double position) {
        pidf.setSetPoint(position * MAX_HEIGHT);
        usingPidf = true;
    }


    /**
     * Stops the motor(s) from autonomously moving
     */
    public void stopRunTo()
    {
        usingPidf = false;
    }

    /**
     * Returns the current position of the lift motor
     * @return current position
     */
    public int getPosition() {
        return this.rightElevator.getCurrentPosition();
    }

    /**
     * Resets the encoder of the lift motor to 0
     */
    public void resetEncoder() {
        motors.resetEncoder();
        motors.setRunMode(Motor.RunMode.RawPower);
    }

    /**
     * Command to set the position of the lift motor in encoder ticks
     * @param position Position in % of range of the lifter
     * @return A Command that can be run
     */
    public Command setPositionCommand(double position) {
        return this.runOnce(()-> setPosition(position))
                .andThen(new WaitUntilCommand(()-> !usingPidf));
    }

    /**
     * Sets the Lifter position with timeout
     * @param position % of max extension
     * @param timeout Max number of ms to wait for the position to be reached
     * @return Command to wait for the position to be reached
     */
    public Command setPositionCommand(double position, long timeout) {
        long endTime = System.currentTimeMillis() + timeout;
        return this.runOnce(()-> setPosition(position))
                .andThen(new WaitUntilCommand(()-> (!usingPidf || System.currentTimeMillis() > endTime)));
    }

    /**
     * Returns the lift motor
     * @return raw DcMotorEx object
     */
    public MotorGroup getMotors() {
        return this.motors;
    }
}
