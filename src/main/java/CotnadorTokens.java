import java.math.BigDecimal;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.ModelType;

public class CotnadorTokens {

	public static void main(String[] args) {

		EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
		Encoding enc = registry.getEncodingForModel(ModelType.GPT_3_5_TURBO);
		int encoded = enc.countTokens("Identifique o perfil de compra.");
		        
		System.out.println("Quantidade de tokens: " + encoded);
		
		BigDecimal custo = new BigDecimal(encoded)
				.divide(new BigDecimal("1000"))
				.multiply(new BigDecimal("0.0010"));
		
		System.out.println("Custo da requisição: " + custo);
		
	}

}
