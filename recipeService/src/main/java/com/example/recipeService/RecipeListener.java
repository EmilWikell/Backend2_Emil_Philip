package com.example.recipeService;

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
		RecipeData informationToPrintOnPDF = recipeInformation(recipeData);
		try {
			pdfC(informationToPrintOnPDF);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Consuming Message - " + recipeData);
	}

	protected RecipeData recipeInformation(String recipeData) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String lineBreaker =
				"--------------------------------------------------------------------";

		return new RecipeData(
				"Store: web-shop",
				"Store number: 154856-4848",
				"Address: Online",
				"Date: " + dtf.format(now),
				lineBreaker,
				recipeData);
	}

	private void pdfC(RecipeData message) throws IOException {
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.setFont(PDType1Font.COURIER, 12);
		contentStream.beginText();
		contentStream.setLeading(16.0f);
		contentStream.newLineAtOffset(25, page.getTrimBox().getHeight() - 25);

		contentStream.showText(message.getStore());
		contentStream.newLine();
		contentStream.showText(message.getStoreNumber());
		contentStream.newLine();
		contentStream.showText(message.getAddress());
		contentStream.newLine();
		contentStream.showText(message.getDate());
		contentStream.newLine();
		contentStream.newLine();
		contentStream.showText(message.getLineBreaker());
		contentStream.newLine();
		contentStream.showText(message.getOrderData());
		contentStream.newLine();
		contentStream.showText(message.getLineBreaker());
		contentStream.newLine();
		contentStream.endText();
		contentStream.close();

		document.save("Recipe.pdf");
		document.close();
	}
}
