import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JanelaInicial extends JFrame{
    private JButton btnCarregar;
    private JButton btnRegistrar;
    private File diretorio;
    public JanelaInicial(){
        //-----------! interface !-------------------//
        super("MapInGo");
        this.setSize(600,210);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,2));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        Icon carregar = new ImageIcon(getClass().getResource("/arquivos/carregar.png"));
        btnCarregar = new JButton("Carregar mapa", carregar);
        add(btnCarregar);

        Icon registrar = new ImageIcon(getClass().getResource("/arquivos/registrar.png"));
        btnRegistrar = new JButton("Registrar mapa", registrar);
        add(btnRegistrar);

        BtnEscolha escolha = new BtnEscolha();
        btnRegistrar.addActionListener(escolha);
        btnCarregar.addActionListener(escolha);
            //-----------! adiciona a label !-------------------//
            LabelDados();
            //-----------! inicia a leitura do sensor !-------------------//
        try{
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Componentes();
                }
            });
        }catch (Exception i){
            if(System.getProperty("os.name").contains("Linux")){
                JOptionPane.showMessageDialog(JanelaInicial.this, "Adicionar |librxtx * .so| ao diretorio /usr/lib/");
            }
            if(System.getProperty("os.name").contains("Windows")){
                JOptionPane.showMessageDialog(JanelaInicial.this, "Adicionar |rxtxSerial.dll| ao diretorio /Windows/System32/");
            }
            System.exit(0);
        }

    }


    private class BtnEscolha implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(evt.getActionCommand() == "Registrar mapa"){
                new Registrar();
                dispose();
            }
            if(evt.getActionCommand() == "Carregar mapa"){
                new Carregar();
                dispose();
            }
        }
    }
    private void LabelDados(){
        setLayout(new FlowLayout());
        JLabel label = new JLabel("Dados Sensor: " );
        JLabel labelDados = Componentes.labelSensor();
        add(label);
        add(labelDados);
    }

}
