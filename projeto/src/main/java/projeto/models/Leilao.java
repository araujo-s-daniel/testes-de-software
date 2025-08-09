package projeto.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {
		if (this.lances.isEmpty() || podeDarLance(lance.getUsuario())) {
			this.lances.add(lance);
		}
	}

	private boolean podeDarLance(Usuario usuario) {
		return !this.ultimoLanceDado().getUsuario().equals(usuario) && this.qtdeDeLancesDo(usuario) < 5;
	}

	private int qtdeDeLancesDo(Usuario usuario) {
		int total = 0;
		for (Lance lance : lances) {
			if (lance.getUsuario().equals(usuario)) {
				total++;
			}
		}
		return total;
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	private Lance ultimoLanceDado() {
		return this.lances.get(this.lances.size() - 1);
	}
}
