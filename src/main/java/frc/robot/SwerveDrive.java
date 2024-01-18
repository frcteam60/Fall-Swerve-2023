// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.management.DescriptorRead;

// convert angle here to angle elsewhere
// check SparkMax CAN numbers

/** Add your docs here. */
public class SwerveDrive {
    private WheelDrive backRight;
    private WheelDrive backLeft;
    private WheelDrive frontRight;
    private WheelDrive frontLeft;
    //dimensions between wheels center-to-center
    public final double length = 27.4;
    public final double width = 12.25;

    // Gyro
    double gyro_radians;
    double gyro_degrees;
    double temp;
    double forward;
    double strafe;
    double rotation;
    double desiredYaw = 0;
    double error;


    // SwerveDrive constructor
    public SwerveDrive (WheelDrive backRight, WheelDrive backLeft, WheelDrive frontRight, WheelDrive frontLeft){
        this.backRight = backRight;
        this.backLeft = backLeft;
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
    }

    // Coerces a value to range
    public double coerceToRange (double number, double min, double max){
        double coercedValue;
        if (number >= max){
            coercedValue = max;
        }
        else if (number <= min){
            coercedValue = min;
        } else {
            coercedValue = number;
        }
        
        return coercedValue;
    }

    // drive method
    public void drive (double x1, double y1, double x2, double gyroAngle) {
        rotation = Math.sqrt((length * length) + (width * width));

        if (x2 >= -0.01 && x2 <=0.01){
           x2 = 0;
        } else {
            desiredYaw = gyroAngle + x2;
            error = desiredYaw - gyroAngle;
            x2 = coerceToRange(error, -1, 1);
        }

        if (y1 >= -0.01 && y1 <= 0.01) {
            forward = 0;
        } else {
            forward = y1 * -1;
        }

        if (x1 >= -0.01 && x1 <= 0.01){
            strafe = 0;
        } else {
            strafe = x1;
        }

        /*rotation = Math.sqrt((length * length) + (width * width));
        forward = y1 * -1;
        strafe = x1;*/

         // Adjusts values to field oriented drive
        gyro_degrees = gyroAngle;
        gyro_radians = gyro_degrees * Math.PI/180;
        temp = forward * Math.cos(gyro_radians) + strafe * Math.sin(gyro_radians);
        strafe = (forward * -1) * Math.sin(gyro_radians) + strafe * Math.cos(gyro_radians);
        forward = temp;
        

        double a = strafe - x2 * (length / rotation); //back horizontal
        double b = strafe + x2 * (length / rotation); //front horizontal
        double c = forward - x2 * (width / rotation);  //left vertical
        double d = forward + x2 * (width / rotation);  //right vertical

        /*We switched left and right(we could also have switched front and back)
        * this change should turn the wheels the right way when the robot is trying to rotate
        */
        //Speed Values
        double backRightSpeed = Math.sqrt ((a * a) + (c * c));
        double backLeftSpeed = Math.sqrt ((a * a) + (d * d));
        double frontRightSpeed = Math.sqrt ((b * b) + (c * c));
        double frontLeftSpeed = Math.sqrt ((b * b) + (d * d));
        //Angle Values
        double backRightAngle = (Math.atan2(a, c) / Math.PI / 2); 
        double backLeftAngle = (Math.atan2(a, d) / Math.PI / 2);
        double frontRightAngle = (Math.atan2(b, c) / Math.PI / 2);
        double frontLeftAngle = (Math.atan2(b, d) / Math.PI / 2);
        
        backRight.drive(backRightSpeed, backRightAngle);
        backLeft.drive(backLeftSpeed, backLeftAngle);
        frontRight.drive(frontRightSpeed, frontRightAngle);
        frontLeft.drive(frontLeftSpeed, frontLeftAngle);
    }

    // drive method
    public void angleDrive (double x1, double y1, double desiredGyroAngle, double gyroAngle) {
        rotation = Math.sqrt((length * length) + (width * width));

        double x2;
        desiredYaw = desiredGyroAngle;
        error = desiredYaw - gyroAngle;
        x2 = coerceToRange(error, -1, 1);
        

        if (y1 >= -0.01 && y1 <= 0.01) {
            forward = 0;
        } else {
            forward = y1 * -1;
        }


        if (x1 >= -0.01 && x1 <= 0.01){
            strafe = 0;
        } else {
            strafe = x1;
        }

        /*rotation = Math.sqrt((length * length) + (width * width));
        forward = y1 * -1;
        strafe = x1;*/

         // Adjusts values to field oriented drive
        gyro_degrees = gyroAngle;
        gyro_radians = gyro_degrees * Math.PI/180;
        temp = forward * Math.cos(gyro_radians) + strafe * Math.sin(gyro_radians);
        strafe = (forward * -1) * Math.sin(gyro_radians) + strafe * Math.cos(gyro_radians);
        forward = temp;
        

        double a = strafe - x2 * (length / rotation); //back horizontal
        double b = strafe + x2 * (length / rotation); //front horizontal
        double c = forward - x2 * (width / rotation);  //left vertical
        double d = forward + x2 * (width / rotation);  //right vertical

        /*We switched left and right(we could also have switched front and back)
        * this change should turn the wheels the right way when the robot is trying to rotate
        */
        //Speed Values
        double backRightSpeed = Math.sqrt ((a * a) + (c * c));
        double backLeftSpeed = Math.sqrt ((a * a) + (d * d));
        double frontRightSpeed = Math.sqrt ((b * b) + (c * c));
        double frontLeftSpeed = Math.sqrt ((b * b) + (d * d));
        //Angle Values
        double backRightAngle = (Math.atan2(a, c) / Math.PI / 2); 
        double backLeftAngle = (Math.atan2(a, d) / Math.PI / 2);
        double frontRightAngle = (Math.atan2(b, c) / Math.PI / 2);
        double frontLeftAngle = (Math.atan2(b, d) / Math.PI / 2);
        
        backRight.drive(backRightSpeed, backRightAngle);
        backLeft.drive(backLeftSpeed, backLeftAngle);
        frontRight.drive(frontRightSpeed, frontRightAngle);
        frontLeft.drive(frontLeftSpeed, frontLeftAngle);
    }

    // 
    public void setDesiredYaw(double setDesiredYaw){
        desiredYaw = setDesiredYaw;
    }

    
}

