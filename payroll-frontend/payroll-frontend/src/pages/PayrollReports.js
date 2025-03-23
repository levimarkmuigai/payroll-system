// src/pages/PayrollReports.js
import React, { useState, useEffect } from 'react';
import './PayrollReports.css';

const PayrollReports = () => {
  const [reports, setReports] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchReports = async () => {
      try {
        // Replace with your actual API endpoint URL
        const response = await fetch('http://localhost:8080/api/payrollreports');
        if (!response.ok) {
          throw new Error('Failed to fetch payroll reports');
        }
        const data = await response.json();
        console.log('Fetched payroll reports:', data); // Debugging log
        setReports(data);
      } catch (err) {
        console.error('Error fetching payroll reports:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchReports();
  }, []);

  if (loading) {
    return <p>Loading payroll reports...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <section className="payroll-reports" aria-labelledby="reports-heading">
      <h2 id="reports-heading" className="reports-title">Payroll Reports</h2>
      <div className="reports-table-container">
        <table className="reports-table">
          <thead>
            <tr>
              <th>Employee</th>
              <th>Gross Salary</th>
              <th>Net Salary</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            {reports.map((report) => (
              <tr key={report.id}>
                <td>{report.employeeName}</td>
                <td>{Number(report.grossSalary).toLocaleString()}</td>
                <td>{Number(report.netSalary).toLocaleString()}</td>
                <td>{report.date}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="reports-graph-placeholder">
        <p>[Graphical Report Placeholder]</p>
      </div>
    </section>
  );
};

export default PayrollReports;
