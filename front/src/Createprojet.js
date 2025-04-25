import React, { useState } from 'react';
import axios from 'axios';

const Createprojet = () => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [technologies, setTechnologies] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();

        // Convertit la chaîne en tableau en séparant par les virgules
        const techArray = technologies.split(',').map(tech => tech.trim());

        axios.post('http://localhost:8080/api/projects', {
            name,
            description,
            technologies: techArray
        })
        .then(response => {
            console.log('Projet créé avec succès !');
            setName('');
            setDescription('');
            setTechnologies('');
        })
        .catch(error => {
            console.error('Erreur lors de la création du projet:', error);
        });
    };

    return (
        <div style={{ padding: '2rem' }}>
            <h1>Créer un projet</h1>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="Nom du projet"
                    required
                    style={{ display: 'block', margin: '1rem 0', padding: '0.5rem' }}
                />
                <input
                    type="text"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Description"
                    required
                    style={{ display: 'block', margin: '1rem 0', padding: '0.5rem' }}
                />
                <input
                    type="text"
                    value={technologies}
                    onChange={(e) => setTechnologies(e.target.value)}
                    placeholder="Technologies (ex: React, Spring Boot)"
                    required
                    style={{ display: 'block', margin: '1rem 0', padding: '0.5rem' }}
                />
                <small style={{ color: '#555' }}>
                    Entrez les technologies séparées par des virgules.
                </small>
                <br />
                <button type="submit" style={{ marginTop: '1rem', padding: '0.5rem 1rem' }}>
                    Créer
                </button>
            </form>

            
        </div>
    );
};

export default Createprojet;
