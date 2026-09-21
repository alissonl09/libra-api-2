# Modelo de Dados — Integração de Acessos de Pesagem

## Escopo

Oracle Database 19c / Oracle EBS R12, owner `XXBALANCA`. O modelo contempla EPA, XFER e OM. RD está fora do escopo atual.

## Modelo lógico

```text
ACESSO 1 ───────────< N ACESSO_LINHA
  |                         |
  | visita/pesagem          | documento ERP individual
  |                         |
  +-- pode consolidar       +-- pertence obrigatoriamente a um ACESSO
      vários EPA/DI
```

### ACESSO
Representa uma visita/pesagem que será integrada ao SMART 1. É o snapshot consolidado dos dados necessários para a pesagem e contém o ciclo técnico de integração.

### ACESSO_LINHA
Representa os documentos individuais do ERP que originaram o acesso. Os IDs internos de EPA/XFER/OM permanecem aqui e não fazem parte do contrato SMART.

## Modelo relacional

- `XXBALANCA.XCXP_BAL_ACESSO_INT` — cabeçalho e fila de integração Oracle → Libra → SMART 1.
- `XXBALANCA.XCXP_BAL_ACESSO_LINHA` — linhas/documentos do ERP, relação obrigatória N:1 para o cabeçalho.

## Colunas — XCXP_BAL_ACESSO_INT

| Coluna | Significado | Oracle | NULL? | Chave/default |
|---|---|---|---|---|
| `ID_ACESSO` | Identificador técnico da visita/pesagem no Oracle. | `NUMBER(15)` | NÃO | PK |
| `CD_UNIDADE_BALANCA` | Código da unidade onde a pesagem será realizada. | `NUMBER(10)` | NÃO | — |
| `CD_PLACA_VEICULO` | Placa do cavalo/veículo utilizada para localizar o acesso. | `VARCHAR2(10)` | NÃO | — |
| `TP_DOCUMENTO` | Processo de origem atualmente contemplado: EPA, XFER ou OM. | `VARCHAR2(10)` | NÃO | — |
| `TP_OPERACAO` | Momento da operação; usado em XFER para SAIDA ou ENTRADA. | `VARCHAR2(10)` | SIM | — |
| `DS_NOTA` | Descrição textual consolidada dos documentos apresentados à balança; não é chave. | `VARCHAR2(2000)` | NÃO | — |
| `DS_EMISSOR` | Descrição/nome consolidado do emissor. | `VARCHAR2(240)` | NÃO | — |
| `NM_MOTORISTA` | Nome do motorista, quando disponível. | `VARCHAR2(240)` | SIM | — |
| `NM_TRANSPORTADOR` | Nome/descrição do transportador, quando disponível. | `VARCHAR2(240)` | SIM | — |
| `QT_TOTAL` | Quantidade ou peso previsto consolidado para a pesagem. | `NUMBER(15,3)` | NÃO | — |
| `DS_ACONDICIONAMENTO` | Acondicionamento consolidado, por exemplo BAG, GRANEL ou BAG / GRANEL; pode ser nulo para OM. | `VARCHAR2(50)` | SIM | — |
| `DS_DESTINO` | Descrição genérica de destino: organização de recebimento, destinatário ou cliente conforme o processo. | `VARCHAR2(500)` | NÃO | — |
| `NR_SEQUENCIA` | Reservado para integrações futuras; inicialmente gravado como NULL. | `NUMBER(15)` | SIM | — |
| `NR_TICKET_ORIGEM` | Ticket válido da pesagem de saída; obrigatório por regra somente em XFER ENTRADA. | `NUMBER(15)` | SIM | — |
| `CD_UNIDADE_ORIGEM` | Código da unidade de origem; obrigatório por regra em XFER ENTRADA. | `NUMBER(10)` | SIM | — |
| `PS_LIQUIDO_ORIGEM` | Peso líquido apurado na origem; obrigatório por regra em XFER ENTRADA. | `NUMBER(15,3)` | SIM | — |
| `CD_SITUACAO_INTEGRACAO` | Estado multivalorado do ciclo Oracle → Libra → SMART 1. | `VARCHAR2(30)` | NÃO | DEFAULT 'PENDENTE' |
| `QT_TENTATIVA` | Quantidade de tentativas de integração realizadas. | `NUMBER(5)` | NÃO | DEFAULT 0 |
| `DT_ULTIMA_TENTATIVA` | Data/hora da tentativa de integração mais recente. | `DATE` | SIM | — |
| `DT_PROXIMA_TENTATIVA` | Data/hora a partir da qual um erro reprocessável pode ser tentado novamente. | `DATE` | SIM | — |
| `DT_INICIO_PROCESSAMENTO` | Momento em que o registro foi reservado/marcado como PROCESSANDO; permite detectar processamento abandonado. | `DATE` | SIM | — |
| `DT_CONCLUSAO_INTEGRACAO` | Data/hora em que a integração foi concluída com sucesso. | `DATE` | SIM | — |
| `CD_ULTIMO_ERRO` | Código técnico do último erro de integração, quando aplicável. | `VARCHAR2(100)` | SIM | — |
| `DS_ULTIMO_ERRO` | Mensagem/descrição do último erro de integração. | `VARCHAR2(4000)` | SIM | — |
| `ID_ACESSO_SMART` | Identificador externo retornado pelo SMART 1, caso o contrato o forneça. | `VARCHAR2(100)` | SIM | — |
| `DT_CRIACAO` | Data de criação do registro. | `DATE` | NÃO | — |
| `NM_USUARIO_CRIACAO` | Usuário responsável pela criação do registro. | `VARCHAR2(100)` | NÃO | — |
| `DT_ALTERACAO` | Data da última alteração do registro. | `DATE` | NÃO | — |
| `NM_USUARIO_ALTERACAO` | Usuário responsável pela última alteração do registro. | `VARCHAR2(100)` | NÃO | — |

## Colunas — XCXP_BAL_ACESSO_LINHA

| Coluna | Significado | Oracle | NULL? | Chave/default |
|---|---|---|---|---|
| `ID_ACESSO_LINHA` | Identificador técnico da linha/documento do acesso. | `NUMBER(15)` | NÃO | PK |
| `ID_ACESSO` | Acesso ao qual o documento pertence; participação obrigatória. | `NUMBER(15)` | NÃO | FK → XCXP_BAL_ACESSO_INT.ID_ACESSO |
| `TP_DOCUMENTO` | Tipo do documento ERP de origem: EPA, XFER ou OM. | `VARCHAR2(10)` | NÃO | — |
| `ID_DOCUMENTO_ORIGEM` | ID técnico interno do ERP (ID_ENTRADA, ID_TRANSFERENCIA ou DELIVERY_ID). Não integra o contrato SMART. | `NUMBER(15)` | NÃO | — |
| `NR_DOCUMENTO_ORIGEM` | Número funcional: NR_ENTRADA, NR_PLANEJAMENTO ou número da DI. | `VARCHAR2(100)` | NÃO | — |
| `NR_DOCUMENTO_REFERENCIA` | Referência complementar: NF/RD, OC/viagem ou PV, conforme processo. | `VARCHAR2(100)` | SIM | — |
| `DT_CRIACAO` | Data de criação do registro. | `DATE` | NÃO | — |
| `NM_USUARIO_CRIACAO` | Usuário responsável pela criação do registro. | `VARCHAR2(100)` | NÃO | — |
| `DT_ALTERACAO` | Data da última alteração do registro. | `DATE` | NÃO | — |
| `NM_USUARIO_ALTERACAO` | Usuário responsável pela última alteração do registro. | `VARCHAR2(100)` | NÃO | — |

## Regras EPA / XFER / OM validadas no modelo

| Regra | Tratamento no modelo |
|---|---|
| Vários EPAs podem formar um acesso | 1:N entre ACESSO e ACESSO_LINHA; nenhuma UK que impeça múltiplas linhas |
| Várias DIs podem formar um acesso OM | Mesmo tratamento 1:N |
| XFER SAÍDA e ENTRADA são acessos distintos | `TP_OPERACAO` no cabeçalho, sem UK simplista por `ID_TRANSFERENCIA` |
| `NR_TICKET_ORIGEM`, `CD_UNIDADE_ORIGEM`, `PS_LIQUIDO_ORIGEM` são condicionais | Permanecem fisicamente NULLABLE; validação de obrigatoriedade fica na regra de negócio para XFER ENTRADA |
| OM pode não ter acondicionamento | `DS_ACONDICIONAMENTO` fisicamente NULLABLE |
| `NR_SEQUENCIA` é futuro | Campo existente e NULLABLE, sem default |
| IDs ERP não devem ir ao SMART | `ID_DOCUMENTO_ORIGEM` existe somente em ACESSO_LINHA |
| Não inventar UK | Nenhuma UK de negócio foi criada nesta versão |

## Decisões de datatype/tamanho

- IDs técnicos: `NUMBER(15)`, seguindo o padrão informado para objetos EBS do módulo.
- Pesos/quantidades: `NUMBER(15,3)`, preservando casas decimais e ampla faixa operacional.
- Placa: `VARCHAR2(10)`, suficiente para formatos com/sem separador e pequena folga.
- Nomes/emissor/transportador: `VARCHAR2(240)`, tamanho convencional e confortável para descrições EBS.
- `DS_NOTA`: `VARCHAR2(2000)` por poder concatenar múltiplos documentos.
- `DS_ULTIMO_ERRO`: `VARCHAR2(4000)` para preservar mensagem técnica sem introduzir CLOB.
- Números funcionais de documentos na linha: `VARCHAR2(100)` para não pressupor que todos os processos permaneçam estritamente numéricos.
- Datas técnicas: `DATE`, coerente com o padrão de auditoria informado.

**Pontos que devem ser homologados com o patch corporativo/dados reais:** tamanhos de códigos de unidade, textos e eventual natureza numérica de `NR_TICKET_ORIGEM`. Esses pontos não estavam definidos de forma explícita no material recebido.

## Integração e concorrência

Situações conceituais previstas: `PENDENTE`, `PROCESSANDO`, `INTEGRADO`, `ERRO_REPROCESSAVEL`, `ERRO_DEFINITIVO`, `CANCELADO`. Não foi criada CHECK CONSTRAINT para congelar esse domínio.

Fluxo recomendado de reserva:

```sql
SELECT ID_ACESSO
  FROM XXBALANCA.XCXP_BAL_ACESSO_INT
 WHERE CD_SITUACAO_INTEGRACAO IN ('PENDENTE','ERRO_REPROCESSAVEL')
   AND (DT_PROXIMA_TENTATIVA IS NULL OR DT_PROXIMA_TENTATIVA <= SYSDATE)
 ORDER BY ID_ACESSO
 FOR UPDATE SKIP LOCKED;
```

Após reservar: marcar `PROCESSANDO`, preencher `DT_INICIO_PROCESSAMENTO` e `COMMIT`; somente depois chamar o HTTP. Assim o lock Oracle não permanece durante a chamada externa. Registros `PROCESSANDO` antigos podem ser identificados por `DT_INICIO_PROCESSAMENTO`.

## Idempotência

Não foi adicionada uma coluna artificial de token nem uma UK de negócio nesta versão. O `ID_ACESSO` é a identidade técnica estável do acesso e deve ser reutilizado em retentativas. A regra definitiva de prevenção de duplicidade deve ser fechada por processo antes de virar constraint física.

## Índices propostos

- `XCXP_BAL_ACESSO_INT_PK`: PK em `ID_ACESSO`.
- `XCXP_BAL_ACESSO_INT_I01`: `(CD_SITUACAO_INTEGRACAO, DT_PROXIMA_TENTATIVA, ID_ACESSO)` para fila/retentativa.
- `XCXP_BAL_ACESSO_INT_I02`: `ID_ACESSO_SMART` para correlação com o sistema externo.
- `XCXP_BAL_ACESSO_LINHA_PK`: PK em `ID_ACESSO_LINHA`.
- `XCXP_BAL_ACESSO_LINHA_I01`: `ID_ACESSO` para a FK/join cabeçalho-linhas.
- `XCXP_BAL_ACESSO_LINHA_I02`: `(TP_DOCUMENTO, ID_DOCUMENTO_ORIGEM)` para localizar documentos ERP e apoiar a futura regra de idempotência.

Não foram utilizados bitmap indexes.

## Sequences

- `XXBALANCA.XCXP_BAL_ACESSO_INT_S`
- `XXBALANCA.XCXP_BAL_ACESSO_LINHA_S`

Nenhuma coluna usa `IDENTITY`.

## Constraints

- `XCXP_BAL_ACESSO_INT_PK`
- `XCXP_BAL_ACESSO_LINHA_PK`
- `XCXP_BAL_ACESSO_LINHA_FK1`: `ID_ACESSO` → `XCXP_BAL_ACESSO_INT.ID_ACESSO`

As constraints são emitidas depois dos `CREATE TABLE`. Não há UK de negócio nesta versão.
