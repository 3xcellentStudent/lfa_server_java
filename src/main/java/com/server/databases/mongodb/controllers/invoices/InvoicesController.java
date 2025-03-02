package com.server.databases.mongodb.controllers.invoices;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import com.server.databases.mongodb.repositories.invoices.InvoicesRepository;
import com.server.services.pdf.models.CaptureResponseObject;

@RestController
@RequestMapping("/api/mongodb/invoices")
public class InvoicesController {
  
  // private InvoicesRepository repository;

  // @PostMapping("/add")
  // public String add(@PathVariable String id, @RequestBody CaptureResponseObject invoice){
  //   boolean existingDocument = repository.existsById(id);
  //   if(existingDocument) return "The document exists";
  //   else {
  //     // repository.save(invoice);
  //     return invoice.toString();
  //   }
  // }
}
