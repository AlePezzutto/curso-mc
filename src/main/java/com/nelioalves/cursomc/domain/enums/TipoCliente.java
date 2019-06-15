package com.nelioalves.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1, "PF"),
	PESSOAJURIDICA(2, "PJ");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descr) {
		this.cod = cod;
		this.descricao = descr;
	}
	public int getCodigo() {
		return this.cod;
	}
	public String getDescricao() {
		return this.descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
	
		if(cod == null)
			return null;
		
		for(TipoCliente tp : TipoCliente.values()) {
		
			if(cod.equals(tp.getCodigo()))
				return tp;
		}
		throw new IllegalArgumentException("Id Inválido " + cod );
		
	}
}
