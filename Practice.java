
package mybanksystem;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public final class Practice {
    boolean negative;

    public Practice() {
        this(false);
    }

    public Practice(final boolean negative) {
        this.negative = negative;
    }

    public String convert(final BufferedImage image) {
        StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
        for (int y = 0; y < image.getHeight(); y++) {
            if (sb.length() != 0) sb.append("\n");
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                // Corrected grayscale formula:
                double gValue = pixelColor.getRed() * 0.2989
                              + pixelColor.getGreen() * 0.5870
                              + pixelColor.getBlue() * 0.1140;
                final char s = negative ? returnStrNeg(gValue) : returnStrPos(gValue);
                sb.append(s);
            }
        }
        return sb.toString();
    }

    private char returnStrPos(double g) {
        final char str;

        if (g >= 230.0) {
            str = ' ';
        } else if (g >= 200.0) {
            str = '.';
        } else if (g >= 180.0) {
            str = '*';
        } else if (g >= 160.0) {
            str = ':';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = '&';
        } else if (g >= 70.0) {
            str = '8';
        } else if (g >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str;
    }

    private char returnStrNeg(double g) {
        final char str;

        if (g >= 230.0) {
            str = '@';
        } else if (g >= 200.0) {
            str = '#';
        } else if (g >= 180.0) {
            str = '8';
        } else if (g >= 160.0) {
            str = '&';
        } else if (g >= 130.0) {
            str = 'o';
        } else if (g >= 100.0) {
            str = ':';
        } else if (g >= 70.0) {
            str = '*';
        } else if (g >= 50.0) {
            str = '.';
        } else {
            str = ' ';
        }
        return str;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "gif", "png"));
            while (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File f = fileChooser.getSelectedFile();
                    final BufferedImage image = ImageIO.read(f);
                    if (image == null) throw new IllegalArgumentException(f + " is not a valid image.");
                    final String ascii = new Practice().convert(image);
                    final JTextArea textArea = new JTextArea(ascii, image.getHeight(), image.getWidth());
                    textArea.setFont(new Font("Monospaced", Font.BOLD, 5));
                    textArea.setEditable(false);
                    final JDialog dialog = new JOptionPane(new JScrollPane(textArea), JOptionPane.PLAIN_MESSAGE).createDialog(Practice.class.getName());
                    dialog.setResizable(true);
                    dialog.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            System.exit(0);
        });
    }
}

    