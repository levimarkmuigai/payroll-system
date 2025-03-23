// src/pages/Payslips.js
import React, { useState, useEffect } from 'react';

// Placeholder for fetching payslip data
const fetchPayslips = () => {
  return Promise.resolve([
    { id: 1, employeeName: 'Alice', month: 'March 2025', netSalary: 40000 },
    { id: 2, employeeName: 'Bob', month: 'March 2025', netSalary: 48000 },
    // More sample payslip data
  ]);
};

function Payslips() {
  const [payslips, setPayslips] = useState([]);

  useEffect(() => {
    fetchPayslips().then((data) => {
      setPayslips(data);
    });
  }, []);

  return (
    <section aria-labelledby="payslips-heading">
      <h2 id="payslips-heading">Access and Download Payslips</h2>
      {payslips.length ? (
        <ul className="payslip-list">
          {payslips.map((slip) => (
            <li key={slip.id}>
              <strong>{slip.employeeName}</strong> - {slip.month} - Net Salary: {slip.netSalary} 
              <button aria-label={`Download payslip for ${slip.month} for ${slip.employeeName}`}>Download</button>
            </li>
          ))}
        </ul>
      ) : (
        <p>Loading payslips...</p>
      )}
    </section>
  );
}

export default Payslips;
