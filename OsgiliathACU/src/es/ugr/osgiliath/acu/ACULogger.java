/*
 * ACULogger.java
 * 
 * Copyright (c) 2013, Pablo Garcia-Sanchez. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 * 
 * Contributors:
 */
package es.ugr.osgiliath.acu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

import javax.security.auth.login.Configuration;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.events.EventCreator;
import es.ugr.osgiliath.evolutionary.elements.EvolutionaryBasicParameters;
import es.ugr.osgiliath.utils.Logger;
import es.ugr.osgiliath.utils.OsgiliathConfiguration;
import es.ugr.osgiliath.utils.Stopwatch;

public class ACULogger extends OsgiliathService implements Logger,EventHandler{
	
	
	int run = 0;
	int iteration = 0;
	int numEvaluations = 0;
	long initTime = 0;
	String filename;

	ACUBank bank;
	public void activate(){
		System.out.println("ACU LOGGER ACTIVATED"); //NOTA: EL SETUP LO HACE EL EVENTO DEL STARTALL
		//setup(null);
		
	}
	@Override
	public void info(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stats(String message) {
		try{
			FileWriter fstream = new FileWriter(filename,true); //EXISTENT FILE
			BufferedWriter out = new BufferedWriter(fstream);
			long time =  System.currentTimeMillis() -initTime;
			out.write(iteration+";"+numEvaluations+";"+time+";"+this.bank.getActualACUs()+";"+message);
			iteration++;
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}

	public void statsX(String message, String appendix) {
		try{
			FileWriter fstream = new FileWriter(filename+"."+appendix,true); //EXISTENT FILE
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(message);
			out.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}
	@Override
	public void setup(Properties props) {
		
		
		//RESET VALUES
		run++;
		iteration = 0;
		numEvaluations = 0;
		//initTime = 0;
		
		//CREATE FILE
		Date d = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd.hh'h'mm'm'ss's'");
		String timestamp = format.format(d);
		
		// Create folder
		String folderName = "";
		String ROOT_FOLDER = (String) this.getAlgorithmParameters().getParameter(OsgiliathConfiguration.LOG_FOLDER);
		String cross = (String) this.getAlgorithmParameters().getParameter(EvolutionaryBasicParameters.CROSSOVER_PROB).toString();
		String mut = (String) this.getAlgorithmParameters().getParameter(EvolutionaryBasicParameters.MUTATOR_PROB).toString();
		String problemName = (String) this.getAlgorithmParameters().getParameter(OsgiliathConfiguration.PROBLEM_NAME).toString();
		folderName += ROOT_FOLDER+"/"+problemName+"_"+cross+"_"+mut;
		filename = folderName+"/"+timestamp+"out.run."+this.run+".fwork."+this.getFrameworkId();
		try{
			System.out.println("Creating logfile: "+filename);
			File folder = new File(folderName);
			 
			// if file doesnt exists, then create it
			boolean status = folder.mkdirs();
			
			FileWriter fstream = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(fstream);

			ArrayList<String> keys = this.getAlgorithmParameters().getKeys();
			Collections.sort(keys);

			for(String k:keys)
				out.write(k+" = "+this.getAlgorithmParameters().getParameter(k)+"\n");
			out.write(filename+"\n");
			out.write("IT;EVALUATIONS;TIME;BANK;BEST;AVERAGE;BESTSENT;AVERAGESENT;BESTRECEIVED;AVERAGERECEIVED;AVG_ACU;MAX_ACU;ACU_OF_BEST;TOTAL_ACUS;AVG_MIGRATION;AVG_FOREIGNS;SENT;RECEIVED;ACUS_REWARDED;TIME;NUM_EVALS\n");
			out.close();
			//System.out.println("CREADO ARCHIVO DE LOG "+filename);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		
		//CONTROLS THE TIME
		this.initTime = System.currentTimeMillis();
		
		

	}

	@Override
	public void handleEvent(Event arg0) {
		String topic = arg0.getTopic();
		
		//ESTO SE EJECUTA DESDE EL START ALL QUE RESETEA!!!
		if(topic.contains(EventCreator.TOPIC_RESET)){
			this.setup(null);
			System.out.println("RESETEO EL LOGGER");
		}
		
		if(topic.contains(EventCreator.TOPIC_EVALUATIONS)){
			int newEv = (Integer) arg0.getProperty(EventCreator.PROP_EVALUATIONS_NUMBER);
			this.numEvaluations+=newEv;
			//System.out.println("EVALUACIONES");
		}
		
		
	}
	
	public void setACUBank(ACUBank bank){
		this.bank = bank;
	}
	
	public void unsetACUBank(ACUBank bank){
		this.bank = null;
	}

	
	

}
