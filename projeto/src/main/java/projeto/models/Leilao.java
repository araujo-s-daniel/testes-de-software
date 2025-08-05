package projeto.models;

import java.util.ArrayList;
import java.util.List;

public class Leilao {

	private String nome;
	private List<Lance> lances = new ArrayList<Lance>();

	public Leilao(String nome) {
		this.nome = nome;
	}

	public List<Lance> getLances() {
		return lances;
	}

	public void propoe(Lance lance) {
		this.lances.add(lance);
	}

}
