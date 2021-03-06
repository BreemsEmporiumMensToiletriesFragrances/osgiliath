/*
 * BinaryProblemZeroInitializer.java
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
package es.ugr.osgiliath.problem.binary;


import java.util.ArrayList;
import java.util.List;

import es.ugr.osgiliath.OsgiliathService;
import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.ListGenome;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.BasicIndividual;
import es.ugr.osgiliath.evolutionary.basiccomponents.individuals.BooleanGene;
import es.ugr.osgiliath.evolutionary.elements.FitnessCalculator;
import es.ugr.osgiliath.evolutionary.individual.Fitness;
import es.ugr.osgiliath.evolutionary.individual.Gene;
import es.ugr.osgiliath.evolutionary.individual.Individual;
import es.ugr.osgiliath.evolutionary.individual.Initializer;

public class BinaryProblemZeroInitializer extends OsgiliathService implements Initializer{

	FitnessCalculator fitnessCalculator;
	@Override
	public ArrayList<Individual> initializeIndividuals(int size) {
		ArrayList<Individual> inds = new ArrayList<Individual>();
		for(int i = 0; i<size;i++){
			BasicIndividual ind = new BasicIndividual();
			ListGenome genome = new ListGenome();
			
			int sizevector = (Integer) this.getProblem().getParameters().getParameter(BinaryProblemParameters.SIZE_PROP);
			for(int j=0; j<sizevector;j++){
				


					Gene g = new BooleanGene(false);
				genome.getGeneList().add(g);

			}
			ind.setGenome(genome);
			inds.add(ind);
			
		}
		
		//Calculate fitness of all individuals
		List<Fitness> fits = this.fitnessCalculator.calculateFitnessForAll(inds);
		int w = 0;
		for(Fitness f:fits){
			inds.get(w).setFitness(f);
			w++;
		}
		
		return inds;
	}
	
	//BIND/UNBIND FITNESS CALCULATOR
	public void setFitnessCalculator(FitnessCalculator fitC){
		this.fitnessCalculator = fitC;
	}

	public void unsetFitnessCalculator(FitnessCalculator fitc){
		this.fitnessCalculator = null;
	}

}