import { axiosClient } from './axiosClient';

const dashboardApi = {
    
    getData(year) {
        return axiosClient.get(`/api/admin/thong-ke-tin-nhan?year=${year}`);
    },
    getTKUser() {
        return axiosClient.get(`/api/admin/thong-ke-nguoi-dung`);
    },
}
export default dashboardApi;