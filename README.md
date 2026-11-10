Link Youtube : https://www.youtube.com/watch?v=3Q6Ax619XoQ
# 🐾 PetCare Web - Sistema de Gestão para Clínica Veterinária

## 📋 1. Tema do Sistema
O **PetCare Web** é uma aplicação voltada para o suporte e controle administrativo de clínicas veterinárias. O sistema otimiza o fluxo de atendimento diário, permitindo o cadastro de tutores (clientes), o registro detalhado de animais vinculados aos donos, a gestão de profissionais de saúde, a marcação de consultas médicas e o monitoramento de estoque de produtos veterinários e pet shop.

### Tabelas do Sistema (5 entidades relacionais):
1. **`cliente`**: Armazena os dados cadastrais e de contato dos tutores.
2. **`animal`**: Contém as informações dos pets, vinculados obrigatoriamente a um cliente (relação 1 para N).
3. **`veterinario`**: Registra os dados dos profissionais da clínica (CRMV e especialidade).
4. **`consulta`**: Tabela de agendamentos e registros de atendimento médico, ligando o animal ao veterinário.
5. **`produto`**: Controle do estoque de mercadorias, medicamentos e insumos.

## 📐 2. Diagrama de Classes das Entidades

Modelo de domínio orientado a objetos implementado na arquitetura da aplicação:

<img width="356" height="583" alt="diagrama" src="https://github.com/user-attachments/assets/9f20f5ed-3e85-472d-8d60-8f1d85654487" />
<img width="413" height="632" alt="diagrama (1)" src="https://github.com/user-attachments/assets/2cf4d410-2778-4798-a88a-21e7428b8637" />

Protótipos e Telas do Projeto
<img width="881" height="590" alt="image" src="https://github.com/user-attachments/assets/c99dc099-9c80-4dc2-ad79-f56045c08bec" />
