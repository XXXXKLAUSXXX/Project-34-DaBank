package gui.dialogs.prosessors;

import gui.language.Languages;
import hardware.Bills;
import hardware.serial.InputHandler;

import javax.swing.*;

public class CustomBillsProcessor {
	private volatile char keypress;
	private final JLabel display;
	private final int[] amounts = new int[4];
	private static volatile boolean going;
	public CustomBillsProcessor(JLabel display) {
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
			System.out.println(amounts);
		}
	}
	public int[] getAmounts() {
		return amounts;
	}
	public static void stopKeypad() {
		going = false;
	}
	private char[] keyConsume() throws InterruptedException {
		char[] amounts = {'\u0000','\u0000','\u0000','\u0000'};
		while (true) {
			for (int i = 0; i < 4; i++) {
				if (Bills.check(i) == 0) {
					amounts[i] = '0';
				}
			}
			synchronized (this) {
				if (!going) throw new InterruptedException();
				display.setText(toText(amounts));
				wait();
				if (keypress != 'K') {
					processInput(amounts, keypress);
				}
				else {
					if (amountFilled(amounts)) return amounts;
				}
			}
		}
	}
	private String toText(char[] amounts) {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			str.append(Languages.getLang().getBills_query()[i]);
			str.append(amounts[i]);
		}
		return "<html>" + str + "</html>";
	}
	private boolean amountFilled(char[] amount) {
		for (int i = 0; i < 4; i++) {
			if (amount[i] == '\u0000') {
				System.out.println("hit");
				return false;
			}
		}
		return true;
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

	private void processInput(char[] amounts, char input) {
		switch (input) {
			case '/':
				break;
			case 'D':
				for (int i = 3; i >= 0; i--) {
					if (amounts[i] != '\u0000' && Bills.check(i) != 0) {
						amounts[i] = '\u0000';
						return;
					}
				}
				break;
			case 'C':
				for (int i = 0; i < 4; i++) {
					amounts[i] = '\u0000';
				}
				break;
			default:
				for (int i = 0; i < 4; i++) {
					if (amounts[i] == '\u0000') {
						amounts[i] = (Bills.check(i) >= Integer.parseInt(String.valueOf(input)))
								? input : Integer.toString(Bills.check(i)).toCharArray()[0];
						return;
					}
				}
		}
	}

	private class RunnableKeyConsumer implements Runnable {
		@Override
		public void run() {
			try {
				char[] amountChars = keyConsume();
				for (int i = 0; i < 4; i++) {
					amounts[i] = Character.getNumericValue(amountChars[i]);
				}
				stopKeypad();
			} catch (InterruptedException | NumberFormatException ignored) {}
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
