package projeto.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import projeto.builders.CriadorDeLeilao;
import projeto.daos.LeilaoDao;
import projeto.models.Leilao;

public class EncerradorDeLeilaoTest {

	@Test
	public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);

		Leilao leilao1 = new CriadorDeLeilao().para("TV de Plasma").naData(antiga).constroi();

		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();

		List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);

		LeilaoDao dao = mock(LeilaoDao.class);
		when(dao.correntes()).thenReturn(leiloesAntigos);

		Carteiro carteiro = mock(Carteiro.class);

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao, carteiro);
		encerrador.encerra();

		assertTrue(leilao1.isEncerrado());
		assertTrue(leilao2.isEncerrado());
		assertEquals(2, encerrador.getTotalEncerrados());
	}

	@Test
	public void deveAtualizarLeiloesEncerrados() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);

		Leilao leilao1 = new CriadorDeLeilao().para("TV de Plasma").naData(antiga).constroi();

		LeilaoDao dao = mock(LeilaoDao.class);
		when(dao.correntes()).thenReturn(Arrays.asList(leilao1));

		Carteiro carteiro = mock(Carteiro.class);

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao, carteiro);
		encerrador.encerra();

		verify(dao, times(1)).atualiza(leilao1);
	}

	@Test
	public void deveContinuarAExecucaoMesmoQuandoDaoFalha() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);

		Leilao leilao1 = new CriadorDeLeilao().para("TV de Plasma").naData(antiga).constroi();

		Leilao leilao2 = new CriadorDeLeilao().para("Geladeira").naData(antiga).constroi();

		LeilaoDao dao = mock(LeilaoDao.class);
		when(dao.correntes()).thenReturn(Arrays.asList(leilao1, leilao2));

		doThrow(new RuntimeException()).when(dao).atualiza(leilao1);

		Carteiro carteiro = mock(Carteiro.class);

		EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao, carteiro);
		encerrador.encerra();

		verify(dao).atualiza(leilao2);
		verify(carteiro).envia(leilao2);
	}
}
