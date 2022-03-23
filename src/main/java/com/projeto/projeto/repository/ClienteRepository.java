package com.projeto.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.projeto.projeto.models.Cliente;
import com.projeto.projeto.models.Produto;

public interface ClienteRepository extends CrudRepository<Cliente, String> {
	
Iterable<Cliente>findByProduto(Produto produto);
	
	Cliente findByRg(String rg);
	
	Cliente findById(long id);
	
	List<Cliente>findByNomeCliente(String nomeCliente);

}
