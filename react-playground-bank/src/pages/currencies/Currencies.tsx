import './CurrenciesStyles.css';
import './LoadingStyles.css';
import { useState, useEffect } from 'react';

interface Currency {
  currencyCode: string;
  currencyName: string;
  currencySymbol: string;
  metalType: string;
}


function Currencies() {
  	
  const [currencies, setCurrencies] = useState<Currency[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Fetch currencies from Spring Boot API
  useEffect(() => {
    const fetchCurrencies = async () => {
      try {
        setLoading(true);
        const response = await fetch('http://localhost:8080/api/v1/currencies');

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        setCurrencies(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch currencies');
        console.error('Error fetching currencies:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchCurrencies();
  }, [])



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



  // Loading state
  if (loading) {
    return (
      <div className="currencies-container">
        <div className="loading-state">
          <div className="loading-spinner" data-testid="loading-spinner"></div>
          <p>Loading currencies...</p>
        </div>
      </div>
    );
  }


  // Error state
    if (error) {
    return (
      <div className="currencies-container">
        <div className="error-state">
          <div className="error-icon">⚠️</div>
          <h2 data-testid="pageTitle">Failed to Load Currencies</h2>
          <p>{error}</p>
          <button 
            className="retry-btn" data-testid="retryBtn"
            onClick={() => window.location.reload()}
          >
            RETRY
          </button>
        </div>
      </div>
    );
  }


  // Empty state
  if (currencies.length === 0) {
    return (
      <div className="currencies-container">
        <div className="empty-state">
          <div className="empty-icon">💰</div>
          <h3 data-testid="pageTitle">No Currencies Found</h3>
          <p>No currency data is currently available</p>
        </div>
      </div>
    );
  }



  return (
    <div className="currencies-container">
      <h1 className="page-title" data-testid="pageTitle">City Currencies</h1>
      
      <div className="currency-overview">
        <div className="overview-header">
          <h2 data-testid="exchangeRateTitle">MONETARY SYSTEM</h2>
          <div className="exchange-rates">
            <span className="rate-label" data-testid="exchangeRateLabel">OFFICIAL EXCHANGE:</span>
            <span className="rate-value" data-testid="exchangeRateValue">1 GOLD = 20 SILVER = 240 COPPER</span>
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
                <div className="currency-symbol" data-testid="currencySymbol">
                  {currency.currencySymbol}
                </div>
              </div>
              
              <div className="currency-info">
                <h2 data-testid="currencyName">{currency.currencyName}</h2>
                <span className="currency-code" data-testid="currencyCode">{currency.currencyCode}</span>
              </div>
            </div>
            
            <div className="currency-body">
              <div className="currency-stats">
                <div className="stat-row">
                  <span className="stat-label" data-testid="currencyMetalTypeLabel">METAL TYPE:</span>
                  <span className="stat-value" data-testid="currencyMetalTypeValue">{currency.metalType}</span>
                </div>
              </div>
            </div>

          </div>
        ))}
      </div>
      
      <div className="currency-footer-note">
        <div className="warning-note" data-testid="warningNote">
          <span className="warning-icon">⚠️</span>
          COUNTERFEITING PUNISHABLE BY CRAGSLEFT IMPRISONMENT
        </div>
      </div>
    </div>
  )
}

export default Currencies;