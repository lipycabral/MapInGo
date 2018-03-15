import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Carregado extends JFrame {
    private JPanel panel = new JPanel(new GridLayout(4,1));
    private JPanel panel2 = new JPanel(new FlowLayout());
    private JLabel backGroud ;
    private JLabel lblSensor ;
    private JButton btnAddPonto;
    private JButton btnRemovePonto;
    private JButton btnLimparPontos;
    private JButton[] btn;
    private String cotaDeEvasao[] = new String[100];
    private String nomeDoBairroEvasao[] = new String[100];
    private String vOf [] = new String[100];
    private String nomeJanela;
    Carregado(String nomeJanela) throws IOException {
        //!------------- iniciando leitura de dados do sensor --------------!//
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Componentes();
            }
        });
        //!------------- iniciando a checagem dos pontos --------------!//
        Tempo tempo = new Tempo();
        tempo.start();
        //!------------- interface --------------!//
        this.nomeJanela = nomeJanela;
        this.setTitle(String.format("Mapa de %s", nomeJanela));
        this.setSize(1280,720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        backGroud = new JLabel(new ImageIcon(Carregar.getUrlImg()));
        backGroud.setBounds(0,0,1280,720);
        panel.setBounds(1200,500,40,300);
        panel2.setBounds(0,0,1280,720);
        panel.setBackground(new Color(0,0,0,0));
        panel2.setBackground(new Color(0,0,0,0));
        Pontos pontos = new Pontos(nomeJanela,Carregar.getUrlImg());
        Icon addPonto = new ImageIcon(getClass().getResource("/arquivos/add.png"));
        Icon rmPonto = new ImageIcon(getClass().getResource("/arquivos/delete.png"));
        Icon rmAll = new ImageIcon(getClass().getResource("/arquivos/deleteAll.png"));
        btnAddPonto = new JButton(addPonto);
        btnAddPonto.setBorder(null);
        btnAddPonto.setBorderPainted(false);
        btnAddPonto.setContentAreaFilled(false);
        btnAddPonto.setOpaque(false);
        //!------------- eventos --------------!//
        btnAddPonto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //!------------- interrompendo a checagem --------------!//
                tempo.interrupt();
                tempo.stop();
                //!------------- abrindo janela pra adicionar pontos --------------!//
                try {
                    pontos.AdicionarPontos();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //!------------- reiniciando janela carregado --------------!//
                dispose();
                try {
                    new Carregado(nomeJanela);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnAddPonto.setBackground(new Color(0,0,0,0));
        btnRemovePonto = new JButton(rmPonto);
        btnRemovePonto.setBorder(null);
        btnRemovePonto.setBorderPainted(false);
        btnRemovePonto.setContentAreaFilled(false);
        btnRemovePonto.setOpaque(false);
        btnRemovePonto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempo.interrupt();
                tempo.stop();
                pontos.RmPontoJ();
                dispose();
                try {
                    new Carregado(nomeJanela);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnLimparPontos = new JButton(rmAll);
        btnLimparPontos.setBorder(null);
        btnLimparPontos.setBorderPainted(false);
        btnLimparPontos.setContentAreaFilled(false);
        btnLimparPontos.setOpaque(false);
        btnLimparPontos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tempo.interrupt();
                tempo.stop();
                pontos.RmAllPontos();
                try {
                    new Carregado(nomeJanela);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                dispose();

            }
        });
        panel.add(btnAddPonto);
        panel.add(btnRemovePonto);
        panel.add(btnLimparPontos);
        panel2.add(backGroud);
        lblSensor = Componentes.labelSensor();
        lblSensor.setBounds(1080,5,200,30);
        backGroud.add(lblSensor);
        add(panel);
        add(panel2);
        //!------------- Faz as leituras dos pontos já existentes --------------!//
        try {
            LeitorPontos();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setVisible(true);
    }
    //!------------- faz as leituras dos pontos já existentes --------------!//
    public void LeitorPontos() throws IOException {
        btn = new JButton[100];
        Bu\fferedReader br = new BufferedReader(new FileReader(new File(Carregar.getUrlImg().substring(0,Carregar.getUrlImg().length()-4)+".txt")));
        String linha = br.readLine();
        String array[] = new String[4];
        Icon carregar = new ImageIcon(getClass().getResource("/arquivos/mapa.png"));
        for(int i = 0 ; linha != null; i++){
            array = linha.split(",");
            btn[i] = new JButton(carregar);
            btn[i].setBounds(Integer.parseInt(array[2]),Integer.parseInt(array[3]),50,50);
            btn[i].setBorder(null);
            btn[i].setBorderPainted(false);
            btn[i].setContentAreaFilled(false);
            btn[i].setOpaque(false);
            String[] finalArray = array;
            btn[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(Carregado.this, "Nome do Ponto: "+finalArray[0]+"\nCota de Evasão: "+finalArray[1]);
                }
            });
            cotaDeEvasao[i] = array[1];
            nomeDoBairroEvasao[i] = array[0];
            vOf[i] = "0";
            backGroud.add(btn[i]);
            linha = br.readLine();
        }
        br.close();
    }
    //!------------- faz as checagem dos pontos --------------!//
    public class Tempo extends Thread{
        Icon MapaV = new ImageIcon(getClass().getResource("/arquivos/mapaV.png"));
        Icon Mapa = new ImageIcon(getClass().getResource("/arquivos/mapa.png"));
        public void run() {
            while (true) {
                try { Thread.sleep(5000);} catch (Exception e) {}
                try {
                    if (!lblSensor.getText().equals("Sem conexão com o sensor")) {
                        if (cotaDeEvasao.length != 0) {
                            for (int i = 0; cotaDeEvasao.length > i; i++) {
                                if (Float.parseFloat(lblSensor.getText()) > Float.parseFloat(cotaDeEvasao[i])) {
                                    if (!vOf[i].equals("1")) {
                                        JOptionPane.showMessageDialog(Carregado.this, String.format("Bairro onde houve transbordamento %s", nomeDoBairroEvasao[i]));
                                        btn[i].setIcon(MapaV);
                                        vOf[i] = "1";
                                    }
                                }
                                else{
                                    btn[i].setIcon(Mapa);
                                    vOf[i] = "0";
                                }
                            }
                        }
                    }
                }catch (Exception ex){}
            }
        }
    }
}
