# Guía Rápida: Ejecutar Todo el Proyecto

## Paso 1: Iniciar la Aplicación Spring Boot

Abre una terminal y ejecuta:

```powershell
cd c:\progra3\progra3
.\mvnw.cmd spring-boot:run
```

**Espera a ver este mensaje:**
```
Started DemoApplication in X.XXX seconds
```

> ⏱️ **Nota**: La primera vez puede tardar 2-3 minutos porque Maven descarga dependencias.

## Paso 2: Cargar Datos de Ejemplo

Una vez que la aplicación esté corriendo, abre **otra terminal** y ejecuta:

```powershell
cd c:\progra3\progra3
.\cargar-datos.ps1
```

O manualmente:
```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/data/load-sample -Method POST -UseBasicParsing
```

## Paso 3: Verificar que Funcionó

```powershell
# Ver todas las ubicaciones cargadas
Invoke-WebRequest -Uri http://localhost:8080/api/data/locations -UseBasicParsing
```

## Paso 4: Visualizar en Neo4j Aura Console

1. Ve a: **https://console-preview.neo4j.io/tools/explore**
2. Inicia sesión con tus credenciales
3. Selecciona tu instancia (ID: 6a1e8244)
4. Deberías ver:
   - **8 nodos** (ciudades)
   - **20 relaciones** (rutas)

### Consulta en Neo4j Browser:
```cypher
MATCH (a:Location)-[r:CONNECTED_TO]->(b:Location) 
RETURN a, r, b
```

## Probar Algoritmos

Una vez cargados los datos, puedes probar los algoritmos:

```powershell
# BFS
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/bfs?start=Buenos Aires&maxDistance=500" -UseBasicParsing

# Dijkstra
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/dijkstra?start=Buenos Aires&end=Córdoba" -UseBasicParsing

# Ver todos los algoritmos disponibles
Invoke-WebRequest -Uri "http://localhost:8080/api/algorithms/info" -UseBasicParsing
```

## Solución de Problemas

### Si la aplicación no inicia:
- Verifica que Java 17 esté instalado: `java -version`
- Verifica que el puerto 8080 esté libre
- Revisa los logs en la consola

### Si no se pueden cargar datos:
- Verifica que la aplicación esté corriendo: `http://localhost:8080/api/test/health`
- Verifica la conexión a Neo4j en `application.properties`
- Revisa los logs de la aplicación

### Si no ves datos en Neo4j Console:
- Espera unos segundos después de cargar (puede haber delay)
- Refresca la página del explorador
- Verifica que estés conectado a la instancia correcta

