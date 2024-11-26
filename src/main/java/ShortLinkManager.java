import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Класс, представляющий менеджер коротких ссылок
class ShortLinkManager {

    private final Map<UUID, Map<String, ShortLink>> userLinks;

    public ShortLinkManager() {
        this.userLinks = new HashMap<>();
    }

    public ShortLink createShortLink(String originalUrl, int maxRedirects, UUID userUuid) {
        ShortLink shortLink = new ShortLink(originalUrl, maxRedirects, userUuid);
        if (!userLinks.containsKey(userUuid)) {
            userLinks.put(userUuid, new HashMap<>());
        }
        userLinks.get(userUuid).put(shortLink.getShortUrl(), shortLink);
        return shortLink;
    }

    public void redirect(String shortUrl, UUID userUuid) throws IOException, URISyntaxException {
        if (userLinks.containsKey(userUuid) && userLinks.get(userUuid).containsKey(shortUrl)) {
            userLinks.get(userUuid).get(shortUrl).redirect();
        } else {
            System.out.println("Ссылка не найдена");
        }
    }

    public void deleteLink(String shortUrl, UUID userUuid) {
        if (userLinks.containsKey(userUuid) && userLinks.get(userUuid).containsKey(shortUrl)) {
            userLinks.get(userUuid).remove(shortUrl);
            System.out.println("Ссылка удалена");
        } else {
            System.out.println("Ссылка не найдена");
        }
    }

    public void printLinks(UUID userUuid) {
        if (userLinks.containsKey(userUuid)) {
            System.out.println("Ссылки пользователя:");
            for (ShortLink shortUrl : userLinks.get(userUuid).values()) {
                if (shortUrl.isValid()) {
                    System.out.println(shortUrl.getShortUrl());
                }
            }
        } else {
            System.out.println("Пользователь не найден");
        }
    }
}
