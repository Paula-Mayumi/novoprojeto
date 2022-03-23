package com.projeto.projeto.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.projeto.projeto.models.Produto;


public interface ProdutoRepository extends CrudRepository<Produto, String>{
	
	Produto findByCodigo(long codigo);
	List<Produto> findByNome(String nome);

}
