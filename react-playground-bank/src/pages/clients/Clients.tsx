import './ClientsStyles.css';
import './LoadingStyles.css';
import { useState, useEffect } from 'react';

interface Client {
  clientCode: string;
  nameOrTitle: string;
  clientTypeCode: string;
  socialRankCode: string;
  districtCode: string;
  isBlocked: boolean;
  subSectorCode: string;
}

interface ClientType {
  clientTypeCode: string;
  clientTypeName: string;
  description: string;
}

interface SocialRank {
  rankCode: string;
  rankName: string;
  description: string;
  privilegeLevel: string;
  regulations: string;
}

interface District {
  districtCode: string;
  districtName: string;
}

interface SubSector {
  subSectorCode: string;
  subSectorName: string;
  description: string;
  sectorCode: string;
}


function Clients() {
  const [clients, setClients] = useState<Client[]>([]);
  const [clientTypes, setClientTypes] = useState<ClientType[]>([]);
  const [socialRanks, setSocialRanks] = useState<SocialRank[]>([]);
  const [districts, setDistricts] = useState<District[]>([]);
  const [subSectors, setSubSectors] = useState<SubSector[]>([]);
  
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [fetchStatus, setFetchStatus] = useState({
    clients: false,
    clientTypes: false,
    socialRanks: false,
    districts: false,
    subSectors: false
  });

  // Fetch all required data in parallel
  useEffect(() => {
    const fetchAllData = async () => {
      try {
        setLoading(true);
        setError(null);

        // Fetch all data in parallel
        const [clientsRes, clientTypesRes, socialRanksRes, districtsRes, subSectorsRes] = await Promise.allSettled([
          fetch('http://localhost:8080/api/v1/clients'),
          fetch('http://localhost:8080/api/v1/clienttypes'),
          fetch('http://localhost:8080/api/v1/socialranks'),
          fetch('http://localhost:8080/api/v1/districts'),
          fetch('http://localhost:8080/api/v1/subsectors'),
        ]);

        // Process each response
        let hasError = false;
        const errors: string[] = [];

        // Clients
        if (clientsRes.status === 'fulfilled' && clientsRes.value.ok) {
          const data = await clientsRes.value.json();
          setClients(data);
          setFetchStatus(prev => ({ ...prev, clients: true }));
        } else {
          hasError = true;
          errors.push('Failed to fetch clients');
        }

        // Client Types
        if (clientTypesRes.status === 'fulfilled' && clientTypesRes.value.ok) {
          const data = await clientTypesRes.value.json();
          setClientTypes(data);
          setFetchStatus(prev => ({ ...prev, clientTypes: true }));
        } else {
          hasError = true;
          errors.push('Failed to fetch client types');
        }

        // Social Ranks
        if (socialRanksRes.status === 'fulfilled' && socialRanksRes.value.ok) {
          const data = await socialRanksRes.value.json();
          setSocialRanks(data);
          setFetchStatus(prev => ({ ...prev, socialRanks: true }));
        } else {
          hasError = true;
          errors.push('Failed to fetch social ranks');
        }

        // Districts
        if (districtsRes.status === 'fulfilled' && districtsRes.value.ok) {
          const data = await districtsRes.value.json();
          setDistricts(data);
          setFetchStatus(prev => ({ ...prev, districts: true }));
        } else {
          hasError = true;
          errors.push('Failed to fetch districts');
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



  // Helper functions to get display names from codes

  const getClientTypeName = (clientTypeCode: string): string => {
    const clientType = clientTypes.find(ct => ct.clientTypeCode === clientTypeCode);
    return clientType?.clientTypeName || clientTypeCode;
  };

  const getSocialRankName = (socialRankCode: string): string => {
    const socialRank = socialRanks.find(sr => sr.rankCode === socialRankCode);
    return socialRank?.rankName || socialRankCode;
  };

  const getDistrictName = (districtCode: string): string => {
    const district = districts.find(d => d.districtCode === districtCode);
    return district?.districtName || districtCode;
  };

  const getSectorName = (subSectorCode: string): string => {
    const subSector = subSectors.find(s => s.subSectorCode === subSectorCode);
    return subSector?.subSectorName || subSectorCode;
};


  // Get social rank color/icon based on rank name

  const getRankBadgeClass = (socialRankCode: string): string => {
    const socialRank = socialRanks.find(sr => sr.rankCode === socialRankCode);
    if (!socialRank) return 'rank-unknown';
    
    const rankName = socialRank.rankName.toLowerCase();
    if (rankName.includes('noble')) return 'rank-noble';
    if (rankName.includes('merchant')) return 'rank-merchant';
    if (rankName.includes('hammerite')) return 'rank-hammerite';
    if (rankName.includes('commoner')) return 'rank-commoner';
    if (rankName.includes('foreigner')) return 'rank-foreigner';
    return 'rank-unknown';
  };


  // Get client type icon

  const getClientTypeIcon = (clientTypeCode: string): string => {
    const clientType = clientTypes.find(ct => ct.clientTypeCode === clientTypeCode);
    const typeName = clientType?.clientTypeName?.toLowerCase() || '';
    
    if (typeName.includes('individual') || typeName.includes('person')) return '👤';
    if (typeName.includes('corporation') || typeName.includes('company')) return '🏢';
    if (typeName.includes('partnership')) return '🤝';
    if (typeName.includes('trust')) return '📜';
    if (typeName.includes('non-profit') || typeName.includes('charity')) return '❤️';
    if (typeName.includes('government')) return '🏛️';
    return '📋';
  };


  // Loading state

  if (loading) {
    const loadedItems = Object.values(fetchStatus).filter(Boolean).length;
    const totalItems = Object.keys(fetchStatus).length;
    
    return (
      <div className="clients-container">
        <div className="loading-state">
          <div className="loading-spinner"></div>
          <h2>Loading Clients</h2>
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
      <div className="clients-container">
        <div className="error-state">
          <div className="error-icon">⚠️</div>
          <h2>Failed to Load Clients</h2>
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

  if (clients.length === 0) {
    return (
      <div className="clients-container">
        <div className="empty-state">
          <div className="empty-icon">🤵</div>
          <h3>No Clients Found</h3>
          <p>No Clients data is currently available</p>
        </div>
      </div>
    );
  }

  
  return (
    <div className="clients-container">
      <h1 className="page-title">Banking Clients</h1>
      
      {/* Stats bar */}
      <div className="clients-stats">
        <div className="stat-item">
          <span className="count-label">TOTAL CLIENTS: </span>
          <span className="count-value">{clients.length}</span>
        </div>
        <div className="stat-item">
          <span className="count-label">ACTIVE: </span>
          <span className="count-value">
            {clients.filter(c => !c.isBlocked).length}
          </span>
        </div>
        <div className="stat-item">
          <span className="count-label">BLOCKED: </span>
          <span className="count-value">
            {clients.filter(c => c.isBlocked).length}
          </span>
        </div>
      </div>
      
      <div className="clients-grid">
        {clients.map(client => (
          <div 
            key={client.clientCode} 
            className={`client-card ${client.isBlocked ? 'blocked' : ''}`}
          >
            <div className="card-header">
              <div className="avatar" style={{ backgroundColor: getClientTypeColor(client.clientTypeCode) }}>
                {client.nameOrTitle.charAt(0).toUpperCase()}
              </div>
              <div className="client-name">
                <h2>{client.nameOrTitle}</h2>
                <span className={`rank-badge ${getRankBadgeClass(client.socialRankCode)}`}>
                  {getSocialRankName(client.socialRankCode)}
                </span>
              </div>
              
              {client.isBlocked && (
                <div className="blocked-badge">
                  <span className="blocked-icon">🔒</span>
                  <span>BLOCKED</span>
                </div>
              )}
            </div>

            <div className="card-body">
              <div className="info-item">
                <span className="label">CLIENT ID:</span>
                <span className="value code">{client.clientCode}</span>
              </div>
              
              <div className="info-item">
                <span className="label">CLIENT TYPE:</span>
                <span className="value">
                  <span className="type-icon">{getClientTypeIcon(client.clientTypeCode)}</span>
                  {getClientTypeName(client.clientTypeCode)}
                </span>
              </div>
              
              <div className="info-item">
                <span className="label">DISTRICT:</span>
                <span className="value">
                  <span className="district-icon">📍</span>
                  {getDistrictName(client.districtCode) || 'Unknown District'}
                </span>
              </div>
              
              <div className="info-item">
                <span className="label">STATUS:</span>
                <span className={`value status ${client.isBlocked ? 'blocked' : 'active'}`}>
                  {client.isBlocked ? 'BLOCKED 🔒' : 'ACTIVE ✅'}
                </span>
              </div>

              <div className="info-item">
                <span className="label">SECTOR:</span>
                <span className={`value status ${client.isBlocked ? 'blocked' : 'active'}`}>
                  {getSectorName(client.subSectorCode) || 'Unknown Sector'}
                </span>
              </div>
            </div>
            
          </div>
        ))}
      </div>
      
      <div className="clients-footer">
        <div className="footnote">
          <span className="warning-icon">⚠️</span>
          Client information is confidential. Unauthorized access is prohibited.
        </div>
        <div className="last-updated">
          Data fetched: {new Date().toLocaleString()}
        </div>
      </div>
    </div>
  );
}

// Helper function for avatar colors based on client type
const getClientTypeColor = (clientTypeCode: string): string => {
  const colorMap: Record<string, string> = {
    'INDV': '#3498db',    // Blue for individuals
    'CORP': '#2ecc71',    // Green for corporations
    'PART': '#9b59b6',    // Purple for partnerships
    'TRST': '#e74c3c',    // Red for trusts
    'NPO': '#f39c12',     // Orange for non-profits
    'GOVT': '#34495e',    // Dark gray for government
  };
  return colorMap[clientTypeCode] || '#95a5a6'; // Default gray
};

export default Clients;