import java.util.Arrays;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

public class TestaIntegracao {

	public static void main(String[] args) {
		
		String user = "Gere 5 produtos";
		String system = "Você é um gerador de produtos ficticios para um ecommerce e deve gerar apenas o nome dos produtos solicitados pelo usuário..";
		
		
		String apiKey = System.getenv("OPENAI_API_KEY");
		OpenAiService service = new OpenAiService(apiKey);
		ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
				.builder()
		        .model("gpt-3.5-turbo-0613")
		        .messages(Arrays.asList(
		        		new ChatMessage(ChatMessageRole.USER.value(), user),
		        		new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
		        		))
		        .build();
		service
		.createChatCompletion(chatCompletionRequest)
		.getChoices()
		.forEach(c -> System.out.println(c.getMessage().getContent()));
		
	}

}
