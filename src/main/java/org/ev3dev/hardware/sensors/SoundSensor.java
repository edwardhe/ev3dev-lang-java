/*******************************************************************************
 * Any modification, copies of sections of this file must be attached with this
 * license and shown clearly in the developer's project. The code can be used
 * as long as you state clearly you do not own it. Any violation might result in
 *  a take-down.
 *
 * MIT License
 *
 * Copyright (c) 2016, 2017 Anthony Law
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package org.ev3dev.hardware.sensors;

import org.ev3dev.exception.EV3LibraryException;
import org.ev3dev.exception.InvalidModeException;
import org.ev3dev.exception.InvalidPortException;
import org.ev3dev.exception.InvalidSensorException;
import org.ev3dev.hardware.ports.LegoPort;

/**
 * LEGO NXT Sound Sensor
 * @author Anthony
 *
 */
public class SoundSensor extends Sensor {
	
	/**
	 * Sound pressure Sysfs required mode
	 */
	public static final String SYSFS_SOUND_PRESSURE_REQUIRED_MODE = "DB";

	/**
	 * Sound pressure Sysfs value index
	 */
	public static final int SYSFS_SOUND_PRESSURE_VALUE_INDEX = 0;

	/**
	 * Sound pressure Low Sysfs required mode
	 */
	public static final String SYSFS_SOUND_PRESSURE_LOW_REQUIRED_MODE = "DBA";
	
	/**
	 * Sound pressure Low Sysfs value index
	 */
	public static final int SYSFS_SOUND_PRESSURE_LOW_VALUE_INDEX = 0;
	
	/**
	 * This device's default driver name
	 */
	public static final String DRIVER_NAME = "lego-nxt-sound";
	
	public boolean autoSwitchMode = true;

	/**
	 * Creates a new SoundSensor instance.
	 * @param port LegoPort
	 * @throws InvalidPortException If the specified port wasn't valid
	 * @throws InvalidSensorException If the specified sensor wasn't a SoundSensor
	 * @throws EV3LibraryException If I/O goes wrong
	 */
	public SoundSensor(LegoPort port) throws EV3LibraryException, InvalidPortException, InvalidSensorException {
		super(port);
		if(!this.getDriverName().equals(DRIVER_NAME)){
			throw new InvalidSensorException("Can't create a SoundSensor instance if the port isn't connected to a sound sensor!");
		}
	}
	
	/**
	 * A measurement of the measured sound pressure level, as a percent. Uses a flat weighting.
	 * @return The measured sound pressure level
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public float getSoundPressure() throws InvalidModeException, EV3LibraryException{
		if (!this.getMode().equals(SYSFS_SOUND_PRESSURE_REQUIRED_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_SOUND_PRESSURE_REQUIRED_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_SOUND_PRESSURE_REQUIRED_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_SOUND_PRESSURE_VALUE_INDEX);
		return Float.parseFloat(str);
	}
	
	/**
	 * A measurement of the measured sound pressure level, as a percent. Uses A-weighting, which focuses on levels up to 55 dB.
	 * @return The measured sound pressure level, A-weighting.
	 * @throws EV3LibraryException If I/O goes wrong
	 * @throws InvalidModeException The mode selected wasn't valid, or <b>Auto Switch Mode</b> has disabled.
	 */
	public float getSoundPressureLow() throws InvalidModeException, EV3LibraryException{
		if (!this.getMode().equals(SYSFS_SOUND_PRESSURE_LOW_REQUIRED_MODE)){
			if (autoSwitchMode){
				this.setMode(SYSFS_SOUND_PRESSURE_LOW_REQUIRED_MODE);
			} else {
				throw new InvalidModeException("[Auto-switch is off] You are not using a correct mode(" + SYSFS_SOUND_PRESSURE_LOW_REQUIRED_MODE + ")! Yours: " + this.getMode());
			}
		}
		String str = this.getAttribute("value" + SYSFS_SOUND_PRESSURE_LOW_VALUE_INDEX);
		return Float.parseFloat(str);
	}
	
	/**
	 * Set Auto Switch Mode to be enabled or disabled.<br>
	 * (Default: enabled)
	 * @param autoswitch A Boolean
	 */
	public void setAutoSwitchMode(boolean autoswitch){
		this.autoSwitchMode = autoswitch;
	}

	/**
	 * Get whether Auto Switch Mode is enabled or disabled.
	 * @return A Boolean
	 */
	public boolean isAutoSwitchMode(){
		return autoSwitchMode;
	}

}
