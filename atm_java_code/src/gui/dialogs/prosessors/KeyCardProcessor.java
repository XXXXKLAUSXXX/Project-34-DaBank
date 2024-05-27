package gui.dialogs.prosessors;

import gui.language.Languages;
import hardware.serial.InputHandler;

import javax.swing.*;

public class KeyCardProcessor {
	private volatile String rfid;
	private final JLabel display;
	private final String TEXT = Languages.getLang().getKeycard_query();
	private static volatile boolean going;
	public KeyCardProcessor(JLabel display) {
		this.display = display;

		going = true;

		Thread keyConsumer = new Thread(new RunnableRfidConsumer());
		Thread keyProducer = new Thread(new RunnableRfidProducer());

		keyConsumer.start();
		keyProducer.start();

		try {
			keyConsumer.join();
			keyProducer.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			System.out.println(this.rfid);
		}
	}
	public String getRfid() {
		return rfid;
	}
	public static void stopRfidScanner() {
		going = false;
	}
	private void rfidConsume() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!going) throw new InterruptedException();
				display.setText(TEXT);
				wait();
				if (!going) throw new InterruptedException();
				if (rfid.length() == 26) {
					going = false;
					throw new InterruptedException(rfid);
				}
				else {
					System.out.println(rfid);
					rfid = "";
				}
			}
		}
	}

	private void rfidProduce() throws InterruptedException {
		InputHandler.setRfid(false);
		while (true) {
			synchronized (this) {
				if (!going) {
					notify();
					throw new InterruptedException();
				}
				wait(100);
				if (InputHandler.isNewRfid()) {
					rfid = InputHandler.getRfid();
					InputHandler.setRfid(false);
					notify();
				}
			}
		}
	}

	private class RunnableRfidConsumer implements Runnable {
		@Override
		public void run() {
			try {
				rfidConsume();
			} catch (InterruptedException e) {
				going = false;
				rfid = e.getMessage();
			}
		}
	}
	private class RunnableRfidProducer implements Runnable {
		@Override
		public void run() {
			try {
				rfidProduce();
			} catch (InterruptedException ignored) {}
		}
	}
}
