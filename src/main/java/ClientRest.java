import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ClientRest {
    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        // Формирование Get запроса
        String urlGet = "https://www.boredapi.com/api/activity/";

        String response = restTemplate.getForObject(urlGet, String.class);
        System.out.println("GET : " + response);

        // Формирование POST запроса
        String urlPost = "https://reqres.in/api/users/";
        Map<String, String> map = new HashMap<>();
        map.put("name", "Artem");
        map.put("job", "Java");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map);

        String responsePost = restTemplate.postForObject(urlPost, request, String.class);
        System.out.println("POST : " + responsePost);
        System.out.println("-------");

        // Факты о кошках
        String urlGetFacts = "https://catfact.ninja/facts";
        String facts = restTemplate.getForObject(urlGetFacts, String.class);
        // Парсим полученный JSON с помощью JACKSON в ручную
        ObjectMapper mapper = new ObjectMapper();
        JsonNode obj = mapper.readTree(facts);
        System.out.println("Факты о кошках");
        System.out.println(obj.get("data").get(3).get("fact"));
        System.out.println(obj.get("data").get(3).get("length"));
        System.out.println("-------");

        // Парсим полученный JSON автоматически
        CatFactResponse facts2 = restTemplate.getForObject(urlGetFacts, CatFactResponse.class);
        System.out.println("Все факты за сегодня:");
        for (int i = 0; i < facts2.getData().size(); i++) {
            System.out.println(i + 1);
            System.out.println("Факт - " + facts2.getData().get(i).getFact());
            System.out.println("Длина строки - " + facts2.getData().get(i).getLength());
        }
    }
}
