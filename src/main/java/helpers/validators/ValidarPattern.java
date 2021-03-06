package helpers.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import helpers.validators.exceptions.InvalidPatternException;

public abstract class ValidarPattern {
    
    private static final String REGEX_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String REGEX_TELEFONO = "^(\\+52|0034|34)?[6789]\\d{8}$";
    
    public static void validarEmail(String email) throws InvalidPatternException {
        Pattern pattern = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidPatternException("la direccion de correo electronica no es valida");
        }
    }
    
    public static void validarTelefono(String telefono) throws InvalidPatternException {
        Pattern pattern = Pattern.compile(REGEX_TELEFONO);
        Matcher matcher = pattern.matcher(telefono);
        if (!matcher.matches()) {
            throw new InvalidPatternException("El numero de telefono no es valido");
        }
    }
}
