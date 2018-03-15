import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class Registrar extends JDialog {
    private JTextField tfNomeCidade;
    private JLabel lbNomeCidade;
    private JTextField tfNomeMapa;
    private JLabel lbNomeMapa;
    private JButton btnImagem;
    private JButton btnOk;
    private JButton btnVoltar;
    private Icon mapa = new ImageIcon(getClass().getResource("arquivos/mapa.png"));
    private BufferedImage imagem;
    private File diretorio;
    Registrar(){
        //--------------! interface !-------------//
        this.setTitle("Registrar Mapa");
        this.setLayout(null);
        this.setSize(600,210);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setModal(true);

        this.lbNomeCidade = new JLabel("Digite o nome da cidade: ");
        this.tfNomeCidade = new JTextField();
        this.lbNomeCidade.setBounds(50,10,200,30);
        this.tfNomeCidade.setBounds(250,10,300,30);

        this.lbNomeMapa = new JLabel("Digite o nome do mapa: ");
        this.tfNomeMapa = new JTextField();
        this.lbNomeMapa.setBounds(50,50,200,30);
        this.tfNomeMapa.setBounds(250,50,300,30);

        this.btnImagem = new JButton("Enviar mapa", mapa);
        this.btnImagem.setBounds(200,90,200,40);

        this.btnOk = new JButton("Registrar");
        this.btnOk.setBounds(200,135,200,40);

        this.btnVoltar = new JButton("Voltar");
        this.btnVoltar.setBounds(0,150,80,30);

        this.add(lbNomeCidade);
        this.add(lbNomeMapa);
        this.add(tfNomeCidade);
        this.add(tfNomeMapa);
        this.add(btnImagem);
        this.add(btnOk);
        this.add(btnVoltar);
        this.btnOk.addActionListener(new BtnEscolha());
        this.btnImagem.addActionListener(new BtnEscolha());
        this.btnVoltar.addActionListener(new BtnEscolha());

        this.setVisible(true);
    }
    private class BtnEscolha implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            //--------------! seleciona a imagem que quero enviar !-------------//
            if(evt.getActionCommand().equals("Enviar mapa")){
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new FileNameExtensionFilter("Imagens", "png", "jpg"));
                fc.setAcceptAllFileFilterUsed(false);
                fc.setDialogTitle("Selecione a imagem");
                fc.setApproveButtonText("Selecionar");
                int res = fc.showOpenDialog(null);

                if (res == JFileChooser.APPROVE_OPTION) {
                    File arquivo = fc.getSelectedFile();

                    try {
                        imagem = ManipularImagem.setImagemDimensao(arquivo.getAbsolutePath(), 1280, 720);
                    } catch (Exception ex) {
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Voce nao selecionou nenhum arquivo.");
                }
            }
            //--------------! salva tudo onde eu quero !-------------//
            if(evt.getActionCommand().equals("Registrar")) {
                if ((!tfNomeMapa.getText().equals("")) && (!tfNomeCidade.getText().equals(""))) {
                    try {
                        if (diretorio == null) {
                            JFileChooser file = new JFileChooser();
                            file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                            file.setDialogTitle("Selecione onde quer salvar");
                            file.setApproveButtonText("Salvar");
                            int res = file.showOpenDialog(null);
                            if (res == JFileChooser.APPROVE_OPTION) {
                                diretorio = file.getSelectedFile();
                            } else
                                JOptionPane.showMessageDialog(null, "Voce nao selecionou nenhum diretorio.");
                        }
                        File outputfile = new File(diretorio.getPath() + "/" + tfNomeMapa.getText() + ".png");
                        try {
                            ImageIO.write(imagem, "png", outputfile);
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(rootPane, "Erro");
                        }
                        JOptionPane.showMessageDialog(rootPane, "Imagem enviada com sucesso");
                        File txt = new File(diretorio.getPath() + "/caminho.txt");
                        File btn = new File(diretorio.getPath() + "/" + tfNomeMapa.getText() + ".txt");
                        try {
                            txt.createNewFile();
                            btn.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            RegistrarArquivo();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dispose();
                        new JanelaInicial();
                    }catch (Exception i){
                        JOptionPane.showMessageDialog(Registrar.this, "Coloque uma imagem");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(Registrar.this, "Coloque o nome do mapa ou o nome da cidade");
                }
            }
            //--------------! volta pra pagina principal !-------------//
            if(evt.getActionCommand()=="Voltar"){
                dispose();
                new JanelaInicial();
            }
        }
    }

    public void RegistrarArquivo()throws IOException{
        //--------------! salva o nome da cidade e o nome do mapa !-------------//
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(diretorio.getPath()+"/caminho.txt",true));
        osw.write(tfNomeCidade.getText() + "," + tfNomeMapa.getText() +"\n");
        osw.close();
    }
}