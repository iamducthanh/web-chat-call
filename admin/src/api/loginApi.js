import axios from 'axios';

export const loginClient = axios.create({
    // baseURL: 'https://yc8vw.sse.codesandbox.io/',
    baseURL: 'http://localhost:8080/chatroom/',
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'Access-Control-Allow-Origin': 'http://localhost:3000',
        'Access-Control-Allow-Methods': 'POST',
        'Access-Control-Allow-Headers': 'Content-Type, Authorization'
    },
});