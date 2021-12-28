package hu.peter.frontend.util;

import hu.peter.frontend.dto.UserReservationDto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;


public class PdfGenerator {

    public PDDocument generateBoardingPass( UserReservationDto reservation ) throws IOException{
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDFont font =  PDType1Font.HELVETICA ;

        contentStream.beginText();
        contentStream.moveTextPositionByAmount(10, 700);
        contentStream.setFont(font, 12);
        contentStream.drawString("BESZÁLLÓKÁRTYA");
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Honnan: " + reservation.getFlightDto().getDeparture().getCity());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Hova: " + reservation.getFlightDto().getArrival().getCity());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Dátum: " + reservation.getFlightDto().getDate().toString());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Indulás ideje" +
                ": " + reservation.getFlightDto().getDateHour().toString());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Név: " + reservation.getSeatDto().getPassenger().getFirstName() + " " + reservation.getSeatDto().getPassenger().getSecondName());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Ülés: " + reservation.seatNumber());
        contentStream.newLineAtOffset(0, -15);
        contentStream.drawString("Útlevél: " + reservation.getSeatDto().getPassenger().getPassport());
        contentStream.endText();
        contentStream.close();
        document.addPage(page);

        return document;
    }
}
