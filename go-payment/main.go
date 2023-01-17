package main

import (
	"context"
	"github.com/gin-gonic/gin"
	"github.com/joho/godotenv"
	"github.com/netlify/PayPal-Go-SDK"
	"kastouri/payment-api/controllers"
	"kastouri/payment-api/services"
	"log"
	"os"
)

var (
	server           *gin.Engine
	ctx              context.Context
	client           *paypalsdk.Client
	paypalController *controllers.Paypal
	paypalService    *services.Paypal
	logger           *log.Logger
)

func init() {
	godotenv.Load()
	logger = log.Default()
	ctx = context.TODO()
	server = gin.Default()
	client, err := paypalsdk.NewClient(os.Getenv("PAYPAL_CLIENT_ID"), os.Getenv("PAYPAL_CLIENT_SECRET"), paypalsdk.APIBaseSandBox)
	if err != nil {
		log.Println("can not resolve env file")
	}
	_, err = client.GetAccessToken()
	if err != nil {
		log.Println("can't get access token")
	}
	paypalService = services.NewPaypalService(client, logger)
	paypalController = controllers.NewPaypalController(paypalService, logger)

}
func main() {
	basePath := server.Group("/v1")
	paypalController.RegisterRoutes(basePath)
	log.Fatalf(server.Run(":9090").Error())
}
