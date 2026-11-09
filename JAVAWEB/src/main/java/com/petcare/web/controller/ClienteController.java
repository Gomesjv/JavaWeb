package com.petcare.web.controller;

import com.petcare.web.model.Cliente;
import com.petcare.web.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("cliente", new Cliente());
        return "clientes";
    }

    @PostMapping
    public String salvar(@Valid Cliente cliente, BindingResult resultado, Model model,
                         RedirectAttributes redirectAttributes) {
        if (resultado.hasErrors()) {
            model.addAttribute("clientes", clienteService.listarTodos());
            return "clientes";
        }

        clienteService.salvar(cliente);
        redirectAttributes.addFlashAttribute("mensagem", "Cliente salvo com sucesso!");
        return "redirect:/clientes";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "clientes";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        clienteService.excluir(id);
        redirectAttributes.addFlashAttribute("mensagem", "Cliente excluído com sucesso!");
        return "redirect:/clientes";
    }
}
