package io.ari.customers.resources;

import io.ari.customers.domain.Customer;
import io.ari.customers.domain.repositories.CustomersRepository;
import io.ari.customers.resources.assemblers.CustomersAssembler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerResourceTest {

	@Test
	public void shouldReturnMe() {
		final Customer clientData = mock(Customer.class);
		Map<String,Object> clientDto = new HashMap<>();
		
		when(customersRepository.findById(CUSTOMER_ID)).thenReturn(clientData);
		when(customersAssembler.convertEntityToDto(clientData)).thenReturn(clientDto);

		ResponseEntity response = customerResource.me(CUSTOMER_ID);


		Map<String, Object> client = (Map<String, Object>)response.getBody();

		assertEquals("The response code should be OK", 200, response.getStatusCodeValue());
		assertNotSame("The client data is not the expected",clientData,client);
	}
	

	@Test
	public void shouldReturnNotFoundIfUserDontExistsWhenMe(){
		when(customersRepository.findById(CUSTOMER_ID)).thenReturn(null);

		ResponseEntity response =customerResource.me(CUSTOMER_ID);
		assertEquals("The response code should be NOT FOUND", 404, response.getStatusCodeValue());
	}

	private static final String CUSTOMER_ID = "customerId";

	@InjectMocks
	private CustomerResource customerResource;

	@Mock
	private CustomersAssembler customersAssembler;

	@Mock
	private CustomersRepository customersRepository;

}
