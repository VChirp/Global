package com.gesua.Calculation;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class CalculationController {
    @RequestMapping(value = "/Calculation", method = RequestMethod.GET)
    public CalculatorDouble.Report report(@RequestParam Integer nTrubok,
                                          @RequestParam Double t2sht,
                                          @RequestParam Double G,
                                          @RequestParam Double T2vuh) throws IOException, URISyntaxException {

        CalculatorDouble.Report report = new CalculatorDouble.Report(nTrubok, t2sht, G, T2vuh);
        generatePdfReport(report);

        return report;
    }

    void generatePdfReport(CalculatorDouble.Report report) {
        try {
            String dest = "/home/chirp/Desktop/Report.pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(dest));
            doc.open();
            doc.addTitle("Turflow  Specification Sheet");

//          MainTable
            PdfPTable mainTable = new PdfPTable(3);
            mainTable.setWidthPercentage(110);
            mainTable.addCell(createCell("Process data"));
            mainTable.addCell(createCell("Shell Side"));
            mainTable.addCell(createCell("Tube Side"));

//          FirstTable
            PdfPTable firstTable = new PdfPTable(2);
            firstTable.addCell(createCell(""));
            firstTable.addCell(createCell("Unit"));

            firstTable.addCell(createCell("Media"));
            firstTable.addCell(createCell(""));

            firstTable.addCell(createCell("Flow rate"));
            firstTable.addCell(createCell("kg/h"));

            firstTable.addCell(createCell("Vapour"));
            firstTable.addCell(createCell("kf/h"));

            firstTable.addCell(createCell("Liquid"));
            firstTable.addCell(createCell("kg/h"));

            firstTable.addCell(createCell("Pressure"));
            firstTable.addCell(createCell("bar gauge"));

            firstTable.addCell(createCell("Temperature"));
            firstTable.addCell(createCell("°C"));

            firstTable.addCell(createCell("Density"));
            firstTable.addCell(createCell("kg/m3"));

            firstTable.addCell(createCell("Specific Heat"));
            firstTable.addCell(createCell("kJ/kg °C"));

            firstTable.addCell(createCell("Latent heat"));
            firstTable.addCell(createCell("kJ/kg"));

            firstTable.addCell(createCell("Flow velocity"));
            firstTable.addCell(createCell("m/s"));
            mainTable.addCell(firstTable);

//          SecondTable
            PdfPTable secondTable = new PdfPTable(2);
            secondTable.addCell(createCell("In"));
            secondTable.addCell(createCell("Out"));

            secondTable.addCell(createCell("Dry"));
            secondTable.addCell(createCell("Saturated Stream"));

            secondTable.addCell(createCell(Double.toString(report.getDnar())));
            secondTable.addCell(createCell(Double.toString(report.getDnar())));

            secondTable.addCell(createCell(Double.toString(report.getDnar())));
            secondTable.addCell(createCell(Double.toString(0.00)));

            secondTable.addCell(createCell(Double.toString(0.00)));
            secondTable.addCell(createCell(Double.toString(report.getDnar())));

            secondTable.addCell(createCell(Double.toString(report.getPnar())));
            secondTable.addCell(createCell(Double.toString(report.getPnar())));

            mainTable.addCell(secondTable);


//          ThirdTable
            PdfPTable thirdTable = new PdfPTable(2);
            thirdTable.addCell(createCell("In"));
            thirdTable.addCell(createCell("Out"));

            thirdTable.addCell(createCell("Water"));
            thirdTable.addCell(createCell(""));
            mainTable.addCell(thirdTable);

//            MainTableBot
            PdfPTable mainTableBot = new PdfPTable(1);
            mainTable.addCell(createCell("Performance Data"));

//            TableBot
            PdfPTable tableBot = new PdfPTable(3);
            tableBot.addCell("Duty");
            tableBot.addCell("kW");
            tableBot.addCell("");

            tableBot.addCell("LMTD");
            tableBot.addCell("°C");
            tableBot.addCell(Double.toString(report.getdTser()));

            tableBot.addCell("Heat transfer area");
            tableBot.addCell("m^2");
            tableBot.addCell("");

            tableBot.addCell("Heat transfer coefficient");
            tableBot.addCell("W/m2 °C");
            tableBot.addCell(Double.toString(report.getK()));

            tableBot.addCell("Overdesign");
            tableBot.addCell("%");
            tableBot.addCell("");
            mainTableBot.addCell(tableBot);
            mainTable.setHorizontalAlignment(Element.ALIGN_LEFT);



            doc.add(mainTable);
            doc.add(mainTableBot);
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell createCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setNoWrap(true);
        return cell;
    }


}
