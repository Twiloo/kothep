package fr.twiloo.iut.kothep;


import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

final public class PasswordEncoder {
    private static final Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 128, 8, 65535, 4);

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean matchPassword(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}

