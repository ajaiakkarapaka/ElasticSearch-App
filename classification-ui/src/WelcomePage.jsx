import React from 'react';
import { useNavigate } from 'react-router-dom';

function WelcomePage() {
  const navigate = useNavigate();

  return (
    <div style={{ textAlign: 'center', padding: '2rem' }}>
      <h1>Welcome to Classifications</h1>
      <button onClick={() => navigate('/labels')} style={{ margin: '1rem', padding: '1rem' }}>View Labels</button>
      <button onClick={() => navigate('/classifications')} style={{ margin: '1rem', padding: '1rem' }}>Manage Classifications</button>
    </div>
  );
}

export default WelcomePage;
