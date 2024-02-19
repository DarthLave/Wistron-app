package com.example.wistron.web.controller;

import com.example.wistron.service.exception.ExistingContactException;
import com.example.wistron.service.model.dto.ContactDTO;
import com.example.wistron.service.model.dto.ContactRegistrationDTO;
import com.example.wistron.service.model.entity.Contact;
import com.example.wistron.service.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    @Operation(summary = "registration of a new contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "confirms the contact registration",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "404", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "500", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string")))})
    @PostMapping("/registration")
    public ResponseEntity<?> register(@RequestBody ContactRegistrationDTO contactRegistrationDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            log.info("registration successful");
            contactService.registration(contactRegistrationDTO.getName(), contactRegistrationDTO.getSurname(),
                    contactRegistrationDTO.getNumber());
            return ResponseEntity.status(HttpStatus.OK).body("The contact has been registered.");
        } catch (Exception e) {
            log.error("registration unsuccessful");
            response.put("error", e.getMessage());
            HttpStatus httpStatus = getHttpStatus(e);
            return ResponseEntity.status(httpStatus).body(response);
        }
    }

    @Operation(summary = "finds a contact through an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "presents the contact",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "404", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "500", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string")))})
    @GetMapping("/list/{id}")
    public ResponseEntity<?> findContactById(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            log.info("user found");
            Contact contact = contactService.getContact(id).get();
            return ResponseEntity.ok(new ContactDTO(contact.getName(), contact.getSurname(), contact.getNumber()));
        } catch (Exception e) {
            log.error("contact not found");
            response.put("error", e.getMessage());
            HttpStatus httpStatus = getHttpStatus(e);
            return ResponseEntity.status(httpStatus).body(response);
        }
    }

    @Operation(summary = "lists all contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "lists all contacts registered",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "404", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "500", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string")))})
    @GetMapping("/listAll")
    public ResponseEntity<?> getAllContacts() {
        Map<String, String> response = new HashMap<>();
        try {
            log.info("contacts found");
            return ResponseEntity.ok().body(contactService.getAllContacts());
        } catch (Exception e) {
            log.error("contacts not found");
            response.put("error", e.getMessage());
            HttpStatus httpStatus = getHttpStatus(e);
            return ResponseEntity.status(httpStatus).body(response);
        }
    }

    @Operation(summary = "deletes a contact through an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "deletes the contact",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "404", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "500", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string")))})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            log.info("user deleted");
            contactService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("The contact has been deleted.");
        } catch (Exception e) {
            log.error("contact couldn't be deleted");
            response.put("error", e.getMessage());
            HttpStatus httpStatus = getHttpStatus(e);
            return ResponseEntity.status(httpStatus).body(response);
        }
    }

    @Operation(summary = "updates a contact through an id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updates the contact",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ContactDTO.class))),
            @ApiResponse(responseCode = "400", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "404", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string"))),
            @ApiResponse(responseCode = "500", description = "returns error details",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "string")))})
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateContact(@RequestBody ContactDTO contactDTO,
                                           @PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            log.info("contact updated");
            contactService.update(contactDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body("The contact has been updated.");
        } catch (Exception e) {
            log.error("contact couldn't be updated");
            response.put("error", e.getMessage());
            HttpStatus httpStatus = getHttpStatus(e);
            return ResponseEntity.status(httpStatus).body(response);
        }
    }

    private HttpStatus getHttpStatus(Exception e) {
        if (e instanceof ExistingContactException || e instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}