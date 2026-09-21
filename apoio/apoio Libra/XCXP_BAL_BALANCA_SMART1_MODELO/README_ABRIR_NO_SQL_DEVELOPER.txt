PACOTE — MODELO DE DADOS BALANÇA / SMART 1
===========================================

CONTEÚDO
---------
1) datamodeler/XCXP_BAL_BALANCA_SMART1.dmd
2) datamodeler/XCXP_BAL_BALANCA_SMART1/  (pasta interna do design; NÃO separar do .dmd)
3) ddl/XCXP_BAL_MODELO_COMPLETO.sql
4) patch/sql/*.tab e *.seq
5) patch/XCXP_BAL_ACESSO.drv
6) documentacao/MODELO_DADOS.md
7) documentacao/VALIDACAO_REGRAS_EPA_XFER_OM.md

COMO ABRIR NO SQL DEVELOPER / DATA MODELER
-------------------------------------------
A) Extraia o ZIP por completo. Não extraia apenas o arquivo .dmd.
B) Garanta que estes dois itens estejam lado a lado:
   XCXP_BAL_BALANCA_SMART1.dmd
   XCXP_BAL_BALANCA_SMART1\
C) No Oracle SQL Developer com Data Modeler integrado:
   File > Data Modeler > Open
   e selecione XCXP_BAL_BALANCA_SMART1.dmd.
   No Data Modeler standalone: File > Open.
D) No Browser do Data Modeler, abra:
   - Logical Models > Logical
   - Relational Models > BALANCA_SMART1_ORACLE19C
E) Na primeira abertura em uma versão mais nova, prefira Save As para uma nova pasta antes de editar.

SE O .DMD NÃO ABRIR NA SUA VERSÃO
----------------------------------
Como fallback, importe o arquivo ddl/XCXP_BAL_MODELO_COMPLETO.sql pelo recurso de Import DDL do Data Modeler. Isso recria o modelo relacional; o .dmd é a entrega principal porque também contém o modelo lógico e a ligação 1:N.

OBSERVAÇÃO SOBRE O PATCH
-------------------------
O material recebido descreve o padrão de um patch corporativo de referência, mas o arquivo desse patch não veio anexado nesta conversa. Por isso:
- .tab/.seq seguem o padrão descrito (SQL*Plus, SPOOL, constraints após CREATE TABLE, auditoria e AD_ZD_TABLE.UPGRADE);
- o .drv foi entregue explicitamente como EXEMPLO;
- cabeçalho e sintaxe exata de forcecopy/sql do .drv devem ser confrontados com o patch corporativo antes de uso oficial.

O bloco AD_ZD_TABLE.UPGRADE nos .tab também está marcado para confirmação contra esse patch de referência.
