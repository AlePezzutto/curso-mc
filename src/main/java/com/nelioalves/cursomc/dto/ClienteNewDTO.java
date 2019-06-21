package com.nelioalves.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.domain.enums.TipoCliente;
import com.nelioalves.cursomc.services.validation.CpfCnpjConstraint;

@CpfCnpjConstraint
public class ClienteNewDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	@Length(min=5, max=120, message="O campo deve ter entre 5 e 120 caracteres")
	private String nome;
	@NotEmpty(message="Preenchimento Obrigatório")
	@Email(message="Email inválido")
	private String email;
	@NotEmpty(message="Preenchimento Obrigatório")
	private String cpfCnpj;
	private Integer tipoCliente;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	private String logradouro;
	@NotEmpty(message="Preenchimento Obrigatório")
	private String numero;
	private String complemento;
	private String bairro;
	@NotEmpty(message="Preenchimento Obrigatório")
	private String cep;
	
	@NotEmpty(message="Preenchimento Obrigatório")
	private String telefone1;
	private String telefone2;
	private String telefone3;
	
	private Integer cidadeId;
	
	public ClienteNewDTO() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Integer getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(Integer tipo) {
		this.tipoCliente = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTelefone3() {
		return telefone3;
	}

	public void setTelefone3(String telefone3) {
		this.telefone3 = telefone3;
	}

	public Integer getCidadeId() {
		return cidadeId;
	}

	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}

	public Cliente toCliente() {
		
		Cidade oCid = new Cidade(this.cidadeId, null, null);
		
		Endereco oEnd = new Endereco();
		oEnd.setLogradouro(this.logradouro);
		oEnd.setNumero(this.numero);
		oEnd.setComplemento(this.complemento);
		oEnd.setBairro(this.bairro);
		oEnd.setCidade(oCid);
		oEnd.setCep(this.cep);
		
		Cliente oCli = new Cliente();
		oCli.setNome(this.nome);
		oCli.setEmail(this.email);
		oCli.setCpfCnpj(this.cpfCnpj);
		oCli.setTipo(TipoCliente.toEnum(this.tipoCliente));
		oCli.getEnderecos().add(oEnd);
		oCli.getTelefones().add(this.telefone1);
		if(this.telefone2 != null) {
			oCli.getTelefones().add(this.telefone2);
		}
		if(this.telefone3 != null) {
			oCli.getTelefones().add(this.telefone3);
		}

		// Liga cliente ao endereço
		oEnd.setCliente(oCli);

		return oCli;
	}
}
