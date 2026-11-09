package com.petcare.web.controller;

import com.petcare.web.model.Animal;
import com.petcare.web.model.Consulta;
import com.petcare.web.model.Produto;
import com.petcare.web.model.Veterinario;
import com.petcare.web.repository.AnimalRepository;
import com.petcare.web.repository.ClienteRepository;
import com.petcare.web.repository.ConsultaRepository;
import com.petcare.web.repository.ProdutoRepository;
import com.petcare.web.repository.VeterinarioRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PetCareController {

    private final AnimalRepository animalRepository;
    private final ClienteRepository clienteRepository;
    private final ConsultaRepository consultaRepository;
    private final ProdutoRepository produtoRepository;
    private final VeterinarioRepository veterinarioRepository;

    public PetCareController(AnimalRepository animalRepository, ClienteRepository clienteRepository,
                             ConsultaRepository consultaRepository, ProdutoRepository produtoRepository,
                             VeterinarioRepository veterinarioRepository) {
        this.animalRepository = animalRepository;
        this.clienteRepository = clienteRepository;
        this.consultaRepository = consultaRepository;
        this.produtoRepository = produtoRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/clientes";
    }

    @GetMapping("/animais/{id}/editar")
    public String editarAnimal(@PathVariable Long id, Model model) {
        model.addAttribute("animal", animalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado: " + id)));
        model.addAttribute("abaAtiva", "animais");
        return "clientes";
    }

    @PostMapping("/animais")
    public String salvarAnimal(Animal animal, @RequestParam(required = false) Long clienteId,
                               RedirectAttributes redirectAttributes) {
        boolean atualizacao = animal.getId() != null;
        if (clienteId != null) {
            animal.setCliente(clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + clienteId)));
        } else {
            animal.setCliente(null);
        }
        animalRepository.save(animal);
        redirectAttributes.addFlashAttribute("mensagem",
                atualizacao ? "Animal atualizado com sucesso!" : "Animal cadastrado com sucesso!");
        return "redirect:/clientes#animais";
    }

    @PostMapping("/animais/{id}/excluir")
    public String excluirAnimal(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        animalRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Animal excluído com sucesso!");
        return "redirect:/clientes#animais";
    }

    @GetMapping("/veterinarios/{id}/editar")
    public String editarVeterinario(@PathVariable Long id, Model model) {
        model.addAttribute("veterinario", veterinarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veterinário não encontrado: " + id)));
        model.addAttribute("abaAtiva", "veterinarios");
        return "clientes";
    }

    @PostMapping("/veterinarios")
    public String salvarVeterinario(Veterinario veterinario, RedirectAttributes redirectAttributes) {
        boolean atualizacao = veterinario.getId() != null;
        veterinarioRepository.save(veterinario);
        redirectAttributes.addFlashAttribute("mensagem",
                atualizacao ? "Veterinário atualizado com sucesso!" : "Veterinário cadastrado com sucesso!");
        return "redirect:/clientes#veterinarios";
    }

    @PostMapping("/veterinarios/{id}/excluir")
    public String excluirVeterinario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        veterinarioRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Veterinário excluído com sucesso!");
        return "redirect:/clientes#veterinarios";
    }

    @GetMapping("/consultas/{id}/editar")
    public String editarConsulta(@PathVariable Long id, Model model) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada: " + id));
        model.addAttribute("consultaEditando", consulta);
        model.addAttribute("dataHoraConsulta",
                consulta.getDataHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        model.addAttribute("abaAtiva", "consultas");
        return "clientes";
    }

    @PostMapping("/consultas")
    public String salvarConsulta(@RequestParam(required = false) Long id,
                                 @RequestParam Long animalId, @RequestParam Long veterinarioId,
                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHora,
                                 @RequestParam(required = false) String descricao,
                                 @RequestParam(required = false) String status,
                                 RedirectAttributes redirectAttributes) {
        boolean atualizacao = id != null;
        Consulta consulta = atualizacao
                ? consultaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada: " + id))
                : new Consulta();
        consulta.setAnimal(animalRepository.findById(animalId)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado: " + animalId)));
        consulta.setVeterinario(veterinarioRepository.findById(veterinarioId)
                .orElseThrow(() -> new IllegalArgumentException("Veterinário não encontrado: " + veterinarioId)));
        consulta.setDataHora(dataHora);
        consulta.setDescricao(descricao);
        consulta.setStatus(status == null || status.isBlank() ? "Agendada" : status);
        consultaRepository.save(consulta);
        redirectAttributes.addFlashAttribute("mensagem",
                atualizacao ? "Consulta atualizada com sucesso!" : "Consulta agendada com sucesso!");
        return "redirect:/clientes#consultas";
    }

    @PostMapping("/consultas/{id}/excluir")
    public String excluirConsulta(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        consultaRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Consulta excluída com sucesso!");
        return "redirect:/clientes#consultas";
    }

    @GetMapping("/produtos/{id}/editar")
    public String editarProduto(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id)));
        model.addAttribute("abaAtiva", "produtos");
        return "clientes";
    }

    @PostMapping("/produtos")
    public String salvarProduto(Produto produto, RedirectAttributes redirectAttributes) {
        boolean atualizacao = produto.getId() != null;
        produtoRepository.save(produto);
        redirectAttributes.addFlashAttribute("mensagem",
                atualizacao ? "Produto atualizado com sucesso!" : "Produto cadastrado com sucesso!");
        return "redirect:/clientes#produtos";
    }

    @PostMapping("/produtos/{id}/excluir")
    public String excluirProduto(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        produtoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("mensagem", "Produto excluído com sucesso!");
        return "redirect:/clientes#produtos";
    }
}
