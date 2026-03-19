package battleship;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.Paragraph;

import java.util.List;

public class PDFexporter {

    public static void export(List<String> jogadas, int naviosRestantes) {
        try {
            PdfWriter writer = new PdfWriter("game_report.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Relatório do Jogo\n"));

            document.add(new Paragraph("Jogadas realizadas:"));

            for (String jogada : jogadas) {
                document.add(new Paragraph("- " + jogada));
            }

            document.add(new Paragraph("\nNavios restantes: " + naviosRestantes));

            document.close();

            System.out.println("PDF criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}