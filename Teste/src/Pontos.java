import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

public class Pontos extends JDialog {
    private JLabel background;
    private String nomeMapa;
    private String url;
    private JButton[] btn;


    public Pontos(String nomeMapa, String url) {
        this.nomeMapa = nomeMapa;
        this.background = new JLabel(new ImageIcon(url));
        this.background.setBounds(0, 0, 1280, 720);
        this.url = url.substring(0, url.length()-4)+".txt";
    }
    //--------------! inserindo os pontos no mapa !-------------//
    public void AdicionarPontos() throws IOException {
        setTitle(String.format("Adicionando ponto no mapa de %s", nomeMapa));
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        Mouse mouse = new Mouse();
        background.addMouseListener(mouse);
        add(background);
        //--------------! lê os pontos do mapa !-------------//
        try{
            LeitorPontos();
        }catch (Exception e){
            JOptionPane.showMessageDialog(Pontos.this,"Sem pontos ainda");
        }
        setModal(true);
        setVisible(true);
    }
    //--------------! remove ponto especifico !-------------//
    public void RmPontoJ(){
        new RmPonto(nomeMapa,url);
    }
    //--------------! remove todos os pontos !-------------//
    public void RmAllPontos(){
        try {
            File file = new File(url);
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //--------------! lê e adiciona os pontos no mapa !-------------//
    public void LeitorPontos() throws IOException {
        btn = new JButton[100];
        BufferedReader br = new BufferedReader(new FileReader(new File(url)));
        String linha = br.readLine();
        String array[] = new String[4];
        Icon carregar = new ImageIcon(getClass().getResource("/arquivos/mapa.png"));
        for(int i = 0 ; linha != null; i++){
            array = linha.split(",");
            btn[i] = new JButton(carregar);
            btn[i].setBounds(Integer.parseInt(array[2]),Integer.parseInt(array[3]),32,32);
            btn[i].setBorder(null);
            btn[i].setBorderPainted(false);
            btn[i].setContentAreaFilled(false);
            btn[i].setOpaque(false);
            background.add(btn[i]);
            linha = br.readLine();
        }
        br.close();

    }
    public class Mouse implements MouseListener{
        public void mousePressed (MouseEvent e){
            //--------------! inserindo os pontos no mapa com o clique do mouse pegando x e y !-------------//
            try {
                new AddPonto(e.getX()-28,e.getY()-38,url,nomeMapa);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            dispose();
        }
        public void mouseReleased (MouseEvent e){
        }
        public void mouseEntered (MouseEvent e){
        }
        public void mouseExited (MouseEvent e){
        }
        public void mouseClicked (MouseEvent e){
        }
    }

}