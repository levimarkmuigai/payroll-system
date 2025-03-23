// src/pages/Payslips.js
import React, { useState, useEffect } from 'react';
import './Payslips.css';

const Payslips = () => {
  const [payslips, setPayslips] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPayslips = async () => {
      try {
        // Fetch dynamic payslip data from the backend API
        const response = await fetch('http://localhost:8080/api/payslips');
        if (!response.ok) {
          throw new Error('Failed to fetch payslips');
        }
        const data = await response.json();
        setPayslips(data);
      } catch (err) {
        console.error('Error fetching payslips:', err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchPayslips();
  }, []);

  if (loading) {
    return <p>Loading payslips...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <section className="payslips" aria-labelledby="payslips-heading">
      <h2 id="payslips-heading" className="payslips-title">Your Payslips</h2>
      {payslips.length === 0 ? (
        <p>No payslips available.</p>
      ) : (
        <ul className="payslips-list">
          {payslips.map((p) => (
            <li key={p.id} className="payslip-item">
              <div className="payslip-info">
                <p><strong>Month:</strong> {p.month}</p>
                <p><strong>Net Salary:</strong> KES {Number(p.netSalary).toLocaleString()}</p>
              </div>
              <button
                className="download-btn"
                onClick={() => window.open(p.downloadUrl, '_blank')}
                aria-label={`Download payslip for ${p.month}`}
              >
                Download
              </button>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
};

export default Payslips;
