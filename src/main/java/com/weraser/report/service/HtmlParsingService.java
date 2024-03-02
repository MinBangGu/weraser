package com.weraser.report.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class HtmlParsingService {

	@Async
	public String parseHtml(String url, int chunkSize, String type) {
		var result = new StringBuilder();
        try {

            Document doc = Jsoup.connect(url).get();
            var text = "";
            if(type.equals("text"))
            	text = doc.html();
            else
            	text = doc.html().replaceAll("\\<.*?\\>", "");

            System.out.println(text);
            var mixedText = mixText(extractEnglish(text), extractNumbers(text));
            var quotient = mixedText.length() / chunkSize;
            var currentIndex = 0;
            
            currentIndex = quotient * chunkSize;

            String remainderChunk = mixedText.substring(currentIndex);
            String quotientChunk = mixedText.substring(0, currentIndex);
            result.append("<p>몫: ").append(quotientChunk).append("</p>");
            result.append("<p>나머지: ").append(remainderChunk).append("</p>");
            

        } catch (Exception e) {
            e.printStackTrace();
            result.append("오류가 발생했습니다. 확인 후 다시 시도해 주세요.");
        }
        return result.toString();
    }

    private String extractEnglish(String text) {

    	var englishRegex = "[a-zA-Z]+";

        return extractByRegex(text, englishRegex);
    }

    private String extractNumbers(String text) {

    	var numberRegex = "\\d+";

        return extractByRegex(text, numberRegex);
    }

    private String extractByRegex(String text, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        StringBuilder resultBuilder = new StringBuilder();
        
        while (matcher.find()) {
            resultBuilder.append(matcher.group());
        }

        return resultBuilder.toString();
    }

    private String mixText(String str1, String str2) {
    	
        StringBuilder mixedTextBuilder = new StringBuilder();
        var minLength = Math.min(str1.length(), str2.length());
        
        for (int i = 0; i < minLength; i++) {
            mixedTextBuilder.append(str1.charAt(i));
            mixedTextBuilder.append(str2.charAt(i));
        }

        if (str1.length() > minLength) {
            mixedTextBuilder.append(str1.substring(minLength));
        }
        
        if (str2.length() > minLength) {
            mixedTextBuilder.append(str2.substring(minLength));
        }
        
        return mixedTextBuilder.toString();
    }
}