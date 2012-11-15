
public class SS2012FPGA_Impl implements SS2012FPGA {

	@Override
	public void setMotorTorque(int id, int torque) {
		System.out.println("setMotorTorque called: id="+id+" torque="+torque);
	}

	@Override
	public void playAlarmSound() {
		System.out.println("playAlarmSound called:");
	}

	@Override
	public void sendIrDAdata(int data) {
		System.out.println("sendIrDAdata called: data="+data);
	}

	@Override
	public byte[] getCameraImage() {
		System.out.println("getCameraImage called:");
		return new byte[]{1,2,3,4};
	}

}
