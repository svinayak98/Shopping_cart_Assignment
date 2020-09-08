package com.example.demo.client;

import com.example.demo.model.ShoppingCart;
import com.example.demo.model.Coupon;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

    @Component
    @RestController
    public class callproduct implements CommandLineRunner {
    private String url;
    @Value("${external.product.url}")
    private String externalProductUrl;
    @Value("${external.coupon.url}")
    private String externalCouponUrl;

    String[] s={"NTB","INK"};
    Long sum=0L;
    List<String> ss=new ArrayList<String>();

    @RequestMapping(value="/cart",method= RequestMethod.GET)
    public ShoppingCart callproduct(){
        ShoppingCart shoppingCart =new ShoppingCart();
        RestTemplate restTemplate=new RestTemplate();

    for(String i:s){
        url=externalProductUrl+i;
        Product productb=restTemplate.getForObject(url,Product.class);
        sum+=productb.getPrice();
        ss.add(i);
    }

    //Product producta=restTemplate.getForObject("http://assignmenttwop1-env.eba-bsxjescx.us-east-2.elasticbeanstalk.com/assign2/product/NTB",Product.class);
    //Product productb=restTemplate.getForObject(externalProductUrl,Product.class);
    //Long a=producta.getPrice();
    //Long b=productb.getPrice();
    Coupon coupon=restTemplate.getForObject(externalCouponUrl,Coupon.class);
    String Discount=coupon.getCouponCode();
    Long c=85*(sum)/100;
    shoppingCart.setProducts(ss);
    shoppingCart.setTotalPrice(sum);
    shoppingCart.setDiscountedPrice(c);
    shoppingCart.setCouponCode(Discount);

    return shoppingCart;
}


    @Override
    public void run(String... args) throws Exception {

    }
}

