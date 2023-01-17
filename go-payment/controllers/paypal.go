package controllers

import (
	"github.com/gin-gonic/gin"
	"kastouri/payment-api/requests"
	"kastouri/payment-api/services"
	"log"
)

type Paypal struct {
	service *services.Paypal
	logger  *log.Logger
}

func NewPaypalController(s *services.Paypal, l *log.Logger) *Paypal {
	return &Paypal{
		service: s,
		logger:  l,
	}
}
func (c *Paypal) CreatePayment(ctx *gin.Context) {
	var paypalRequest requests.Paypal
	err := ctx.BindJSON(&paypalRequest)
	if err != nil {
		return
	}
	response, err := c.service.CreatePayment(paypalRequest)
	if err != nil {
		ctx.JSON(400, gin.H{"message": err.Error()})
		return
	}
	c.logger.Println(response)

	ctx.JSON(200, response)
}

func (c *Paypal) ExecutePayment(ctx *gin.Context) {
	payerID := ctx.Query("PayerID")
	paymentID := ctx.Query("paymentId")
	err, p := c.service.ExecutePayment(paymentID, payerID)
	if err != nil {
		ctx.JSON(500, gin.H{"error": err.Error()})
	}
	ctx.JSON(200, p)
}

func (c *Paypal) RegisterRoutes(rg *gin.RouterGroup) {
	paypalRoute := rg.Group("/paypal")
	paypalRoute.POST("/create", c.CreatePayment)
	paypalRoute.GET("/execute", c.ExecutePayment)
}
