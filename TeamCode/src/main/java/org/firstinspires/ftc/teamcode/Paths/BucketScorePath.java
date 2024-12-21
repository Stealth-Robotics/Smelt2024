package org.firstinspires.ftc.teamcode.Paths;

import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.teamcode.pedroPathing.tuning.FollowerConstants;
import org.stealthrobotics.library.Alliance;

/**
 * Example of building a path chain. and chunking it into segments.
 * This allows for doing other operations at the end of each segment like get a sample etc.
 */
public class BucketScorePath extends PathManager {

    // Sets the default start point for this path. Use Limelight during whileWaitingToStart to
    // update this location in your Command code.
    private static final Pose DEFAULT_START_POINT_BLUE = new Pose(
            11, 118,
            Math.toRadians(135));

    private static final Pose DEFAULT_START_POINT_RED = new Pose(
            11, 118,
            Math.toRadians(135));

    public BucketScorePath() {
        super(DEFAULT_START_POINT_BLUE);

        if (Alliance.isRed()) {
            startPose = DEFAULT_START_POINT_RED;
        }

        this.bluePathChain = buildBluePathChain();
        this.redPathChain = buildRedPathChain();
        // chop up the full path chain into separate path chains
        // so that other operations (like lifter movements) can be done at each segment
        createSegment(0);
        createSegment(1, 3);
        buildSegments();
    }

    public PathChain buildBluePathChain() {
        PathBuilder builder = new PathBuilder();

        return builder
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(11.000, 118.000, Point.CARTESIAN),
                                new Point(11.500, 123.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(135))
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(11.500, 123.000, Point.CARTESIAN),
                                new Point(28.000, 112.600, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(135), Math.toRadians(180))
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(28.000, 112.600, Point.CARTESIAN),
                                new Point(50.000, 109.500, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(50.000, 109.500, Point.CARTESIAN),
                                new Point(50.700, 99.700, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    /**
     * Builds the red path chain
     * @return Returns the Red PathChain for when on the Red Alliance
     */
    protected PathChain buildRedPathChain() {
        // Can just invert the blue chain to get the red chain
        return buildBluePathChain() ;
    }
}
