# Frontend/Dockerfile
FROM node:18-alpine

WORKDIR /app
COPY . .

RUN npm install
RUN npm run build

# Si usás algo como serve para servir el build
RUN npm install -g serve
CMD ["serve", "-s", "build"]

EXPOSE 3000
