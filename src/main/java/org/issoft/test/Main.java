package org.issoft.test;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static List<String[]> getFileLines(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        reader.readLine();
        String str;
        List<String[]> fileLines = new ArrayList<>();
        while ((str = reader.readLine()) != null) {
            fileLines.add(str.split(","));
        }
        return fileLines;
    }

    public static void main(String[] args) throws IOException {
        Map<String, LocalDateTime> orders = new HashMap<>();
        Map<String, Product> products = new HashMap<>();
        Map<String, Order> orderItems = new HashMap<>();
        Map<String, Price> prices = new HashMap<>();

        getFileLines("files/orders.csv").forEach(line -> orders.put(line[0], LocalDateTime.parse(line[1])));
        getFileLines("files/products.csv").forEach(line ->  products.put(line[0], new Product(line[1], Integer.parseInt(line[2]))));
        getFileLines("files/order_items.csv").forEach(line -> orderItems.put(line[0], new Order(line[1], Integer.parseInt(line[2]))));

        orders.entrySet()
                .stream()
                .filter(ord -> ord.getValue().getDayOfMonth() == 21)
                .map(Map.Entry::getKey).collect(Collectors.toList()).stream().map(orderItems::get)
                .forEachOrdered(order -> {
                    if (prices.containsKey(order.getProductId())) {
                        prices.get(order.getProductId()).addPrice(order.getCount() * products.get(order.getProductId()).getPrice());
                    } else {
                        prices.put(order.getProductId(), new Price(order.getCount() * products.get(order.getProductId()).getPrice()));
                    }
                });

        prices.forEach((k, v) -> System.out.println(v.getPrice()));

        Optional<Map.Entry<String, Price>> optionalKey = prices.entrySet().stream().findFirst();
        String[] key = new String[1];
        optionalKey.ifPresent(priceEntry -> key[0] = priceEntry.getKey());
        prices.forEach((k, v) -> {
            if (v.getPrice() > prices.get(key[0]).getPrice()) {
                key[0] = k;
            }
        });
    }
}