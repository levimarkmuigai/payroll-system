// src/pages/LeaveOverview.js
import React, { useState, useEffect } from 'react';
import './LeaveOverview.css';

const LeaveOverview = () => {
  const [leaveCount, setLeaveCount] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchLeaveData = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/leaves');
        if (!response.ok) {
          throw new Error('Failed to fetch leave applications.');
        }
        const data = await response.json();
        setLeaveCount(data.length);
      } catch (err) {
        console.error('Error fetching leave applications:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchLeaveData();
  }, []);

  if (loading) {
    return <p>Loading leave overview...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <div className="leave-overview-container">
      <h2 className="leave-overview-heading">Leave Overview</h2>
      <div className="leave-overview-card" role="region" aria-labelledby="leave-overview-heading">
        <p className="overview-number">{leaveCount}</p>
        <p className="overview-label">Total Leave Applications</p>
      </div>
    </div>
  );
};

export default LeaveOverview;
