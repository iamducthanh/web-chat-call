'use strict';

let stompClientSystem = null;
let stompClientMessageListen = null;
let stompClientCall = null;
let stompClientRoom = null;
let stompClientFriend = null;
let userOnline = null;
let userIdOnline = null;
let socketClient = null;
let socketSystem = null;
let socketCall = null;
let socketRoom = null;
let socketFriend = null;
userOnline = document.querySelector('#userOnline').value.trim();
userIdOnline = document.querySelector('#userIdOnline').value.trim();

function online(event) {
    userOnline = document.querySelector('#userOnline').value.trim();
    if (userOnline) {
        socketClient = new SockJS('/chatroom/system');
        stompClientSystem = Stomp.over(socketClient);
        stompClientSystem.connect({}, socketClientConected, onError1);

        socketSystem = new SockJS('/chatroom/system');
        stompClientMessageListen = Stomp.over(socketSystem);
        stompClientMessageListen.connect({}, socketSystemConected, onError2);

        socketCall = new SockJS('/chatroom/system');
        stompClientCall = Stomp.over(socketCall);
        stompClientCall.connect({}, socketCallConected, onError3);

        socketRoom = new SockJS('/chatroom/wss');
        stompClientRoom = Stomp.over(socketRoom);
        stompClientRoom.connect({}, socketRoomConected, onError4);

        socketFriend = new SockJS('/chatroom/wss');
        stompClientFriend = Stomp.over(socketFriend);
        stompClientFriend.connect({}, socketFriendConected, onError5);
    }
}

function socketClientConected(){
    stompClientSystem.subscribe('/topic/system.adduser', onMessageReceivedOnline);
    stompClientSystem.send("/app/system.adduser",
        {},
        JSON.stringify({username: userOnline, type: 'ONLINE'})
    );
}
function socketSystemConected(){
    stompClientMessageListen.subscribe('/topic/system.onmessage/' + userOnline, onMessageRealtime);
}
function socketCallConected(){
    stompClientCall.subscribe('/topic/call/' + userOnline, onCall);
}
function socketRoomConected(){
    stompClientRoom.subscribe('/topic/system/' + userOnline, onAddRoom);
}
function socketFriendConected(){
    stompClientRoom.subscribe('/topic/system/friend/' + userOnline, onFriendRequest);
}

function onError1() {
    console.log("error ------ stompClientSystem")
}

function onError2() {
    console.log("error ------ stompClientMessageListen")
}

function onError3() {
    console.log("error ------ stompClientCall")
}

function onError4() {
    console.log("error ------ stompClientRoom")
}

function onError5() {
    console.log("error ------ stompClientFriend")
}

function onAddRoom(payload) {
    let messageUser = JSON.parse(payload.body);
    console.log(messageUser)
    let contentUserMessage = document.getElementById("contentUserMessage");
    let name = 'messUser' + document.querySelector("#userOnline").value + messageUser.sender;
    let idUsername = messageUser.sender + "NotFriend";
    let statusOnline = "avatar";
    if(messageUser.friend){
        idUsername = messageUser.sender;
        if(messageUser.online){
            statusOnline = "avatar avatar-online";
        } else {
            statusOnline = "avatar avatar-offline";
        }
    }
    contentUserMessage.innerHTML =
        "<div onclick='onRoomMessage(\""+messageUser.roomId+"\")' " +
        "class='card border-0 text-reset' id='"+name+"'>" +
        "<div class='card-body'>" +
        "<div class='row gx-5'>" +
        "<div class='col-auto'>" +
        "<div class='"+statusOnline+"' id='" + idUsername + "'>" +
        "<img src='" + messageUser.image + "' alt='#' class='avatar-img'>" +
        "</div>" +
        "</div>" +
        "<div class='col'>" +
        "<div class='d-flex align-items-center mb-3'>" +
        "<h5 class='me-auto mb-0 messageSend' name='" + name + "'>" + messageUser.fullname + "</h5>" +
        "<span class='text-muted extra-small ms-2 messageSend' name='" + name + "'>"+messageUser.time+"</span>" +
        "</div>" +
        "<div class='d-flex align-items-center'>" +
        "<div class='line-clamp me-auto messageSend' name='" + name + "'>" +
        messageUser.content +
        "</div>" +
        "<div class='badge badge-circle bg-primary ms-5'>" +
        "<span name='" + name + "'>1</span>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" + contentUserMessage.innerHTML;

}

function onCall(payload) {
    let call = JSON.parse(payload.body);
    console.log(call.fullnameCaller + "  " + call.ImageCaller)
    if (call.status == 'SENDCALL') {
        document.getElementById("waitcall").style.display = "unset";
        document.getElementById("idRoomMeet").value = call.rooomId;
        document.getElementById("nameCall1").innerHTML = call.fullnameCaller + ' đang gọi cho bạn!';
        document.getElementById("avtCall1").src = call.imageCaller;
        document.getElementById("caller1").value = call.caller;
    } else if (call.status == 'CALLRETURN') {
        document.getElementById("container2call").style.width = '90%'
        document.getElementById("container2call").style.height = '90%'
        document.getElementsByClassName("waitCall1")[0].style.display = 'none';
        document.getElementById("containerCall").style.display = 'unset';
        document.getElementById("inCall").style.display = 'unset';
    }
}

function onMessageRealtime(payload) {
    let messageRealtime = JSON.parse(payload.body);
    let classname = 'messUser' + messageRealtime.reader + messageRealtime.sender;
    let contentUserMessage = document.getElementById("contentUserMessage");
    let userMessage = document.getElementById(classname);
    if(userMessage != null){
        contentUserMessage.removeChild(userMessage);
    }
    let divAdd = document.createElement("div");
    divAdd.appendChild(userMessage);
    contentUserMessage.innerHTML = divAdd.innerHTML + contentUserMessage.innerHTML
    console.log("classname: ---------------- " + classname)
    let elms = document.getElementsByName(classname);
    console.log(elms)
    if (elms != null) {
        // tạo thông báo tin nhắn
        elms[0].className = 'me-auto mb-0 messageSend';
        elms[1].className = 'text-muted extra-small ms-2 messageSend';
        elms[2].className = 'line-clamp me-auto messageSend';
        elms[2].innerText = messageRealtime.content;
        console.log("count:  " + elms[3].innerText);
        elms[3].innerText = Number(elms[3].innerText) + 1;

        // tạo số lượng tin nhắn
        let countMessage = document.getElementById('countMessage');
        if (countMessage != null) {
            countMessage.innerText = Number(countMessage.innerText) + 1;
        } else {
            let divCountMessage = document.getElementById("divCountMessage");
            divCountMessage.className += ' icon-badged';
            let divSpan = document.createElement('div');
            divSpan.className = 'badge badge-circle bg-primary';
            divSpan.id = 'onRemoveCount';
            divCountMessage.appendChild(divSpan);
            let span = document.createElement("span");
            span.id = 'countMessage';
            span.innerText = '1';
            divSpan.appendChild(span);
        }
    }
}

function onMessageReceivedOnline(payload) {
    let user = JSON.parse(payload.body);
    let userClass = document.getElementById(user.username);
    let userClassFriend = document.getElementById(user.username + 'Friend');
    let userStatus = document.getElementsByClassName(user.username)[0];
    let userStatusFriend = document.getElementsByClassName(user.username + 'Friend')[0];
    console.log("thông báo online từ " + user.username)
    if (userClass != null) {
        if (user.type == 'ONLINE') {
            userClass.className = 'avatar avatar-online';
            userClassFriend.className = 'avatar avatar-online';
            if (userStatus != null) {
                userStatus.innerText = 'Đang hoạt động';
            }
            if (userStatusFriend != null) {
                userStatusFriend.innerText = 'Đang hoạt động';
            }
        } else if (user.type == 'OFFLINE') {
            if (userStatus != null) {
                userStatus.innerText = 'Không hoạt động';
            }
            if (userStatusFriend != null) {
                userStatusFriend.innerText = 'Không hoạt động';
            }
            userClass.className = 'avatar avatar-offline';
            userClassFriend.className = 'avatar avatar-offline';
        }
    }
}

online();

function deleteAttack(aa) {
    console.log(aa)
    $.ajax({
        url: 'removeImage',
        data: {
            fileName: aa,
            userId: userOnline,
        },
        error: function () {
            console.log("error")
        },
        success: function (data) {
            console.log("thanh cong")
        },
        type: 'POST'
    });
}

const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});

function uuid() {
    let temp_url = URL.createObjectURL(new Blob());
    let uuid = temp_url.toString();
    URL.revokeObjectURL(temp_url);
    return uuid.substr(uuid.lastIndexOf('/') + 1);
}



