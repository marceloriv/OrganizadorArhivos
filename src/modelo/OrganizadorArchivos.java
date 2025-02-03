package modelo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class OrganizadorArchivos {

    private String carpetaOrigen;
    private ArrayList<String> archivosMovidos;

    public OrganizadorArchivos(String carpetaOrigen) {
        this.carpetaOrigen = carpetaOrigen;
        this.archivosMovidos = new ArrayList<>();
    }

    public String getCarpetaOrigen() {
        return carpetaOrigen;
    }

    public void setCarpetaOrigen(String carpetaOrigen) {
        this.carpetaOrigen = carpetaOrigen;
    }

    public ArrayList<String> getArchivosMovidos() {
        return archivosMovidos;
    }

    public void OrganizarArchivos() {
        File carpeta = new File(carpetaOrigen);
        File[] archivos = carpeta.listFiles();

        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isFile()) {
                    String nombreCompleto = archivo.getName();
                    String extension = obtenerExtension(nombreCompleto);
                    // Ignorar archivos .ini
                    if (extension.equalsIgnoreCase("ini")) {
                        continue;
                    }

                    String nombreBase = obtenerNombreBase(nombreCompleto);

                    try {
                        crearCarpetaIndividual(nombreCompleto, extension);
                        moverArchivo(nombreCompleto, extension);
                    } catch (IOException e) {
                        System.out.println("Error al mover el archivo " + nombreCompleto);
                    }
                }
            }
        }
    }

    private String obtenerNombreBase(String nombreCompleto) {
        int index = nombreCompleto.lastIndexOf('.');
        if (index > 0) {
            return nombreCompleto.substring(0, index);
        } else {
            return nombreCompleto;
        }
    }

    private String obtenerExtension(String nombreCompleto) {
        int index = nombreCompleto.lastIndexOf('.');
        if (index > 0) {
            return nombreCompleto.substring(index + 1);
        } else {
            return "";
        }
    }

    private void crearCarpetaIndividual(String nombreCompleto, String extension) throws IOException {
        String nombreCarpeta = "Archivos " + obtenerExtension(nombreCompleto);
        Path rutaCarpeta = Paths.get(carpetaOrigen, nombreCarpeta);
        Files.createDirectories(rutaCarpeta);
    }

    private void moverArchivo(String nombreCompleto, String extension) throws IOException {
        String nombreCarpeta = "Archivos " + extension;
        Path rutaCarpeta = Paths.get(carpetaOrigen, nombreCarpeta);
        Path rutaDestino = Paths.get(rutaCarpeta.toString(), nombreCompleto);
        Files.move(Paths.get(carpetaOrigen, nombreCompleto), rutaDestino);
        String separador = "-----".repeat(15);
        archivosMovidos.add("Archivo: " + nombreCompleto + "\nMovido a: " + nombreCarpeta + "\n" + separador + "\n");

    }

}
