import './CurrenciesStyles.css';


interface Currency {
  currencyCode: string;
  currencyName: string;
  symbol: string;
}


function Currencies() {

  const currencies: Currency[] = [
    {currencyCode: 'BBC-123', currencyName: 'Gold', symbol: '₲'},
    {currencyCode: 'BBC-321', currencyName: 'Silver', symbol: '₴'},
    {currencyCode: 'BBC-555', currencyName: 'Copper', symbol: '₡'}
  ];

  const getCoinColor = (currencyName: string): string => {
    const colors: Record<string, string> = {
      'Gold': '#ffd700',
      'Silver': '#c0c0c0', 
      'Copper': '#cd7f32'
    };
    return colors[currencyName] || '#8b8b8b';
  };

  const getCoinIcon = (currencyName: string): string => {
    const icons: Record<string, string> = {
      'Gold': '👑',
      'Silver': '⚓',
      'Copper': '⚙️'
    };
    return icons[currencyName] || '●';
  };

  return (
    <div className="currencies-container">
      <h1 className="page-title">City Currencies</h1>
      
      <div className="currency-overview">
        <div className="overview-header">
          <h2>MONETARY SYSTEM</h2>
          <div className="exchange-rates">
            <span className="rate-label">OFFICIAL EXCHANGE:</span>
            <span className="rate-value">1 GOLD = 20 SILVER = 240 COPPER</span>
          </div>
        </div>
      
      </div>

      <div className="currencies-grid">
        {currencies.map(currency => (
          <div key={currency.currencyCode} className="currency-card" style={{borderLeftColor: getCoinColor(currency.currencyName)}}>
            <div className="currency-header">
              <div className="coin-display">
                <div className="coin-icon" style={{backgroundColor: getCoinColor(currency.currencyName)}}>
                  {getCoinIcon(currency.currencyName)}
                </div>
                <div className="currency-symbol">
                  {currency.symbol}
                </div>
              </div>
              
              <div className="currency-info">
                <h2>{currency.currencyName}</h2>
                <span className="currency-code">{currency.currencyCode}</span>
              </div>
            </div>
            
            <div className="currency-body">
              <div className="currency-stats">
                <div className="stat-row">
                  <span className="stat-label">METAL TYPE:</span>
                  <span className="stat-value">{currency.currencyName}</span>
                </div>
              </div>
            </div>

          </div>
        ))}
      </div>
      
      <div className="currency-footer-note">
        <div className="warning-note">
          <span className="warning-icon">⚠️</span>
          COUNTERFEITING PUNISHABLE BY CRAGSLEFT IMPRISONMENT
        </div>
      </div>
    </div>
  )
}

export default Currencies;