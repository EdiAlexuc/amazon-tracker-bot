package com.example.amazontracker.restservice.service;

import com.example.amazontracker.restservice.model.Product;
import com.example.amazontracker.restservice.repository.ProductRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

@Service
public class ProductService {

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public String trackProduct(String url, String from){
        Document doc;
        try{
            Product product = productRepository.findItemByUrl(url);
            if(product != null){
                product.usersTracking.add(from);
                productRepository.save(product);
                return ("Tracer initialize for user " + from + " for Product " + product.name);
            }

            doc = Jsoup.connect(url).userAgent("Mozzila/5.0 (Windows; U; WIndowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();

            Elements priceSpan = doc.select(".a-price-whole");
            Elements productTitleElem = doc.select("#productTitle");

            if(productTitleElem.size() == 0){
                return "Invalid Product URL, kindly send only the Amazon product URL.";
            }

            String productTitle = productTitleElem.first().text();
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            int finalPrice = 0;

            if(!priceSpan.isEmpty()){
                try{
                    finalPrice = format.parse(priceSpan.first().text()).intValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            product = new Product(productTitle, url, finalPrice, from);
            productRepository.save(product);

            return ("Tracker initialized for user " + from + " for Product " + productTitle);
        } catch (IOException e) {
            e.printStackTrace();
            return "Invalid Porduct URL, kindly send an Amazon product URL.";
        }
    }

    public void trackAndSendReply(String from, String to, String text){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String message = trackProduct(text, from);

        Message.creator(
                new com.twilio.type.PhoneNumber(from),
                new com.twilio.type.PhoneNumber(to),
                message
        ).create();
    }
}
