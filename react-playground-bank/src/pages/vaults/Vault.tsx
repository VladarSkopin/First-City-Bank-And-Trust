import './VaultStyles.css';
import './VaultModalStyles.css';
import { useState } from 'react';


interface VaultData {
  vaultCode: string;
  clientCode: string;
  clientName: string;
  clientSurname: string;
  createdAt: string;
  modifiedAt: string;
  amount: number;
  currency: string;
}

interface OperationData {
  vaultCode: string;
  amount: number;
  operationName: string;
}


function Vault() {

  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentOperation, setCurrentOperation] = useState<'INSERT' | 'WITHDRAW' | null>(null);
  const [currentVault, setCurrentVault] = useState<VaultData | null>(null);
  const [amount, setAmount] = useState<string>('');
  const [isLoading, setIsLoading] = useState(false);

  const vaults: VaultData[] = [
    {vaultCode: '104', clientCode: 'ABC-123', clientName: 'Truart', clientSurname: 'Norman', createdAt: '1242-12-23', modifiedAt: '1245-05-17', amount: 890_000, currency: 'Gold'},
    {vaultCode: '107', clientCode: 'XFD-762', clientName: 'Victoria', clientSurname: 'Driad', createdAt: '1231-03-19', modifiedAt: '1245-05-15', amount: 9_520_000, currency: 'Silver'},
    {vaultCode: '108', clientCode: 'XFD-762', clientName: 'Victoria', clientSurname: 'Driad', createdAt: '1231-03-19', modifiedAt: '1245-05-15', amount: 710_032, currency: 'Copper'}
  ];

  const openModal = (vault: VaultData, operation: 'INSERT' | 'WITHDRAW') => {
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
    setIsLoading(false);
  };

  const handleAmountChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value.replace(/[^0-9]/g, '');
    setAmount(value);
  };

  const handleSubmit = async () => {
    if (!currentVault || !currentOperation || !amount) return;
    const operationData: OperationData = {
      vaultCode: currentVault.vaultCode,
      amount: parseInt(amount, 10),
      operationName: currentOperation
    };
    setIsLoading(true);
    try {
      // Simulate API call
      console.log('Sending operation data:', JSON.stringify(operationData, null, 2));
      
      // Here you would make your actual API call
      // const response = await fetch('/api/vault/operation', {
      //   method: 'POST',
      //   headers: { 'Content-Type': 'application/json' },
      //   body: JSON.stringify(operationData)
      // });
      
      // Simulate API delay
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      console.log('Operation successful:', operationData);
      closeModal();
      
      // You might want to update the vaults data here
      // or trigger a refetch of vault data
      
    } catch (error) {
      console.error('Operation failed:', error);
    } finally {
      setIsLoading(false);
    }
  };


  // Helper function

  const formatAmount = (amount: number | bigint) => {
    return new Intl.NumberFormat().format(amount);
  };

  const formatDate = (dateString: string | number | Date) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  	
  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && amount) {
      handleSubmit();
    }
    if (e.key === 'Escape') {
      closeModal();
    }
  };


  return (
    <div className="vaults-container">
      <h1 className="page-title">First City Bank & Trust Vault</h1>
      <div className="vaults-grid">
        {vaults.map((vault) => (
          <div key={vault.vaultCode} className="vault-card">
            <div className="vault-header">
              <div className="vault-icon">
                <div className="safe-wheel">⚙️</div>
              </div>
              <div className="vault-info">
                <h2>VAULT {vault.vaultCode}</h2>
                <span className={`security-level security-${vault.currency.toLowerCase()}`}>
                  {vault.currency}
                </span>
              </div>
            </div>
            
            <div className="vault-body">
              <div className="data-row">
                <span className="data-label">CLIENT ID:</span>
                <span className="data-value">{vault.clientCode}</span>
              </div>
              <div className="data-row">
                <span className="data-label">CLIENT NAME:</span>
                <span className="data-value">{vault.clientName} {vault.clientSurname}</span>
              </div>
              <div className="data-row">
                <span className="data-label">ESTABLISHED:</span>
                <span className="data-value">{formatDate(vault.createdAt)}</span>
              </div>
              <div className="data-row">
                <span className="data-label">LAST ACCESS:</span>
                <span className="data-value">{formatDate(vault.modifiedAt)}</span>
              </div>
              
              <div className="amount-display">
                <div className="amount-label">AMOUNT:</div>
                <div className="amount-value">{formatAmount(vault.amount)}</div>
              </div>
            </div>
            
            <div className="vault-footer">
              <button className="vault-btn" onClick={() => openModal(vault, 'INSERT')}>INSERT</button>
              <button className="vault-btn" onClick={() => openModal(vault, 'WITHDRAW')}>WITHDRAW</button>
            </div>
          </div>
        ))}
      </div>







      {/* Operation Modal */}
      {isModalOpen && currentVault && currentOperation && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content vault-modal" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>
                {currentOperation === 'INSERT' ? 'Deposit to ' : 'Withdraw from '}
                Vault {currentVault.vaultCode}
              </h2>
              <button className="modal-close-btn" onClick={closeModal}>✕</button>
            </div>
            
            <div className="modal-body">
              <div className="vault-info-summary">
                <div className="info-row">
                  <span>Client:</span>
                  <span>{currentVault.clientName} {currentVault.clientSurname}</span>
                </div>
                <div className="info-row">
                  <span>Current Balance:</span>
                  <span>{formatAmount(currentVault.amount)} {currentVault.currency}</span>
                </div>
              </div>
              
              <div className="amount-input-section">
                <label htmlFor="amount-input" className="amount-label">
                  Enter Amount ({currentVault.currency}):
                </label>
                <input
                  id="amount-input"
                  type="text"
                  className="amount-input"
                  value={amount ? formatAmount(parseInt(amount)) : ''}
                  onChange={handleAmountChange}
                  onKeyPress={handleKeyPress}
                  placeholder="0"
                  disabled={isLoading}
                  autoFocus
                />
                <div className="input-hint">
                  Enter numeric value only
                </div>
              </div>
              {amount && (
                <div className="operation-preview">
                  <h4>Operation Preview:</h4>
                  <pre className="json-preview">
                    {JSON.stringify({
                      vaultCode: currentVault.vaultCode,
                      amount: parseInt(amount),
                      operationName: currentOperation
                    }, null, 2)}
                  </pre>
                </div>
              )}
            </div>
            
            <div className="modal-footer">
              <button
                className="vault-btn"
                onClick={closeModal}
                disabled={isLoading}
              >
                Cancel
              </button>
              <button
                className="vault-btn"
                onClick={handleSubmit}
                disabled={!amount || isLoading}
              >
                {isLoading ? 'Processing...' : `Confirm ${currentOperation}`}
              </button>
            </div>
          </div>
        </div>
      )}









    </div>
  );
}

export default Vault;