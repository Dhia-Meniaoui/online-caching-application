package response

type Paypal struct {
	PaymentId   string `json:"paymentId"`
	SuccessLink string `json:"successLink"`
	Type        string `json:"type"`
}
