// src/pages/EmployeeSearch.js
import React, { useState, useEffect } from "react";
import "./EmployeeSearch.css";

const EmployeeSearch = () => {
  const [employees, setEmployees] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchEmployees = async () => {
      try {
        console.log("Fetching employees...");
        const response = await fetch("http://localhost:8080/api/employees");
        if (!response.ok) {
          throw new Error("Failed to fetch employees");
        }
        const data = await response.json();
        console.log("Fetched employees:", data);
        setEmployees(data);
      } catch (err) {
        console.error("Error fetching employees:", err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchEmployees();
  }, []);

  // Filter employees based on search term
  const filteredEmployees = employees.filter((emp) =>
    emp.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    emp.phoneNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
    emp.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
    emp.department.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return <p>Loading employees...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <section className="employee-search" aria-labelledby="employee-search-heading">
      <h2 id="employee-search-heading">Employee Search</h2>
      <div className="search-container">
        <label htmlFor="search-input">
          Search by name, phone, email, or department:
        </label>
        <input
          id="search-input"
          type="text"
          placeholder="Enter search term..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
      </div>
      {filteredEmployees.length === 0 ? (
        <p>No employees found.</p>
      ) : (
        <table className="employee-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Phone</th>
              <th>Email</th>
              <th>Gender</th>
              <th>Department</th>
              <th>Basic Salary</th>
            </tr>
          </thead>
          <tbody>
            {filteredEmployees.map((emp) => (
              <tr key={emp.id}>
                <td>{emp.name}</td>
                <td>{emp.phoneNumber}</td>
                <td>{emp.email}</td>
                <td>{emp.gender}</td>
                <td>{emp.department}</td>
                <td>KES {Number(emp.basicSalary).toLocaleString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  );
};

export default EmployeeSearch;
