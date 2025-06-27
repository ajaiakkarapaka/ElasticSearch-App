
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';


function LabelsPage() {
  const [labels, setLabels] = useState([]);
  const navigate = useNavigate();
const BASE_URL = import.meta.env.VITE_API_BASE_URL;

 useEffect(() => {
axios.get(`${BASE_URL}/api/classifications/label`)
    .then(res =>setLabels(res.data.content || []))
    .catch(() => {
      alert('Failed to fetch classification labels');
      setLabels([]);
    });
}, []);

      
  return (
    <div style={{ padding: '2rem' }}>
      <h2>Classification Labels</h2>
      <button onClick={() => navigate('/')}>🏠 Home</button>
      <p></p>
      <table border="1" cellPadding="8">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Created By</th>
            <th>Created At</th>
          </tr>
        </thead>
        <tbody>
          {labels.map(label => (
            <tr key={label.id}>
              <td>{label.name}</td>
              <td>{label.description}</td>
              <td>{label.createdBy}</td>
              <td>{label.createdAt}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default LabelsPage;
