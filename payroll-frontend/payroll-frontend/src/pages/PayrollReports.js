// src/pages/PayrollReports.js
import React, { useState, useEffect } from 'react';

// Placeholder for fetching payroll data
const fetchPayrollReports = () => {
  return Promise.resolve([
    { id: 1, employeeName: 'Alice', grossSalary: 50000, netSalary: 40000 },
    { id: 2, employeeName: 'Bob', grossSalary: 60000, netSalary: 48000 },
    // Add more sample data
  ]);
};

function PayrollReports() {
  const [reports, setReports] = useState([]);

  useEffect(() => {
    fetchPayrollReports().then((data) => {
      setReports(data);
    });
  }, []);

  return (
    <section aria-labelledby="reports-heading">
      <h2 id="reports-heading">Payroll Reports</h2>
      {reports.length ? (
        <div className="reports">
          <table>
            <thead>
              <tr>
                <th>Employee</th>
                <th>Gross Salary</th>
                <th>Net Salary</th>
              </tr>
            </thead>
            <tbody>
              {reports.map((report) => (
                <tr key={report.id}>
                  <td>{report.employeeName}</td>
                  <td>{report.grossSalary}</td>
                  <td>{report.netSalary}</td>
                </tr>
              ))}
            </tbody>
          </table>
          {/* Placeholder for graphical reports */}
          <div className="graphical-report">
            <p>[Graphical Report Placeholder]</p>
          </div>
        </div>
      ) : (
        <p>Loading reports...</p>
      )}
    </section>
  );
}

export default PayrollReports;
