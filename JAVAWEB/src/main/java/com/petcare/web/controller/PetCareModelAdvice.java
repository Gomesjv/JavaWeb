package com.petcare.web.controller;

import com.petcare.web.model.Animal;
import com.petcare.web.model.Consulta;
import com.petcare.web.model.Produto;
import com.petcare.web.model.Veterinario;
import com.petcare.web.repository.AnimalRepository;
import com.petcare.web.repository.ConsultaRepository;
import com.petcare.web.repository.ProdutoRepository;
import com.petcare.web.repository.VeterinarioRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class PetCareModelAdvice {

    private final AnimalRepository animalRepository;
    private final ConsultaRepository consultaRepository;
    private final ProdutoRepository produtoRepository;
    private final VeterinarioRepository veterinarioRepository;

    public PetCareModelAdvice(AnimalRepository animalRepository, ConsultaRepository consultaRepository,
                              ProdutoRepository produtoRepository, VeterinarioRepository veterinarioRepository) {
        this.animalRepository = animalRepository;
        this.consultaRepository = consultaRepository;
        this.produtoRepository = produtoRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @ModelAttribute
    public void adicionarDadosDasAbas(Model model) {
        model.addAttribute("animais", animalRepository.findAll());
        model.addAttribute("veterinarios", veterinarioRepository.findAll());
        model.addAttribute("consultas", consultaRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("animal", new Animal());
        model.addAttribute("veterinario", new Veterinario());
        model.addAttribute("produto", new Produto());
        model.addAttribute("consultaEditando", null);
        model.addAttribute("dataHoraConsulta", "");
        model.addAttribute("abaAtiva", "clientes");
    }
}
