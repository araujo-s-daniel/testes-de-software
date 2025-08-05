package projeto.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import projeto.models.Lance;
import projeto.models.Leilao;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Lance> maiores;

	public void avalia(Leilao leilao) {
		if (leilao.getLances().size() == 0) {
			throw new RuntimeException("Não é possível avaliar um leilão sem lances");
		}

		for (Lance lance : leilao.getLances()) {
			if (lance.getValor() > this.maiorDeTodos) {
				this.maiorDeTodos = lance.getValor();
			}

			if (lance.getValor() < this.menorDeTodos) {
				this.menorDeTodos = lance.getValor();
			}
		}

		pegaOsMaioresNo(leilao);
	}

	public double getMaiorLance() {
		return this.maiorDeTodos;
	}

	public double getMenorLance() {
		return this.menorDeTodos;
	}

	public List<Lance> getTresMaiores() {
		return this.maiores;
	}

	private void pegaOsMaioresNo(Leilao leilao) {
		this.maiores = new ArrayList<Lance>(leilao.getLances());

		Collections.sort(maiores, new Comparator<Lance>() {

			@Override
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor())
					return 1;
				if (o1.getValor() > o2.getValor())
					return -1;
				return 0;
			}

		});

		maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}

}
