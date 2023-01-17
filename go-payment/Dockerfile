FROM golang:1.18-alpine

WORKDIR /app
COPY . .

RUN go mod download
RUN go mod tidy
RUN go build -o /docker-gs-ping

EXPOSE 9090

CMD [ "/docker-gs-ping" ]