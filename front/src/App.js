import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './login';
import Dashboard from './Dashboard';
import Register from './register';
import Createprojet from './Createprojet';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/" element={<Dashboard />} />
        <Route path="/register" element={<Register />} />
        <Route path="/createprojet" element={<Createprojet />} />
      </Routes>
    </Router>
  );
};

export default App;
