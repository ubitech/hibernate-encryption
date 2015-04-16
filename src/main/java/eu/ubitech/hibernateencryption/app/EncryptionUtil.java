package eu.ubitech.hibernateencryption.app;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate4.encryptor.HibernatePBEEncryptorRegistry;

/**
 *
 * @author Christos Paraskeva
 */
public final class EncryptionUtil {

    private static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDES";
    private static final String MASTER_KEY = "mypass";

    public static String encryptText(String input, String password, String algorithm_name) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        encryptor.setAlgorithm(algorithm_name);
        return encryptor.encrypt(input);
    }

    public static String decryptText(String input, String password, String algorithm_name) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        encryptor.setAlgorithm(algorithm_name);
        return encryptor.decrypt(input);
    }

    public static void main(String[] args) {

        //Encrypt text
        String encryptedText = encryptText("sample text for encryption", "mypass", DEFAULT_ALGORITHM);
        System.out.println("Encryped text is : " + encryptedText);

        //Decrypt text
        String decryptedText = decryptText(encryptedText, "mypass", DEFAULT_ALGORITHM);
        System.out.println("Decryped text is : " + decryptedText);
    }

    public static void registerHibernatePBEEncryptor() {
        StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
        strongEncryptor.setAlgorithm(DEFAULT_ALGORITHM);
        strongEncryptor.setPassword(MASTER_KEY);
        HibernatePBEEncryptorRegistry registry = HibernatePBEEncryptorRegistry.getInstance();
        registry.registerPBEStringEncryptor("configurationHibernateEncryptor", strongEncryptor);
    }

}
