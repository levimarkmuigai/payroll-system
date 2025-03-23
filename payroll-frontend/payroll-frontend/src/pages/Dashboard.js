// src/pages/Dashboard.js
import React, { useState, useEffect } from 'react';
import './Dashboard.css';

function Dashboard() {
  const [dashboardData, setDashboardData] = useState({
    totalEmployees: 0,
    totalPayroll: 0,
    netSalarySum: 0,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Using async/await for clarity
    const fetchDashboardData = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/dashboard');
        if (!response.ok) {
          throw new Error('Failed to fetch dashboard data.');
        }
        const data = await response.json();
        console.log('Fetched dashboard data:', data); // Debug log
        setDashboardData(data);
      } catch (err) {
        console.error('Error fetching dashboard data:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  if (loading) {
    return <p>Loading dashboard data...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <section className="dashboard" aria-labelledby="dashboard-heading">
      <h2 id="dashboard-heading" className="dashboard-title">Dashboard</h2>
      <div className="dashboard-cards">
        <div className="dashboard-card">
          <h3>Total Employees</h3>
          <p>{dashboardData.totalEmployees}</p>
        </div>
        <div className="dashboard-card">
          <h3>Total Payroll</h3>
          <p>KES {Number(dashboardData.totalPayroll).toLocaleString()}</p>
        </div>
        <div className="dashboard-card">
          <h3>Net Salary Sum</h3>
          <p>KES {Number(dashboardData.netSalarySum).toLocaleString()}</p>
        </div>
      </div>
      <div className="dashboard-report">
        <h3>Payroll Overview</h3>
        <p>
          View a summary of payroll details including gross and net salaries, deductions, and trends over time.
        </p>
        <button className="btn-primary">View Detailed Report</button>
      </div>
    </section>
  );
}

export default Dashboard;
