import axios from 'axios';

const handlerDelete = async (id) => {
  return axios.delete(`http://localhost:8080/api/projects/${id}`);
};

export default handlerDelete;
