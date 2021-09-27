package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RootControllerTest {

	@Test
	public void deveRetornarMensagem() {
		RootController controller = new RootController();
		
		String hello = controller.hello();
		
		assertEquals(RootController.HELLO_WORLD, hello);
	}
}
