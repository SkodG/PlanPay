package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.ServicesImpl;

class ServicesTest {
	private ServicesImpl services;
	
	@BeforeEach
	public void setUp() {
		services = new ServicesImpl(null);
		
	}
	
	@Test
	void testClassGetters() {
		fail("Not yet implemented");
	}
	
	@Test
	void testClassSetters() {
		fail("Not yet implemented");
	}

}
