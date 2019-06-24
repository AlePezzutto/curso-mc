package com.nelioalves.cursomc.services;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.Estado;
import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.Pagamento;
import com.nelioalves.cursomc.domain.PagamentoBoleto;
import com.nelioalves.cursomc.domain.PagamentoCartao;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.domain.Produto;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.repositories.CategoriaRepository;
import com.nelioalves.cursomc.repositories.CidadeRepository;
import com.nelioalves.cursomc.repositories.ClienteRepository;
import com.nelioalves.cursomc.repositories.EnderecoRepository;
import com.nelioalves.cursomc.repositories.EstadoRepository;
import com.nelioalves.cursomc.repositories.ItemPedidoRepository;
import com.nelioalves.cursomc.repositories.PagamentoRepository;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository catRepo;
	@Autowired
	private ProdutoRepository prodRepo;
	@Autowired
	private EstadoRepository estRepo;
	@Autowired
	private CidadeRepository cidRepo;
	@Autowired
	private ClienteRepository cliRepo;
	@Autowired
	private EnderecoRepository endRepo;
	@Autowired
	private PedidoRepository pedRepo;
	@Autowired
	private PagamentoRepository pagRepo;
	@Autowired
	private ItemPedidoRepository itpedRepo;
	
	public void instatiateTestDatabase()  throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cameba");
		Categoria cat4 = new Categoria(null, "Ferramentas");
		Categoria cat5 = new Categoria(null, "Material Elétrico");
		Categoria cat6 = new Categoria(null, "Jardinagem");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		Categoria cat8 = new Categoria(null, "Móveis e Decoração");
		
		catRepo.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
	
		Produto prd1 = new Produto(null, "Computador", 2000.00);
		Produto prd2 = new Produto(null, "Impressora", 800.00);
		Produto prd3 = new Produto(null, "Mouse", 80.00);
		Produto prd4 = new Produto(null, "Mesa de escritório", 300.00);
		Produto prd5 = new Produto(null, "Toalha", 50.00);
		Produto prd6 = new Produto(null, "Colcha", 200.00);
		Produto prd7 = new Produto(null, "TV true color", 1200.00);
		Produto prd8 = new Produto(null, "Roçadeira", 800.00);
		Produto prd9 = new Produto(null, "Abajour", 100.00);
		Produto prd10 = new Produto(null, "Pendente", 180.00);
		Produto prd11 = new Produto(null, "Shampoo", 13.00);
		
		
		cat1.getProdutos().addAll(Arrays.asList(prd1, prd2, prd3));
		cat2.getProdutos().addAll(Arrays.asList(prd2, prd4));
		cat3.getProdutos().addAll(Arrays.asList(prd5, prd6));
		cat4.getProdutos().addAll(Arrays.asList(prd1, prd2, prd3, prd7));
		cat5.getProdutos().addAll(Arrays.asList(prd8));
		cat6.getProdutos().addAll(Arrays.asList(prd9, prd10));
		cat7.getProdutos().addAll(Arrays.asList(prd11));
		
		prd1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prd2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		prd3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		prd4.getCategorias().addAll(Arrays.asList(cat2));
		prd5.getCategorias().addAll(Arrays.asList(cat3));
		prd6.getCategorias().addAll(Arrays.asList(cat3));
		prd7.getCategorias().addAll(Arrays.asList(cat4));
		prd8.getCategorias().addAll(Arrays.asList(cat5));
		prd9.getCategorias().addAll(Arrays.asList(cat6));
		prd10.getCategorias().addAll(Arrays.asList(cat6));
		prd11.getCategorias().addAll(Arrays.asList(cat7));	
		
		
		prodRepo.saveAll(Arrays.asList(prd1, prd2, prd3, prd4, prd5, prd6, prd7, prd8, prd9, prd10, prd11 ));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
		Cidade cid2 = new Cidade(null, "Osasco", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		estRepo.saveAll(Arrays.asList(est1, est2));
		cidRepo.saveAll(Arrays.asList(cid1, cid2, cid3));
	
		Cliente cli1 = new Cliente(null, "Maria", "maria@acme.com", "111.111.111-11", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("8574857", "554158542", "44787444"));
		
		Endereco end1 = new Endereco(null, "Rua xpto", "123", "fundos", "Centro", "11111-000", cli1, cid1);
		Endereco end2 = new Endereco(null, "Rua aksjkajskasj", "133", "Apto 123", "Vila Yara", "11111-000", cli1, cid2);
		
		cliRepo.saveAll(Arrays.asList(cli1));
		endRepo.saveAll(Arrays.asList(end1, end2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), null, cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), null, cli1, end2);
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		Pagamento pagto1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		pedRepo.saveAll(Arrays.asList(ped1, ped2));
		pagRepo.saveAll(Arrays.asList(pagto1, pagto2));
	
		ItemPedido ip1 = new ItemPedido(ped1, prd1, 0.00, 1, 2000.00 );
		ItemPedido ip2 = new ItemPedido(ped1, prd3, 0.00, 2, 80.00 );
		ItemPedido ip3 = new ItemPedido(ped2, prd2, 100.00, 1, 800.00 );
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		prd1.getItens().add(ip1);
		prd2.getItens().add(ip3);
		prd3.getItens().add(ip2);
		
		itpedRepo.saveAll(Arrays.asList(ip1, ip2,ip3));
	}
}
