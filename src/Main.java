//Usiamo librerie HttpClient che da java21 è dentro e Gson (da installare sulla cartella del progetto per renderla
// disponibile anche a chi la pulla).
public class Main
{
    public static void main(String[] args)
    {
        ApiClient client = new ApiClient();
        String response = client.fetchQuestions(5, "easy", "multiple");
        System.out.println(response);
    }
}