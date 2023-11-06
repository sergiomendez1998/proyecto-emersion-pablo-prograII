package com.example.proyectofinalprograiimvc;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {

    public static String encriptarContrasenia(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static String generarNumeroDeExpediente() {
        Random random = new Random();
        int firstGroup = random.nextInt(10000); // Generate a random number between 0 and 9999
        int secondGroup = random.nextInt(100);
        int thirdGroup = random.nextInt(100);
        int fourthGroup = random.nextInt(100);
        int fifthGroup = random.nextInt(10000000); // Generate a random number between 0 and 9999999
        return String.format("%04d-%02d-%02d-%02d-%07d", firstGroup, secondGroup, thirdGroup, fourthGroup, fifthGroup);
    }

    public static String generarNumeroSolicitudRandom(Date dateOfReception, String tipoUsuario) {
        Random random = new Random();
        String letters = tipoUsuario.equalsIgnoreCase("externo") ? "EX" : "IN";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = dateFormat.format(dateOfReception);
        int numbers = random.nextInt(100000);
        return String.format("%s-%s-%05d", letters, date, numbers);
    }

}
