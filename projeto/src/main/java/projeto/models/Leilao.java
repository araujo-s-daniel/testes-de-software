package projeto.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Leilao {

	private String descricao;
	private Calendar data;
	private boolean encerrado;
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

	public void encerra() {
		this.encerrado = true;
	}

	public Calendar getData() {
		return this.data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public boolean isEncerrado() {
		return this.encerrado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, descricao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leilao other = (Leilao) obj;
		return Objects.equals(data, other.data) && Objects.equals(descricao, other.descricao);
	}

	@Override
	public String toString() {
		return "Leilao [descricao=" + descricao + ", encerrado=" + encerrado + "]";
	}
}
