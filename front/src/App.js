import React, { useState, useEffect } from 'react';
import axios from 'axios';

const App = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    // Remplace par l'URL de ton API Java
    axios.get('http://localhost:8080/api/projects')
      .then(response => {
        setData(response.data);
      })
      .catch(error => {
        console.error('Il y a eu une erreur avec la requête API:', error);
      });
  }, []);

  return (
    <div>
      <h1>Data from Java API:</h1>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
};

export default App;
