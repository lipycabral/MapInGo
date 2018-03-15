import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//------------! cria a nova imagem !---------//
public class ManipularImagem {
    public static BufferedImage setImagemDimensao(String caminhoImg, Integer imgLargura, Integer imgAltura) {
        Double novaImgLargura = null;
        Double novaImgAltura = null;
        Graphics2D g2d = null;
        BufferedImage imagem = null, novaImagem = null;
        try {
            imagem = ImageIO.read(new File(caminhoImg));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(ManipularImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        novaImgLargura = (double) imagem.getWidth();
        novaImgAltura = (double) imagem.getHeight();
        novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
        g2d = novaImagem.createGraphics();
        g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);
        return novaImagem;
    }
}