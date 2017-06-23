#Show PIC
## How to deploy
- Change Tomcat configurations
**server.xml**
Configure docBase:
```xml
<Host appBase="webapps" autoDeploy="true" name="localhost" unpackWARs="true">
	<Context path="/showpic" docBase="C:\D\eclipse\workspace\sts\showpic\src\main\webapp" />
	...
</Host>
```
Configure UTF-8 get parameter:
```xml
<Connector port="8080" maxThreads="150" minSpareThreads="25" maxSpareThreads="75"
    enableLookups="false" redirectPort="8443" acceptCount="100"
    connectionTimeout="20000" disableUploadTimeout="true" URIEncoding='UTF-8' />
```

- Change DB configuration
**application-db.xml**
Update DB file path:
```xml
<property name="url" value="jdbc:sqlite:C:\\D\\eclipse\\workspace\\sts\\showpic\\src\\main\\webapp\\db\\showpic.db" />
```