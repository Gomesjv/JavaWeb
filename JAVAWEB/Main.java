import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {

    private static final Color COR_PRIMARIA = new Color(35, 105, 95);
    private static final Color COR_TEXTO = new Color(45, 55, 65);
    private static final Color COR_FUNDO = new Color(244, 247, 248);
    private static final Color COR_LINHA = new Color(222, 230, 232);

    private JTable tabelaClientes;
    private DefaultTableModel modeloTabelaClientes;
    private JTextField txtNome, txtCpf, txtTelefone, txtEmail, txtEndereco;
    private int linhaSelecionada = -1;

    public Main() {
        // Configuração da janela principal
        setTitle("PetCare Web - Protótipo do Sistema de Gestão Veterinária");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(COR_FUNDO);

        JPanel cabecalho = new JPanel(new BorderLayout());
        cabecalho.setBackground(COR_PRIMARIA);
        cabecalho.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        JLabel titulo = new JLabel("PetCare");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        JLabel subtitulo = new JLabel("Gestão veterinária");
        subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(220, 240, 236));
        JPanel textosCabecalho = new JPanel(new GridLayout(2, 1, 0, 2));
        textosCabecalho.setOpaque(false);
        textosCabecalho.add(titulo);
        textosCabecalho.add(subtitulo);
        cabecalho.add(textosCabecalho, BorderLayout.CENTER);
        conteudo.add(cabecalho, BorderLayout.NORTH);

        // Painel com abas para representar as entidades/tabelas do sistema
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 13));
        tabbedPane.setBackground(COR_FUNDO);
        tabbedPane.setForeground(COR_TEXTO);
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));

        tabbedPane.addTab("Clientes", criarPainelClientes());
        tabbedPane.addTab("Animais", criarPainelModulo("Módulo de Gestão de Animais (Espécie, Raça, Idade, Peso e Vínculo com Tutor)"));
        tabbedPane.addTab("Veterinários", criarPainelModulo("Módulo de Gestão de Veterinários (CRMV e Especialidade)"));
        tabbedPane.addTab("Consultas", criarPainelModulo("Módulo de Agendamento (Relaciona Animal e Veterinário)"));
        tabbedPane.addTab("Produtos", criarPainelModulo("Módulo de Controle de Estoque (Medicamentos e Produtos Pet)"));

        conteudo.add(tabbedPane, BorderLayout.CENTER);
        setContentPane(conteudo);
    }

    // Painel de interface CRUD para a entidade Cliente
    private JPanel criarPainelClientes() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.setBackground(COR_FUNDO);

        // Formularidade de Entrada de Dados
        JPanel painelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        painelForm.setBackground(Color.WHITE);
        TitledBorder bordaForm = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(COR_LINHA), "Dados do cliente"
        );
        bordaForm.setTitleColor(COR_PRIMARIA);
        bordaForm.setTitleFont(new Font("SansSerif", Font.BOLD, 13));
        painelForm.setBorder(BorderFactory.createCompoundBorder(
            bordaForm, BorderFactory.createEmptyBorder(8, 10, 10, 10)
        ));

        txtNome = new JTextField();
        txtCpf = new JTextField();
        txtTelefone = new JTextField();
        txtEmail = new JTextField();
        txtEndereco = new JTextField();

        adicionarCampo(painelForm, "Nome completo:", txtNome);
        adicionarCampo(painelForm, "CPF:", txtCpf);
        adicionarCampo(painelForm, "Telefone:", txtTelefone);
        adicionarCampo(painelForm, "E-mail:", txtEmail);
        adicionarCampo(painelForm, "Endereço:", txtEndereco);

        painel.add(painelForm, BorderLayout.NORTH);

        // Tabela para Exibição / Listagem dos Dados
        modeloTabelaClientes = new DefaultTableModel(
            new String[]{"ID", "Nome", "CPF", "Telefone", "E-mail", "Endereço"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável diretamente nas células
            }
        };

        tabelaClientes = new JTable(modeloTabelaClientes);
        tabelaClientes.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabelaClientes.setForeground(COR_TEXTO);
        tabelaClientes.setRowHeight(30);
        tabelaClientes.setShowVerticalLines(false);
        tabelaClientes.setGridColor(COR_LINHA);
        tabelaClientes.setSelectionBackground(new Color(213, 237, 231));
        tabelaClientes.setSelectionForeground(COR_TEXTO);
        tabelaClientes.setFillsViewportHeight(true);
        tabelaClientes.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabelaClientes.getTableHeader().setBackground(COR_PRIMARIA);
        tabelaClientes.getTableHeader().setForeground(Color.WHITE);
        tabelaClientes.getTableHeader().setPreferredSize(new Dimension(0, 34));
        tabelaClientes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                Component celula = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column
                );
                if (!isSelected) {
                    celula.setBackground(row % 2 == 0 ? Color.WHITE : COR_FUNDO);
                    celula.setForeground(COR_TEXTO);
                }
                setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return celula;
            }
        });
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);
        scrollPane.setBorder(BorderFactory.createLineBorder(COR_LINHA));
        painel.add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões de Ação (CRUD)
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        painelBotoes.setBackground(COR_FUNDO);
        JButton btnSalvar = new JButton("Cadastrar");
        JButton btnEditar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

        estilizarBotao(btnSalvar, COR_PRIMARIA, Color.WHITE);
        estilizarBotao(btnEditar, new Color(63, 126, 168), Color.WHITE);
        estilizarBotao(btnExcluir, new Color(190, 76, 68), Color.WHITE);
        estilizarBotao(btnLimpar, new Color(229, 234, 236), COR_TEXTO);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        // --- MÉTODOS E EVENTOS DOS BOTÕES ---

        // Cadastrar Novo Registo
        btnSalvar.addActionListener(e -> {
            if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha pelo menos os campos Nome e CPF!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int id = modeloTabelaClientes.getRowCount() + 1;
            modeloTabelaClientes.addRow(new Object[]{
                id, txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText()
            });
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            limparCampos();
        });

        // Selecionar linha na tabela para carregar nos campos
        tabelaClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                linhaSelecionada = tabelaClientes.getSelectedRow();
                if (linhaSelecionada >= 0) {
                    txtNome.setText(modeloTabelaClientes.getValueAt(linhaSelecionada, 1).toString());
                    txtCpf.setText(modeloTabelaClientes.getValueAt(linhaSelecionada, 2).toString());
                    txtTelefone.setText(modeloTabelaClientes.getValueAt(linhaSelecionada, 3).toString());
                    txtEmail.setText(modeloTabelaClientes.getValueAt(linhaSelecionada, 4).toString());
                    txtEndereco.setText(modeloTabelaClientes.getValueAt(linhaSelecionada, 5).toString());
                }
            }
        });

        // Atualizar Registo Selecionado
        btnEditar.addActionListener(e -> {
            if (linhaSelecionada >= 0) {
                modeloTabelaClientes.setValueAt(txtNome.getText(), linhaSelecionada, 1);
                modeloTabelaClientes.setValueAt(txtCpf.getText(), linhaSelecionada, 2);
                modeloTabelaClientes.setValueAt(txtTelefone.getText(), linhaSelecionada, 3);
                modeloTabelaClientes.setValueAt(txtEmail.getText(), linhaSelecionada, 4);
                modeloTabelaClientes.setValueAt(txtEndereco.getText(), linhaSelecionada, 5);
                JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                limparCampos();
                linhaSelecionada = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Excluir Registo Selecionado
        btnExcluir.addActionListener(e -> {
            if (linhaSelecionada >= 0) {
                modeloTabelaClientes.removeRow(linhaSelecionada);
                limparCampos();
                linhaSelecionada = -1;
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Limpar os campos do formulário
        btnLimpar.addActionListener(e -> {
            limparCampos();
            tabelaClientes.clearSelection();
            linhaSelecionada = -1;
        });

        return painel;
    }

    // Painel genérico para demonstrar as abas das outras entidades
    private JPanel criarPainelModulo(String descricao) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + descricao + "</div></html>", JLabel.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(COR_TEXTO);
        painel.add(label, BorderLayout.CENTER);
        return painel;
    }

    private void adicionarCampo(JPanel painel, String texto, JTextField campo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(COR_TEXTO);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COR_LINHA),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        painel.add(label);
        painel.add(campo);
    }

    private void estilizarBotao(JButton botao, Color fundo, Color texto) {
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setBackground(fundo);
        botao.setForeground(texto);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorder(BorderFactory.createEmptyBorder(9, 16, 9, 16));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
    }

    // Método principal para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Utiliza o estilo visual nativo do sistema operacional
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            
            new Main().setVisible(true);
        });
    }
}