import React, { useState } from "react";
import "./ManageConfigurations.css";

const ManageConfigurations = () => {
  const [config, setConfig] = useState({
    taxRate: "",
    deductionPercentage: "",
    paymentFrequency: "monthly",
    enablePayrollRules: true,
  });
  const [message, setMessage] = useState(null);
  const [errors, setErrors] = useState({});

  const validate = () => {
    let errors = {};
    if (config.taxRate < 0 || config.taxRate > 50) {
      errors.taxRate = "Tax rate must be between 0% and 50%.";
    }
    if (config.deductionPercentage < 0 || config.deductionPercentage > 100) {
      errors.deductionPercentage = "Deduction must be between 0% and 100%.";
    }
    return errors;
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setConfig((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validate();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
      return;
    }
    setErrors({});
    setMessage("Configurations saved successfully!");
    setTimeout(() => setMessage(null), 3000);
  };

  return (
    <div className="config-container">
      <h2>Manage Payroll Configurations</h2>
      {message && <p className="success-message">{message}</p>}
      <form onSubmit={handleSubmit} className="config-form">
        <label>
          Tax Rate (%):
          <input
            type="number"
            name="taxRate"
            value={config.taxRate}
            onChange={handleChange}
          />
          {errors.taxRate && <span className="error">{errors.taxRate}</span>}
        </label>

        <label>
          Deduction Percentage (%):
          <input
            type="number"
            name="deductionPercentage"
            value={config.deductionPercentage}
            onChange={handleChange}
          />
          {errors.deductionPercentage && (
            <span className="error">{errors.deductionPercentage}</span>
          )}
        </label>

        <label>
          Payment Frequency:
          <select
            name="paymentFrequency"
            value={config.paymentFrequency}
            onChange={handleChange}
          >
            <option value="weekly">Weekly</option>
            <option value="bi-weekly">Bi-Weekly</option>
            <option value="monthly">Monthly</option>
          </select>
        </label>

        <label className="checkbox-label">
          <input
            type="checkbox"
            name="enablePayrollRules"
            checked={config.enablePayrollRules}
            onChange={handleChange}
          />
          Enable Payroll Rules
        </label>

        <div className="button-group">
          <button type="submit" className="save-btn">Save</button>
          <button type="button" className="cancel-btn" onClick={() => setConfig({
            taxRate: "",
            deductionPercentage: "",
            paymentFrequency: "monthly",
            enablePayrollRules: true,
          })}>Cancel</button>
        </div>
      </form>
    </div>
  );
};

export default ManageConfigurations;
