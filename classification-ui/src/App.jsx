import React from 'react';
import { BrowserRouter as Router, Routes, Route, BrowserRouter } from 'react-router-dom';
import WelcomePage from './WelcomePage';
import LabelsPage from './LabelsPage';
import ClassificationsPage from './ClassificationsPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<WelcomePage />} />
        <Route path="/labels" element={<LabelsPage />} />
        <Route path="/classifications" element={<ClassificationsPage />} />
      </Routes>
    </Router>
  );
}

export default App;
