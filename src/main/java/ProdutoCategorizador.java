import java.time.Duration;
import java.util.Arrays;
import java.util.Scanner;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class ProdutoCategorizador {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Digite as categorias validas: ");
		String categorias = sc.nextLine();
		
		while (true) {
			
		System.out.println("Digite o nome do produto: ");
		String user = sc.nextLine();
		
		String system = "Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado"
				+ "Escolha uma categoria dentre a lista abaixo:"
				+ "%s"
				+ "caso a resposta seja diferente de um produto, você deve informar ao usuário que não pode ajuda-lo e que seu papel é apenas fornecer categorias de produtos"
				.formatted(categorias);
		
		disparaRequisicao(user, system);
		
		}
	}

	public static void disparaRequisicao(String user, String system) {

		String apiKey = System.getenv("OPENAI_API_KEY");
		OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));
		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
				.builder()
				.model("gpt-3.5-turbo-0613")
				.messages(
						Arrays.asList(
								new ChatMessage(ChatMessageRole.USER.value(), user),
								new ChatMessage(ChatMessageRole.SYSTEM.value(), system)))
				.build();
		
		service.createChatCompletion(chatCompletionRequest).getChoices().forEach(c ->
			System.out.println(c.getMessage().getContent())
		);

	}

}
