import { useState, useEffect } from 'react';
import './SectorsStyles.css';
import './ModalStyles.css';


interface Sector {
  sectorCode: string;
  sectorName: string;
  description: string;
}

interface SubSector {
  subSectorCode: string;
  subSectorName: string;
  description: string;
  sectorCode: string;
}


function Sectors() {
  const [selectedSubSector, setSelectedSubSector] = useState<SubSector | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [sectors, setSectors] = useState<Sector[]>([]);
  const [subSectors, setSubSectors] = useState<SubSector[]>([]);
  
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [fetchStatus, setFetchStatus] = useState({
    sectors: false,
    subSectors: false,
  });


  // Fetch all required data in parallel
  useEffect(() => {
    const fetchAllData = async () => {
      try {
        setLoading(true);
        setError(null);

        // Fetch all data in parallel
        const [sectorsRes, subSectorsRes] = await Promise.allSettled([
          fetch('http://localhost:8080/api/v1/sectors'),
          fetch('http://localhost:8080/api/v1/subsectors')
        ]);

        // Process each response
        let hasError = false;
        const errors: string[] = [];

        // Sectors
        if (sectorsRes.status === 'fulfilled' && sectorsRes.value.ok) {
          const data = await sectorsRes.value.json();
          setSectors(data);
          setFetchStatus(prev => ({ ...prev, sectors: true }));
        } else {
          hasError = true;
          errors.push('Failed to fetch sectors');
        }

        // Sub-Sectors
        if (subSectorsRes.status === 'fulfilled' && subSectorsRes.value.ok) {
          const data = await subSectorsRes.value.json();
          setSubSectors(data);
          setFetchStatus(prev => ({ ...prev, subSectors: true }));
        } else {
          hasError = true;
          errors.push('Failed to fetch sub-sectors');
        }

        if (hasError) {
          setError(errors.join(', '));
        }

      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch data');
        console.error('Error fetching data:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchAllData();
  }, []);


    const getSectorName = (sectorCode: string): string => {
        const sector = sectors.find(s => s.sectorCode === sectorCode);
        return sector?.sectorName || sectorCode;
    };



    const handleViewInfo = (subSector: SubSector) => {
      setSelectedSubSector(subSector);
      setIsModalOpen(true);
    };

    const closeModal = () => {
      setIsModalOpen(false);
      setSelectedSubSector(null);
    };


  // Loading state

  if (loading) {
    const loadedItems = Object.values(fetchStatus).filter(Boolean).length;
    const totalItems = Object.keys(fetchStatus).length;
    
    return (
      <div className="sectors-container">
        <div className="loading-state">
          <div className="loading-spinner" data-testid="loading-spinner"></div>
          <h2>Loading Sectors</h2>
          <p>Initializing banking records... ({loadedItems}/{totalItems})</p>
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
      <div className="sectors-container">
        <div className="error-state">
          <div className="error-icon">⚠️</div>
          <h2>Failed to Load Sectors</h2>
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

  if (subSectors.length === 0) {
    return (
      <div className="sectors-container">
        <div className="empty-state">
          <div className="empty-icon">📊</div>
          <h3>No Sectors Found</h3>
          <p>No Sectors data is currently available</p>
        </div>
      </div>
    );
  }


    return (
        <div className="sectors-container">
            <h1 className="page-title">Economic Sub-Sectors</h1>
            
            {/* Stats bar */}
            <div className="sectors-stats">
                <div className="stat-item">
                <span className="count-label">TOTAL SUB-SECTORS: </span>
                <span className="count-value">{subSectors.length}</span>
                </div>
                <div className="stat-item">
                <span className="count-label">TOTAL SECTORS: </span>
                <span className="count-value">{sectors.length}</span>
                </div>
            </div>
            
            <div className="sectors-grid">
                {subSectors.map(subSector => (
                <div key={subSector.subSectorCode} className="sector-card">
                    <div className="card-header">
                        <div className="client-name">
                            <h2>{subSector.subSectorName}</h2>
                        </div>
                    </div>

                    <div className="card-body">
                        <div className="info-item">
                            <span className="label">SUB-SECTOR ID: </span>
                            <span className="value code">{subSector.subSectorCode}</span>
                        </div>

                        <div className="info-item">
                            <span className="label">SECTOR: </span>
                            <span className="value">
                            {getSectorName(subSector.sectorCode) || 'Unknown Sector'}
                            </span>
                        </div>
                    </div>

                    <div className="sector-footer">
                        <button className="view-description-btn" 
                        onClick={() => handleViewInfo(subSector)}>INFO</button>
                    </div>
                    
                </div>
                ))}
            </div>


      {/* Description Modal */}
      {isModalOpen && selectedSubSector && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2 data-testid="modalHeader">{selectedSubSector.subSectorName} Description</h2>
              <button className="modal-close-btn" data-testid="closeButton" onClick={closeModal}>✕</button>
            </div>
            
            <div className="modal-body" data-testid="modalBody">
              <div className="regulation-info">
                <div className="regulation-meta">
                  <span className="regulation-code">CODE: {selectedSubSector.subSectorCode}</span>
                  <span className="regulation-code">SECTOR: {getSectorName(selectedSubSector.sectorCode)}</span>
                </div>
                
                <div className="regulation-text">
                  <p>{selectedSubSector.description}</p>
                </div>
                
              </div>
            </div>
            
            <div className="modal-footer">
              <button className="modal-confirm-btn" data-testid="confirmButton" onClick={closeModal}>
                OK
              </button>
            </div>
          </div>
        </div>
      )}




            <div className="sector-footer">
                <div className="last-updated">
                Data fetched: {new Date().toLocaleString()}
                </div>
            </div>
        </div>
    );
}

export default Sectors;