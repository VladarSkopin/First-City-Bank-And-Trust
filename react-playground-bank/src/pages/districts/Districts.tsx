import { useState, useEffect } from 'react';
import './DistrictsStyles.css';
import './ModalStyles.css';
import './LoadingStyles.css';


interface District {
  districtCode: string;
  districtName: string;
}


function Districts() {

  const [selectedDistrict, setSelectedDistrict] = useState<District | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [districts, setDistricts] = useState<District[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Fetch districts from Spring Boot API
  useEffect(() => {
    const fetchDistricts = async () => {
      try {
        setLoading(true);
        const response = await fetch('http://localhost:8080/api/v1/districts');
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        setDistricts(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch districts');
        console.error('Error fetching districts:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchDistricts();
  }, []);

	
  const handleViewMap = (district: District): void => {
    setSelectedDistrict(district);
    setIsModalOpen(true);
  };

  const closeModal = (): void => {
    setIsModalOpen(false);
    setSelectedDistrict(null);
  };

  const getMapImagePath = (districtCode: string): string => {
    return `/static/city_map/${districtCode.replace('-', '_')}.png`;
  };


  // Loading state
  if (loading) {
    return (
      <div className="districts-container">
        <div className="loading-state">
          <div className="loading-spinner" data-testid="loading-spinner"></div>
          <p>Loading districts...</p>
        </div>
      </div>
    );
  }


  // Error state
  if (error) {
    return (
      <div className="districts-container">
        <div className="error-state">
          <div className="error-icon">⚠️</div>
          <h2 data-testid="pageTitle">Failed to Load Districts</h2>
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
  if (districts.length === 0) {
    return (
      <div className="districts-container">
        <div className="empty-state">
          <div className="empty-icon">🏙️</div>
          <h3 data-testid="pageTitle">No Districts Found</h3>
          <p>No district data is currently available</p>
        </div>
      </div>
    );
  }


  return (
    <div className="districts-container">
      <h1 className="page-title" data-testid="pageTitle">City Districts</h1>

      <div className="districts-info">
        <div className="districts-count">
          <span className="count-label" data-testid="countLabel">TOTAL DISTRICTS: </span>
          <span className="count-value" data-testid="countValue">{districts.length}</span>
        </div>
      </div>

      <div className="districts-grid">
        {districts.map(district => (
          <div key={district.districtCode} className="district-card">
            <div className="district-header">
              <div className="district-info">
                <h2 data-testid="districtName">{district.districtName}</h2>
                <span className="district-code" data-testid="districtCode">{district.districtCode}</span>
              </div>
            </div>
            
            <div className="district-footer">
              <button className="district-map-btn" data-testid="detailedMapButton"
              onClick={() => handleViewMap(district)}>DETAILED MAP</button>
            </div>
          </div>
        ))}
      </div>

      {/* Map Modal */}
      {isModalOpen && selectedDistrict && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content map-modal" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2 data-testid="modalHeader">{selectedDistrict.districtName} - Detailed Map</h2>
              <button className="modal-close-btn" data-testid="closeButton" onClick={closeModal}>✕</button>
            </div>
            
            <div className="modal-body" data-testid="modalBody">
              <div className="map-container">
                <div className="map-image-wrapper">
                  <img
                    src={getMapImagePath(selectedDistrict.districtCode)}
                    alt={`Detailed map of ${selectedDistrict.districtName}`}
                    className="detailed-map"
                    data-testid="districtMapImg"
                    onError={(e) => {
                      const target = e.target as HTMLImageElement;
                      target.style.display = 'none';
                      const fallback = target.nextSibling as HTMLElement;
                      if (fallback) fallback.style.display = 'block';
                    }}
                  />
                  <div className="map-fallback" style={{display: 'none'}}>
                    <div className="fallback-content">
                      <span className="fallback-icon">🗺️</span>
                      <h3 data-testid="mapBanner">MAP UNAVAILABLE</h3>
                      <p  data-testid="mapDescription">Cartographic data for {selectedDistrict.districtName} is currently classified</p>
                    </div>
                  </div>
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


      
    </div>
  );
}

export default Districts;