import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
public class Carregar extends JDialog{
    private JList listaMapas;
    private JButton irMapa;
    private JButton removerMapa;
    private JButton btnVoltar = new JButton("Voltar");
    private JPanel panel;
    private File caminho;
    static private String nomeMapa;
    static private String urlImg;
    private String nomeJanela[] = new String[100];
    private int contador = 0;
    DefaultListModel dm = new DefaultListModel();
    Carregar(){
        //!------------- define onde vai ser a pasta de checagem de arquivos --------------!//
        JFileChooser file = new JFileChooser();
        file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        file.setDialogTitle("Selecione o caminho");
        file.setApproveButtonText("Selecionar");
        int res = file.showOpenDialog(null);
        if(res == JFileChooser.APPROVE_OPTION){
            caminho = file.getSelectedFile();
        }
        else {
            JOptionPane.showMessageDialog(null, "Voce nao selecionou nenhum diretorio.");
            dispose();
            new JanelaInicial();
        }
        //!------------- interface --------------!//
        this.setTitle("Registrar Mapa");
        this.setSize(600,210);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new GridLayout(1,2));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModal(true);
        //!------------- faz a leitura dos mapas --------------!//
        try{
            leituraMapa();
        } catch (IOException io){
            JOptionPane.showMessageDialog(Carregar.this,"Mapas não carregados");
            dispose();
        }
        listaMapas = new JList(dm);
        listaMapas.setVisibleRowCount(5);
        listaMapas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaMapas));
        panel = new JPanel(new GridLayout(3,1));
        irMapa = new JButton("Abrir");
        panel.add(irMapa);
        irMapa.addActionListener(new BtnEscolha());
        removerMapa = new JButton("Remover");
        panel.add(removerMapa);
        panel.add(btnVoltar);
        btnVoltar.addActionListener(new BtnEscolha());
        removerMapa.addActionListener(new BtnEscolha());
        add(panel);
        this.setVisible(true);
    }
    //!------------- faz a leitura dos mapas --------------!//
    public void leituraMapa() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho+"/caminho.txt"));
        String linha = br.readLine();
        String array[] = new String[3];
        while (linha != null){
            array = linha.split(",");
            dm.addElement(array[1]);
            nomeJanela[contador] = array[0];
            contador++;
            linha = br.readLine();
        }
        br.close();
    }
    private class BtnEscolha implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            try{
                if (evt.getActionCommand()== "Abrir"){
                    setNomeMapa(listaMapas.getSelectedValue().toString());
                    setUrlImg(listaMapas.getSelectedValue().toString());
                    dispose();
                    try {
                        new Carregado(nomeJanela[listaMapas.getSelectedIndex()]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (NullPointerException npe){
                JOptionPane.showMessageDialog(Carregar.this,"Não foi selecionado nenhum mapa");
            }
            try{
                if (evt.getActionCommand()== "Remover"){
                    setNomeMapa(listaMapas.getSelectedValue().toString());
                    setUrlImg(listaMapas.getSelectedValue().toString());
                    File apagar = new File(getUrlImg());
                    apagar.delete();
                    RemoverMapa(getNomeMapa());
                    dm.remove(listaMapas.getSelectedIndex());
                }
            }catch (NullPointerException npe){
                JOptionPane.showMessageDialog(Carregar.this,"Não foi selecionado nenhum mapa ou mapa não removido");
            }
            try {
                if (evt.getActionCommand() == "Voltar") {
                    new JanelaInicial();
                    dispose();
                }
            }catch (Exception i){}
        }
    }
    static public String getNomeMapa() {
        return nomeMapa;
    }
    public void setNomeMapa(String nomeMapa){
        this.nomeMapa = nomeMapa;
    }
    static public String getUrlImg() {
        return urlImg;
    }
    public void setUrlImg(String urlImg){
        this.urlImg = caminho.getPath()+"/"+urlImg+".png";
    }
    //!------------- remove mapa do arquivo caminho, apaga imagem --------------!//
    private void RemoverMapa(String nomeMapa){
        try{
            BufferedReader br = new BufferedReader(new FileReader(caminho.getPath()+"/caminho.txt"));
            File rm = new File(caminho.getPath()+"/"+nomeMapa+".txt");
            rm.delete();
            String linha = br.readLine();
            ArrayList<String> salvar = new ArrayList();
            String array[] = new String[3];
            while (linha != null){
                array = linha.split(",");
                if(!array[1].equals(nomeMapa)){
                    salvar.add(linha);
                }
                linha = br.readLine();
            }
            br.close();
            FileWriter fw = new FileWriter(caminho.getPath()+"/caminho.txt", true);
            fw.close();
            BufferedWriter bw = new BufferedWriter(new FileWriter(caminho.getPath()+"/caminho.txt"));
            for (int i = 0; i < salvar.size();i++){
                bw.write(salvar.get(i));
                bw.newLine();
            }
            bw.close();

        }catch (IOException ioe){
        }
    }
}

