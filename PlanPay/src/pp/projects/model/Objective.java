package pp.projects.model;

import java.util.List;

public interface Objective {
	
	public Double projection(double inflationRate, double interestRate, double monthlySaving, int years);
	public Transaction getTransaction();
}
