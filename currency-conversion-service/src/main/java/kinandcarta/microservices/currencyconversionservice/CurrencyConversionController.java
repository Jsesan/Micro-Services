package kinandcarta.microservices.currencyconversionservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {
    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionValue calculate(@PathVariable String from,
                                             @PathVariable String to, @PathVariable BigDecimal quantity){

        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        ResponseEntity<CurrencyConversionValue> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionValue.class, uriVariables);

        CurrencyConversionValue currencyConversionValue = responseEntity.getBody();

        return new CurrencyConversionValue(currencyConversionValue.getId(), from, to,
                quantity,
                currencyConversionValue.getConversionMultiple(),
                currencyConversionValue.getConversionMultiple().multiply(quantity),
                currencyConversionValue.getEnvironment());
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionValue calculateFeign(@PathVariable String from,
                                             @PathVariable String to, @PathVariable BigDecimal quantity){

        HashMap<String,String> uriVariables = new HashMap<>();
        uriVariables.put("from",from);
        uriVariables.put("to",to);

        CurrencyConversionValue currencyConversionValue = proxy.retrieveExchangeValue(from , to);

        return new CurrencyConversionValue(currencyConversionValue.getId(), from, to,
                quantity,
                currencyConversionValue.getConversionMultiple(),
                currencyConversionValue.getConversionMultiple().multiply(quantity),
                currencyConversionValue.getEnvironment());
    }
}
