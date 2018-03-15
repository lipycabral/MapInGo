import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AdicionarArquivos extends JFrame{
    AdicionarArquivos(){
        setTitle("Adicionar arquivos ao devido local");
        setSize(600,180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,2));
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }
}
