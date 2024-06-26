package gui.dialogs.prosessors;

import gui.language.Languages;
import hardware.serial.InputHandler;

import javax.swing.*;

public class PinProcessor {
	private volatile char keypress;
	private final JLabel display;
	private final String TEXT = Languages.getLang().getPin_query();
	private String pin;
	private static volatile boolean going;
	public PinProcessor(JLabel display) {
		this.display = display;

		going = true;

		Thread keyConsumer = new Thread(new RunnableKeyConsumer());
		Thread keyProducer = new Thread(new RunnableKeyProducer());

		keyConsumer.start();
		keyProducer.start();

		try {
			keyConsumer.join();
			keyProducer.join();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
			System.out.println(pin);
		}
	}
	public String getPinCode() {
		return pin;
	}
	public static void stopKeypad() {
		going = false;
	}
	private void keyConsume() throws InterruptedException {
		String pin = "";
		StringBuilder blurryPin = new StringBuilder();
		while (true) {
			synchronized (this) {
				if (!going) throw new InterruptedException();
				display.setText(TEXT + blurryPin);
				wait();
				if (keypress != 'K') {
					pin = processInput(pin, keypress);
					blurryPin.setLength(0);
					blurryPin.append("*".repeat(pin.length()));
				}
				else {
					if (pin.length() < 4) continue;
					throw new InterruptedException(pin);
				}
			}
		}
	}

	private void keyProduce() throws InterruptedException {
		InputHandler.setKey(false);
		while (true) {
			synchronized (this) {
				if (!going) {
					notify();
					throw new InterruptedException();
				}
				wait(10);
				if (InputHandler.isNewKey()) {
					keypress = InputHandler.getKeyPress();
					InputHandler.setKey(false);
					notify();
				}
			}
		}
	}

	private String processInput(String pin, char input) {
		switch (input) {
			case '/':
				return pin;
			case 'D':
				if (!pin.isEmpty()) return pin.substring(0,pin.length() - 1);
			case 'C':
				return "";
			default:
				if (pin.length() < 4) pin += input;
				return pin;
		}
	}

	private class RunnableKeyConsumer implements Runnable {
		@Override
		public void run() {
			try {
				keyConsume();
			} catch (InterruptedException e) {
				going = false;
				pin = e.getMessage();
			}
		}
	}
	private class RunnableKeyProducer implements Runnable {
		@Override
		public void run() {
			try {
				keyProduce();
			} catch (InterruptedException ignored) {}
		}
	}
}
