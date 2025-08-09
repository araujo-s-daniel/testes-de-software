package projeto.models;

import java.util.Objects;

public class Lance {

	private Usuario usuario;
	private double valor;

	public Lance(Usuario usuario, double valor) {
		this.usuario = usuario;
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lance other = (Lance) obj;
		return Objects.equals(usuario, other.usuario)
				&& Double.doubleToLongBits(valor) == Double.doubleToLongBits(other.valor);
	}
}
