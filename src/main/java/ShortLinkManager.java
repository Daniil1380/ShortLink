import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

// Класс, представляющий менеджер коротких ссылок
class ShortLinkManager {

    private final Map<UUID, Map<String, ShortLink>> userLinks;
    private final Config config;

    public ShortLinkManager(Config config) {
        this.userLinks = new HashMap<>();
        this.config = config;
    }

    public ShortLink createShortLink(String originalUrl, int maxRedirects, int maxDaysOfLiveTime, UUID userUuid) {
        validateLinks(userUuid);
        ShortLink shortLink = new ShortLink(originalUrl, Math.max(maxRedirects, config.getMaxAttempts()), userUuid,
                Math.min(maxDaysOfLiveTime, config.getMaxDaysOfLiveTime()));
        if (!userLinks.containsKey(userUuid)) {
            userLinks.put(userUuid, new HashMap<>());
        }
        userLinks.get(userUuid).put(shortLink.getShortUrl(), shortLink);
        return shortLink;
    }

    public void redirect(String shortUrl, UUID userUuid) {
        validateLinks(userUuid);

        userLinks.entrySet().stream()
                .flatMap(urls -> urls.getValue().entrySet().stream())
                .map(Map.Entry::getValue)
                .filter(url -> url.getShortUrl().equals(shortUrl))
                .findFirst()
                .ifPresentOrElse(ShortLink::redirect, () -> System.out.println("Ссылка не найдена"));
    }

    public void deleteLink(String shortUrl, UUID userUuid) {
        validateLinks(userUuid);
        if (userLinks.containsKey(userUuid) && userLinks.get(userUuid).containsKey(shortUrl)) {
            userLinks.get(userUuid).remove(shortUrl);
            System.out.println("Ссылка удалена");
        } else {
            System.out.println("Ссылка не найдена");
        }
    }

    public void changeLink(String shortUrl, UUID userUuid, int newMax) {
        if (userLinks.containsKey(userUuid) && userLinks.get(userUuid).containsKey(shortUrl)) {
            ShortLink shortLink = userLinks.get(userUuid).get(shortUrl);
            shortLink.setMaxRedirects(newMax);
            System.out.println("Изменено");
        } else {
            System.out.println("Ссылка не найдена");
        }
    }

    public void printLinks(UUID userUuid) {
        validateLinks(userUuid);
        if (userLinks.containsKey(userUuid)) {
            System.out.println("Ссылки пользователя:");
            for (ShortLink shortUrl : userLinks.get(userUuid).values()) {
                System.out.println(shortUrl.getInfo());

            }
        } else {
            System.out.println("Пользователь не найден");
        }
    }

    public void validateLinks(UUID userUuid) {
        if (userLinks.containsKey(userUuid)) {
            Map<String, ShortLink> links = userLinks.get(userUuid);

            int size = links.size();

            links = links.entrySet().stream()
                    .filter((entry) -> entry.getValue().isValid())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            userLinks.put(userUuid, links);


            System.out.println("Удалено устаревших ссылок: " + (size - links.size()));
        }
    }
}
