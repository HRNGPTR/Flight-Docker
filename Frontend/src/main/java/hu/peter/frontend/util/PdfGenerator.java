package hu.peter.frontend.util;

import hu.peter.frontend.dto.UserReservationDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
//import java.io.RandomAccessFile;

public class PdfGenerator {
    public void generatePdf() throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        String title = "HELLO WORLD";
        PDFont font = PDType1Font.HELVETICA_BOLD;
        int fontSize = 16;
        int marginTop = 30;

        float titleWidth = font.getStringWidth(title) / 1000 * fontSize;
        float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;

        contentStream.setFont(font, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight);
        contentStream.showText(title);
        contentStream.endText();
        contentStream.close();

        document.save("src/main/resources/pdfBoxHelloWorld.pdf");
        document.save("src/main/resources/web/pdf/pdfBoxHelloWorld.pdf");
        document.close();

    }
    public void generateBoardingPass( UserReservationDto reservation ) throws IOException{
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        PDFont font =  PDType1Font.HELVETICA ;
        String uri = "src/main/resources/static/pdf/";

        contentStream.beginText();
        contentStream.moveTextPositionByAmount(10, 700);
        contentStream.setFont(font, 12);
        contentStream.drawString("BOARDING PASS");
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("From: " + reservation.getFlightDto().getDeparture().getCity());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("To: " + reservation.getFlightDto().getArrival().getCity());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Date: " + reservation.getFlightDto().getDate().toString());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Departure: " + reservation.getFlightDto().getDateHour().toString());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Name: " + reservation.getSeatDto().getPassenger().getFirstName() + " " + reservation.getSeatDto().getPassenger().getSecondName());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Seat: " + reservation.seatNumber());
        contentStream.endText();
        contentStream.close();
        document.addPage(page);

        document.save(uri+reservation.getUsername()+".pdf");
        document.close();

    }
}
