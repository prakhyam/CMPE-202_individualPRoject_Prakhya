package com.prakhya.individualproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

// For JSON parsing
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// For XML parsing
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class CreditCardProcessor {
    private List<String> invalidCards = new ArrayList<>();

    public CreditCard processCardFromString(String cardDetails) {
        String[] parts = cardDetails.split(",");
        if (parts.length != 3) {
            return null; // Or handle error appropriately
        }
        String cardNumber = parts[0].trim();
        String expirationDate = parts[1].trim();
        String cardHolderName = parts[2].trim();

        return CreditCardFactory.createCreditCard(cardNumber, expirationDate, cardHolderName);
    }


    //csv
    public List<CreditCard> processCreditCardsFromFile(String filename) throws Exception {
        List<CreditCard> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 3) {
                    String cardNumber = fields[0].trim();
                    String expirationDate = fields[1].trim();
                    String cardHolderName = fields[2].trim();

                    try {
                        CreditCard card = CreditCardFactory.createCreditCard(cardNumber, expirationDate, cardHolderName);
                        if (card.isValid()) {
                            cards.add(card);
                        }
                    } catch (IllegalArgumentException e) {
                        invalidCards.add(cardNumber);
                        System.out.println("Invalid card data: " + line);
                    }
                }
            }
        }
        return cards;
    }
    //json
    public List<CreditCard> processCreditCardsFromJson(String filename) throws Exception {
        List<CreditCard> cards = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(filename)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray cardList = (JSONArray) jsonObject.get("cards");

            for (Object cardObj : cardList) {
                JSONObject cardObject = (JSONObject) cardObj;
                String cardNumber = (String) cardObject.get("cardNumber");
                if (cardNumber == null) {
                    System.out.println("Skipping card with null number");
                    continue; // Skip this card and continue with the next one
                }
                String expirationDate = (String) cardObject.get("expirationDate");
                String cardHolderName = (String) cardObject.get("cardHolderName");

                try {
                    CreditCard card = CreditCardFactory.createCreditCard(cardNumber, expirationDate, cardHolderName);
                    if (card.isValid()) {
                        cards.add(card);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid card: " + cardNumber);
                }
            }
        }
        return cards;
    }


    //xml
    public List<CreditCard> processCreditCardsFromXml(String filename) throws Exception {
        List<CreditCard> cards = new ArrayList<>();
        SAXReader reader = new SAXReader();

        try {
            Document document = reader.read(new File(filename));
            Element root = document.getRootElement();


            for (Iterator<?> it = root.elementIterator("CARD"); it.hasNext();) {
                System.out.println("inside for");

                Element cardElement = (Element) it.next();

                String cardNumber = cardElement.elementText("CARD_NUMBER");
                String expirationDate = cardElement.elementText("EXPIRATION_DATE");
                String cardHolderName = cardElement.elementText("CARD_HOLDER_NAME");

                try {
                    //System.out.println("inside try");
                    CreditCard card = CreditCardFactory.createCreditCard(cardNumber, expirationDate, cardHolderName);
                    if (card.isValid()) {
                        //System.out.println("inside isValid");
                        cards.add(card);
                        System.out.println("Added valid card: " + cardNumber); // Debug print
                    }else {
                        System.out.println("Invalid card skipped: " + card.getCardNumber()); // Debug print
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping invalid card: " + cardNumber); // Debug print
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return cards;
    }


    //write  csv
    public void writeCreditCardsToFile(List<CreditCard> cards, String filename) throws Exception {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (CreditCard card : cards) {
                writer.println(card.getCardType() + "," + card.getCardNumber() + "," + card.getExpirationDate() + "," + card.getCardHolderName());
            }
            for (String invalidCardNumber : invalidCards) {
                writer.println("Invalid," + invalidCardNumber);
            }
        }
    }
    //write json
    @SuppressWarnings("unchecked")
    public void writeCreditCardsToJsonFile(List<CreditCard> cards, String filename) throws Exception {
        JSONArray cardList = new JSONArray();
        for (CreditCard card : cards) {
            JSONObject cardObject = new JSONObject();
            cardObject.put("cardType", card.getCardType());
            cardObject.put("cardNumber", card.getCardNumber());
            cardObject.put("expirationDate", card.getExpirationDate());
            cardObject.put("cardHolderName", card.getCardHolderName());
            cardList.add(cardObject);
        }

        JSONObject rootObject = new JSONObject();
        rootObject.put("cards", cardList);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(rootObject.toJSONString());
        }
    }

    //write xml
    public void writeCreditCardsToXmlFile(List<CreditCard> cards, String filename) throws Exception {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("CreditCards");

        for (CreditCard card : cards) {
            Element cardElement = root.addElement("CreditCard");
            cardElement.addElement("cardType").addText(card.getCardType());
            cardElement.addElement("cardNumber").addText(card.getCardNumber());
            cardElement.addElement("expirationDate").addText(card.getExpirationDate());
            cardElement.addElement("cardHolderName").addText(card.getCardHolderName());
        }

        // Write to file
        try (FileWriter writer = new FileWriter(filename)) {
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            xmlWriter.write(document);
            System.out.println("XML file written successfully."); // Debug print
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java CreditCardProcessor <input_file> <output_file>");
            return;
        }


        String inputFilename = args[0];
        String outputFilename = args[1];

        System.out.println("Input Filename: " + inputFilename);

        CreditCardProcessor processor = new CreditCardProcessor();
        try {
            List<CreditCard> cards;
            if (inputFilename.endsWith(".csv")) {
                cards = processor.processCreditCardsFromFile(inputFilename);
            } else if (inputFilename.endsWith(".json")) {
                cards = processor.processCreditCardsFromJson(inputFilename);
            } else if (inputFilename.endsWith(".xml")) {
                //System.out.println("inside main xml");
                cards = processor.processCreditCardsFromXml(inputFilename);
            } else {
                throw new IllegalArgumentException("Unsupported file type for input file: " + inputFilename);
            }

            //output
            if (outputFilename.endsWith(".json")) {
                processor.writeCreditCardsToJsonFile(cards, outputFilename);
            } else if (outputFilename.endsWith(".xml")) {
                processor.writeCreditCardsToXmlFile(cards, outputFilename);
            } else {
                // Default to CSV
                processor.writeCreditCardsToFile(cards, outputFilename);
            }

            // Assuming output is always in CSV format
            //processor.writeCreditCardsToFile(cards, outputFilename);
            System.out.println("Processing complete. Output written to " + outputFilename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
