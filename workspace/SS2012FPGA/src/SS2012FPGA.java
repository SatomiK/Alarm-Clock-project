/**
 * 
 */

/**
 * @author ohkawa
 *
 */
public interface SS2012FPGA {
	public void setMotorTorque(int id, int torque);
	public void playAlarmSound();
	public void sendIrDAdata(int data);
	public byte[] getCameraImage();
}
