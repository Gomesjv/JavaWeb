package com.petcare.web.controller;

import com.petcare.web.model.Animal;
import com.petcare.web.model.Cliente;
import com.petcare.web.model.Consulta;
import com.petcare.web.model.Produto;
import com.petcare.web.model.Veterinario;
import com.petcare.web.repository.AnimalRepository;
import com.petcare.web.repository.ClienteRepository;
import com.petcare.web.repository.ConsultaRepository;
import com.petcare.web.repository.ProdutoRepository;
import com.petcare.web.repository.VeterinarioRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class PetCareModelAdvice {

    private final AnimalRepository animalRepository;
    private final ClienteRepository clienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ProdutoRepository produtoRepository;
    private final VeterinarioRepository veterinarioRepository;

    public PetCareModelAdvice(AnimalRepository animalRepository, ClienteRepository clienteRepository,
                              ConsultaRepository consultaRepository,
                              ProdutoRepository produtoRepository, VeterinarioRepository veterinarioRepository) {
        this.animalRepository = animalRepository;
        this.clienteRepository = clienteRepository;
        this.consultaRepository = consultaRepository;
        this.produtoRepository = produtoRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @ModelAttribute
    public void adicionarDadosDasAbas(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        model.addAttribute("animais", animalRepository.findAll());
        model.addAttribute("veterinarios", veterinarioRepository.findAll());
        model.addAttribute("consultas", consultaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        if (!model.containsAttribute("animal")) {
            model.addAttribute("animal", new Animal());
        }
        if (!model.containsAttribute("veterinario")) {
            model.addAttribute("veterinario", new Veterinario());
        }
        if (!model.containsAttribute("produto")) {
            model.addAttribute("produto", new Produto());
        }
        if (!model.containsAttribute("consultaEditando")) {
            model.addAttribute("consultaEditando", null);
        }
        if (!model.containsAttribute("dataHoraConsulta")) {
            model.addAttribute("dataHoraConsulta", "");
        }
        if (!model.containsAttribute("abaAtiva")) {
            model.addAttribute("abaAtiva", "clientes");
        }
    }
}
