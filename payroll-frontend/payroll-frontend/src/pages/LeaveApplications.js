// src/pages/LeaveApplications.js
import React, { useState, useEffect } from 'react';

// Placeholder for leave applications API
const fetchLeaveApplications = () => {
  return Promise.resolve([
    { id: 1, employeeName: 'Alice', startDate: '2025-04-01', endDate: '2025-04-05', status: 'Approved' },
    { id: 2, employeeName: 'Bob', startDate: '2025-05-10', endDate: '2025-05-12', status: 'Pending' },
    // More sample leave data
  ]);
};

function LeaveApplications() {
  const [leaveApps, setLeaveApps] = useState([]);

  useEffect(() => {
    fetchLeaveApplications().then((data) => {
      setLeaveApps(data);
    });
  }, []);

  return (
    <section aria-labelledby="leave-heading">
      <h2 id="leave-heading">Process Leave Applications</h2>
      {leaveApps.length ? (
        <table>
          <thead>
            <tr>
              <th>Employee</th>
              <th>Start Date</th>
              <th>End Date</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {leaveApps.map((app) => (
              <tr key={app.id}>
                <td>{app.employeeName}</td>
                <td>{app.startDate}</td>
                <td>{app.endDate}</td>
                <td>{app.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>Loading leave applications...</p>
      )}
    </section>
  );
}

export default LeaveApplications;
