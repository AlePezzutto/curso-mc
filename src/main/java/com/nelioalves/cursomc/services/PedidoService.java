package com.nelioalves.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.PagamentoBoleto;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.domain.enums.EstadoPagamento;
import com.nelioalves.cursomc.repositories.ItemPedidoRepository;
import com.nelioalves.cursomc.repositories.PagamentoRepository;
import com.nelioalves.cursomc.repositories.PedidoRepository;
import com.nelioalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedRepo;
	
	@Autowired
	private PagamentoRepository pagtoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService cliService;

	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService prodService;

	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id){
		
		Optional<Pedido> opt = pedRepo.findById(id);
		return opt.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}
	
	public Pedido insert(Pedido pedido) {
		
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(cliService.find(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagto = (PagamentoBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoBoleto(pagto, pedido.getInstante());
		}
		
		// Salva pedido
		pedido = pedRepo.save(pedido);
		
		// Salva pagametno
		pagtoRepo.save(pedido.getPagamento());
		
		// Salvar itens
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(prodService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		//System.out.println(pedido);
		//emailService.sendOrderConfirmationEmail(pedido);
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		return pedido;
	}
}
