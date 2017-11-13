package com.toutiao.util;

import java.util.Random;

public class RandomCount {
	public static int getRandomCount() {
		int max = 2000;
		int min = 1500;
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
}
