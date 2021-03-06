/*
 * TPXListCrossover.java
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
package es.ugr.osgiliath.evolutionary.basiccomponents.operators;

import java.util.ArrayList;
import java.util.List;

import es.ugr.osgiliath.evolutionary.basiccomponents.genomes.ListGenome;
import es.ugr.osgiliath.evolutionary.elements.Crossover;
import es.ugr.osgiliath.evolutionary.individual.Gene;
import es.ugr.osgiliath.evolutionary.individual.Genome;

public class TPXListCrossover implements Crossover{
		/**
		 * Two point crossover of a list. The point is inclusive. Example point = 3-> 0 1 2 3 | 4 5 6. 
		 * So the point must be in range [0,size()-2]
		 * @param father
		 * @param mother
		 * @param point
		 * @return
		 */

		@Override
		public ArrayList<Genome> cross(Genome mother, Genome father) {
			ArrayList<Genome> childs = new ArrayList<Genome>();
			ListGenome fatherL = (ListGenome)father;
			ListGenome motherL = (ListGenome)mother;
			ListGenome a = new ListGenome();
			ListGenome b = new ListGenome();
			
			int maxpoint = (fatherL.getGeneList().size()<motherL.getGeneList().size())?fatherL.getGeneList().size():motherL.getGeneList().size();
			maxpoint--; //The point is inclusive, so, to avoid children equal to parents the maxpoint must be size-2
			int p1 = (int) (Math.random()*maxpoint);
			int p2 = (int) (Math.random()*maxpoint);
			
			int point1,point2;
			if(p1<p2){
				point1 = p1;
				point2 = p2;
			}else{
				point1 = p2;
				point2 = p1;
			}
			
		
			
			List<Gene> fatherLeft  = (List<Gene>) fatherL.getGeneList().subList(0, point1+1); //right is exclusive!
			List<Gene> fatherMiddle = (List<Gene>) fatherL.getGeneList().subList(point1+1, point2+1);
			List<Gene> fatherRight = (List<Gene>) fatherL.getGeneList().subList(point2+1, fatherL.getGeneList().size());
			
			List<Gene> motherLeft  =  (List<Gene>) motherL.getGeneList().subList(0, point1+1);
			List<Gene> motherMiddle = (List<Gene>) motherL.getGeneList().subList(point1+1,point2+1);
			List<Gene> motherRight =  (List<Gene>) motherL.getGeneList().subList(point2+1, motherL.getGeneList().size());
			
			for(Gene g:fatherLeft){
				a.getGeneList().add((Gene) g.clone());
			}
			for(Gene g:motherMiddle){
				a.getGeneList().add((Gene) g.clone());
			}		
			for(Gene g:fatherRight){
				a.getGeneList().add((Gene) g.clone());
			}
			
			//
			//int i = 0;
			for(Gene g:motherLeft){
				b.getGeneList().add((Gene) g.clone());
				
			}
			for(Gene g:fatherMiddle){
				b.getGeneList().add((Gene) g.clone());
				
			}
			for(Gene g:motherRight){
				b.getGeneList().add((Gene) g.clone());
				
			}
			
			childs.add(a);
			childs.add(b);
			return childs;
		}

	}
