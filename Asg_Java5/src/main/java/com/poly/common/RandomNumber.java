package com.poly.common;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumber {

	public int randomNumber() {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		int value = rand.nextInt(100000, 999999);
		return value;
	}

	public static void main(String[] args) {
		RandomNumber rand = new RandomNumber();
		System.out.println(rand.randomNumber());
	}
}
