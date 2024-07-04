package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.controller.ConsoleControllerImpl;
import pp.projects.controller.LoginControllerImpl;
import pp.projects.model.AccountImpl;
import pp.projects.model.IllegalOperationException;
import pp.projects.model.OperationType;

class ControllerOperationTest {
	
	private ConsoleControllerImpl controller;
	
	@BeforeEach
	 public void setUp() {
		try {
			controller = new ConsoleControllerImpl(new LoginControllerImpl());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInitialization() {
		assertTrue(controller.getAccount() instanceof AccountImpl);
		assertTrue(controller.getAllTransactions().isEmpty());
		assertTrue(controller.getObjectiveList().isEmpty());
		assertTrue(controller.getDatiTransazione().isEmpty());
	}
	
	@Test
	public void testServiceOperations() {	
		//testo che il metodo sollevi un Exception 
		//nel caso in cui si tenti di prelevare dal conto più di quanto è presente
		assertThrows(IllegalOperationException.class, 
				() -> controller.updateConto(1.0, false, "test prelievo 1 euro con conto al verde", OperationType.SERVIZIO));
		//deposito soldi nell'account
		try {
			assertTrue(controller.updateConto(1508.63, true, "stipendio giugno", OperationType.SERVIZIO));
			//controllo che il saldo sia stato incrementato del valore corretto
			assertTrue(controller.getAccount().getBalance() == 1508.63);
			//adesso provo a prelevare una cifra inferiore al saldo complessivo del Conto
			assertTrue(controller.updateConto(500.0, false, "Affitto", OperationType.SERVIZIO));
		} 
		catch (IllegalOperationException e) {
			e.printStackTrace();
		}
		//controllo che il saldo sia calato della quantità prelevata
		assertTrue(controller.getAccount().getBalance() == 1008.63);
	}
	
	@Test
	public void testObjectiveOperations() {
	
		//salvo 2 obbiettivi nella lista obbiettivi
		controller.saveObjective(true, "Nuovo Frigo", "Classe Energetica B", 890);
		controller.saveObjective(true, "Nuovo Portatile", "con SSD da 1TB", 950);
		//tento di depositare soldi dal conto in un obbiettivo
		try {
			controller.updateConto(1000.0, true, "Tredicesima", OperationType.SERVIZIO);
			// Verifico che il deposito in un obbiettivo non salvato in lista fallisca
			assertThrows(IllegalOperationException.class,
					() -> controller.updateConto(100.0, false, "Servizio da Te'", OperationType.OBIETTIVO));
			// Verfico che il deposito verso un obbiettivo salvato in lista riesca solo se il saldo del Conto è sufficiente			
			assertThrows(IllegalOperationException.class,
					() -> controller.updateConto(2000.0, true, "Nuovo Portatile", OperationType.OBIETTIVO));
			assertTrue(controller.updateConto(890.0, true, "Nuovo Frigo", OperationType.OBIETTIVO));
			assertTrue(controller.updateConto(10.0, true, "Nuovo Portatile", OperationType.OBIETTIVO));
			// Verifico che i saldi siano aggiornati
			assertTrue(controller.getAccount().getBalance() == 100.0);
			assertTrue(controller.getObjective("Nuovo Frigo").get().getBalance() == 890.0);
			assertTrue(controller.getObjective("Nuovo Portatile").get().getBalance() == 10.0);
			
			// Verifico fallimento del prelievo da un obbiettivo in cui non è stata raggiunta la soglia di risparmio
			assertThrows(IllegalOperationException.class,
					() -> controller.updateConto(950.0, false, "Nuovo Portatile", OperationType.OBIETTIVO));
			assertTrue(controller.getObjective("Nuovo Portatile").get().getBalance() == 10.0);
			// Verifico riuscita prelievo da obbiettivo in cui la soglia risparmio è stata raggiunta
			assertTrue(controller.updateConto(890.0, false, "Nuovo Frigo", OperationType.OBIETTIVO));
			assertTrue(controller.getObjective("Nuovo Frigo").get().getBalance() == 0.0);			
		} 
		catch (IllegalOperationException e) {
			e.printStackTrace();
		}
	}
}

