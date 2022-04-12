public class CircuitMod {

	static CirSim ogf;

	public static void main(String args[]) {
		// Create program frame
		ogf = new CirSim(null);

		// Init program in background thread.
		// Show logo in the meantime.
		final LogoFrame logoFrame = new LogoFrame();

		final String[] a = args;

		final long time = System.nanoTime();

		Thread t1 = new Thread() {
			public void run() {
				// If a file is being loaded at startup

				boolean initOk = false;
				if (a.length > 0) {
					initOk = ogf.init(a[0]);
				} else {
					initOk = ogf.init(null);
				}

				if (!initOk) {
					logoFrame.dispose();
				} else {

					// When program finishes initializing, dispose logo and show
					// program frame.
					// Make it so that the logo is shown at least 1.5 seconds.
					long timeElapsed = (System.nanoTime() - time) / 1000000;

					if (timeElapsed < 1500) {
						try {
							sleep(1500 - (int) timeElapsed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					logoFrame.dispose();
					ogf.setFrameAndShow();
				}
			}
		};
		t1.start();
	}
};
