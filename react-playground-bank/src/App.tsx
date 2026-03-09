import { BrowserRouter, NavLink, Route, Routes } from 'react-router-dom'
import Clients from './pages/clients/Clients';
import Vault from './pages/vaults/Vault';
import SocialRanks from './pages/social_ranks/SocialRanks';
import Districts from './pages/districts/Districts';
import Currencies from './pages/currencies/Currencies';
import Sectors from './pages/sectors/Sectors';
import './AppStyles.css';


interface NavLinkStyleProps {
  isActive: boolean;
}

const navLinkStyles = ({isActive}: NavLinkStyleProps): React.CSSProperties => ({
  color: isActive ? '#ffd700' : '#f1f1f1ff',
  borderBottom: isActive ? '2px solid #b8860b' : '2px solid transparent',
  textShadow: isActive ? '0 0 8px rgba(255, 215, 0, 0.6)' : 'none'
})


function App() {
  return (
    <BrowserRouter>
      <div className="app-container">
        {/* Navigation */}
        <nav className="main-nav">
          <div className="nav-container">
            <NavLink to="/" style={navLinkStyles} className="nav-link" data-testid="vaultsPageBtn">
              <span className="nav-icon">⚙️</span>
              VAULT
            </NavLink>
            <span className="nav-separator">|</span>
            <NavLink to="/clients" style={navLinkStyles} className="nav-link" data-testid="clientsPageBtn">
              <span className="nav-icon">👥</span>
              CLIENTS
            </NavLink>
            <span className="nav-separator">|</span>
            <NavLink to="/socialranks" style={navLinkStyles} className="nav-link" data-testid="socialRanksPageBtn">
              <span className="nav-icon">⚜️</span>
              SOCIAL RANKS
            </NavLink>
            <span className="nav-separator">|</span>
            <NavLink to="/districts" style={navLinkStyles} className="nav-link" data-testid="districtsPageBtn">
              <span className="nav-icon">🗺️</span>
              DISTRICTS
            </NavLink>
            <span className="nav-separator">|</span>
            <NavLink to="/currencies" style={navLinkStyles} className="nav-link" data-testid="currenciesPageBtn">
              <span className="nav-icon">💰</span>
              CURRENCIES
            </NavLink>
            <span className="nav-separator">|</span>
            <NavLink to="/sectors" style={navLinkStyles} className="nav-link" data-testid="sectorsPageBtn">
              <span className="nav-icon">📋</span>
              SECTORS
            </NavLink>
          </div>
        </nav>

        {/* Routes */}
        <main className="main-content">
          <Routes>
            <Route path="/" element={<Vault />} />
            <Route path="/clients" element={<Clients />} />
            <Route path="/socialranks" element={<SocialRanks />} />
            <Route path="/districts" element={<Districts />} />
            <Route path="/currencies" element={<Currencies />} />
            <Route path="/sectors" element={<Sectors />} />
          </Routes>
        </main>
      </div>
    </BrowserRouter>
  )
}

export default App;
