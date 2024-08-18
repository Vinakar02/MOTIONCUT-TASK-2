import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;

public class URLShortener {
    private HashFunction hashFunction;
    private Storage storage;

    public URLShortener() {
        this.hashFunction = new HashFunction();
        this.storage = new Storage();
    }

     
    public String shortenURL(String longURL) {
        String shortURL = hashFunction.generateShortURL(longURL);
        if (!storage.exists(shortURL)) {
            storage.save(shortURL, longURL);
        }
        return shortURL;
    }

     
    public String expandURL(String shortURL) throws Exception {
        String longURL = storage.getLongURL(shortURL);
        if (longURL == null) {
            throw new Exception("Short URL not found");
        }
        return longURL;
    }

    
    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
             
            System.out.println("Choose an option: 1. Shorten URL 2. Expand URL 3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();   

            if (choice == 1) {
                 
                System.out.println("Enter the long URL:");
                String longURL = scanner.nextLine();
                String shortURL = urlShortener.shortenURL(longURL);
                System.out.println("Short URL: " + shortURL);
            } else if (choice == 2) {
                 
                System.out.println("Enter the short URL:");
                String shortURL = scanner.nextLine();
                try {
                    String longURL = urlShortener.expandURL(shortURL);
                    System.out.println("Long URL: " + longURL);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                 
                break;
            }
        }

        scanner.close();
    }
}

 
class HashFunction {
    public String generateShortURL(String longURL) {
         
        return Base64.getUrlEncoder().encodeToString(longURL.getBytes()).substring(0, 8);
    }
}


class Storage {
    private HashMap<String, String> urlMap;

    public Storage() {
        urlMap = new HashMap<>();
    }

     
    public void save(String shortURL, String longURL) {
        urlMap.put(shortURL, longURL);
    }

     
    public String getLongURL(String shortURL) {
        return urlMap.get(shortURL);
    }

     
    public boolean exists(String shortURL) {
        return urlMap.containsKey(shortURL);
    }
}
