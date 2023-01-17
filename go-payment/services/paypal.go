package services

import (
	paypalsdk "github.com/netlify/PayPal-Go-SDK"
	dto "kastouri/payment-api/dto"
	"kastouri/payment-api/requests"
	"kastouri/payment-api/response"
	"log"
)

type Paypal struct {
	client *paypalsdk.Client
	logger *log.Logger
}

func NewPaypalService(c *paypalsdk.Client, l *log.Logger) *Paypal {
	return &Paypal{
		client: c,
		logger: l,
	}
}
func (s *Paypal) CreatePayment(request requests.Paypal) (response.Paypal, error) {
	amount := paypalsdk.Amount{
		Total:    request.Total,
		Currency: request.Currency,
	}
	var payment *paypalsdk.PaymentResponse
	var err error
	payment, err = s.client.CreateDirectPaypalPayment(amount, request.RedirectUri, request.CancelUri, "")
	if err != nil {
		s.logger.Println(err)
		return response.Paypal{}, err
	}
	var successLink string
	for _, link := range payment.Links {
		if link.Rel == "approval_url" {
			successLink = link.Href
		}
	}

	creationResponse := response.Paypal{
		PaymentId:   payment.ID,
		SuccessLink: successLink,
	}
	return creationResponse, nil

}
func (s *Paypal) ExecutePayment(paymentID, payerID string) (error, *dto.Payment) {
	p, err := s.client.ExecuteApprovedPayment(paymentID, payerID)
	if err != nil {
		s.logger.Println(err.Error())
		return err, nil
	}
	amount := p.Transactions[0].Amount
	payment := dto.Payment{
		PaymentId: paymentID,
		PayerId:   payerID,
		Amount:    amount.Total,
		Currency:  amount.Currency,
	}
	return nil, &payment
}
