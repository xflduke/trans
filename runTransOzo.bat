cd %~dp0
java -Dfile.encoding=UTF-8 -Djdk.http.auth.tunneling.disabledSchemes="" -jar transToOzo.jar transSystem.properties
pause