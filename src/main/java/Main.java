import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        ConfigLoader configLoader = new ConfigLoader();
        ShortLinkManager manager = new ShortLinkManager(configLoader.loadConfig());
        Scanner scanner = new Scanner(System.in);
        UUID userUuid = null;

        while (true) {
            System.out.println("0. Войти в аккаунт по UUID");
            System.out.println("1. Создать короткую ссылку");
            System.out.println("2. Перейти по короткой ссылке");
            System.out.println("3. Удалить короткую ссылку");
            System.out.println("4. Вывести все короткие ссылки");
            System.out.println("5. Изменить максимальное количество переходов по ссылке");
            System.out.println("6. Выход");
            System.out.print("Выберите действие: ");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.print("Введите UUID пользователя: ");
                    String uuid = scanner.nextLine();
                    userUuid = UUID.fromString(uuid);
                    break;
                case 1:
                    System.out.print("Введите исходную ссылку: ");
                    String originalUrl = scanner.nextLine();
                    System.out.print("Введите максимальное количество переходов: ");
                    int maxRedirects = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Введите максимальное время жизни в днях: ");
                    int maxDaysOfLiveTime = scanner.nextInt();
                    scanner.nextLine();
                    if (userUuid == null) {
                        userUuid = UUID.randomUUID();
                        System.out.println("Ваш UUID пользователя: " + userUuid);
                    }
                    ShortLink shortLink = manager.createShortLink(originalUrl, maxRedirects, maxDaysOfLiveTime, userUuid);
                    System.out.println("Короткая ссылка: " + shortLink.getInfo());
                    break;
                case 2:
                    System.out.print("Введите короткую ссылку: ");
                    String shortUrl = scanner.nextLine();
                    if (userUuid != null) {
                        manager.redirect(shortUrl, userUuid);
                    } else {
                        System.out.println("Пользователь не найден");
                    }
                    break;
                case 3:
                    System.out.print("Введите короткую ссылку: ");
                    shortUrl = scanner.nextLine();
                    if (userUuid != null) {
                        manager.deleteLink(shortUrl, userUuid);
                    } else {
                        System.out.println("Пользователь не найден");
                    }
                    break;
                case 4:
                    if (userUuid != null) {
                        manager.printLinks(userUuid);
                    } else {
                        System.out.println("Пользователь не найден");
                    }
                    break;
                case 5:
                    System.out.print("Введите короткую ссылку: ");
                    shortUrl = scanner.nextLine();
                    System.out.print("Введите максимальное количество переходов: ");
                    maxRedirects = scanner.nextInt();
                    scanner.nextLine();
                    if (userUuid != null) {
                        manager.changeLink(shortUrl, userUuid, maxRedirects);
                    } else {
                        System.out.println("Пользователь не найден");
                    }
                    break;
                case 6:
                    userUuid = null;
                    System.out.println("Вы вышли из аккаунта пользователя");
                    break;
                default:
                    System.out.println("Недопустимое действие");
            }
        }
    }
}