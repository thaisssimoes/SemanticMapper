package br.uniriotec.ppgi.mapping.controller.sampler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.controller.ParameterLoader;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.LexFile;

public class SampleSelector {
	
	private static Logger logger = Logger.getLogger(SampleSelector.class);
	
	
	/**
	 * Calculates the size of the sample for each supersense subgroup.
	 * 
	 * @param synsetsPerSupersenses - the map with all synsets for a supersense
	 * @return a map describing how many samples should be used for each supersense
	 * @throws Exception
	 */
	public static Map<Integer, Integer> calculateSupersenseSamplesSizes(
			Map<Integer, HashSet<ISynset>> synsetsPerSupersenses) throws Exception{
		
		/*
		 * Foreach index on the map of supersenses and synsets
		 * count how many synsets there are and calculate the sample size
		 */
		Map<Integer, Integer> samplesSizes = new HashMap<Integer,Integer>();
		for(Entry<Integer, HashSet<ISynset>> e : synsetsPerSupersenses.entrySet()){
			
			
			//Calculate the sample size as if it was an infinite population
			double ssRaw = SampleSizeCalculator.calculateSampleSize(
								ParameterLoader.getInstance().getByName("z_value"),
								ParameterLoader.getInstance().getByName("confidence_interval"));
		
			
			//correct the sample size to a finite population, i.e. the the number of synsets
			double ssCorrected = SampleSizeCalculator.correctToFinitePopulation(
									ssRaw, 
									e.getValue().size());
			
			//debug log
			logger.debug("Positive Sample size for "+ LexFile.getLexicalFile(e.getKey())+": "
					+Math.ceil(ssCorrected));
			
			
			//If the corrected value is closer to ceil, round up, otherwise, round down.
			int ss = 0;
			if(ssCorrected >= (Math.floor(ssCorrected)+0.5)){
				ss = (int) Math.ceil(ssCorrected);
			}else{
				ss = (int) Math.floor(ssCorrected);
			}
			
			//Save the sample size
			samplesSizes.put(e.getKey(), ss);
		}
		
		return samplesSizes;
	}

	
	
	
	/**
	 * Selects the sample synsets for each supersense based on a map
	 * describing the sample size that should be used for each of them.
	 * adjust the inputed mao of sysnets by reference, avoiding unecessary
	 * memory use.
	 * 
	 * @param synsetsPerSupersenses
	 * @param samplesSizes
	 * @return
	 */
	public static void selectSampleSynsets(
			Map<Integer, HashSet<ISynset>> synsetsPerSupersenses,
			Map<Integer, Integer> samplesSizes) {
		
		
		for(Entry<Integer, HashSet<ISynset>> e : synsetsPerSupersenses.entrySet()){
			e.setValue(systematicSampling(e.getValue(),samplesSizes.get(e.getKey())));
		}
		
	}




	/**
	 * Applies systematic sampling to select a certain amount of units
	 * from a set of ISynset objects. 
	 * 
	 * @param synsetSet - the complete set of ISynset objects
	 * @param sampleSize - the amount of objects that mus be picked
	 * @return - a set of ISynset objects with the size asked for
	 */
	private static HashSet<ISynset> systematicSampling(HashSet<ISynset> synsetSet,
			Integer sampleSize) {
		
		HashSet<ISynset> sample = new HashSet<ISynset>();
		List<ISynset> list = new ArrayList<ISynset>(synsetSet);
		Collections.shuffle(list);
		
		int step = (int) Math.floor(list.size()/sampleSize);
		int index = (new Random()).nextInt(step);	//Selects a random int ranging from 0 to step-1
		
		while((index < list.size()) && (sample.size() < sampleSize)){
			sample.add(list.get(index));
			index += step;
		}

		return sample;
		
	}




	/**
	 * Calculates the sample size of negative examples that should be
	 * used when evaluating the mappings.
	 * 
	 * OBS: this is a further step that is conducted when taking
	 * into account the evaluation of the mapping rules
	 *  
	 * @param positiveSamplesSizes
	 * @return
	 */
	public static Map<Integer, Integer> calculateNegativeSamplesSizes(
			Map<Integer, Integer> positiveSamplesSizes) {
		
		Map<Integer, Integer> negativeSamplesSizes = new HashMap<Integer,Integer>();
		
		for(Entry<Integer,Integer> e : positiveSamplesSizes.entrySet()){
			int sampleSize = (int) Math.ceil(e.getValue()/10.0);
			negativeSamplesSizes.put(e.getKey(), sampleSize);
			
			//debug log
			logger.debug("Negative Sample size for "+ LexFile.getLexicalFile(e.getKey())+": "
					+Math.ceil(sampleSize));
		}
		
		return negativeSamplesSizes;
	}
	
	
	
	
	
}
