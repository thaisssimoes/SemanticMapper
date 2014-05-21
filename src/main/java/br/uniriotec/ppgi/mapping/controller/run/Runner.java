package br.uniriotec.ppgi.mapping.controller.run;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.uniriotec.ppgi.mapping.controller.wordnet.MITWordnetUtils;

public class Runner {
	private static Logger logger = Logger.getLogger(Runner.class);
	
	public void run() throws IOException{
//		logger.info(MITWordnetUtils.stemWord("bought", POS.VERB));
		MITWordnetUtils.listAllNouns();
	}
	
}
