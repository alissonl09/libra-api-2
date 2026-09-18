package com.cooxupe.libra.repository.r12.preticket;

import com.cooxupe.libra.dto.preticket.PreTicketResponse;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Types;

@Repository
public class PreTicketRepository {

    private final JdbcTemplate jdbcTemplate;

    public PreTicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PreTicketResponse buscarPreTicket(
            Long cdUnidadeBalanca,
            String placa
    ) {

        String procedure = """
                {call XCXP_BAL_PRE_TICKET_PKG.BUSCAR_PRE_TICKET_PRC(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}
                """;

        /*
         * Prepara a chamada da procedure Oracle.
         */
        CallableStatementCreator statementCreator = connection -> {

            CallableStatement callableStatement =
                    connection.prepareCall(procedure);

            /*
             * Parâmetros de entrada
             */
            callableStatement.setLong(1, cdUnidadeBalanca);
            callableStatement.setString(2, placa);

            /*
             * Parâmetros de saída
             */
            callableStatement.registerOutParameter(3, Types.VARCHAR);
            callableStatement.registerOutParameter(4, Types.VARCHAR);
            callableStatement.registerOutParameter(5, Types.VARCHAR);
            callableStatement.registerOutParameter(6, Types.VARCHAR);
            callableStatement.registerOutParameter(7, Types.VARCHAR);
            callableStatement.registerOutParameter(8, Types.VARCHAR);
            callableStatement.registerOutParameter(9, Types.NUMERIC);
            callableStatement.registerOutParameter(10, Types.VARCHAR);
            callableStatement.registerOutParameter(11, Types.VARCHAR);
            callableStatement.registerOutParameter(12, Types.NUMERIC);
            callableStatement.registerOutParameter(13, Types.NUMERIC);
            callableStatement.registerOutParameter(14, Types.NUMERIC);
            callableStatement.registerOutParameter(15, Types.NUMERIC);
            callableStatement.registerOutParameter(16, Types.VARCHAR);
            callableStatement.registerOutParameter(17, Types.VARCHAR);

            return callableStatement;
        };

        /*
         * Executa a procedure e lê os valores retornados.
         */
        CallableStatementCallback<PreTicketResponse> statementCallback =
                callableStatement -> {

                    callableStatement.execute();

                    /*
                     * P_QT_TOTAL
                     */
                    long quantidadeTotalValor =
                            callableStatement.getLong(9);

                    Long quantidadeTotal =
                            callableStatement.wasNull()
                                    ? null
                                    : quantidadeTotalValor;

                    /*
                     * P_NR_SEQUENCIA
                     */
                    long numeroSequenciaValor =
                            callableStatement.getLong(12);

                    Long numeroSequencia =
                            callableStatement.wasNull()
                                    ? null
                                    : numeroSequenciaValor;

                    /*
                     * P_NR_TICKET_ORIGEM
                     */
                    long numeroTicketOrigemValor =
                            callableStatement.getLong(13);

                    Long numeroTicketOrigem =
                            callableStatement.wasNull()
                                    ? null
                                    : numeroTicketOrigemValor;

                    /*
                     * P_CD_UNIDADE_ORIGEM
                     */
                    int codigoUnidadeOrigemValor =
                            callableStatement.getInt(14);

                    Integer codigoUnidadeOrigem =
                            callableStatement.wasNull()
                                    ? null
                                    : codigoUnidadeOrigemValor;

                    /*
                     * P_PS_LIQUIDO_ORIGEM
                     */
                    long pesoLiquidoOrigemValor =
                            callableStatement.getLong(15);

                    Long pesoLiquidoOrigem =
                            callableStatement.wasNull()
                                    ? null
                                    : pesoLiquidoOrigemValor;

                    return new PreTicketResponse(
                            callableStatement.getString(3),
                            callableStatement.getString(4),
                            callableStatement.getString(5),
                            callableStatement.getString(6),
                            callableStatement.getString(7),
                            callableStatement.getString(8),
                            quantidadeTotal,
                            callableStatement.getString(10),
                            callableStatement.getString(11),
                            numeroSequencia,
                            numeroTicketOrigem,
                            codigoUnidadeOrigem,
                            pesoLiquidoOrigem,
                            callableStatement.getString(16),
                            callableStatement.getString(17)
                    );
                };

        return jdbcTemplate.execute(
                statementCreator,
                statementCallback
        );
    }
}