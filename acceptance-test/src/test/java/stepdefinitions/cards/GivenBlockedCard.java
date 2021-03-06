package stepdefinitions.cards;

import cucumber.api.java.en.Given;
import io.ari.RestClient;
import io.ari.RestJsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import stepdefinitions.customers.CustomersRegistry;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

@ContextConfiguration("classpath:cucumber.xml")
public class GivenBlockedCard {

	@Given("^the customer \"(.*?)\" with the tva card blocked$")
	public void theUserXHasBlockedItsTvaCard(String customerIdCard) {
		String customerId = getCustomerId(customerIdCard);
		blockCard(customerId);
	}

	private void blockCard(String customerId) {
		String cardId = cardsRegistry.get(customerId, "tva");
		Response response = cardsService.blockCard(customerId, cardId);
		assertEquals(201, response.getStatus());
	}

	private String getCustomerId(String customerIdCard) {
		return customersRegistry.getCustomerId(customerIdCard);
	}

	@Autowired
	private CustomersRegistry customersRegistry;

	@Autowired
	private CardsRegistry cardsRegistry;

	@Autowired
	private RestJsonReader restJsonReader;

	@Autowired
	private RestClient dataExtractorClient;

	@Autowired
	private CardsService cardsService;
}
