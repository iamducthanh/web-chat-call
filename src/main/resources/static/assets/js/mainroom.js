function closeRoomMessage() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    document.querySelector('#message-direct').style.display = 'none';
}

function onRoomMessage(roomId) {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    document.getElementById("contentMedia").innerHTML = "";
    document.querySelector('#trang-chu').style.display = 'none';
    document.querySelector('#message-direct').style.display = 'unset';
    document.querySelector('#header-message-direct').style.display = 'unset';
    document.querySelector('#header-message-group').style.display = 'none';

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

function onRoomMessageGroup(roomId) {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    document.querySelector('#trang-chu').style.display = 'none';
    document.querySelector('#message-direct').style.display = 'unset';
    document.querySelector('#header-message-direct').style.display = 'none';
    document.querySelector('#header-message-group').style.display = 'unset';
    console.log(roomId)
    $.ajax({
        url: 'api/room/message-group',
        data: {
            roomId: roomId
        },
        error: function () {
            console.log("error")
        },
        success: function (roomDetail) {
            setRoomGroupDetail(roomDetail);
            console.log(roomDetail)
        },
        type: 'GET'
    });
}

function showMember() {
    $.ajax({
        url: 'api/room/message-group',
        data: {
            roomId: room
        },
        error: function () {
            console.log("error")
        },
        success: function (roomDetail) {
            let containerMemberGroup = document.getElementById("containerMemberGroup");
            containerMemberGroup.innerHTML = "";
            for (let i = 0; i < roomDetail.userInRooms.length; i++) {
                containerMemberGroup.innerHTML += addDivMember(roomDetail.userInRooms[i]);
            }
        },
        type: 'GET'
    });
}

function setBaseRoom(roomDetail) {
    document.getElementById("room").value = roomDetail.roomId;
    document.getElementById("name").value = roomDetail.user.username;
    document.getElementById("fullname").value = roomDetail.user.fullname;
    document.getElementById("pageIndex").value = -1;
    document.getElementById("statusMedia").value = 0;
    document.getElementById("imageUserLogin").value = roomDetail.user.image;
}

let roomIdUploadFile = null;

function setRoomDetail(roomDetail) {
    document.getElementById("isGroup").value = false;
    console.log(roomDetail)
    if (stompClient != null) {
        stompClient.disconnect();
    }
    roomIdUploadFile = roomDetail.roomId;
    setBaseRoom(roomDetail);
    document.getElementById("first").value = roomDetail.userInRoom.first;
    document.getElementById("userInRoomDirect").value = roomDetail.userInRoom.username;
    document.getElementById("imageUserInRoom").value = roomDetail.userInRoom.image;
    document.getElementById("userNameRoom").innerText = roomDetail.userInRoom.fullname;
    document.getElementById("fullnameTask").innerText = roomDetail.userInRoom.fullname;
    document.getElementById("fullnameDetail").innerText = roomDetail.userInRoom.fullname;
    document.getElementById("userImageRoom").src = roomDetail.userInRoom.image;
    document.getElementById("avtUserInroom").src = roomDetail.userInRoom.image;
    document.getElementById("avtDetail").src = roomDetail.userInRoom.image;
    document.getElementById("emailDetail").innerText = roomDetail.userInRoom.email;
    document.getElementById("sdtDetail").innerText = roomDetail.userInRoom.phone;
    document.getElementById("birthDayDetail").value = roomDetail.userInRoom.birthday;
    if (roomDetail.userInRoom.gender) {
        document.getElementById("genderDetail").innerText = 'Nam';
    } else {
        document.getElementById("genderDetail").innerText = 'Nữ';
    }

    document.getElementById("avtMyUser").src = roomDetail.user.image;

    document.getElementById("messageArea").innerHTML = "";
    loadMessage();

    let messForm = document.getElementById('messForm');
    messForm.scrollTop = messForm.scrollHeight;
    if (roomDetail.userInRoom.statusMessage.length == 0) {
        document.getElementById("statusMessage").innerHTML = ''
    } else if (roomDetail.userInRoom.statusMessage == 'SEND') {
        document.getElementById("statusMessage").innerHTML = 'Đã gửi'
    } else if (roomDetail.userInRoom.statusMessage == 'READ') {
        document.getElementById("statusMessage").innerHTML = 'Đã xem'
    }
    console.log("là bạn bè: ---------------- " + roomDetail.userInRoom.friend)
    let statusOn = document.getElementById("statusOn");

    if (roomDetail.userInRoom.friend == true) {
        if (roomDetail.userInRoom.online) {
            statusOn.innerText = "Đang hoạt động";
        } else {
            statusOn.innerText = roomDetail.userInRoom.lastOnline;
        }
        statusOn.className = "text-truncate " + roomDetail.userInRoom.username;
    } else {
        statusOn.innerText = "Người lạ";
    }
    // loadImage();
    connect();
}

function setRoomGroupDetail(roomDetail) {
    document.getElementById("isGroup").value = true;
    document.getElementsByName("nameMessageGroup")[0].innerText = roomDetail.name;
    document.getElementsByName("nameMessageGroup")[1].innerText = roomDetail.name;
    document.getElementsByName("nameMessageGroup")[2].innerText = roomDetail.name;
    document.getElementById("userInRoomDirect").value = roomDetail.roomId;
    document.getElementsByName("countMember")[0].innerText = (roomDetail.userInRooms.length + 1) + " người " + roomDetail.countOnline + " đang hoạt động";
    document.getElementsByName("countMember")[1].innerText = (roomDetail.userInRooms.length + 1) + " người " + roomDetail.countOnline + " đang hoạt động";
    document.getElementsByName("countMember")[2].innerText = (roomDetail.userInRooms.length + 1) + " người " + roomDetail.countOnline + " đang hoạt động";
    setBaseRoom(roomDetail);
    let containerMemberGroup = document.getElementById("containerMemberGroup");
    containerMemberGroup.innerHTML = "";
    document.getElementById("statusMessage").innerHTML = ''
    for (let i = 0; i < roomDetail.userInRooms.length; i++) {
        containerMemberGroup.innerHTML += addDivMember(roomDetail.userInRooms[i]);

    }
    document.getElementById("messageArea").innerHTML = "";

    loadMessage();
    connect();
}

function addDivMember(user) {
    let avtOnline = "";
    let status = "";
    if (user.friend) {
        if (user.online) {
            avtOnline = " avatar-online";
            status = "Đang hoạt động";
        } else {
            avtOnline = " avatar-offline";
            status = user.lastOnline;
        }
    } else {
        status = "Người lạ"
    }
    let div =
        "<li class=\"list-group-item\" id='member" + user.username + "'>" +
        "<div class=\"row align-items-center gx-5\">" +
        "<div class=\"col-auto\">" +
        "<a href=\"#\" class=\"avatar " + avtOnline + "\">" +
        "<img class=\"avatar-img\" src=\"" + user.image + "\" alt=\"\">" +
        "</a>" +
        "</div>" +
        "<div class=\"col\">" +
        "<h5><a href=\"#\">" + user.fullname + "</a></h5>" +
        "<p>" + status + "</p>" +
        "</div>" +
        "<div class=\"col-auto\">" +
        "<div class=\"dropdown\">" +
        "<a class=\"icon text-muted\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\"><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-more-vertical\">" +
        "<circle cx=\"12\" cy=\"12\" r=\"1\"></circle>" +
        "<circle cx=\"12\" cy=\"5\" r=\"1\"></circle>" +
        "<circle cx=\"12\" cy=\"19\" r=\"1\"></circle>" +
        "</svg>" +
        "</a>" +
        "<ul class=\"dropdown-menu\">" +
        "<li>" +
        "<a onclick='deleteUserInGroup(" + user.id + ")' class=\"dropdown-item d-flex align-items-center text-danger\" href=\"#\"> Xóa khỏi nhóm&ensp; " +
        "<div class=\"icon ms-auto\">" +
        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-trash-2\">" +
        "<polyline points=\"3 6 5 6 21 6\"></polyline>" +
        "<path d=\"M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2\"></path>" +
        "<line x1=\"10\" y1=\"11\" x2=\"10\" y2=\"17\"></line>" +
        "<line x1=\"14\" y1=\"11\" x2=\"14\" y2=\"17\"></line>" +
        "</svg>" +
        "</div>" +
        "</a>" +
        "</li>" +
        "</ul>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</li>";
    return div;
}

async function deleteUserInGroup(userId) {
    let data = {
        userId: userId,
        roomId: room
    }
    let dataOut = await callAjax('api/room/delete-member', data, 'POST');
    document.getElementById("btnShowMember").click();
    toastInfo("Thông báo", "Xóa thành công!")
}

async function showUserProfile(username) {
    let data = {
        username: username
    }
    let user = await callAjax('user/get-by-username', data, 'GET');
    document.getElementById("usernameDetailModal").innerText = user.username;
    document.getElementById("fullnameDetailModal").innerText = user.fullName;
    document.getElementById("avtDetailModal").src = user.image;
    document.getElementById("emailDetailModal").innerText = user.email;
    document.getElementById("descriptionModal").innerText = user.descreption;
    document.getElementById("phoneDetailModal").innerText = user.phone;
    document.getElementById("birthDayDetailModal").value = user.birthdayString;
    if (user.gender) {
        document.getElementById("genderDetailModal").innerText = 'Nam';
    } else {
        document.getElementById("genderDetailModal").innerText = 'Nữ';
    }
}

async function showFriendCreateRroupChat() {
    let friend = await callAjax('api/friend', null, 'GET')

    let containerAddMemberFriend = document.getElementById("containerAddMemberFriend");
    containerAddMemberFriend.innerHTML = "";
    for (let i = 0; i < friend.length; i++) {
        containerAddMemberFriend.innerHTML += addDivAddMemberCreateChat(friend[i]);
    }

}

function addDivAddMemberCreateChat(friend) {
    let avtOnline = "";
    let status = "";
    if (friend.online) {
        avtOnline = " avatar-online";
        status = "Đang hoạt động";
    } else {
        avtOnline = " avatar-offline";
        status = friend.birthdayString;
    }
    let divAdd =
        "<li class=\"list-group-item\" id='addMemberCreateChat" + friend.id + "'>" +
        "<div class=\"row align-items-center gx-5\">" +
        "<div class=\"col-auto\">" +
        "<div class=\"avatar " + avtOnline + "\">" +
        "<img class=\"avatar-img\" src=\"" + friend.image + "\" alt=\"\">" +
        "</div>" +
        "</div>" +
        "<div class=\"col\">" +
        "<h5>" + friend.fullName + "</h5>" +
        "<p>" + status + "</p>" +
        "</div>" +
        "<div class=\"col-auto\">" +
        "<div class=\"form-check\">" +
        "<input class=\"form-check-input\" name='checkBoxAddMemberCreateChat' type=\"checkbox\" value=\"" + friend.id + "\" id=\"id-add-user-user-1\">" +
        "<label class=\"form-check-label\" htmlFor=\"id-add-user-user-1\"></label>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</li>";
    return divAdd;
}