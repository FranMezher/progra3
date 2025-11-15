# Instrucciones para Cargar y Visualizar Datos en Neo4j Aura

## Paso 1: Iniciar la Aplicación Spring Boot

En una terminal, ejecuta:
```bash
cd c:\progra3\progra3
.\mvnw.cmd spring-boot:run
```

Espera a que la aplicación inicie completamente (verás "Started DemoApplication" en la consola).

## Paso 2: Cargar Datos de Ejemplo

Una vez que la aplicación esté corriendo, en otra terminal o usando Postman/curl:

### Opción A: Usando PowerShell
```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/data/load-sample -Method POST -UseBasicParsing
```

### Opción B: Usando curl (si está disponible)
```bash
curl -X POST http://localhost:8080/api/data/load-sample
```

### Opción C: Usando Postman o cualquier cliente REST
- Método: POST
- URL: http://localhost:8080/api/data/load-sample
- Headers: (ninguno necesario)

## Paso 3: Verificar que los Datos se Cargaron

```powershell
Invoke-WebRequest -Uri http://localhost:8080/api/data/locations -UseBasicParsing
```

Deberías ver una lista de las 8 ciudades cargadas.

## Paso 4: Visualizar en Neo4j Aura Console

1. Ve a: https://console-preview.neo4j.io/tools/explore
2. Inicia sesión con tus credenciales de Neo4j Aura
3. Selecciona tu instancia (ID: 6a1e8244)
4. En el explorador de grafos, deberías ver:
   - **8 nodos** de tipo "Location" (ciudades)
   - **20 relaciones** de tipo "CONNECTED_TO" (rutas bidireccionales)

## Datos que se Cargarán

### Ubicaciones (8 ciudades):
- Buenos Aires
- Córdoba
- Rosario
- Mendoza
- La Plata
- Mar del Plata
- Tucumán
- Salta

### Rutas (20 conexiones bidireccionales):
- Buenos Aires ↔ Córdoba (700 km)
- Buenos Aires ↔ Rosario (300 km)
- Buenos Aires ↔ La Plata (60 km)
- Buenos Aires ↔ Mar del Plata (400 km)
- Rosario ↔ Córdoba (400 km)
- Córdoba ↔ Mendoza (400 km)
- Córdoba ↔ Tucumán (500 km)
- Tucumán ↔ Salta (200 km)
- La Plata ↔ Mar del Plata (400 km)
- Rosario ↔ La Plata (350 km)

## Consultas Útiles en Neo4j Browser

Una vez en Neo4j Aura Console, puedes ejecutar estas consultas:

### Ver todas las ubicaciones:
```cypher
MATCH (l:Location) RETURN l
```

### Ver todas las rutas:
```cypher
MATCH (a:Location)-[r:CONNECTED_TO]->(b:Location) 
RETURN a, r, b
```

### Ver el grafo completo:
```cypher
MATCH (a:Location)-[r:CONNECTED_TO]->(b:Location) 
RETURN a, r, b
LIMIT 50
```

### Contar nodos y relaciones:
```cypher
MATCH (l:Location) RETURN count(l) as totalLocations
UNION ALL
MATCH ()-[r:CONNECTED_TO]->() RETURN count(r) as totalRoutes
```

## Solución de Problemas

### Si no ves los datos en Neo4j Console:
1. Verifica que la aplicación Spring Boot esté corriendo
2. Verifica que los datos se cargaron correctamente usando el endpoint `/api/data/locations`
3. Asegúrate de estar conectado a la instancia correcta en Neo4j Aura Console
4. Espera unos segundos después de cargar los datos (puede haber un pequeño delay)

### Si hay errores al cargar:
- Verifica la conexión a Neo4j Aura en `application.properties`
- Revisa los logs de la aplicación Spring Boot
- Asegúrate de que la instancia de Neo4j Aura esté activa

