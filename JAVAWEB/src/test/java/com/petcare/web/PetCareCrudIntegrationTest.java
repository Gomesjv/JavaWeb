package com.petcare.web;

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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PetCareCrudIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void createsEditsAndDeletesRecordsInEveryModule() throws Exception {
        mockMvc.perform(post("/clientes")
                        .param("nome", "Tutor CRUD")
                        .param("cpf", "12345678900")
                        .param("email", "tutor@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/clientes"));
        Cliente cliente = clienteRepository.findAll().get(0);
        mockMvc.perform(get("/clientes/{id}/editar", cliente.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Tutor CRUD")));
        mockMvc.perform(post("/clientes")
                        .param("id", cliente.getId().toString())
                        .param("nome", "Tutor atualizado")
                        .param("cpf", "12345678900")
                        .param("email", "tutor@example.com"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/animais")
                        .param("nome", "Pet CRUD")
                        .param("especie", "Cão")
                        .param("clienteId", cliente.getId().toString()))
                .andExpect(status().is3xxRedirection());
        Animal animal = animalRepository.findAll().get(0);
        mockMvc.perform(get("/animais/{id}/editar", animal.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Pet CRUD")))
                .andExpect(content().string(containsString("Tutor atualizado")));
        mockMvc.perform(post("/animais")
                        .param("id", animal.getId().toString())
                        .param("nome", "Pet atualizado")
                        .param("clienteId", cliente.getId().toString()))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/veterinarios")
                        .param("nome", "Veterinário CRUD")
                        .param("crmv", "CRMV-123")
                        .param("especialidade", "Clínica"))
                .andExpect(status().is3xxRedirection());
        Veterinario veterinario = veterinarioRepository.findAll().get(0);
        mockMvc.perform(get("/veterinarios/{id}/editar", veterinario.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Veterinário CRUD")));
        mockMvc.perform(post("/veterinarios")
                        .param("id", veterinario.getId().toString())
                        .param("nome", "Veterinário atualizado")
                        .param("crmv", "CRMV-123")
                        .param("especialidade", "Cirurgia"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/produtos")
                        .param("nome", "Produto CRUD")
                        .param("categoria", "Teste")
                        .param("preco", "12.50")
                        .param("quantidadeEstoque", "4"))
                .andExpect(status().is3xxRedirection());
        Produto produto = produtoRepository.findAll().get(0);
        mockMvc.perform(get("/produtos/{id}/editar", produto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Produto CRUD")));
        mockMvc.perform(post("/produtos")
                        .param("id", produto.getId().toString())
                        .param("nome", "Produto atualizado")
                        .param("categoria", "Teste")
                        .param("preco", "15.00")
                        .param("quantidadeEstoque", "8"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/consultas")
                        .param("animalId", animal.getId().toString())
                        .param("veterinarioId", veterinario.getId().toString())
                        .param("dataHora", "2026-11-10T12:30")
                        .param("descricao", "Consulta CRUD"))
                .andExpect(status().is3xxRedirection());
        Consulta consulta = consultaRepository.findAll().get(0);
        mockMvc.perform(get("/consultas/{id}/editar", consulta.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Consulta CRUD")));
        mockMvc.perform(post("/consultas")
                        .param("id", consulta.getId().toString())
                        .param("animalId", animal.getId().toString())
                        .param("veterinarioId", veterinario.getId().toString())
                        .param("dataHora", "2026-11-11T13:30")
                        .param("descricao", "Consulta atualizada")
                        .param("status", "Realizada"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/consultas/{id}/excluir", consulta.getId()))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(post("/animais/{id}/excluir", animal.getId()))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(post("/veterinarios/{id}/excluir", veterinario.getId()))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(post("/produtos/{id}/excluir", produto.getId()))
                .andExpect(status().is3xxRedirection());
        mockMvc.perform(post("/clientes/{id}/excluir", cliente.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attributeCount(1));
    }
}
