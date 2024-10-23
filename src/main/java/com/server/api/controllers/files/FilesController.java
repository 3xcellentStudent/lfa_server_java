package com.server.api.controllers.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.services.pdf.PDFService;
import com.server.services.pdf.models.CaptureResponseObject;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/files")
public class FilesController {

  @PostMapping(value = "/create-pdf")
  public void createpdfFile(@RequestBody String body){
    JSONObject jsonBody = new JSONObject(body);
    new PDFService().create(new CaptureResponseObject(jsonBody));
    return;
  }
  
  @PostMapping(value = "/write-pdf")
  public String writePdfFile(HttpServletRequest request, @RequestHeader("Content-Disposition") String contentDisposition){
    try {
      String fileName = extractFileName(contentDisposition);
      if (fileName == null) return "Filename not found in Content-Disposition header";

      Path filePath = Paths.get("/home/andrew/Desktop/invoices", fileName);

      try (FileOutputStream outputStream = new FileOutputStream(filePath.toFile())) {
        outputStream.write(request.getInputStream().readAllBytes());
      }

      return "File uploaded and saved to " + filePath.toString();
    } catch (IOException e) {
      e.printStackTrace();
      return "Failed to save file: " + e.getMessage();
    }
  }

  private String extractFileName(String contentDisposition) {
    String[] parts = contentDisposition.split(";");
    for (String part : parts) {
      if (part.trim().startsWith("filename=")) {
        return part.split("=")[1].trim().replaceAll("\"", "");
      }
    }
    return null;
  }
}
