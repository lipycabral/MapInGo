import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


public class RmPonto extends JDialog {
    private String nomeMapa, url;
    private JList listaMapas;
    private DefaultListModel dm = new DefaultListModel();
    private JPanel panel;
    private JButton btnVoltar;
    private JButton btnRemover;
    RmPonto(String nomeMapa, String url){
        this.nomeMapa = nomeMapa;
        this.url = url.substring(0, url.length()-4)+".txt";
        RmPontoJanela();
    }
    public void RmPontoJanela(){
        //--------------! interface !-------------//
        btnRemover = new JButton("Remover");
        btnVoltar = new JButton("Concluido");
        setTitle(String.format("Removendo ponto no mapa de %s", nomeMapa));
        setSize(600,180);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE   );
        setLayout(new GridLayout(1,2));
        setLocationRelativeTo(null);
        setResizable(false);
        RmPonto();
        panel = new JPanel(new GridLayout(2,1));
        panel.add(btnRemover);

        listaMapas = new JList(dm);
        listaMapas.setVisibleRowCount(5);
        listaMapas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoverPonto(listaMapas.getSelectedValue().toString());
                dm.remove(listaMapas.getSelectedIndex());
            }
        });
        panel.add(btnVoltar);
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        add(new JScrollPane(listaMapas));
        add(panel);
        setModal(true);
        setVisible(true);
    }
    public void RmPonto(){
        //--------------! lê os pontos e os adiciona ao jlist !-------------//
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(url)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String linha = null;
        try {
            linha = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String array[] = new String[4];
        while (linha != null){
            array = linha.split(",");
            dm.addElement(array[0]);
            try {
                linha = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //--------------! remove o ponto especifico e o apaga do arquivo !-------------//
    private void RemoverPonto(String nomeBairro){
        try{
            BufferedReader br = new BufferedReader(new FileReader(new File(url)));
            String linha = br.readLine();
            ArrayList<String> salvar = new ArrayList();
            String array[] = new String[4];
            while (linha != null){
                array = linha.split(",");
                if(!array[0].equals(nomeBairro)){
                    salvar.add(linha);
                }
                linha = br.readLine();
            }
            br.close();
            FileWriter fw = new FileWriter(new File(url), true);
            fw.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(url)));
            for (int i = 0; i < salvar.size();i++){
                bw.write(salvar.get(i));
                bw.newLine();
            }
            bw.close();

        }catch (IOException ioe){
        }
    }
}
