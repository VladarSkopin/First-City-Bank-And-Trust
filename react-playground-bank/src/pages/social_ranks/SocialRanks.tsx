import { useState, useEffect } from 'react';
import './SocialRanksStyles.css';
import './ModalStyles.css';


interface SocialRank {
  rankCode: string;
  rankName: string;
  description: string;
  privilegeLevel: 'HIGHEST' | 'HIGH' | 'ELEVATED' | 'STANDARD' | 'RESTRICTED';
  regulations: string;
}


function SocialRanks() {

  const [selectedRank, setSelectedRank] = useState<SocialRank | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [socialRanks, setSocialRanks] = useState<SocialRank[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);


  // Fetch social ranks from Spring Boot API
  useEffect(() => {
    const fetchSocialRanks = async () => {
      try {
        setLoading(true);
        setError(null);
        
        const response = await fetch('http://localhost:8080/api/v1/socialranks');
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();
        setSocialRanks(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch social ranks');
        console.error('Error fetching social ranks:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchSocialRanks();
  }, []);


  const getRankIcon = (rankName: string): string => {
    const icons: Record<string, string> = {
        'Noble': '👑',
        'Merchant': '💰',
        'Hammerite': '⚒️',
        'Commoner': '👨‍🌾',
        'Foreigner': '🚫'
      };
    return icons[rankName] || '⚫';
  };

const getAccessLevel = (privilege: SocialRank['privilegeLevel']): string => {
  const access: Record<SocialRank['privilegeLevel'], string> = {
    'HIGHEST': 'UNRESTRICTED',
    'HIGH': 'ELEVATED',
    'ELEVATED': 'MODERATE',
    'STANDARD': 'BASIC',
    'RESTRICTED': 'ESCORTED'
  };
  return access[privilege] || 'UNKNOWN';
};


const handleViewRegulations = (rank: SocialRank) => {
  setSelectedRank(rank);
  setIsModalOpen(true);
};

const closeModal = () => {
  setIsModalOpen(false);
  setSelectedRank(null);
};



  // Loading state
  if (loading) {
    return (
      <div className="social-ranks-container">
        <div className="loading-state">
          <div className="loading-spinner"></div>
          <p>Loading social ranks...</p>
        </div>
      </div>
    );
  }


  // Error state
  if (error) {
    return (
      <div className="social-ranks-container">
        <div className="error-state">
          <div className="error-icon">⚠️</div>
          <h2>Failed to Load Social Ranks</h2>
          <p>{error}</p>
          <button 
            className="retry-btn"
            onClick={() => window.location.reload()}
          >
            RETRY
          </button>
        </div>
      </div>
    );
  }


  // Empty state
  if (socialRanks.length === 0) {
    return (
      <div className="social-ranks-container">
        <div className="empty-state">
          <div className="empty-icon">📜</div>
          <h3>No Social Ranks Found</h3>
          <p>No data about social ranks is currently available</p>
        </div>
      </div>
    );
  }


return (
    <div className="social-ranks-container">
      <h1 className="page-title">Social Ranks</h1>

            
      <div className="ranks-stats">
        <div className="stats-item">
          <span className="stats-label">TOTAL RANKS: </span>
          <span className="stats-value">{socialRanks.length}</span>
        </div>
      </div>
      
      <div className="ranks-grid">
        {socialRanks.map((rank) => (
          <div key={rank.rankCode} className="rank-card">
            <div className="rank-header">
              <div className="rank-icon">
                {getRankIcon(rank.rankName)}
              </div>
              <div className="rank-title">
                <h2>{rank.rankName}</h2>
                <span className="rank-code">{rank.rankCode}</span>
              </div>
            </div>
            
            <div className="rank-body">
              <p className="rank-description">{rank.description}</p>
              
              <div className="privilege-info">
                <div className="info-item">
                  <span className="info-label">PRIVILEGE LEVEL:</span>
                  <span className="info-value">
                    {rank.privilegeLevel}
                  </span>
                </div>
                <div className="info-item">
                  <span className="info-label">ACCESS RIGHTS:</span>
                  <span className="info-value">
                    {getAccessLevel(rank.privilegeLevel)}
                  </span>
                </div>
              </div>
              
            </div>
            
            <div className="rank-footer">
              <button className="view-regulations-btn" 
              onClick={() => handleViewRegulations(rank)}>VIEW REGULATIONS</button>
            </div>
          </div>
        ))}
      </div>


	
      {/* Regulations Modal */}
      {isModalOpen && selectedRank && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <div className="modal-header">
              <h2>{selectedRank.rankName} Regulations</h2>
              <button className="modal-close-btn" onClick={closeModal}>✕</button>
            </div>
            
            <div className="modal-body">
              <div className="regulation-info">
                <div className="regulation-meta">
                  <span className="regulation-code">CODE: {selectedRank.rankCode}</span>
                  <span className="regulation-level">PRIVILEGE LEVEL: {selectedRank.privilegeLevel}</span>
                </div>
                
                <div className="regulation-text">
                  <p>{selectedRank.regulations}</p>
                </div>
                
                <div className="regulation-footer">
                  <div className="seal-of-approval">
                    <span className="seal-icon">⚖️</span>
                    <span>APPROVED BY CITY COUNCIL</span>
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
  );
}




export default SocialRanks;