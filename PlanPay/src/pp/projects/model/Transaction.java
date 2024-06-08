package pp.projects.model;

import java.time.LocalDate;

public class Transaction implements Data {
	// Definizione dei campi
	private LocalDate date;
    private String name;
    private double amount;
    
	// Costruttore per inizializzare la transazione
	public Transaction(LocalDate date, String name, double amount) {
		this.date = date;
        this.name = name;
        this.amount = amount;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	// potrebbe diventare il toString
	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public void setDescription(String d) {
		// TODO Cosa posso fargli fare?
	}

	@Override
	public LocalDate getDate() {
		return this.date;
	}
	
	public double getAmount() {
		return this.amount;
	}
}
