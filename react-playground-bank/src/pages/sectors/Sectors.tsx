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



  // Loading state

  if (loading) {
    const loadedItems = Object.values(fetchStatus).filter(Boolean).length;
    const totalItems = Object.keys(fetchStatus).length;
    
    return (
      <div className="sectors-container">
        <div className="loading-state">
          <div className="loading-spinner"></div>
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
        <div>



        </div>
    );
}

export default Sectors;