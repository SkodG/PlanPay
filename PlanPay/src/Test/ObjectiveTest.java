package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.AbstractOperations;
import pp.projects.model.AccountImpl;
import pp.projects.model.IllegalInputException;
import pp.projects.model.ObjectiveImpl;

class ObjectiveTest {
	
	private ObjectiveImpl objective1,
						  objective2;
	private AccountImpl account;
	private LocalDate actualDayMonth;

	@BeforeEach
	public void setUp() {
		objective1 = new ObjectiveImpl(null, "Auto", "Tesla", 30000);
		objective2 = new ObjectiveImpl(null, "Gaming", "Playstation5", 600);
		account = new AccountImpl("Giovanni");
		actualDayMonth = LocalDate.now();
	}
    @Test
    public void testInitialization() {
		assertTrue(objective1 instanceof AbstractOperations);
		assertTrue(objective2 instanceof AbstractOperations);
		assertTrue(objective1.getName().equals("Auto"));		
		assertTrue(objective2.getName().equals("Gaming"));		
		assertTrue(objective1.getDescription().equals("Tesla"));		
		assertFalse(objective2.getDescription().equals("Playstation5"));		
		assertTrue(objective1.getSavingTarget() == 30000);
		assertTrue(objective2.getSavingTarget() == 600.0);
		assertTrue(objective1.getBalance() == 0.0);
		assertFalse(objective1.isTargetMet());			
		assertFalse(objective2.isTargetMet());
		assertTrue(objective1.getDate().equals(actualDayMonth));	
		assertTrue(objective2.getDate().equals(actualDayMonth));
		assertTrue(objective1.getList().isEmpty());
		assertTrue(objective2.getList().isEmpty());
    }
    
	@Test
	public void testClassSetters() {
		objective1.setDescription("Auto Elettrica");
		assertTrue(objective1.getDescription().equals("Auto Elettrica"));
		
		objective1.setName("Next-gen Console");
		assertTrue(objective1.getName().equals("Next-gen Console"));
		assertFalse(objective1.isTargetMet());
		objective2.setSavingTarget(0.0);
		assertTrue(objective1.getBalance() == 0.0);
		assertTrue(objective2.getSavingTarget() == 0.0);
		assertTrue(objective2.isTargetMet());
	}
	
	@Test
	public void testClassOperations() {
		account.addBalance(280.22);
		assertTrue(objective1.deposit(280.22, "risparmi mese"));
		assertTrue(objective1.getBalance() == 280.22);
		assertFalse(objective1.withdraw(50.0, "tentativo di prelievo1"));
		assertTrue(objective1.getList().size() == 1);
		assertTrue(account.getBalance() == 0.0);
		
		assertTrue(objective2.deposit(700.0, "vendita vecchie console e giochi"));
		assertTrue(objective2.isTargetMet());
		assertTrue(objective2.withdraw(700.0, "Compro PS5 e giochi"));
		assertTrue(objective2.getList().size() == 2);
		assertTrue(account.getBalance() == 700.0);
	}
	//TODO  assertEquals(objective1.hashCode(), sameObjective1.hashCode());
	@Test
	public void TestSavingForecast() {
		try {
			assertTrue(objective2.savingForecast(0, 0, 0, 0, false) == 0);
		} catch (IllegalInputException e) {
			e.printStackTrace();
		}
	}
}