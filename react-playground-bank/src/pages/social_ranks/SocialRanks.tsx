import { useState } from 'react';
import './SocialRanksStyles.css';
import './ModalStyles.css';


// TODO: 📜 !!! (No ranks found !!!)

interface SocialRank {
  rankCode: string;
  rankName: string;
  description: string;
  privilegeLevel: 'Highest' | 'High' | 'Elevated' | 'Standard' | 'Restricted';
  regulations: string;
}


function SocialRanks() {

  const [selectedRank, setSelectedRank] = useState<SocialRank | null>(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const socialRanks: SocialRank[] = [
    {rankCode: 'N-1', rankName: 'Noble', description: 'Aristocratic elite with ancient lineage and political influence', privilegeLevel: 'Highest', regulations: 'By order of the City Council, nobles are to be allowed any amount of assets kept in their vaults, and ANY amount of loans.'},
    {rankCode: 'M-1', rankName: 'Merchant', description: 'Wealthy traders and guild masters controlling commerce', privilegeLevel: 'High', regulations: 'By order of the City Council, merchants are to be allowed any amount of assets kept in their vaults, and AT MOST THE SAME amount as loans.'},
    {rankCode: 'H-1', rankName: 'Hammerite', description: 'Religious order with architectural and spiritual authority', privilegeLevel: 'Elevated', regulations: 'By order of the City Council, hammerites are to be allowed any amount of assets kept in their vaults, and AT MOST HALF of that amount in loans.'},
    {rankCode: 'C-1', rankName: 'Commoner', description: 'Working class citizens and skilled artisans', privilegeLevel: 'Standard', regulations: 'By order of the City Council, commoners are to be allowed any amount of assets kept in their vaults, and AT MOST ONE THIRD of that amount in loans.'},
    {rankCode: 'F-1', rankName: 'Foreigner', description: 'Outsiders with limited rights and constant surveillance', privilegeLevel: 'Restricted', regulations: 'By order of the City Council, foreigners are to be allowed any amount of assets kept in their vaults, but NO loans.'}
  ];

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
    'Highest': 'UNRESTRICTED',
    'High': 'ELEVATED',
    'Elevated': 'MODERATE',
    'Standard': 'BASIC',
    'Restricted': 'ESCORTED'
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


return (
    <div className="social-ranks-container">
      <h1 className="page-title">Social Ranks</h1>
      
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
      
      <div className="hierarchy-footer">
        <div className="footnote">
          <span className="warning">⚠️</span>
          Social mobility requires official approval from the City Council
        </div>
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