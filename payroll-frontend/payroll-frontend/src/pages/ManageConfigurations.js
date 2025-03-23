// src/pages/ManageConfigurations.js
import React, { useState, useEffect } from 'react';

// Placeholder API integration (you can replace with real API calls)
const fetchConfigurations = () => {
  return Promise.resolve({
    taxBands: [
      { id: 1, lowerLimit: 0, upperLimit: 24000, rate: 10 },
      { id: 2, lowerLimit: 24001, upperLimit: 32333, rate: 25 },
      // add more bands as needed...
    ],
    nhif: { rate: 2.75, minContribution: 300, maxContribution: 0 },
    nssf: { tier1LowerLimit: 8000, tier1Rate: 6, tier2LowerLimit: 8001, tier2UpperLimit: 72000, tier2Rate: 6 },
  });
};

function ManageConfigurations() {
  const [configurations, setConfigurations] = useState(null);

  useEffect(() => {
    // Simulate API call
    fetchConfigurations().then((data) => {
      setConfigurations(data);
    });
  }, []);

  return (
    <section aria-labelledby="configurations-heading">
      <h2 id="configurations-heading">Manage Configurations</h2>
      {configurations ? (
        <div className="configurations">
          <h3>Tax Bands</h3>
          <ul>
            {configurations.taxBands.map((band) => (
              <li key={band.id}>
                {band.lowerLimit} - {band.upperLimit} : {band.rate}%
              </li>
            ))}
          </ul>
          <h3>NHIF Settings</h3>
          <p>
            Rate: {configurations.nhif.rate}% | Min: {configurations.nhif.minContribution} | Max: {configurations.nhif.maxContribution || 'No Max'}
          </p>
          <h3>NSSF Settings</h3>
          <p>
            Tier I: Up to {configurations.nssf.tier1LowerLimit} at {configurations.nssf.tier1Rate}%<br />
            Tier II: {configurations.nssf.tier2LowerLimit} - {configurations.nssf.tier2UpperLimit} at {configurations.nssf.tier2Rate}%
          </p>
          {/* Additional configuration management controls (forms, buttons) can be added here */}
        </div>
      ) : (
        <p>Loading configurations...</p>
      )}
    </section>
  );
}

export default ManageConfigurations;
