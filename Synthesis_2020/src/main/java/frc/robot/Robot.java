/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import javax.swing.plaf.basic.BasicTreeUI.TreeSelectionHandler;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;
  private Timer m_timer = new Timer();

  // Variables for the auto
  private double trenchRunLength = 2.25;
  private double trenchRunReturnStart = trenchRunLength + 2;
  private double trenchRunReturnEnd = trenchRunLength + trenchRunReturnStart;

  @Override
  public void robotInit() {
    m_myRobot = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopPeriodic() {
    m_myRobot.tankDrive(m_leftStick.getY(), m_rightStick.getY());
  }

  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  @Override
  public void autonomousPeriodic() {
    // A simple auto to pick up power cells from the trench run and shoot them into the power port

    if (m_timer.get() < trenchRunLength) {
      m_myRobot.arcadeDrive(0.3, 0.0);
    } else if (m_timer.get() > trenchRunReturnStart && m_timer.get() < trenchRunReturnEnd) {
      m_myRobot.arcadeDrive(-0.3, 0.0);
    } else if (m_timer.get() > trenchRunReturnEnd) {
      m_myRobot.stopMotor();
    }
  }
}
