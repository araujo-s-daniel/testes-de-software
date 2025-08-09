package projeto.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import projeto.builders.CriadorDeLeilao;
import projeto.daos.LeilaoDao;
import projeto.daos.RepositorioDePagamentos;
import projeto.models.Leilao;
import projeto.models.Pagamento;
import projeto.models.Usuario;

public class GeradorDePagamentoTest {

	@Test
	public void deveGerarPagamentoParaUmLeilaoEncerrado() {
		LeilaoDao leiloes = mock(LeilaoDao.class);
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		Avaliador avaliador = mock(Avaliador.class);

		Leilao leilao = new CriadorDeLeilao().para("Playstation").lance(new Usuario("José da Silva"), 2000.0)
				.lance(new Usuario("Maria Pereira"), 2500.0).constroi();

		when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));
		when(avaliador.getMaiorLance()).thenReturn(2500.0);

		GeradorDePagamento gerador = new GeradorDePagamento(pagamentos, leiloes, avaliador);
		gerador.gera();

		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(argumento.capture());

		Pagamento pagamentoGerado = argumento.getValue();
		assertEquals(2500.0, pagamentoGerado.getValor(), 0.0001);
	}

	@Test
	public void deveEmpurrarParaOProximoDiaUtil() {
		LeilaoDao leiloes = mock(LeilaoDao.class);
		RepositorioDePagamentos pagamentos = mock(RepositorioDePagamentos.class);
		Relogio relogio = mock(Relogio.class);

		Calendar sabado = Calendar.getInstance();
		sabado.set(2012, Calendar.APRIL, 7);
		when(relogio.hoje()).thenReturn(sabado);

		Leilao leilao = new CriadorDeLeilao().para("Playstation").lance(new Usuario("José da Silva"), 2000.0)
				.lance(new Usuario("Maria Pereira"), 2500.0).constroi();

		when(leiloes.encerrados()).thenReturn(Arrays.asList(leilao));

		GeradorDePagamento gerador = new GeradorDePagamento(pagamentos, leiloes, new Avaliador(), relogio);
		gerador.gera();

		ArgumentCaptor<Pagamento> argumento = ArgumentCaptor.forClass(Pagamento.class);
		verify(pagamentos).salva(argumento.capture());
		Pagamento pagamentoGerado = argumento.getValue();

		assertEquals(Calendar.MONDAY, pagamentoGerado.getData().get(Calendar.DAY_OF_WEEK));
		assertEquals(9, pagamentoGerado.getData().get(Calendar.DAY_OF_MONTH));
	}
}
