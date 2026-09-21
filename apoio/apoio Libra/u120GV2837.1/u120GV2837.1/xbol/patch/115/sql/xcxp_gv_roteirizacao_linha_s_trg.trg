WHENEVER SQLERROR CONTINUE;
CONNECT &1/&2
SET DEF OFF
SET VERIFY OFF
SPOOL XCXP_GV_ROTEIRIZACAO_LINHA_S_TRG.trg.log
SET SQLBLANKLINES ON

PROMPT OBJETO: XCXP_GV_ROTEIRIZACAO_LINHA_S_TRG.trg 

CREATE OR REPLACE TRIGGER APPS.XCXP_GV_ROTEIRIZACAO_LINHA_S_TRG
  BEFORE INSERT OR UPDATE ON XCXP_GV_ROTEIRIZACAO_LINHA
  FOR EACH ROW
BEGIN
  -- +=====================================================================+
  -- |                    Copyright (c) 2025 COOXUPE                       |
  -- |                         All rights reserved.                        |
  -- +=====================================================================+
  -- | ARQUIVO                                                             |
  -- |    XCXP_GV_ROTEIRIZACAO_LINHA_S_TRG                                 |
  -- |                                                                     |
  -- | DESCRICAO                                                           |
  -- |    Trigger utilizada para gravar informações de auditoria.          |
  -- |                                                                     |
  -- | MODULO                                                              |
  -- |    SGAA                                                             |
  -- |                                                                     |
  -- | CONTROLE DE VERSAO                                                  |
  -- |    Versão: $Revision: 1.0$                                          |
  -- |    Data  : $Date: 30.06.2025$                                       |
  -- |                                                                     |
  -- | HISTORICO                                                           |
  -- |    30.06.2025 - Rhuan  PRJ - 2837 Gestão de Pátio                   |
  -- |                                                                     |
  -- +=====================================================================+
  --
  IF INSERTING THEN
    :NEW.ID_ROTEIRIZACAO_LINHA     := XCXP_GV_ROTEIRIZACAO_LINHA_S.NEXTVAL;
    :NEW.DT_CRIACAO            := SYSDATE;
    :NEW.DT_ALTERACAO          := SYSDATE;
  ELSIF UPDATING THEN
    :NEW.DT_ALTERACAO          := SYSDATE;
  ELSE
    NULL;
  END IF;
  --
END XCXP_GV_ROTEIRIZACAO_LINHA_S_TRG;

/
SHOW ERRORS
SPOOL OFF
EXIT;