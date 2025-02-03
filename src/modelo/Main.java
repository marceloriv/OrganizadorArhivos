package modelo;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author river
 */
public class Main {

    public static void main(String[] args) {

        JFileChooser selectorCarpeta = new JFileChooser();
        selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JOptionPane.showMessageDialog(null, "Seleccione el directorio para ordenar");
        int resultado = selectorCarpeta.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String carpetaOrigen = selectorCarpeta.getSelectedFile().getAbsolutePath();
            File archivo = new File(carpetaOrigen);
            OrganizadorArchivos organizador = new OrganizadorArchivos(carpetaOrigen);
            int respuesta = JOptionPane.showConfirmDialog(null, "Deseas ordenar este directorio\n" + archivo.getAbsolutePath());
            if (respuesta == JOptionPane.YES_OPTION) {
                organizador.OrganizarArchivos();
                ArrayList<String> archivosMovidos = organizador.getArchivosMovidos();
                mostrarArchivosMovidos(archivosMovidos);
                JOptionPane.showMessageDialog(null, "Se ha ordenado el directorio");
            } else {
                JOptionPane.showMessageDialog(null, "Se ha cancelado la operación");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún directorio");
        }
    }

    private static void mostrarArchivosMovidos(ArrayList<String> archivosMovidos) {
        JTextArea textArea = new JTextArea(20, 40);
        for (String archivo : archivosMovidos) {
            textArea.append(archivo + "\n");
        }
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "Archivos Movidos", JOptionPane.INFORMATION_MESSAGE);
    }
}
