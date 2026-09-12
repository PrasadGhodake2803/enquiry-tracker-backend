package com.EnquriyTracker.EnquriyTracker.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.EnquriyTracker.EnquriyTracker.entity.CourseEntity;
import com.EnquriyTracker.EnquriyTracker.entity.EnquiryEntity;
import com.EnquriyTracker.EnquriyTracker.entity.RegistrationEntity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFService {
	
	//// Enquiry PDF Service

    public byte[] generateEnquiryPdf(List<EnquiryEntity> enquiries) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Document document = new Document(PageSize.A3.rotate());
            PdfWriter.getInstance(document, out);

            document.open();
            InputStream is = getClass().getResourceAsStream("/static/images/logo.png");

            if (is != null) {
                Image logo = Image.getInstance(is.readAllBytes());

                logo.scaleToFit(140, 140);

                logo.setAlignment(Element.ALIGN_CENTER);

                document.add(logo);
            }

            document.add(Chunk.NEWLINE);

            Font titleFont = FontFactory.getFont(
                    FontFactory.TIMES_BOLD, 20);

            Paragraph title =
                    new Paragraph("Enquiry Report", titleFont);

            title.setAlignment(Element.ALIGN_CENTER);
            Font companyFont =
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 26);

            Paragraph company =
                    new Paragraph("Mass IT Solutions", companyFont);

            company.setAlignment(Element.ALIGN_CENTER);

            document.add(company);

            Font addressFont =
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
                    		BaseColor.DARK_GRAY);

            Paragraph address = new Paragraph(
                    "Pune | https://massitsolutions.co.in/ | +91-9175023392",
                    addressFont);

            address.setAlignment(Element.ALIGN_CENTER);

            document.add(address);

            //document.add(Chunk.NEWLINE);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(20);
            table.setWidthPercentage(100);

            addHeader(table, "ID");
            addHeader(table, "Date of Enquiry");
            addHeader(table, "Name");
            addHeader(table, "Mobile");
            addHeader(table, "Email");
            addHeader(table, "Date of Birth");
            addHeader(table, "Gender");
            addHeader(table, "Residencial Address");
            addHeader(table, "Education");
            addHeader(table, "Specailization");
            addHeader(table, "College Name");
            addHeader(table, "Course");
            
            addHeader(table, "Work Experience");
            addHeader(table, "Prefered Batch");
            addHeader(table, "Planning to Start");
            
            addHeader(table, "Source Of Enquiry");
            addHeader(table, "Enquiry Type");
            addHeader(table, "Follow Up");
            addHeader(table, "Remarks");
            addHeader(table, "Status");


            

            for (EnquiryEntity enquiry : enquiries) {

                table.addCell(String.valueOf(enquiry.getEnquiry_id()));
                table.addCell(createCell(enquiry.getDateOfEnquiry().toString(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getName(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getMobileNumber(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getEmail(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getDob(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getGender(), cellFont, rowColor));

                table.addCell(createCell(enquiry.getResidencial_address(), cellFont, rowColor));	
                table.addCell(createCell(enquiry.getEducation_details(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getSpecialization(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getCollegeName(), cellFont, rowColor));

                table.addCell(createCell(enquiry.getCourse(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getWork_exp(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getPrefered_batch(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getPlanning_to_start(), cellFont, rowColor));

                table.addCell(createCell(enquiry.getReference(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getEnquiryType(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getFollowUp(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getRemarks(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getStatus(), cellFont, rowColor));

            }	

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    Font cellFont = FontFactory.getFont(
            FontFactory.TIMES_ROMAN,
            12,
            Font.NORMAL,
            BaseColor.BLACK);

    BaseColor rowColor = new BaseColor(245, 245, 245);
    private PdfPCell createCell(String text, Font font, BaseColor background) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
     
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(background);
        return cell;
    }
    
    private void addHeader(PdfPTable table, String title) {

        PdfPCell header = new PdfPCell();

        header.setBackgroundColor(BaseColor.LIGHT_GRAY);

        header.setPhrase(
                new Phrase(title,
                        FontFactory.getFont(FontFactory.TIMES_BOLD)));

        table.addCell(header);
    }
    
    
    
    
    /////// Registration PDF Service 
    public byte[] generateRegistrationPdf(List<RegistrationEntity> registration) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Document document = new Document(PageSize.A3.rotate());
            PdfWriter.getInstance(document, out);

            document.open();
            InputStream is = getClass().getResourceAsStream("/static/images/logo.png");

            if (is != null) {
                Image logo = Image.getInstance(is.readAllBytes());

                logo.scaleToFit(140, 140);

                logo.setAlignment(Element.ALIGN_CENTER);

                document.add(logo);
            }

            document.add(Chunk.NEWLINE);

            Font titleFont = FontFactory.getFont(
                    FontFactory.TIMES_BOLD, 20);

            Paragraph title =
                    new Paragraph("Registration Report", titleFont);

            title.setAlignment(Element.ALIGN_CENTER);
            Font companyFont =
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 26);

            Paragraph company =
                    new Paragraph("Mass IT Solutions", companyFont);

            company.setAlignment(Element.ALIGN_CENTER);

            document.add(company);

            Font addressFont =
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
                    		BaseColor.DARK_GRAY);

            Paragraph address = new Paragraph(
                    "Pune | https://massitsolutions.co.in/ | +91-9175023392",
                    addressFont);

            address.setAlignment(Element.ALIGN_CENTER);

            document.add(address);

            //document.add(Chunk.NEWLINE);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(12);
            table.setWidthPercentage(100);

            addHeader(table, "ID");
            addHeader(table, "Name");
            addHeader(table, "Mobile Number");
            addHeader(table, "Parent Mobile Number");
            addHeader(table, "Email");
            addHeader(table, "Date of Birth");
            addHeader(table, "Residencial Address");
            addHeader(table, "Permanent Address");
            addHeader(table, "Education");
            
            addHeader(table, "Seleted Course");
            addHeader(table, "Batch Time");
            addHeader(table, "Start Date");
            
          
            

            for (RegistrationEntity enquiry : registration) {

                table.addCell(String.valueOf(enquiry.getRegisterid()));
                table.addCell(createCell(enquiry.getName(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getMobileNumber(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getParentmobileNumber(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getEmail(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getDob(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getResidentialAddress(), cellFont, rowColor));	
                table.addCell(createCell(enquiry.getPermanentAddress(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getEducationDetails(), cellFont, rowColor));

//                table.addCell(createCell(enquiry.getCourseName(), cellFont, rowColor));
//                table.addCell(createCell(enquiry.getBatchTime(), cellFont, rowColor));
//                table.addCell(createCell(enquiry.getStartDate(), cellFont, rowColor));
//       
            }	

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
    
    
    

    
    ///// Course PDF Service 
    public byte[] generateCoursePdf(List<CourseEntity> courses) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            Document document = new Document(PageSize.A3.rotate());
            PdfWriter.getInstance(document, out);

            document.open();
            InputStream is = getClass().getResourceAsStream("/static/images/logo.png");

            if (is != null) {
                Image logo = Image.getInstance(is.readAllBytes());

                logo.scaleToFit(140, 140);

                logo.setAlignment(Element.ALIGN_CENTER);

                document.add(logo);
            }

            document.add(Chunk.NEWLINE);

            Font titleFont = FontFactory.getFont(
                    FontFactory.TIMES_BOLD, 20);

            Paragraph title =
                    new Paragraph("Course Report", titleFont);

            title.setAlignment(Element.ALIGN_CENTER);
            Font companyFont =
                    FontFactory.getFont(FontFactory.TIMES_BOLD, 26);

            Paragraph company =
                    new Paragraph("Mass IT Solutions", companyFont);

            company.setAlignment(Element.ALIGN_CENTER);

            document.add(company);

            Font addressFont =
                    FontFactory.getFont(FontFactory.TIMES_ROMAN, 14,
                    		BaseColor.DARK_GRAY);

            Paragraph address = new Paragraph(
                    "Pune | https://massitsolutions.co.in/ | +91-9175023392",
                    addressFont);

            address.setAlignment(Element.ALIGN_CENTER);

            document.add(address);

            //document.add(Chunk.NEWLINE);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(9);
            table.setWidthPercentage(100);

            addHeader(table, "ID");
            addHeader(table, "Course Code ");
            addHeader(table, "Course Name");
            addHeader(table, "Course Duration");
            addHeader(table, "Course Fees");
            addHeader(table, "Batch Time");
            addHeader(table, "Course Start Date");
            addHeader(table, "Course Description");
            addHeader(table, "Created At");
            

            for (CourseEntity enquiry : courses) {

                table.addCell(String.valueOf(enquiry.getId()));
                table.addCell(createCell(enquiry.getCourseCode(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getCourseName(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getCourseDuration(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getCourseFess(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getBatchTime(), cellFont, rowColor));
                table.addCell(createCell(enquiry.getCourseStartDate(), cellFont, rowColor));	
                table.addCell(createCell(enquiry.getCourseDiscriptioin(), cellFont, rowColor));
                table.addCell(String.valueOf(enquiry.getCreatedAt()));
            }	

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }



   
}