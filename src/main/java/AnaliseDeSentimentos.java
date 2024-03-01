import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Arrays;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class AnaliseDeSentimentos {

	public static void main(String[] args) {
		
		String promptSistema = "Você é um analisador de sentimentos de avaliações de produtos."
				+ "Escreva um paragrafo com até 50 palavras resumindo as avaliações e depois atribua qual o sentimento geraç para o produto."
				+ "Identifique também 3 pontos fortes e 3 pontos fracos identificados a partir das avaliações"
				+ ""
				+ "#### Formato de saída:"
				+ "Nome do produto: "
				+ "Resumos das avaliações: "
				+ "Sentimento geral: "
				+ "Pontos fortes: "
				+ "Pontos fracos: ";
		
		String produto = "tapete-de-yoga";
		
		String promptUsuario = carregarArquivo(produto);
		
		ChatCompletionRequest request = ChatCompletionRequest
				.builder()
				.model("gpt-3.5-turbo-0613")
				.messages(
						Arrays.asList(
								new ChatMessage(ChatMessageRole.SYSTEM.value(), promptSistema),
								new ChatMessage(ChatMessageRole.USER.value(), promptUsuario)))
				.build();
		
		String apiKey = System.getenv("OPENAI_API_KEY");
		OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(60));
		
		String resposta = service
				.createChatCompletion(request)
				.getChoices().get(0).getMessage().getContent();
				
		salvarAnalise(produto, resposta);
	};

	private static String carregarArquivo(String arquivo) {
		try {
			Path path = Path.of("src/main/resources/avaliacoes/avaliacoes-" + arquivo + ".txt");
			return Files.readAllLines(path).toString();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao carregar arquivo!", e);
		}
	}

	private static void salvarAnalise(String arquivo, String analise) {
		try {
			Path path = Path.of("src/main/resources/analises/analise-sentimentos-" + arquivo + ".txt");
			Files.writeString(path, analise, StandardOpenOption.CREATE_NEW);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao salvar arquivo!", e);
		}
	}




}