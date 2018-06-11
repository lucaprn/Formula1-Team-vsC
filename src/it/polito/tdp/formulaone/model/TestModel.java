package it.polito.tdp.formulaone.model;

public class TestModel {

	public static void main(String[] args) {
		
		Constructor cd = new Constructor(131,"mercedes");
		Model model = new Model();
		
		model.creaGrafo(cd);
		
		System.out.println("-----Grafo Creato-----\n");
		System.out.println("Numero Vertici : "+model.getGrafo().vertexSet().size());
		System.out.println("Numero Archi : "+model.getGrafo().edgeSet().size());
		
		System.out.println(model.topDriver());
		
		model.dreamTeam(5);
		
		System.out.println("Il top Team è : "+model.getDreamTeam()+" con : "+model.getPunteggio()+" punti");

	}

}
