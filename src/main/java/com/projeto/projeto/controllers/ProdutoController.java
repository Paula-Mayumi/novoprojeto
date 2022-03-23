package com.projeto.projeto.controllers;


import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.projeto.models.Cliente;
import com.projeto.projeto.models.Produto;
import com.projeto.projeto.repository.ClienteRepository;
import com.projeto.projeto.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private ClienteRepository cr;

	// CADASTRA PRODUTO
	@RequestMapping(value = "/cadastrarProduto", method = RequestMethod.GET)
	public String form() {
		return "produto/formProduto";
	}

	@RequestMapping(value = "/cadastrarProduto", method = RequestMethod.POST)
	public String form(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarProduto";
		}

		pr.save(produto);
		attributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso!");
		return "redirect:/cadastrarProduto";
	}
	

	
	

	// LISTA PRODUTOS

	@RequestMapping("/produtos")
	public ModelAndView listaProdutos() {
		ModelAndView mv = new ModelAndView("produto/listaProduto");
		Iterable<Produto> produtos = pr.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}

	//
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesProduto(@PathVariable("codigo") long codigo) {
		Produto produto = pr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("produto/detalhesProduto");
		mv.addObject("produto", produto);

		Iterable<Cliente> clientes = cr.findByProduto(produto);
		mv.addObject("clientes", clientes);

		return mv;

	}

	// DELETA PRODUTO
	@RequestMapping("/deletarProduto")
	public String deletarProduto(long codigo) {
		Produto produto = pr.findByCodigo(codigo);
		pr.delete(produto);
		return "redirect:/produtos";
	}

	// ADICIONAR CLIENTE
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesProdutoPost(@PathVariable("codigo") long codigo, @Valid Cliente cliente,
			BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{codigo}";
		}

		// rg duplicado
		if (cr.findByRg(cliente.getRg()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "RG duplicado");
			return "redirect:/{codigo}";
		}

		Produto produto = pr.findByCodigo(codigo);
		cliente.setProduto(produto);
		cr.save(cliente);
		attributes.addFlashAttribute("mensagem", "Cliente adionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	


	// DELETA CLIENTE pelo RG
	@RequestMapping("/deletarCliente")
	public String deletarCliente(String rg) {
		Cliente cliente = cr.findByRg(rg);
		Produto produto = cliente.getProduto();
		String codigo = "" + produto.getCodigo();

		cr.delete(cliente);
		
	
		
		return "redirect:/" + codigo;
		
	}
	

	
	// Métodos que atualiza o produto
		// GET que chama o formulário de edição do produto
		@RequestMapping("/editar-produto")
		public ModelAndView editarProduto(long codigo) {
			Produto produto = pr.findByCodigo(codigo);
			ModelAndView mv = new ModelAndView("produto/update-produto");
			mv.addObject("produto", produto);
			return mv;
		}

		// POST do FORM que atualiza o produto
		@RequestMapping(value = "/editar-produto", method = RequestMethod.POST)
		public String updateProduto(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
			pr.save(produto);
			attributes.addFlashAttribute("success", "Produto alterado com sucesso!");

			long codigoLong = produto.getCodigo();
			String codigo = "" + codigoLong;
			return "redirect:/" + codigo;
		}

}


