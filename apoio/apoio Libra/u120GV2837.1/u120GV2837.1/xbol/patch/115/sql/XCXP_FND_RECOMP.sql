WHENEVER SQLERROR EXIT FAILURE ROLLBACK
CONNECT &1/&2
SET DEF OFF
SET SERVEROUTPUT ON
SET SERVEROUTPUT ON SIZE 100000
SET WRAP OFF
SET HEADING OFF
SET FEEDBACK OFF
SET VERIFY OFF
SET LINES 300
SPOOL XCXP_FND_RECOMP.sql.log

PROMPT Versao: $Version: XCXP_FND_RECOMP.sql 120.1 23/01/23 17:43:35 appldev ship $

-- $Version: XCXP_FND_RECOMP.sql 120.1 23/01/23 17:43:35 appldev ship $
-- +===================================================================+
-- |                  Copyright (c) 2016 COOXUPE                       |
-- |                     All rights reserved.                          |
-- +===================================================================+
-- | FILENAME                                                          |
-- |   XCXP_FND_RECOMP.sql                                             |
-- |                                                                   |
-- | DESCRIPTION                                                       |
-- |   recompile                                                       |
-- |                                                                   |
-- +===================================================================+

BEGIN
dbms_output.put_line('');
END;
/

DECLARE
 t NUMBER := 0;
BEGIN
  --
  SELECT count(*) AS invalidos
    INTO t
    FROM all_objects
   WHERE status = 'INVALID'
     AND owner IN ('APPS', 'APPLSYS', 'BOLINF');
  --
  dbms_output.put_line('Total de invalidos: ' || t);
  --
EXCEPTION WHEN OTHERS THEN 
  NULL;
END;
/

BEGIN
dbms_output.put_line('Recompilando invalidos, aguarde...');
END;
/

exec sys.utl_recomp.recomp_parallel(2);
exec sys.utl_recomp.recomp_parallel(2);

BEGIN
dbms_output.put_line('Recompilacao concluida.');
END;
/

DECLARE
 t NUMBER := 0;
BEGIN
  --
  SELECT count(*) AS invalidos
    INTO t
    FROM all_objects
   WHERE status = 'INVALID'
     AND owner IN ('APPS', 'APPLSYS', 'BOLINF');
  --
  dbms_output.put_line('Total de invalidos: ' || t);
  --
EXCEPTION WHEN OTHERS THEN 
  NULL;
END;
/

BEGIN
dbms_output.put_line('');
END;
/
BEGIN
dbms_output.put_line('');
END;
/
BEGIN
dbms_output.put_line('');
END;
/

spool off

EXIT;
