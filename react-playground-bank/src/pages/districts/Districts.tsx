import { useState } from 'react';
import './DistrictsStyles.css';
import './ModalStyles.css';


interface District {
  districtCode: string;
  districtName: string;
}


function Districts() {

  const [selectedDistrict, setSelectedDistrict] = useState<District | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const districts: District[] = [
    {districtCode: 'D-1', districtName: 'Dayport'},
    {districtCode: 'D-2', districtName: 'Wayside Docks'},
    {districtCode: 'D-3', districtName: 'Eastport'},
    {districtCode: 'D-4', districtName: 'Olde Quarter'},
    {districtCode: 'D-5', districtName: 'New Quarter'},
    {districtCode: 'D-6', districtName: 'South Quarter'},
    {districtCode: 'D-7', districtName: 'North Quarter'},
    {districtCode: 'D-8', districtName: 'Stonemarket'},
    {districtCode: 'D-9', districtName: 'Newmarket'},
    {districtCode: 'D-10', districtName: 'Auldale'},
    {districtCode: 'D-11', districtName: 'Downtowne'},
    {districtCode: 'D-12', districtName: 'Hightowne'},
    {districtCode: 'D-13', districtName: 'Shalebridge'}
  ];

	
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


  return (
    <div className="districts-container">
      <h1 className="page-title">City Districts</h1>

      <div className="districts-grid">
        {districts.map(district => (
          <div key={district.districtCode} className="district-card">
            <div className="district-header">
              <div className="district-info">
                <h2>{district.districtName}</h2>
                <span className="district-code">{district.districtCode}</span>
              </div>
            </div>
            
            <div className="district-footer">
              <button className="district-map-btn" 
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
              <h2>{selectedDistrict.districtName} - Detailed Map</h2>
              <button className="modal-close-btn" onClick={closeModal}>✕</button>
            </div>
            
            <div className="modal-body">
              <div className="map-container">
                <div className="map-image-wrapper">
                  <img
                    src={getMapImagePath(selectedDistrict.districtCode)}
                    alt={`Detailed map of ${selectedDistrict.districtName}`}
                    className="detailed-map"
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
                      <h3>MAP UNAVAILABLE</h3>
                      <p>Cartographic data for {selectedDistrict.districtName} is currently classified</p>
                      <span className="fallback-code">REF: {selectedDistrict.districtCode}-MAP-404</span>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
            
            <div className="modal-footer">
              <button className="modal-confirm-btn" onClick={closeModal}>
                OK
              </button>
            </div>
          </div>
        </div>
      )}


      
    </div>
  )
}

export default Districts;