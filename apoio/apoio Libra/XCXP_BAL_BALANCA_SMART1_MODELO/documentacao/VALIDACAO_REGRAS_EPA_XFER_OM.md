# Validação das regras de negócio antes do DDL

## EPA
- `ID_ENTRADA` é representado por `ACESSO_LINHA.ID_DOCUMENTO_ORIGEM`.
- `NR_ENTRADA` é representado por `NR_DOCUMENTO_ORIGEM`.
- NF/RD, quando aplicável, cabe em `NR_DOCUMENTO_REFERENCIA`.
- Vários EPAs podem compor o mesmo `ID_ACESSO`.
- Não foi criada UK que impeça esse agrupamento.

## XFER
- `ID_TRANSFERENCIA` fica em `ID_DOCUMENTO_ORIGEM`.
- `NR_PLANEJAMENTO` fica em `NR_DOCUMENTO_ORIGEM`.
- OC/viagem fica em `NR_DOCUMENTO_REFERENCIA`.
- SAÍDA e ENTRADA são diferenciadas em `ACESSO_INT.TP_OPERACAO`.
- Ticket/unidade/peso de origem são NULLABLE fisicamente e obrigatórios apenas por regra de negócio na ENTRADA.

## OM
- `DELIVERY_ID` fica em `ID_DOCUMENTO_ORIGEM`.
- número da DI fica em `NR_DOCUMENTO_ORIGEM`.
- número do PV fica em `NR_DOCUMENTO_REFERENCIA`.
- Várias DIs podem compor o mesmo acesso.
- `DS_ACONDICIONAMENTO` permanece NULLABLE.

## Resultado
A estrutura proposta é compatível com as regras informadas sem introduzir UK ou campos de negócio não definidos.
