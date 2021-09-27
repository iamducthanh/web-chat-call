function closeRoomMessage(){
    if(stompClient != null){
        stompClient.disconnect();
    }
    document.querySelector('#message-direct').style.display = 'none'
}
function onRoomMessage(roomId){
    document.querySelector('#trang-chu').style.display = 'none'
    document.querySelector('#message-direct').style.display = 'unset'
    console.log(roomId)
    $.ajax({
        url: 'api/room/message-direct',
        data: {
            roomId: roomId
        },
        error: function () {
            console.log("error")
        },
        success: function (roomDetail) {
            setRoomDetail(roomDetail)
        },
        type: 'GET'
    });
}
let roomIdUploadFile = null;
function setRoomDetail(roomDetail){
    console.log(roomDetail)
    if(stompClient != null){
        stompClient.disconnect();
    }
    roomIdUploadFile = roomDetail.roomId;
    document.getElementById("room").value = roomDetail.roomId;
    document.getElementById("name").value = roomDetail.user.username;
    document.getElementById("fullname").value = roomDetail.user.fullname;
    document.getElementById("pageIndex").value = -1;
    document.getElementById("first").value = roomDetail.userInRoom.first;

    document.getElementById("userInRoomDirect").value = roomDetail.userInRoom.username;
    document.getElementById("imageUserLogin").value = roomDetail.user.image;
    document.getElementById("imageUserInRoom").value = roomDetail.userInRoom.image;
    document.getElementById("statusMedia").value = 0;
    document.getElementById("userNameRoom").innerText=roomDetail.userInRoom.fullname;
    document.getElementById("userImageRoom").src=roomDetail.userInRoom.image;

    document.getElementById("messageArea").innerHTML = "";
    scrollFunction_ct();
    let messForm = document.getElementById('messForm');
    messForm.scrollTop = messForm.scrollHeight;
    if(roomDetail.userInRoom.statusMessage.length == 0){
        document.getElementById("statusMessage").innerHTML = ''
    } else if(roomDetail.userInRoom.statusMessage == 'SEND'){
        document.getElementById("statusMessage").innerHTML = 'Đã gửi'
    } else if(roomDetail.userInRoom.statusMessage == 'READ'){
        document.getElementById("statusMessage").innerHTML = 'Đã xem'
    }
    console.log("là bạn bè: ---------------- " + roomDetail.userInRoom.friend)
    let statusOn = document.getElementById("statusOn");
    if(roomDetail.userInRoom.friend == true){
        if(roomDetail.userInRoom.online){
            statusOn.innerText = "Đang hoạt động";
        } else {
            statusOn.innerText = "Không hoạt động";
        }
        statusOn.className = "text-truncate " + roomDetail.userInRoom.username;
    } else {
        statusOn.innerText = "Người lạ";
    }
    // loadImage();
    connect();
}