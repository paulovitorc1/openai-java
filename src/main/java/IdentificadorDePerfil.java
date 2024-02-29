import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class IdentificadorDePerfil {

	public static void main(String[] args) {
		
		String promptSistema = "Identifique o perfil de compra de cada cliente."
				+ "A resposta deve ser:"
				+ "Cliente - desdcreva o perfil do cliente em três palavras";
		
		String clientes = carregarClientesDoArquivo();
		
		int quantidadeToken = contarToken(clientes);
		
		String modelo = "gpt-3.5-turbo-0613";
		
		if (quantidadeToken > 1000) {
			modelo = "gpt-3.5-turbo-16k";
		}

		System.out.println("QTD Token: " + quantidadeToken);
		System.out.println("Modelo: " + modelo);
		
		ChatCompletionRequest request = ChatCompletionRequest
				.builder()
				.model(modelo)
				.messages(
						Arrays.asList(
								new ChatMessage(ChatMessageRole.SYSTEM.value(), promptSistema),
								new ChatMessage(ChatMessageRole.SYSTEM.value(), promptSistema)))
				.build();
		
		String apiKey = System.getenv("OPENAI_API_KEY");
		OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));
		
		System.out.println(
				service
				.createChatCompletion(request)
				.getChoices().get(0).getMessage().getContent()
			);
				
	};

	private static int contarToken(String prompt) {
		EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
		Encoding enc = registry.getEncodingForModel(ModelType.GPT_3_5_TURBO);
		return enc.countTokens(prompt);
	}

	private static String carregarClientesDoArquivo() {
		try {
			Path path = Path.of(ClassLoader
					.getSystemResource("lista_de_compras_100_clientes.csv")
					.toURI());
			return Files.readAllLines(path).toString();
		} catch (Exception e) {
			throw new RuntimeException("Erro ao carregar arquivo!", e);
		}
	}

}
