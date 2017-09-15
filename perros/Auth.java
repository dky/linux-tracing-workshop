package perros;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

class AuthHandler implements ApiHandler {
    public void handle(HttpExchange request, Map<String, String> queryParams,
                       Map bodyJson) throws IOException {
        String username = bodyJson.get("username").toString();
        String password = bodyJson.get("password").toString();
        
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = (getSalt(username) + password).getBytes();
            for (int i = 0; i < 1000; ++i) {
                // Do it a few times, for good measure.
                byte[] hash = sha.digest(passwordBytes);
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Oops, no SHA-256 support.");
        }

        // For now, we're OK with any password :-)
        request.sendResponseHeaders(200, 0);
        request.getResponseBody().close();
    }

    private String getSalt(String username) {
        char[] salt = new char[4096];
        Arrays.fill(salt, '*');
        return new String(salt);
    }
}

