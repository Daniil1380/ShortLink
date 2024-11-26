import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

// Класс, представляющий короткую ссылку
class ShortLink {
    private final String originalUrl;
    private final String shortUrl;
    private int maxRedirects;
    private int currentRedirects;
    private final LocalDateTime creationTime;
    private final BaseShortener baseShortener;
    private final int maxDaysOfLiveTime;

    public ShortLink(String originalUrl, int maxRedirects, UUID userUuid, int maxDaysOfLiveTime) {
        this.originalUrl = originalUrl;
        this.maxRedirects = maxRedirects;
        this.currentRedirects = 0;
        this.creationTime = LocalDateTime.now();
        this.baseShortener = new BaseShortener();
        this.shortUrl = generateShortUrl(originalUrl, userUuid);
        this.maxDaysOfLiveTime = maxDaysOfLiveTime;
    }

    private String generateShortUrl(String originalUrl, UUID userUuid) {
        return "shrt.ru/" + baseShortener.shorten(originalUrl, userUuid);
    }

    public String getInfo() {
        return shortUrl + String.format(" | Потрачено %d переходов из %d", currentRedirects, maxRedirects);
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void redirect() throws IOException, URISyntaxException {
        if (isValid()) {
            currentRedirects++;
            Desktop.getDesktop().browse(new URI(originalUrl));
        } else {
            System.out.println("Ссылка недоступна");
            notifyUser();
        }
    }

    private void notifyUser() {
        System.out.println("Лимит переходов исчерпан или время жизни ссылки истекло");
    }

    public boolean isValid() {
        return currentRedirects < maxRedirects && ChronoUnit.DAYS.between(creationTime, LocalDateTime.now()) < maxDaysOfLiveTime;
    }


    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }
}
