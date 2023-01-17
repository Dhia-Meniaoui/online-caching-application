package dto

type Payment struct {
	PayerId   string `json:"payerId"`
	PaymentId string `json:"paymentId"`
	Amount    string `json:"amount"`
	Currency  string `json:"currency"`
}

func (p *Payment) setPayerId(pid string) {
	p.PayerId = pid
}
func (p *Payment) setAmount(a string) {
	p.Amount = a
}
func (p *Payment) setCurrency(c string) {
	p.Currency = c
}
