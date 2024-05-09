package pp.projects.model;

public interface Objective {
	
	public Double projection(double inflationRate, double interestRate, double monthlySaving, int years);
	public Transaction getTransaction();
}
