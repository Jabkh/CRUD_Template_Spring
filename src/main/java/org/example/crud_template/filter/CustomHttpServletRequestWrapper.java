package org.example.crud_template.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    // Champ pour stocker le corps de la requête
    private final String body;

    // Constructeur qui initialise le corps de la requête
    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = getBody(request);
    }

    // Méthode privée pour lire le corps de la requête
    private String getBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        // Utilisation d'un InputStream pour lire le contenu de la requête
        try (InputStream inputStream = request.getInputStream();
             Scanner scanner = new Scanner(inputStream, "UTF-8")) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
        }
        return stringBuilder.toString();
    }

    // Redéfinition de la méthode getInputStream pour retourner un flux basé sur le corps stocké
    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                // Indique si toutes les données ont été lues
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                // Indique que le stream est prêt à être lu
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // Non implémenté, pourrait être utilisé pour la lecture asynchrone
            }
        };
    }
}