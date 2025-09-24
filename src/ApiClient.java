import com.google.gson.Gson;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ApiClient
{
    private final HttpClient client = HttpClient.newHttpClient(); //final vuol dire che l'oggetto non è modificale
    //è come un const

    public String fetchQuestions(int amount, String difficulty, String type)
    {
        //https://opentdb.com/api.php?amount=10&difficulty=hard&type=multiple
        String url = "https://opentdb.com/api.php?amount=" + amount + "&difficulty=" + difficulty + "&type=" + type;

        //E' un design pattern builder: sono un modo per creare delle classe per risolvere determinati problemi.
        //Possiamo mettere i metodi non in modo ordinato, perchè il design builder li fa lui.
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json") //Che tipo di contenuto ci deve essere.
                .uri(java.net.URI.create(url)) //L'url.
                .GET() //Tipo di richiesta da mandare.
                .build(); //Costruiscimi l'oggetto.

        HttpResponse<String> response; //Creazione oggetto response.
        //La risposta può dare errori, che dobbiamo controllare:
        try
        {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e)
        {
            return "Errore nella richiesta API.";
        }

        Gson gson = new Gson();
        ApiResponse apiResponse = gson.fromJson(response.body(), ApiResponse.class);

        //Ora possiamo accedere alle domande come oggetti Java
        for (ApiQuestion q : apiResponse.results)
        {
            System.out.println(q.question);
            System.out.println("Risposta corretta: " + q.correct_answer);
        }

        return response.body();
    }
}
