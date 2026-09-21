WHENEVER SQLERROR CONTINUE;
CONNECT &1/&2
SET DEF OFF
SET VERIFY OFF
SPOOL DROP_TABLES.log
SET SQLBLANKLINES ON

PROMPT OBJETO: DROP_TABLES.sql

  -- Versao: $Version: DROP_TABLES.sql 120.1 17/07/2025 10:55:00 tiagochagas ship $
  -- +=====================================================================+
  -- |                    Copyright (c) 2025 COOXUPE                       |
  -- |                         All rights reserved.                        |
  -- +=====================================================================+
  -- | ARQUIVO                                                             |
  -- |     DROP_TABLES                                                     |
  -- |                                                                     |
  -- | DESCRICAO                                                           |
  -- |    Projeto 2837                                                     |
  -- |                                                                     |
  -- | MODULO                                                              |
  -- |    GV                                                               |
  -- |                                                                     |
  -- | CONTROLE DE VERSAO                                                  |
  -- |    Versão: $Revision: 1.0$                                          |
  -- |    Data  : $Date: 2025/07/17 10:55:00 $                             |
  -- |                                                                     |
  -- | HISTORICO                                                           |
  -- |   17/07/2025 - Tiago Chagas - Criacao objeto                        |
  -- |                                                                     |
  -- +=====================================================================+

DROP TABLE XXMCC.XCXP_GV_MOVIMENTACAO_LINHA;
DROP VIEW XXMCC.XCXP_GV_MOVIMENTACAO_LINHA#;

DROP TABLE XXMCC.XCXP_GV_ROTEIRIZACAO_LINHA;
DROP VIEW XXMCC.XCXP_GV_ROTEIRIZACAO_LINHA#;

DROP TABLE XXMCC.XCXP_GV_PONTO_CONTROLE;
DROP VIEW XXMCC.XCXP_GV_PONTO_CONTROLE#;

DROP TABLE XXMCC.XCXP_GV_ROTEIRIZACAO;
DROP VIEW XXMCC.XCXP_GV_ROTEIRIZACAO#;

DROP TABLE XXMCC.XCXP_GV_MOVIMENTACAO;
DROP VIEW XXMCC.XCXP_GV_MOVIMENTACAO#;

DROP TABLE XXMCC.XCXP_GV_PROCESSO;
DROP VIEW XXMCC.XCXP_GV_PROCESSO#;

DROP TABLE XXMCC.XCXP_GV_ACONDICIONAMENTO;
DROP VIEW XXMCC.XCXP_GV_ACONDICIONAMENTO#;

DROP TABLE XXMCC.XCXP_GV_OPERACAO;
DROP VIEW XXMCC.XCXP_GV_OPERACAO#

/
SHOW ERRORS
SPOOL OFF
EXIT;