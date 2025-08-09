package projeto.builders;

import java.util.Calendar;

import projeto.models.Lance;
import projeto.models.Leilao;
import projeto.models.Usuario;

public class CriadorDeLeilao {

	private Leilao leilao;

	public CriadorDeLeilao para(String descricao) {
		this.leilao = new Leilao(descricao);
		return this;
	}

	public CriadorDeLeilao lance(Usuario usuario, double valor) {
		this.leilao.propoe(new Lance(usuario, valor));
		return this;
	}

	public Leilao constroi() {
		return this.leilao;
	}

	public CriadorDeLeilao naData(Calendar data) {
		this.leilao.setData(data);
		return this;
	}

}
