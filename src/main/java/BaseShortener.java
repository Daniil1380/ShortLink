import java.util.UUID;

public class BaseShortener {

    private static final String BASE_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 7;

    public String shorten(String longUrl, UUID userUUID) {
        UUID salt = UUID.randomUUID();
        String combinedString = longUrl + userUUID.toString() + salt;
        int hash = Math.abs(combinedString.hashCode());
        StringBuilder shortKey = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            shortKey.append(BASE_CHARS.charAt(hash % BASE_CHARS.length()));
            hash /= BASE_CHARS.length();
        }
        return shortKey.toString();
    }
}
