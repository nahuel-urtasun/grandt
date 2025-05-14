FROM node:18-alpine AS build

WORKDIR /app

# Copiar solo los archivos de dependencias
COPY package*.json ./

# Instalar dependencias
RUN npm install

# Copiar el resto del código
COPY . .

# Construir el proyecto
RUN npm run build

FROM node:18-alpine

WORKDIR /app

# Copiar solo los archivos necesarios del contenedor anterior
COPY --from=build /app/build /app/build

# Instalar serve globalmente
RUN npm install -g serve

# Servir la aplicación
CMD ["serve", "-s", "build"]

EXPOSE 3000
