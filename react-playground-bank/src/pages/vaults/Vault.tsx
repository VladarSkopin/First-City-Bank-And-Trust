import './VaultStyles.css';
import './VaultModalStyles.css';
import { useState, useEffect } from 'react';


interface Vault {
  vaultCode: string;
  clientCode: string;
  amount: string; // BigInteger returns as string from JSON
  createdAt: string;
  modifiedAt: string;
  currencyCode: string;
  isArchived: boolean;
}

interface Client {
  clientCode: string;
  nameOrTitle: string;
  clientTypeCode: string;
  socialRankCode: string;
  districtCode: string;
  isBlocked: boolean;
}

interface Currency {
  currencyCode: string;
  currencyName: string;
  metalType?: string;
}

interface VaultDisplayData {
  vaultCode: string;
  clientCode: string;
  clientName: string;
  clientTitle: string;
  createdAt: string;
  modifiedAt: string;
  amount: number; // Converted from string to number for display
  currencyCode: string;
  currencyName: string;
  isArchived: boolean;
  clientIsBlocked: boolean;
}

interface OperationData {
  vaultCode: string;
  amount: number;
  operationName: string;
}


function Vault() {

  const [vaults, setVaults] = useState<Vault[]>([]);
  const [clients, setClients] = useState<Client[]>([]);
  const [currencies, setCurrencies] = useState<Currency[]>([]);
  const [displayVaults, setDisplayVaults] = useState<VaultDisplayData[]>([]);
  const [clientBlockedStatus, setClientBlockedStatus] = useState<Map<string, boolean>>(new Map());
  
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [fetchStatus, setFetchStatus] = useState({
    vaults: false,
    clients: false,
    currencies: false
  });

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentOperation, setCurrentOperation] = useState<'INSERT' | 'WITHDRAW' | null>(null);
  const [currentVault, setCurrentVault] = useState<VaultDisplayData | null>(null);
  const [amount, setAmount] = useState<string>('');
  const [isLoadingOperation, setIsLoadingOperation] = useState(false);

  // Fetch all required data
  useEffect(() => {
    const fetchAllData = async () => {
      try {
        setLoading(true);
        setError(null);

        // Fetch all data in parallel
        const [vaultsRes, clientsRes, currenciesRes] = await Promise.allSettled([
          fetch('http://localhost:8080/api/v1/vaults'),
          fetch('http://localhost:8080/api/v1/clients'),
          fetch('http://localhost:8080/api/v1/currencies')
        ]);

        // Process each response
        const errors: string[] = [];

        // Vaults
        if (vaultsRes.status === 'fulfilled' && vaultsRes.value.ok) {
          const data = await vaultsRes.value.json();
          setVaults(data);
          setFetchStatus(prev => ({ ...prev, vaults: true }));
        } else {
          errors.push('Failed to fetch vaults');
        }

        // Clients
        if (clientsRes.status === 'fulfilled' && clientsRes.value.ok) {
          const data = await clientsRes.value.json();
          setClients(data);
          setFetchStatus(prev => ({ ...prev, clients: true }));
        } else {
          errors.push('Failed to fetch clients');
        }

        // Currencies
        if (currenciesRes.status === 'fulfilled' && currenciesRes.value.ok) {
          const data = await currenciesRes.value.json();
          setCurrencies(data);
          setFetchStatus(prev => ({ ...prev, currencies: true }));
        } else {
          errors.push('Failed to fetch currencies');
        }

        if (errors.length > 0) {
          setError(errors.join(', '));
        }

      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch data');
        console.error('Error fetching vault data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchAllData();
  }, []);


  // Combine vaults with client and currency data when all data is loaded
  useEffect(() => {
    if (vaults.length > 0 && clients.length > 0 && currencies.length > 0) {
      const blockedStatusMap = new Map<string, boolean>();
      
      // Build map of client blocked statuses
      clients.forEach(client => {
        blockedStatusMap.set(client.clientCode, client.isBlocked);
      });
      
      setClientBlockedStatus(blockedStatusMap);

      const combinedData: VaultDisplayData[] = vaults.map(vault => {
        // Find client data
        const client = clients.find(c => c.clientCode === vault.clientCode);
        const isClientBlocked = client?.isBlocked || false;
        
        // Find currency data
        const currency = currencies.find(curr => curr.currencyCode === vault.currencyCode);
        
        // Parse client name (handle different formats)
        const clientName = client?.nameOrTitle || 'Unknown Client';
        let clientDisplayName = clientName;
        let clientTitle = '';
        
        // Try to split name if it contains a comma or other separators
        if (clientName.includes(', ')) {
          const parts = clientName.split(', ');
          clientTitle = parts[0];
          clientDisplayName = parts.slice(1).join(' ');
        } else if (clientName.includes(' ')) {
          const parts = clientName.split(' ');
          if (parts.length >= 2) {
            clientDisplayName = parts[0];
            clientTitle = parts.slice(1).join(' ');
          }
        }

        return {
          vaultCode: vault.vaultCode,
          clientCode: vault.clientCode,
          clientName: clientDisplayName,
          clientTitle: clientTitle,
          createdAt: vault.createdAt,
          modifiedAt: vault.modifiedAt,
          amount: parseInt(vault.amount) || 0, // Convert BigInteger string to number
          currencyCode: vault.currencyCode,
          currencyName: currency?.currencyName || vault.currencyCode,
          isArchived: vault.isArchived,
          clientIsBlocked: isClientBlocked
        };
      }).filter(vault => !vault.isArchived); // Filter out archived vaults for display

      setDisplayVaults(combinedData);
    }
  }, [vaults, clients, currencies]);


  // Modal handlers
  const openModal = (vault: VaultDisplayData, operation: 'INSERT' | 'WITHDRAW') => {
    // Check if client is blocked
    if (vault.clientIsBlocked) {
      alert(`Cannot perform ${operation} operation. Client ${vault.clientName} is BLOCKED.`);
      return;
    }
    
    // Check if vault is archived
    if (vault.isArchived) {
      alert(`Cannot perform operations on archived vault ${vault.vaultCode}.`);
      return;
    }

    setCurrentVault(vault);
    setCurrentOperation(operation);
    setAmount('');
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentOperation(null);
    setCurrentVault(null);
    setAmount('');
    setIsLoadingOperation(false);
  };

  const handleAmountChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.replace(/[^0-9]/g, '');
    setAmount(value);
  };


  const handleSubmit = async () => {
    if (!currentVault || !currentOperation || !amount) return;

    // Double-check client blocked status before submitting
    if (currentVault.clientIsBlocked) {
      alert(`Operation cancelled. Client ${currentVault.clientName} is BLOCKED.`);
      closeModal();
      return;
    }
    
    const operationData: OperationData = {
      vaultCode: currentVault.vaultCode,
      amount: parseInt(amount, 10),
      operationName: currentOperation
    };
    
    setIsLoadingOperation(true);
    try {
      // Call your Spring Boot API
      const response = await fetch('http://localhost:8080/api/v1/vaults/operations', {
        method: 'POST',
        headers: { 
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify(operationData)
      });
      
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
      }
      
      const result = await response.json();
      console.log('Operation successful:', result);
      
      // Refresh vault data to show updated amounts
      const vaultsResponse = await fetch('http://localhost:8080/api/v1/vaults');
      if (vaultsResponse.ok) {
        const updatedVaults = await vaultsResponse.json();
        setVaults(updatedVaults);
      }
      
      closeModal();
      
    } catch (error) {
      console.error('Operation failed:', error);
      setError(error instanceof Error ? error.message : 'Operation failed');
      // Optionally show error in modal instead of clearing it
    } finally {
      setIsLoadingOperation(false);
    }
  };

  // Helper functions
  const formatAmount = (amount: number): string => {
    return new Intl.NumberFormat().format(amount);
  };

  const formatDateTime = (dateString: string): string => {
    try {
      const date = new Date(dateString);
      if (isNaN(date.getTime())) {
        return 'Unknown';
      }
      return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
      });
    } catch {
      return 'Invalid date';
    }
  };

  const getCurrencyColor = (currencyCode: string): string => {
    const currency = currencies.find(c => c.currencyCode === currencyCode);
    const name = currency?.currencyName?.toLowerCase() || '';
    
    if (name.includes('gold')) return '#ffd700';
    if (name.includes('silver')) return '#c0c0c0';
    if (name.includes('copper')) return '#cd7f32';
    if (name.includes('bronze')) return '#b08d57';
    if (name.includes('iron')) return '#a19d94';
    return '#95a5a6';
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && amount) {
      handleSubmit();
    }
    if (e.key === 'Escape') {
      closeModal();
    }
  };



  // Loading state

  if (loading) {
    const loadedItems = Object.values(fetchStatus).filter(Boolean).length;
    const totalItems = Object.keys(fetchStatus).length;
    
    return (
      <div className="vaults-container">
        <div className="loading-state">
          <div className="loading-spinner" data-testid="loading-spinner"></div>
          <h2>Loading Vault</h2>
          <p>Initializing vault access protocols... ({loadedItems}/{totalItems})</p>
          <div className="loading-progress">
            <div 
              className="progress-bar" 
              style={{ width: `${(loadedItems / totalItems) * 100}%` }}
            ></div>
          </div>
        </div>
      </div>
    );
  }
  

  // Error state

  if (error) {
    return (
      <div className="vaults-container">
        <div className="error-state">
          <div className="error-icon">⚠️</div>
          <h2 data-testid="pageTitle">Failed to Load Vaults</h2>
          <p className="error-message">{error}</p>
          
          {displayVaults.length > 0 && (
            <div className="partial-data-warning">
              <span className="warning-icon">🔒</span>
              <span>Showing limited vault data. Some operations may be restricted.</span>
            </div>
          )}
          
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

  if (displayVaults.length === 0) {
    const hasData = vaults.length > 0;
    
    return (
      <div className="vaults-container">
        <div className="empty-state">
          <div className="empty-icon">🔮</div>
          <h2 data-testid="pageTitle">No Active Vaults</h2>
          <p>
            {hasData 
              ? 'All vaults are currently archived or inactive' 
              : 'No vault data available in the system'
            }
          </p>
          {hasData && (
            <div className="archived-info">
              <span className="info-icon">📁</span>
              <span>{vaults.length} vault(s) in archive</span>
            </div>
          )}
        </div>
      </div>
    );
  }



  return (
    <div className="vaults-container">
      <h1 className="page-title" data-testid="pageTitle">First City Bank & Trust Vault</h1>
      
      {/* Vault stats */}
      <div className="vault-stats">
        <div className="stat-item">
          <span className="stat-label" data-testid="countLabelVaults">ACTIVE VAULTS: </span>
          <span className="stat-value" data-testid="countValueVaults">{displayVaults.length}</span>
        </div>
        <div className="stat-item">
          <span className="stat-label" data-testid="countLabelCurrencies">UNIQUE CURRENCIES: </span>
          <span className="stat-value" data-testid="countValueCurrencies">
            {Array.from(new Set(displayVaults.map(v => v.currencyCode))).length}
          </span>
        </div>
      </div>
      
<div className="vaults-grid">
  {displayVaults.map((vault) => {
    const isDisabled = vault.clientIsBlocked;
    
    return (
      <div key={vault.vaultCode} className={`vault-card ${isDisabled ? 'blocked' : ''}`}>
        <div className="vault-header">
          <div className="vault-icon">
            <div className="safe-wheel">⚙️</div>
          </div>
          <div className="vault-info">
            <h2 data-testid="vaultTitle">VAULT {vault.vaultCode}</h2>
            <span 
              className={`security-level ${isDisabled ? 'blocked' : ''}`}
              style={{ 
                color: getCurrencyColor(vault.currencyCode),
                borderColor: getCurrencyColor(vault.currencyCode)
              }}
              data-testid="vaultCurrencyName"
            >
              {vault.currencyName}
            </span>
          </div>
        </div>
        
        <div className="vault-body">
          <div className="data-row">
            <span className="data-label" data-testid="clientIdLabel">CLIENT ID:</span>
            <span className={`data-value code ${isDisabled ? 'blocked-text' : ''}`} data-testid="clientIdCode">
              {vault.clientCode}
            </span>
          </div>
          <div className="data-row">
            <span className="data-label" data-testid="clientNameLabel">CLIENT:</span>
            <span className={`data-value ${isDisabled ? 'blocked-text' : ''}`} data-testid="clientNameValue">
              {vault.clientName} {vault.clientTitle && <span className="client-title">{vault.clientTitle}</span>}
              {vault.clientIsBlocked && <span className="blocked-indicator"> (BLOCKED)</span>}
            </span>
          </div>
          <div className="data-row">
            <span className="data-label" data-testid="statusLabel">STATUS:</span>
            <span className={`data-value ${isDisabled ? 'blocked-text' : 'active-text'}`} data-testid="statusValue">
              {vault.clientIsBlocked ? 'BLOCKED 🔒' : 'ACTIVE ✅'}
            </span>
          </div>
          <div className="data-row">
            <span className="data-label" data-testid="lastAccessLabel">LAST ACCESS:</span>
            <span className="data-value" data-testid="lastAccessDateTime">{formatDateTime(vault.modifiedAt)}</span>
          </div>
          
          <div className="amount-display">
            <div className="amount-label" data-testid="balanceLabel">CURRENT BALANCE:</div>
            <div className={`amount-value ${isDisabled ? 'disabled-amount' : ''}`} data-testid="balanceValue">
              {formatAmount(vault.amount)}
            </div>
          </div>
        </div>
        
        <div className="vault-footer">
          <button 
            className="vault-btn" 
            onClick={() => openModal(vault, 'INSERT')}
            disabled={isDisabled}
            title={isDisabled ? 'Client is blocked. No operations allowed.' : 'Deposit into vault'}
            data-testid="depositBtn"
          >
            DEPOSIT
          </button>
          <button 
            className="vault-btn" 
            onClick={() => openModal(vault, 'WITHDRAW')}
            disabled={isDisabled || vault.amount <= 0}
            title={
              isDisabled ? 'Client is blocked. No operations allowed.' : 
              vault.amount <= 0 ? 'Insufficient funds' : 'Withdraw from vault'
            }
            data-testid="withdrawBtn"
          >
            WITHDRAW
          </button>
        </div>
      </div>
    );
  })}
</div>

      {/* Operation Modal */}
      {isModalOpen && currentVault && currentOperation && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content vault-modal" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2 data-testid="modalHeader">
                {currentOperation === 'INSERT' ? 'Deposit to ' : 'Withdraw from '}
                Vault {currentVault.vaultCode}
              </h2>
              <button className="modal-close-btn" data-testid="closeButton" onClick={closeModal}>✕</button>
            </div>
            
            <div className="modal-body" data-testid="modalBody">
              <div className="vault-info-summary">
                <div className="info-row">
                  <span data-testid="clientLabelWindow">Client:</span>
                  <span className="highlight" data-testid="clientNameWindow">{currentVault.clientName} {currentVault.clientTitle}</span>
                </div>
                <div className="info-row">
                  <span data-testid="vaultLabelWindow">Vault ID:</span>
                  <span className="code" data-testid="vaultCodeWindow">{currentVault.vaultCode}</span>
                </div>
                <div className="info-row">
                  <span data-testid="currentBalanceLabelWindow">Current Balance:</span>
                  <span className="amount-highlight" data-testid="currentBalanceValueWindow">
                    {formatAmount(currentVault.amount)} {currentVault.currencyName}
                  </span>
                </div>
              </div>
              
              <div className="amount-input-section">
                <label htmlFor="amount-input" className="amount-label" data-testid="amountLabelWindow">
                  Enter Amount ({currentVault.currencyName}):
                </label>
                <div className="input-wrapper">
                  <input
                    id="amount-input"
                    type="text"
                    className="amount-input"
                    value={amount ? formatAmount(parseInt(amount) || 0) : ''}
                    onChange={handleAmountChange}
                    onKeyDown={handleKeyPress}
                    placeholder="0"
                    disabled={isLoadingOperation}
                    autoFocus
                    data-testid="amountInput"
                  />
                </div>
                
                {/* Validation message */}
                {amount && parseInt(amount) > 0 && currentOperation === 'WITHDRAW' && (
                  <div className={`validation-message ${parseInt(amount) > currentVault.amount ? 'error' : 'success'}`} data-testid="validationMessage">
                    {parseInt(amount) > currentVault.amount 
                      ? `❌ Exceeds available balance of ${formatAmount(currentVault.amount)}`
                      : `✅ Within available balance`
                    }
                  </div>
                )}
                
                <div className="input-hint" data-testid="inputHint">
                  Enter numeric value only.
                </div>
                
                {amount && parseInt(amount) > 0 && (
                  <div className="operation-preview">
                    <div className="preview-header">
                      <h4>Operation Summary: </h4>
                    </div>
                    <div className="preview-details">
                      <div className="preview-row">
                        <span>Type: </span>
                        <span className={`operation-type ${currentOperation.toLowerCase()}`}>
                          {currentOperation}
                        </span>
                      </div>
                      <div className="preview-row">
                        <span>Amount: </span>
                        <span className="preview-amount">
                          {formatAmount(parseInt(amount))} {currentVault.currencyName}
                        </span>
                      </div>
                      <div className="preview-row">
                        <span>New Balance: </span>
                        <span className="preview-balance">
                          {formatAmount(
                            currentOperation === 'INSERT' 
                              ? currentVault.amount + parseInt(amount)
                              : currentVault.amount - parseInt(amount)
                          )} {currentVault.currencyName}
                        </span>
                      </div>
                    </div>
                  </div>
                )}
              </div>
            </div>
            
            <div className="modal-footer">
              <button
                className="vault-btn"
                onClick={closeModal}
                disabled={isLoadingOperation}
                data-testid="cancelButton"
              >
                CANCEL
              </button>
              <button
                className="vault-btn"
                onClick={handleSubmit}
                disabled={!amount || parseInt(amount) <= 0 || isLoadingOperation ||
                  (currentOperation === 'WITHDRAW' && parseInt(amount) > currentVault.amount)}
                data-testid="submitButton"
              >
                {isLoadingOperation ? (
                  <>
                    <span className="spinner"></span>
                    Processing...
                  </>
                ) : (
                  `CONFIRM`
                )}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default Vault;