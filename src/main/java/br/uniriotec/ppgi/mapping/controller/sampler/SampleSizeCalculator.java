package br.uniriotec.ppgi.mapping.controller.sampler;


/**
 * Class to calculate the size of a sample for a propbabilistic analisys.
 * 
 * @author felipe
 *
 */
public class SampleSizeCalculator {
	
	
	/**
	 * Calculates the size of a sample based for a infinet population (greater than 50.000). 
	 * The calculus is based on a confidence Z-level (from the Cumulative Normal Probability 
	 * Table), the confidence interval expressed in decimal form (e.g. 0.04 for 4%). This method
	 * must be used when a likelly choice of the population is not previously known. Usually 
	 * sample sizes are calculate onsiderin5 90%, 95% or 99% of confidence levels, which reflects 
	 * into 1.645, 1.96 and 2.58 Z-values, respectivelly. The result is a double and must be 
	 * rounded.
	 * 
	 * @param zValue - the confidence level Z-score value
	 * @param confInterval - the confindence interval, a.k.a Margin of error
	 * @return the sample size needed for a infinite population.
	 */
	public static double calculateSampleSize(double zValue, double confInterval){
		return calculateSampleSize(zValue, confInterval, .5);
	}
	
	
	/**
	 * Calculates the size of a sample based for a infinet population (greater than 50.000). 
	 * The calculus is based on a confidence Z-level (from the Cumulative Normal Probability 
	 * Table), the confidence interval expressed in decimal form (e.g. 0.04 for 4%) and known
	 * percentage of a population picking a certain choice (also expressed as decimal). Usually 
	 * sample sizes are calculate onsiderin5 90%, 95% or 99% of confidence levels, which reflects 
	 * into 1.645, 1.96 and 2.58 Z-values, respectivelly. The result is a double and must be 
	 * rounded.
	 * 
	 * @param zValue - the confidence level Z-score value
	 * @param confInterval - the confindence interval, a.k.a Margin of error
	 * @param knownPercentage - previously known percentage of likelly choice
	 * @return the sample size needed for a infinite population.
	 */
	public static double calculateSampleSize(double zValue, double confInterval, double knownPercentage){
		/*
		 * Thats a second example of a comment block
		 */
		double sampleSize = (Math.pow(zValue,2) * knownPercentage * (1 - knownPercentage)) / Math.pow(confInterval,2);
		
		return sampleSize;
	}
	
	
	/**
	 * Corrects the sample size to a certain finite population size. The result is a double
	 * value that mus be rounded for further use.
	 * 
	 * @param rawSampleSize - the finite size population sample size
	 * @param population - the population which the sample should be corrected to
	 * @return the sample size needed for a specific population
	 */
	public static double correctToFinitePopulation(double rawSampleSize, int population){
		return (int)(rawSampleSize / (1 + ((rawSampleSize - 1)/population)));
	}
	
}
