package application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import entities.JsonParser;

public class Program {

	public static void main(String[] args) throws Exception{
		
		Scanner sc = new Scanner(System.in);
		String url = null;
		int option;
		
		//String imdbKey = System.getenv("IMDB_API_KEY");
		
		// Create a menu
		System.out.println("Choose your option:");
		System.out.println("1- Top Movies");
		System.out.println("2- Most Popular Movies");
		System.out.println("3- Top Tv shows");
		System.out.println("4- Most Popular Tv shows");
		option = sc.nextInt();
		// Using switch to chose the url
		switch(option) {
		case 1:
			url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
			break;
		case 2:
			url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";
			break;
		case 3:
			url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopTVs.json";
			break;
		case 4: 
			url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularTVs.json";
			break;
		}
		
		URI address = URI.create(url);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder(address).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		String body = response.body();
		
		
		// extrair só os dados que interessam (titulo,poster, classificação)
		JsonParser parser= new JsonParser();
		List<Map<String, String>> movieList = parser.parse(body); 
		
		
		// exibir e manipular os dados
		for (Map<String, String> movie : movieList) {
			System.out.println("Title: " + "\u001b[1m" + movie.get("title") + "\u001b[0m" );
			System.out.println("Poster: " + "\u001b[1m" + movie.get("image") + "\u001b[0m");
			System.out.println("\u001b[35;1m \u001b[102mRating: " + movie.get("imDbRating") + "\u001b[m");
			for (int i=0; i<Math.round(Float.parseFloat(movie.get("imDbRating"))); i++) {
				System.out.print("\u2B50");
			}
			System.out.println();
		}
		
	}

}
