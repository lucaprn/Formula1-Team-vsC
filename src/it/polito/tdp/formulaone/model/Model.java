package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	
	private FormulaOneDAO dao;
	private ConstructorIDMap mapConstructor;
	private List<Constructor> listaConstructors;
	private Graph<Driver,DefaultWeightedEdge> grafo;
	private DriverIDMap mapDriver;
	private List<Driver> dreamTeam;
	private List<Driver> drivers;
	private int maxPunteggio;
	private List<Driver> topList;
	
	public Model() {
		dao = new FormulaOneDAO();
		mapConstructor = new ConstructorIDMap();
		mapDriver = new DriverIDMap();
		this.listaConstructors = new ArrayList<>(dao.getAllConstructors(mapConstructor));
	}

	public ConstructorIDMap getMapConstructor() {
		return mapConstructor;
	}

	public List<Constructor> getListaConstructors() {
		return listaConstructors;
	}

	public void creaGrafo(Constructor c) {
		drivers = dao.getAllDriversFromConstructor(c, mapDriver);
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, drivers);
		for(int i =0 ; i<drivers.size()-1; i++) {
		Driver d1=drivers.get(i);
			for(int j=i+1; j<drivers.size(); j++) {
			Driver d2=drivers.get(j);
				int numero = dao.gareDisputate(d1, d2);
				if(numero>0) {
					Graphs.addEdge(grafo, d1, d2, numero);
				}
			}	
		}	
	}

	public Graph<Driver, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public Driver topDriver() {
		Driver top = null;
		int max = Integer.MIN_VALUE;
		for(Driver d : grafo.vertexSet()) {
			int grado=0;
			if((grado=grafo.degreeOf(d))>max) {
				top = d;
				max = grado;
			}
		}
		return top;
	}
	
	public void dreamTeam(int k) {
		List<Driver> parziale = new ArrayList<>();
		
		
		recursive(k,parziale);
	}

	private void recursive(int k,List<Driver> parziale) {
		//System.out.println(parziale);
		
		if(parziale!=null && parziale.size()==k && calcola(parziale)>this.maxPunteggio) {
			this.maxPunteggio = calcola(parziale);
			this.topList = new ArrayList<>(parziale);
		}
		
		for(Driver d : grafo.vertexSet()) {
			if(!parziale.contains(d)) {
				parziale.add(d);
				recursive(k,parziale);
				parziale.remove(d);
			}
			
		}
		
	}
	
	public int getPunteggio() {
		return this.maxPunteggio;
	}

	private int calcola(List<Driver> parziale) {
		int punteggio=0;
		if(parziale.size()>=2) {
		for(int i =0 ; i<parziale.size()-1; i++) {
			Driver d1=parziale.get(i);
			for(int j=i+1; j<parziale.size(); j++) {
				Driver d2=parziale.get(j);
				DefaultWeightedEdge e = grafo.getEdge(d1, d2);
				if(e!=null) {
					double peso = grafo.getEdgeWeight(e);
					punteggio+=peso;
				}
				
				
			}
		}
	}
		return punteggio;
	}

	public List<Driver> getDreamTeam() {
		
		return this.topList;
	}
	

	
	
	
}
