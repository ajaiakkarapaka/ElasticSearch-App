
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';



function ClassificationsPage() {
  const [labels, setLabels] = useState([]);
  const [indices, setIndices] = useState([]);
  const [form, setForm] = useState({ fieldName: '', classificationName: '', approvedBy: '' });
  const [editingId, setEditingId] = useState(null);
  const [showForm, setShowForm] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();
  const BASE_URL = import.meta.env.VITE_API_BASE_URL;



  useEffect(() => {
    fetchData();
  }, []);


const fetchData = () => {
  axios.get(`${BASE_URL}/api/classifications/label`)
    .then(res => {
      // Support both { content: [...] } and plain array
      if (res.data && Array.isArray(res.data.content)) {
        setLabels(res.data.content);
      } else if (Array.isArray(res.data)) {
        setLabels(res.data);
      } else {
        setLabels([]);
      }
    });
  axios.get(`${BASE_URL}/api/classifications/index`)
    .then(res => {
      if (res.data && Array.isArray(res.data.content)) {
        setIndices(res.data.content);
      } else if (Array.isArray(res.data)) {
        setIndices(res.data);
      } else {
        setIndices([]);
      }
    });
};
const handleSearch = () => {
  if (!searchQuery) {
    fetchData(); // fallback to full list
    return;
  }

  axios.get(`${BASE_URL}/api/classifications/index/search?q=${searchQuery}`)
    .then(res => setIndices(res.data || []))
    .catch(() => toast.error('Search failed'));
};

  const handleEdit = (index) => {
    setForm({
      fieldName: index.fieldName,
      classificationName: index.classificationName,
      approvedBy: index.approvedBy,
      modifiedBy: index.modifiedBy
    });
    setEditingId(index.id);
    setShowForm(true);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const apiCall = editingId
      ? axios.put(`${BASE_URL}/api/classifications/index/${editingId}`, form)
      : axios.post(`${BASE_URL}/api/classifications/index`, form);

    apiCall.then(() => {
      toast.success('Saved!');
      setForm({ fieldName: '', classificationName: '', approvedBy: '' });
      setEditingId(null);
      fetchData();
      setShowForm(false);
    }).catch(() => toast.error('Save failed!'));
  };

  const handleDelete = (id) => {
    axios.delete(`${BASE_URL}/api/classifications/index/${id}`)
      .then(() => {
        fetchData();
        toast.success('Deleted!');
      })
      .catch(() => toast.error('Delete failed!'));
  };

  return (
    <div style={{ padding: '2rem' }}>
      <h1>CLASSIFICATIONS</h1> <button onClick={() => navigate('/')}>🏠 Home</button>
      <p></p>
      <button onClick={() => setShowForm(true)}>Add New</button>  {showForm && (
        <form onSubmit={handleSubmit} style={{ marginTop: '1rem' }}>
          <input
            placeholder="Field Name"
            value={form.fieldName}
            onChange={e => setForm({ ...form, fieldName: e.target.value })}
            required
          />
          <select
            value={form.classificationName}
            onChange={e => setForm({ ...form, classificationName: e.target.value })}
            required
          >
            <option value="">Select Label</option>
           {Array.isArray(labels) && labels.map(label => (
            <option key={label.id} value={label.persistentData}>
            {label.persistentData}
            </option>
            ))}
          </select>
          <input
            placeholder="Approved By"
            value={form.approvedBy}
            onChange={e => setForm({ ...form, approvedBy: e.target.value })}
            required
          />
           <input
            placeholder="Modified By"
            value={form.modifiedBy ?? ''}
            onChange={e => setForm({ ...form, modifiedBy: e.target.value || null })}
          />
          <button type="submit">{editingId ? 'Update' : 'Submit'}</button>
        </form>
      )}
      <div style={{ marginBottom: '1rem' }}>
  <div style={{ display: 'inline-block', marginBottom: '2rem' }}></div>
  <input
    type="text"
    placeholder="Search..."
    value={searchQuery}
    onChange={e => setSearchQuery(e.target.value)}
  />
  <button onClick={handleSearch}>Search</button>

<button onClick={() => {
  setSearchQuery('');
  fetchData();
}}>Clear</button>
</div>

      <table border="1" cellPadding="8" style={{ marginTop: '2rem' }}>
        <thead>
          <tr>
            <th>Field Name</th>
            <th>Classification</th>
            <th>Approved By</th>
            <th>Created At</th>
            <th>Modified By</th>
            <th>Updated</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {indices.map(index => (
            <tr key={index.id}>
              <td>{index.fieldName}</td>
              <td>{ (() => {  const match = labels.find(label => label.persistentData === index.classificationName);
                              return match?.persistentData || 'Not found'; })()}</td>
              <td>{index.approvedBy}</td>
              <td>{index.createdAt}</td>
              <td>{index.modifiedBy}</td>
              <td>{index.updatedAt}</td>
              <td>
                <button onClick={() => handleEdit(index)}>Edit</button>
                <button onClick={() => handleDelete(index.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <ToastContainer position="top-right" autoClose={500} />
    </div>
  );
}

export default ClassificationsPage;
