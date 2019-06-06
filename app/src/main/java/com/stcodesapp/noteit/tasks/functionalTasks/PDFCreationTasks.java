package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.text.Html;
import android.util.Log;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.html.HtmlEncoder;
import com.itextpdf.text.html.HtmlParser;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.stcodesapp.noteit.constants.templates.HTMLTemplates;
import com.stcodesapp.noteit.factory.TasksFactory;
import com.stcodesapp.noteit.listeners.TestDocListener;
import com.stcodesapp.noteit.models.Contact;
import com.stcodesapp.noteit.models.Email;
import com.stcodesapp.noteit.models.Note;
import com.stcodesapp.noteit.models.NoteComponents;
import com.stcodesapp.noteit.tasks.utilityTasks.AppPermissionTrackingTasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

public class PDFCreationTasks {

    private Activity activity;
    private FileIOTasks fileIOTasks;
    private TasksFactory tasksFactory;
    private AppPermissionTrackingTasks appPermissionTrackingTasks;

    public PDFCreationTasks(Activity activity, TasksFactory tasksFactory) {
        this.activity = activity;
        this.fileIOTasks = tasksFactory.getFileIOTasks();
        this.appPermissionTrackingTasks= tasksFactory.getAppPermissionTrackingTasks();
        this.tasksFactory = tasksFactory;
    }

    public void createPDF(NoteComponents noteComponents)
    {

        try
        {
            Document document = new Document();
            float fontSize = 18.0f;
            Note note = noteComponents.getNote();
            List<Contact> contactList = noteComponents.getChosenContacts();
            List<Email> emailList = noteComponents.getEmails();

            File file = fileIOTasks.getFileForSaving("testFile");

            PdfWriter.getInstance(document, new FileOutputStream(file));

            Log.e("CreatingPDF","Starting....");

            document.open();

            document.setPageSize(PageSize.A4);
            document.addCreationDate();

//            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 26.0f;

            BaseFont urName = BaseFont.createFont("assets/fonts/roboto_black.ttf", "UTF-8", BaseFont.EMBEDDED);

            LineSeparator lineSeparator = new LineSeparator();
//            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

//            Font mOrderDetailsTitleFont = new Font(urName, fontSize, Font.NORMAL, BaseColor.BLACK);

            String testHTML = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "\t<title></title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "\t<div>\n" +
                    "\t\t<h2 style=\"color: blue\"> "+note.getNoteTitle()+" </h2>\n" +
                    "\t</div>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";



//            HtmlEncoder.encode(HTMLTemplates.TEMPLATE_1);
            HTMLWorker hw = new HTMLWorker(document);
            hw.parse(new StringReader(HTMLTemplates.TEMPLATE_1));
//            Chunk mOrderDetailsTitleChunk = hw.createChunk(testHTML);

            HtmlParser.parse(new TestDocListener(),HTMLTemplates.TEMPLATE_1);

//            Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);

            Log.e("CreatingPDF","Paragraphing....");

//            mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);

//            document.add(mOrderDetailsTitleParagraph);

//            Font mOrderIdFont = new Font(urName, mHeadingFontSize, Font.NORMAL, mColorAccent);
//            Chunk mOrderIdChunk = new Chunk("Order No:", mOrderIdFont);
//            Paragraph mOrderIdParagraph = new Paragraph(mOrderIdChunk);
//            document.add(mOrderIdParagraph);

            document.add(new Paragraph(""));
            document.add(new Chunk(lineSeparator));
            document.add(new Paragraph(""));

            Log.e("CreatingPDF","Closing....");

            document.close();



        }catch (Exception e)
        {
            Log.e("Exception",e.getMessage());

        }


    }
}
