import './ClientsStyles.css';


function Clients() {

  const clients = [
    {code: 'ABC-123', name: 'Norman', surname: 'Truart', sex: 'male', birthdate: '1211-01-25', socialRank: 'Noble', district: 'Old Quarter'},
    {code: 'XFD-762', name: 'Victoria', surname: 'Driad', sex: 'female', birthdate: 'unknown', socialRank: 'Foreigner', district: 'Auldale'},
    {code: 'GTG-515', name: 'Garrett', surname: 'Master Thief', sex: 'male', birthdate: '1223-09-21', socialRank: 'Commoner', district: 'South Quarter'}
  ];

  const formatDate = (dateString: string | number | Date) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };
  

  return (
    <div className="clients-container">
      <h1 className="page-title">Clients</h1>
      <div className="clients-grid">
        {
          clients.map(client => (
            <div key={client.code} className="client-card">

              <div className="card-header">
                <div className="avatar">
                  {client.name.charAt(0)}{client.surname.charAt(0)}
                </div>
                <div className="client-name">
                  <h2>{client.name} {client.surname}</h2>
                  <span className={`rank-badge rank-${client.socialRank?.toLowerCase()}`}>
                    {client.socialRank}
                  </span>
                </div>
              </div>

              <div className="card-body">
                <div className="info-item">
                  <span className="label">Client ID: </span>
                  <span className="value">{client.code.toString().padStart(4, '0')}</span>
                </div>
                <div className="info-item">
                  <span className="label">Birthdate: </span>
                  <span className="value">{formatDate(client.birthdate)}</span>
                </div>
                <div className="info-item">
                  <span className="label">Sex: </span>
                  <span className="value">{client.sex}</span>
                </div>
                <div className="info-item">
                  <span className="label">District: </span>
                  <span className="value">{client.district}</span>
              </div>

              </div>


            </div>
          ))
        }
      </div>
    </div>
  )
}

export default Clients;