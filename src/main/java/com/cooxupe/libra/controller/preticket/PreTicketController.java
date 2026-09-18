package com.cooxupe.libra.controller.preticket;

import com.cooxupe.libra.dto.preticket.PreTicketRequest;
import com.cooxupe.libra.dto.preticket.PreTicketResponse;
import com.cooxupe.libra.service.preticket.PreTicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/pre-tickets")
public class PreTicketController {

    private final PreTicketService preTicketService;

    public PreTicketController(PreTicketService preTicketService) {
        this.preTicketService = preTicketService;
    }

    @GetMapping
    public ResponseEntity<PreTicketResponse> buscarPreTicket(
            @Valid @ModelAttribute PreTicketRequest request
    ) {

        PreTicketResponse response =
                preTicketService.buscarPreTicket(request);

        return switch (response.codigoRetorno()) {

            case "S" ->
                    ResponseEntity.ok(response);

            case "N" ->
                    ResponseEntity
                            .status(HttpStatus.NOT_FOUND)
                            .body(response);

            case "V" ->
                    ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(response);

            case "C" ->
                    ResponseEntity
                            .status(HttpStatus.CONFLICT)
                            .body(response);

            case "E" ->
                    ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(response);

            default ->
                    ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(response);
        };
    }
}