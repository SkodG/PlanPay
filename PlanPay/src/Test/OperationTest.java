package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.AbstractOperations;
import pp.projects.model.AccountImpl;
import pp.projects.model.Objective;
import pp.projects.model.ObjectiveImpl;
import pp.projects.model.ServicesImpl;
import pp.projects.model.Transaction;

class OperationTest {
	
	private AccountImpl account;
	private ServicesImpl services;
	private ObjectiveImpl objective1;
	private ObjectiveImpl objective2;
	private LocalDate actualDayMonth;
	
	@BeforeEach
	 public void setUp() {
	//istanzio un AccountImpl, un ServicesImpl e due ObjectiveImpl
		account = new AccountImpl("Luca");
		services = new ServicesImpl(account);
		objective1 = new ObjectiveImpl(account, "Auto", "Tesla", 30000);
		objective2 = new ObjectiveImpl(account, "Gaming", "Playstation5", 600);
		actualDayMonth = LocalDate.now();	
	}

	
	@Test
	public void TestInitialization() {
		
		//testo che gli oggetti siano stati inizializzati correttamentte
		assertTrue(account.getName().equals("Luca"));
		assertTrue(account.getBalance() == 0.0);
		
		assertTrue(services.nome().equals(""));
		assertTrue(services.getList().isEmpty());
		
		assertTrue(services  instanceof AbstractOperations);
		assertTrue(objective1 instanceof AbstractOperations);
		assertTrue(objective2 instanceof AbstractOperations);
		
		assertTrue(objective1.getName().equals("Auto Elettrica"));		
		assertTrue(objective2.getName().equals("Gaming"));
		
		assertTrue(objective1.getDescription().equals("Tesla"));		
		assertFalse(objective2.getDescription().equals("Playstation5"));	
		
		assertTrue(objective1.getSavingTarget() == 30000);
		assertTrue(objective2.getSavingTarget() == 600.0);
		assertTrue(objective1.getBalance() == 0.0);
		assertFalse(objective1.isTargetMet());		
		
		assertTrue(objective1.getDate().equals(actualDayMonth));
		
		assertTrue(objective1.getList().isEmpty());
		assertTrue(objective2.getList().isEmpty());
	}
	@Test
	public void TestObjectiveSetters(){
		objective1.setName("Auto Elettrica");
		objective2.setDescription("Next-gen console");
	}

	
	@Test
	public void TestServicesOperations(){
		account.setBalance(0.0);
		assertTrue(services.getList().isEmpty());
		
		services.deposit(1500.73, "stipendio");
		assertTrue(account.getBalance() == 1500.73);		
		assertTrue(services.withdraw(150, "cena fuori"));
		assertTrue(account.getBalance() == 1350.73);
		assertFalse(services.getList().isEmpty());
		assertTrue(services.getList().size() == 2);		
	}
	
	@Test
	public void TestObjectiveOperations() {		
		assertTrue(objective1.getBalance() == 0.0);
		assertTrue(objective1.getList().isEmpty());
		account.setBalance(10000.0);
		objective1.deposit(317.90, "risparmi del mese");
		
		assertTrue(objective1.getDate().equals(actualDayMonth));
		assertTrue(objective1.getBalance() == 317.90);
		assertFalse(objective1.isTargetMet());
		assertFalse(objective1.withdraw(317.90, "ho cambiato idea"));
		assertTrue(objective1.getBalance() == 317.90);
		assertTrue(objective1.getDate().equals(actualDayMonth));
		assertTrue(objective1.getList().size() == 1);
		
		Transaction tr1 = objective1.getList().get(0); 
		assertTrue(tr1.getAmount() == 317.90);
		assertTrue(tr1.getDescription().contains("risparmi del mese"));
		
		objective1.setSavingTarget(0);
		assertTrue(objective1.withdraw(317.90, "mi servono assolutamente"));
		assertTrue(objective1.getBalance() == 0.0);
	}
	
	//@Test
	//public void TestSavingForecast() {
	//	assertTrue(objective2.savingForecast(0, 0, 0, 0, false));
	//}
	
	//@Test
	//public void TestTransaction() {
	//	assertTrue(objective2.savingForecast(0, 0, 0, 0, false));
	//}
}
