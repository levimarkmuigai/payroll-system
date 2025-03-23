// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Header from './components/Header';
import Navigation from './components/Navigation';
import Footer from './components/Footer';
import Dashboard from './pages/Dashboard';
import ManageConfigurations from './pages/ManageConfigurations';
import PayrollReports from './pages/PayrollReports';
import Payslips from './pages/Payslips';
import './App.css'; // App-specific styles
import LeaveOverview from './pages/LeaveOverview';
import EmployeeSearch from './pages/EmployeeSearch';

function App() {
  return (
    <Router>
      <div className="app-container">
        <Header />
        <Navigation />
        <main className="main-content" aria-label="Main content area">
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/employees" element={<EmployeeSearch />} />
            <Route path="/configurations" element={<ManageConfigurations />} />
            <Route path="/reports" element={<PayrollReports />} />
            <Route path="/leave" element={<LeaveOverview />} />
            <Route path="/payslips" element={<Payslips />} />
            {/* Redirect any unknown routes to dashboard */}
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
