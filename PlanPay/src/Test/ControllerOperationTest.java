package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.model.AccountImpl;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;

class ControllerOperationTest {
	
	private AccountImpl account;
	private ServicesImpl services;
	private ObjectiveImpl objective1;
	private ObjectiveImpl objective2;
	private LocalDate actualDayMonth;
	private ConsoleControllerImpl controller;
	
	@BeforeEach
	 public void setUp() {
		account= new AccountImpl("Maria");
		services = new ServicesImpl(account);
		objective1 = new ObjectiveImpl(account, "Nuovo Frigo", "Classe Energetica B", 890);
		objective2 = new ObjectiveImpl(account, "Nuovo Portatile", "con SSD da 1TB", 950);
		actualDayMonth = LocalDate.now();
		controller = new ConsoleControllerImpl(null);
	}

	@Test
	public void TestInitialization() {
		assertTrue(controller.getAccount().equals(account));
		assertTrue(controller.getAllTransactions().isEmpty());
		assertTrue(controller.getObjectiveList().isEmpty());
		assertTrue(controller.getDatiTransazione().isEmpty());
	}
	
	@Test
	public void TestOperations() {	
		//assertThrows(IllegalOperationException.class,
	//			controller.updateConto(1, false, "test prelievo con conto al verde", OperationType.SERVIZIO));
		//deposito soldi nell'account
		//assertTrue(controller.updateConto(1508.63, true, "stipendio giugno", OperationType.SERVIZIO));
	}
	
	//@Test
	//public void TestTransaction() {
	//	assertTrue(objective2.savingForecast(0, 0, 0, 0, false));
	//}
}
