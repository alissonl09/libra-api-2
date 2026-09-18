package com.cooxupe.libra.service.preticket;

import com.cooxupe.libra.dto.preticket.PreTicketRequest;
import com.cooxupe.libra.dto.preticket.PreTicketResponse;
import com.cooxupe.libra.repository.r12.preticket.PreTicketRepository;
import org.springframework.stereotype.Service;

// essa classe representa a camada de serviço da aplicação

@Service
public class PreTicketService {

    private final PreTicketRepository preTicketRepository;

    public PreTicketService(PreTicketRepository preTicketRepository) {
        this.preTicketRepository = preTicketRepository;
    }

    public PreTicketResponse buscarPreTicket(PreTicketRequest request) {

        return preTicketRepository.buscarPreTicket(
                request.cdUnidadeBalanca(),
                request.placa()
        );
    }
}