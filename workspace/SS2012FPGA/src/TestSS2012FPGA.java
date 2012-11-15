
public class TestSS2012FPGA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SS2012FPGA_Impl fpga = new SS2012FPGA_Impl();
		fpga.setMotorTorque(1, 10);
		fpga.playAlarmSound();
		fpga.sendIrDAdata(99);
		byte[] img = fpga.getCameraImage();
		for(int i=0; i<img.length; i++) {
			System.out.println(img[i]);
		}
	}

}
