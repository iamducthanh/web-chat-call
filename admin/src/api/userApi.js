import { axiosClient } from './axiosClient';
import { loginClient } from './loginApi';

const userApi = {
    getToken(data){
        return loginClient.get(`api/login?username=${data.username}&password=${data.password}`);
    },

    getUsers(username){
        return axiosClient.get(`/api/admin/users?username=${username}`);
    },

    getUser(id){
        return axiosClient.get(`/api/admin/user/${id}`);
    },

    updateUser(data){
        return axiosClient.post(`/api/admin/user/save`, data);
    }
}
export default userApi;