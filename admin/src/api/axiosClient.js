import axios from 'axios';

export const axiosClient = axios.create({
    // baseURL: 'https://yc8vw.sse.codesandbox.io/',
    baseURL: 'http://localhost:8080/chatroom/',
    headers: {
        'Authorization': sessionStorage.getItem('accessToken'),
        // 'Content-Type': 'application/json'
    },
});
