package com.example.rabbitmq;

import org.apache.catalina.Store;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class RecipeListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		String recipeData = new String(message.getBody());
		try {
			pdfC(recipeData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Consuming Message - " + recipeData);
	}

	private void pdfC(String message) throws IOException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.setFont(PDType1Font.COURIER, 12);
		contentStream.beginText();
		contentStream.setLeading(16.0f);
		contentStream.newLineAtOffset(25, page.getTrimBox().getHeight() - 25);

		String line1 = "Store: webshop";
		String line2 = "Store number: 154856-4848";
		String line3 = "Address: Online";
		String line4 = "Date: " + dtf.format(now);
		String line5 = "--------------------------------------------------------------------";

		contentStream.showText(line1);
		contentStream.newLine();
		contentStream.showText(line2);
		contentStream.newLine();
		contentStream.showText(line3);
		contentStream.newLine();
		contentStream.showText(line4);
		contentStream.newLine();
		contentStream.newLine();
		contentStream.showText(line5);
		contentStream.newLine();
		contentStream.showText(message);
		contentStream.newLine();
		contentStream.showText(line5);
		contentStream.newLine();
		contentStream.endText();
		contentStream.close();

		document.save("Recipe.pdf");
		document.close();
	}
}
