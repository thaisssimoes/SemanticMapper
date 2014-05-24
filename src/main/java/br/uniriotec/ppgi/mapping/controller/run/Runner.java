package br.uniriotec.ppgi.mapping.controller.run;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;
import br.uniriotec.ppgi.mapping.model.exception.WordnetHelperException;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.POS;

public class Runner {
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public void run(){
		try{
			Map<String, List<ISynset>> synsetsPerSupersenses = MITWordnetUtils.listAllSynsets(POS.NOUN);
			
			
			for(Entry<String, List<ISynset>> e : synsetsPerSupersenses.entrySet()){
				logger.info("Supersense: "+e.getKey());
				logger.info(" -- Total de synsets: "+e.getValue().size());
				logger.info(" -- "+e.getValue().get(0));
				logger.info(" -- "+e.getValue().get(0).getGloss());
			}
		}catch(WordnetHelperException e){
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
}
