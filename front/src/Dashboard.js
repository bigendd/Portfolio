import React, { useEffect, useState } from 'react';
import axios from 'axios';
import handlerDelete from './Deleteprojet';

const Dashboard = () => {
  const [projects, setProjects] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/projects')
      .then(response => setProjects(response.data))
      .catch(error => console.error('Erreur lors de la récupération des projets :', error));
  }, []);

  const handleDelete = async (id) => {
    try {
      await handlerDelete(id);
      setProjects(projects.filter(project => project.id !== id));
    } catch (error) {
      console.error('Erreur lors de la suppression du projet :', error);
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Projets</h1>
      {projects.length === 0 ? (
        <p>Aucun projet trouvé.</p>
      ) : (
        <ul>
          {projects.map((project, index) => (
            <li key={index}>
              <strong>{project.name}</strong><br />
              {project.description}
              <br />
              Technologies : {project.technologies}
              <br />
              <button onClick={() => handleDelete(project.id)}>Supprimer</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Dashboard;
