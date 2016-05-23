package org.sandragon;

import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class MicroserviceController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public Map<String, Boolean> getResource() {
    	HashMap<String, Boolean> map = new HashMap<>();
    	map.put("value", true);
        return map;
    }

    @RequestMapping("/products")
    @Counted(name = "get_product_calls", help = "Count of calls to /products")
    public Map<String, Boolean> getProducts() {
    	HashMap<String, Boolean> map = new HashMap<>();
    	map.put("confirmation", true);
        return map;
    }
}