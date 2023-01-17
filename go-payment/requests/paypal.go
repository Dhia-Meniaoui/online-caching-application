package requests

type Paypal struct {
	Total       string `json:"total"`
	Currency    string `json:"currency"`
	RedirectUri string `json:"redirectUri"`
	CancelUri   string `json:"cancelUri"`
}
