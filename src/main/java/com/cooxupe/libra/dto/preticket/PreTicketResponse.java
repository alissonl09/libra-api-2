package com.cooxupe.libra.dto.preticket;


// DTO de resposta, que representará os dados devolvidos pela procedure XCXP_BAL_PRE_TICKET_PKG.

public record PreTicketResponse(

        String tipoDocumento,
        String tipoOperacao,
        String nota,
        String emissor,
        String motorista,
        String transportador,
        Long quantidadeTotal,
        String codigoEmbalagem,
        String deposito,
        Long numeroSequencia,
        Long numeroTicketOrigem,
        Integer codigoUnidadeOrigem,
        Long pesoLiquidoOrigem,
        String codigoRetorno,
        String descricaoRetorno

) {
}