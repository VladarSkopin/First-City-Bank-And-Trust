import './ClientsStyles.css';
import './LoadingStyles.css';
import './SearchStyles.css';
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

  // Search criteria
  const [searchName, setSearchName] = useState('');
  const [selectedSocialRank, setSelectedSocialRank] = useState('');
  const [selectedClientType, setSelectedClientType] = useState('');
  const [selectedSubSector, setSelectedSubSector] = useState('');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [isBlockedFilter, setIsBlockedFilter] = useState<boolean | null>(null); // null = any

  // Search loading indicator
  const [searching, setSearching] = useState(false);

  // Fetch all required data in parallel (reusable)
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

  useEffect(() => {
    fetchAllData();
  }, []);


  // Search handler
  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    setSearching(true);
    setError(null);

    try {
      const params = new URLSearchParams();
      if (searchName.trim()) params.append('nameOrTitle', searchName.trim());
      if (selectedSocialRank) params.append('socialRankCode', selectedSocialRank);
      if (selectedClientType) params.append('clientTypeCode', selectedClientType);
      if (selectedSubSector) params.append('subSectorCode', selectedSubSector);
      if (selectedDistrict) params.append('districtCode', selectedDistrict);
      if (isBlockedFilter !== null) params.append('isBlocked', String(isBlockedFilter));

      const response = await fetch(`http://localhost:8080/api/v1/clients/search?${params.toString()}`);
      if (!response.ok) throw new Error('Search failed');
      const data = await response.json();
      setClients(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to search clients');
    } finally {
      setSearching(false);
    }
  };

  // Reset filters and reload all clients
  const resetFilters = () => {
    setSearchName('');
    setSelectedSocialRank('');
    setSelectedClientType('');
    setSelectedSubSector('');
    setSelectedDistrict('');
    setIsBlockedFilter(null);
    fetchAllData(); // Reload all clients
  };



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
          <div className="loading-spinner" data-testid="loading-spinner"></div>
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
          <h2 data-testid="pageTitle">Failed to Load Clients</h2>
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

  if (clients.length === 0) {
    return (
      <div className="clients-container">
        <div className="empty-state">
          <div className="empty-icon">🤵</div>
          <h3 data-testid="pageTitle">No Clients Found</h3>
          <p>No clients match your search criteria or the database is empty.</p>
        </div>
      </div>
    );
  }

  
  return (
    <div className="clients-container">
      <h1 className="page-title" data-testid="pageTitle">Banking Clients</h1>

      {/* Search Form */}
      <div className="search-section">
        <form onSubmit={handleSearch} className="search-form">
          <div className="search-row">
            <input
              type="text"
              placeholder="Client name..."
              value={searchName}
              onChange={(e) => setSearchName(e.target.value)}
              className="search-input"
            />
            <select value={selectedSocialRank} onChange={(e) => setSelectedSocialRank(e.target.value)}>
              <option value="">All Social Ranks</option>
              {socialRanks.map(rank => (
                <option key={rank.rankCode} value={rank.rankCode}>{rank.rankName}</option>
              ))}
            </select>
            <select value={selectedClientType} onChange={(e) => setSelectedClientType(e.target.value)}>
              <option value="">All Client Types</option>
              {clientTypes.map(type => (
                <option key={type.clientTypeCode} value={type.clientTypeCode}>{type.clientTypeName}</option>
              ))}
            </select>
          </div>
          <div className="search-row">
            <select value={selectedSubSector} onChange={(e) => setSelectedSubSector(e.target.value)}>
              <option value="">All Sub‑Sectors</option>
              {subSectors.map(ss => (
                <option key={ss.subSectorCode} value={ss.subSectorCode}>{ss.subSectorName}</option>
              ))}
            </select>
            <select value={selectedDistrict} onChange={(e) => setSelectedDistrict(e.target.value)}>
              <option value="">All Districts</option>
              {districts.map(d => (
                <option key={d.districtCode} value={d.districtCode}>{d.districtName}</option>
              ))}
            </select>
            <label className="checkbox-label">
              <input
                type="checkbox"
                checked={isBlockedFilter === true}
                onChange={(e) => setIsBlockedFilter(e.target.checked ? true : null)}
              />
              Blocked only
            </label>
          </div>
          <div className="search-actions">
            <button type="submit" disabled={searching}>
              {searching ? 'Searching...' : 'Search Clients'}
            </button>
            <button type="button" onClick={resetFilters} disabled={searching}>
              Reset
            </button>
          </div>
        </form>
      </div>
      
      {/* Stats bar */}
      <div className="clients-stats">
        <div className="stat-item">
          <span className="count-label" data-testid="countLabel">TOTAL CLIENTS: </span>
          <span className="count-value" data-testid="countValue">{clients.length}</span>
        </div>
        <div className="stat-item">
          <span className="count-label" data-testid="countLabelActive">ACTIVE: </span>
          <span className="count-value" data-testid="countValueActive">
            {clients.filter(c => !c.isBlocked).length}
          </span>
        </div>
        <div className="stat-item">
          <span className="count-label" data-testid="countLabelBlocked">BLOCKED: </span>
          <span className="count-value" data-testid="countValueBlocked">
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
              <div className="avatar" style={{ backgroundColor: getClientTypeColor(client.clientTypeCode) }} data-testid="avatar">
                {client.nameOrTitle.charAt(0).toUpperCase()}
              </div>
              <div className="client-name">
                <h2 data-testid="nameOrTitle">{client.nameOrTitle}</h2>
                <span className={`rank-badge ${getRankBadgeClass(client.socialRankCode)}`} data-testid="rankName">
                  {getSocialRankName(client.socialRankCode)}
                </span>
              </div>
              
              {client.isBlocked && (
                <div className="blocked-badge">
                  <span className="blocked-icon">🔒</span>
                  <span data-testid="blockedBanner">BLOCKED</span>
                </div>
              )}
            </div>

            <div className="card-body">
              <div className="info-item">
                <span className="label" data-testid="clientCodeLabel">CLIENT ID:</span>
                <span className="value code" data-testid="clientCodeValue">{client.clientCode}</span>
              </div>
              
              <div className="info-item">
                <span className="label" data-testid="clientTypeLabel">CLIENT TYPE:</span>
                <span className="value" data-testid="clientTypeValue">
                  <span className="type-icon">{getClientTypeIcon(client.clientTypeCode)}</span>
                  {getClientTypeName(client.clientTypeCode)}
                </span>
              </div>
              
              <div className="info-item">
                <span className="label" data-testid="districtLabel">DISTRICT:</span>
                <span className="value" data-testid="districtValue">
                  <span className="district-icon">📍</span>
                  {getDistrictName(client.districtCode) || 'Unknown District'}
                </span>
              </div>
              
              <div className="info-item">
                <span className="label" data-testid="statusLabel">STATUS:</span>
                <span className={`value status ${client.isBlocked ? 'blocked' : 'active'}`} data-testid="statusValue">
                  {client.isBlocked ? 'BLOCKED 🔒' : 'ACTIVE ✅'}
                </span>
              </div>

              <div className="info-item">
                <span className="label" data-testid="sectorLabel">SECTOR:</span>
                <span className={`value status ${client.isBlocked ? 'blocked' : 'active'}`} data-testid="sectorValue">
                  {getSectorName(client.subSectorCode) || 'Unknown Sector'}
                </span>
              </div>
            </div>
            
          </div>
        ))}
      </div>
      
      <div className="clients-footer">
        <div className="footnote" data-testid="warningNote">
          <span className="warning-icon">⚠️</span>
          Client information is confidential. Unauthorized access is prohibited.
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