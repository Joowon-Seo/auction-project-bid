package com.sjw.bid;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaFooTest {

	private JavaFoo javaFoo = new JavaFoo();

	@Test
	public void partiallyCoveredHelloMethodTest() {
		String actual = javaFoo.hello("펭");
		assertEquals(actual, "하");
	}

}