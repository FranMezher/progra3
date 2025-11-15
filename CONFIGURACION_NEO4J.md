# Configuración de Neo4j Aura

## Cómo encontrar tus credenciales

### Opción 1: Panel de Neo4j Aura
1. Ve a https://console.neo4j.io/
2. Inicia sesión con tu cuenta
3. Selecciona tu instancia de base de datos Aura
4. Haz clic en "Connection Details" o "Connect"
5. Copia la información de conexión:
   - **URI**: Formato `neo4j+s://xxxxx.databases.neo4j.io`
   - **Username**: Generalmente `neo4j`
   - **Password**: La contraseña que se mostró al crear la instancia

### Opción 2: Archivo de credenciales descargado
Si tienes el archivo de credenciales descargado cuando creaste la instancia:
- Busca en tu carpeta de Descargas un archivo `.txt` o similar
- Contiene la información de conexión completa

### Opción 3: Crear nueva instancia
Si perdiste la contraseña:
1. Ve al panel de Neo4j Aura
2. Crea una nueva instancia
3. **IMPORTANTE**: Descarga y guarda el archivo de credenciales que se genera

## Configuración en application.properties

Edita el archivo `src/main/resources/application.properties` y reemplaza:

```properties
spring.neo4j.uri=neo4j+s://TU-INSTANCIA.databases.neo4j.io
spring.neo4j.authentication.username=neo4j
spring.neo4j.authentication.password=TU_CONTRASEÑA_AQUI
```

## Verificar la conexión

1. Inicia la aplicación Spring Boot
2. Abre tu navegador y ve a: `http://localhost:8080/api/test/health`
3. Si la conexión es exitosa, verás un mensaje con el estado "connected"

## Formato de URI Neo4j Aura

- **Aura Free**: `neo4j+s://xxxxx.databases.neo4j.io`
- **Aura Professional**: `neo4j+s://xxxxx.databases.neo4j.io`
- El protocolo `neo4j+s://` es para conexiones seguras (requerido para Aura)

