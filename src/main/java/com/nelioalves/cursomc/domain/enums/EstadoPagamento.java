package com.nelioalves.cursomc.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");

	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descr) {
		this.cod = cod;
		this.descricao = descr;
	}
	public int getCodigo() {
		return this.cod;
	}
	public String getDescricao() {
		return this.descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
	
		if(cod == null)
			return null;
		
		for(EstadoPagamento ep : EstadoPagamento.values()) {
		
			if(cod.equals(ep.getCodigo()))
				return ep;
		}
		throw new IllegalArgumentException("Id Inválido " + cod );
	}
	
}
