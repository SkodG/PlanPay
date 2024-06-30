package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pp.projects.model.AccountImpl;

class AccountTest {
	
	private AccountImpl account;
	
	@BeforeEach
	public void setUp() {
		account = new AccountImpl("Luca");
	}
	
	@Test
	void testInitialization() {
		//testo che l'account sia stato inizializzato con successo
		assertTrue(account.getName().equals("Luca"));
		assertTrue(account.getBalance() == 0.0);
		
	}
	
	@Test
	public void testAccountOperations(){
		//test sulle operazioni di aggiunta e sottrazione dal saldo dell'account
		account.addBalance(100);
		assertTrue(account.getBalance() == 100);
		account.setBalance(0.0);
		assertTrue(account.getBalance() == 0.0);
		account.addBalance(1000);
		assertTrue(account.subBalance(500));
		assertTrue(account.getBalance() == 500.0);
		//se prelevo una quantità superiore al saldo dell'account restituisce false
		//e non diminuisce il saldo
		assertFalse(account.subBalance(10000));
		//testo che l'operazione non sia effettivamente avvenuta (il saldo è invariato)
		assertTrue(account.getBalance() == 500.0);
		account.setBalance(300.45);
		assertTrue(account.getBalance() == 300.45);
	}
	
}

