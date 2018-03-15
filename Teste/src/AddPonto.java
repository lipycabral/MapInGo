import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class AddPonto extends JDialog{
    private JLabel lblBairro = new JLabel("Bairro");
    private JTextField tfBairro = new JTextField();
    private JLabel lblCota = new JLabel("Cota de Evasão");
    private JTextField tfCota = new JTextField();
    private JLabel X;
    private JLabel Y;
    private JButton btnEnviar;
    AddPonto(int x, int y, String url, String nomeMapa) throws IOException {
        //!----------- interface -------------!//
        setTitle(String.format("Adicionando pontos no mapa de %s", nomeMapa));
        setSize(500, 80);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2,4));
        X = new JLabel("X: "+ x);
        Y = new JLabel("Y: "+ y);
        btnEnviar = new JButton("Enviar");
        add(lblBairro);
        add(tfBairro);
        add(X);
        add(btnEnviar);
        add(lblCota);
        add(tfCota);
        add(Y);
        setLocationRelativeTo(null);
        setResizable(false);
        //!----------- evento -------------!//
        btnEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OutputStreamWriter osw = null;
                try {
                    //!----------- abrindo arquivo -------------!//
                    osw = new OutputStreamWriter(new FileOutputStream(url,true));
                    //!----------- escrevendo no arquivo -------------!//
                    osw.write(tfBairro.getText()+","+tfCota.getText()+","+x +","+ y +"\n");
                    //!----------- fechando arquivo -------------!//
                    osw.close();
                    dispose();
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(AddPonto.this, "Erro no formato do cota de evasão por favor corrija");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        setModal(true);
        setVisible(true);
    }
}
