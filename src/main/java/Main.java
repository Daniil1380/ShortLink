import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ShortLinkManager manager = new ShortLinkManager();
        Scanner scanner = new Scanner(System.in);
        UUID userUuid = null;

        while (true) {
            System.out.println("1. Создать короткую ссылку");
            System.out.println("2. Перейти по короткой ссылке");
            System.out.println("3. Удалить короткую ссылку");
            System.out.println("4. Вывести все короткие ссылки");
            System.out.println("5. Выход");
            System.out.print("Выберите действие: ");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 1:
                    System.out.print("Введите исходную ссылку: ");
                    String originalUrl = scanner.nextLine();
                    System.out.print("Введите максимальное количество переходов: ");
                    int maxRedirects = scanner.nextInt();
                    scanner.nextLine();
                    if (userUuid == null) {
                        userUuid = UUID.randomUUID();
                    }
                    ShortLink shortLink = manager.createShortLink(originalUrl, maxRedirects, userUuid);
                    System.out.println("Короткая ссылка: " + shortLink.getShortUrl());
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
                    System.exit(0);
                    break;
                default:
                    System.out.println("Недопустимое действие");
            }
        }
    }
}