import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Class {
    public static AtomicInteger counter_3 = new AtomicInteger();
    public static AtomicInteger counter_4 = new AtomicInteger();
    public static AtomicInteger counter_5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread palindrome = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String text : texts) {
                    if (filter_palindrome(text) && !filter_sameLetters(text)) {
                        incrementCounter(text.length());
                    }
                }
            }
        });
        Thread ascending = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String text : texts) {
                    if (filter_ascending(text) && !filter_sameLetters(text)) {
                        incrementCounter(text.length());
                    }
                }
            }
        });
        Thread sameLetters = new Thread(new Runnable() {
            @Override
            public void run() {
                for (String text : texts) {
                    if (filter_sameLetters(text)) {
                        incrementCounter(text.length());
                    }
                }
            }
        });
        ascending.start();
        ascending.join();

        sameLetters.start();
        sameLetters.join();

        palindrome.start();
        palindrome.join();

        System.out.println("Красивых слов с длинной 3 " + counter_3);
        System.out.println("Красивых слов с длинной 4 " + counter_4);
        System.out.println("Красивых слов с длинной 5 " + counter_5);
    }

    public static boolean filter_palindrome(String text) {
        return text.equals(new StringBuilder(text).reverse().toString());
    }

    public static boolean filter_ascending(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean filter_sameLetters(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            counter_3.getAndIncrement();
        } else if (textLength == 4) {
            counter_4.getAndIncrement();
        } else if (textLength == 5) {
            counter_5.getAndIncrement();
        }
    }
}
