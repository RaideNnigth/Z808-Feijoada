package z808_gui.components;

import virtual_machine.VirtualMachine;
import z808_gui.components.panels.CentralPanel;
import z808_gui.observerpattern.ProgramPathEventManager;
import z808_gui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    // Control key mask
    final int CTRL_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
    JMenu arquivoMenu;
    JMenu executarMenu;
    JMenu loggerMenu;
    JMenu configsMenu;
    JMenu ajudaMenu;
    CentralPanel centralPanel;
    VirtualMachine vm;
    final ProgramPathEventManager ppm = ProgramPathEventManager.getInstance();

    public MenuBar(CentralPanel centralPanel, VirtualMachine vm) {
        this.centralPanel = centralPanel;

        // Itens da barra de menus
        this.arquivoMenu = new JMenu("Arquivo");
        this.executarMenu = new JMenu("Executar");
        this.loggerMenu = new JMenu("Logger");
        this.configsMenu = new JMenu("Configurações");
        this.ajudaMenu = new JMenu("Ajuda");

        this.populateArquivoMenu();
        this.populateExecutarMenu();
        this.populateLoggerMenu();
        this.populateAjudaMenu();

        // Adicionando itens da barra principal
        this.add(arquivoMenu);
        this.add(executarMenu);
        this.add(loggerMenu);
        this.add(configsMenu);
        this.add(ajudaMenu);
    }

    private void populateArquivoMenu() {
        // --------------------- Sub-itens menu Arquivo ---------------------
        // Novo
        JMenuItem novoMenItem = new JMenuItem("Novo");
        novoMenItem.setAccelerator(KeyStroke.getKeyStroke('N', this.CTRL_MASK));

        novoMenItem.addActionListener(e -> UIUtils.newFile(this.centralPanel.getTabs()));

        // Abrir
        JMenuItem abrirMenItem = new JMenuItem("Abrir");
        abrirMenItem.setAccelerator(KeyStroke.getKeyStroke('O', this.CTRL_MASK));

        abrirMenItem.addActionListener(e -> UIUtils.openFile(this.centralPanel.getTabs()));

        // Salvar
        JMenuItem salvarMenItem = new JMenuItem("Salvar");
        salvarMenItem.setAccelerator(KeyStroke.getKeyStroke('S', this.CTRL_MASK));

        salvarMenItem.addActionListener(e -> UIUtils.saveFile(this.centralPanel.getTabs()));

        JMenuItem exportarMemMenuItem = new JMenuItem("Exportar memória");
        exportarMemMenuItem.addActionListener(e -> UIUtils.exportMemoryData(this.centralPanel.getTabs(), this.vm));

        JMenuItem fecharMenuItem = new JMenuItem("Fechar");
        fecharMenuItem.addActionListener(e -> UIUtils.closeFile(this.centralPanel.getTabs()));

        // Sair
        JMenuItem sairMenItem = new JMenuItem("Sair");
        sairMenItem.setAccelerator(KeyStroke.getKeyStroke('Q', this.CTRL_MASK));

        sairMenItem.addActionListener(e -> System.exit(0));

        // Adicionando itens em Arquivo
        this.arquivoMenu.add(novoMenItem);
        this.arquivoMenu.add(abrirMenItem);
        this.arquivoMenu.add(salvarMenItem);
        this.arquivoMenu.add(exportarMemMenuItem);
        this.arquivoMenu.add(fecharMenuItem);
        this.arquivoMenu.add(sairMenItem);
    }

    private void populateExecutarMenu() {
        // --------------------- Sub-itens menu Executar ---------------------
        JMenuItem montarMenItem = new JMenuItem("Montar código");
        montarMenItem.setAccelerator(KeyStroke.getKeyStroke('M', this.CTRL_MASK));
        montarMenItem.addActionListener(e -> UIUtils.assembleFile(this.centralPanel.getTabs()));

        JMenuItem executarTudoMenItem = new JMenuItem("Executar tudo");
        executarTudoMenItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        executarTudoMenItem.addActionListener(e -> UIUtils.runFile(this.centralPanel.getTabs(), this.vm));

        JMenuItem definirDependenciasMenuItem = new JMenuItem("Definir dependências");
        definirDependenciasMenuItem.addActionListener(e -> UIUtils.setDependecies(this.centralPanel.getTabs()));

        this.executarMenu.add(montarMenItem);
        this.executarMenu.add(executarTudoMenItem);
        this.executarMenu.add(definirDependenciasMenuItem);
    }

    private void populateLoggerMenu() {
        JMenuItem openCloseLoggerMenuItem = new JMenuItem("Open/Close logger");
        openCloseLoggerMenuItem.addActionListener(e -> UIUtils.openCloseLogger(this.centralPanel));

        JMenuItem clearLoggerTextMenuItem = new JMenuItem("Limpar saída do logger");
        clearLoggerTextMenuItem.addActionListener(e -> UIUtils.clearLoggerText(this.centralPanel.getLoggerPanel()));

        this.loggerMenu.add(openCloseLoggerMenuItem);
        this.loggerMenu.add(clearLoggerTextMenuItem);
    }

    private void populateAjudaMenu() {
        // --------------------- Sub-itens menu Sobre ---------------------
        JMenuItem devsMenItem = new JMenuItem("Desenvolvedores");
        devsMenItem.addActionListener(e -> JOptionPane.showMessageDialog(null, " Gabriel Bessa\n Daniel Lisboa\n Gustavo Pereira\n Henrique Rodrigues\n Miguel Strelow\n Nicolas Machado\n Pedro da Silva\n Rafael Ferrão\n Ravilon dos Santos\n", "Desenvolvedores", JOptionPane.INFORMATION_MESSAGE, null));

        JMenuItem sobreMenItem = new JMenuItem("Sobre");
        sobreMenItem.setMnemonic(KeyEvent.VK_F1);

        JMenuItem comoUsarMenItem = new JMenuItem("Como usar");
        comoUsarMenItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Te vira.", "Como usar", JOptionPane.INFORMATION_MESSAGE, null));

        this.ajudaMenu.add(devsMenItem);
        this.ajudaMenu.add(sobreMenItem);
        this.ajudaMenu.add(new JSeparator());
        this.ajudaMenu.add(comoUsarMenItem);
    }
}
