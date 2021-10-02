function sendFriendRequest(event, userId) {
    event.id = 'cancel' + userId;
    event.innerHTML = "Hủy lời mời kết bạn";
    document.getElementById('cancel' + userId).onclick = cancelFriendRequest.bind(this, userId, event);
    let friendRequest = {
        senderId: userIdOnline,
        userId: userId,
        fullname: "",
        image: "",
        type: "REQUEST",
        time: "",
        time: userOnline
    }
    stompClientSystem.send("/app/system/friend/" + userId,
        {},
        JSON.stringify(friendRequest)
    );
    toastInfo("Thông báo", "Gửi yêu cầu kết bạn thành công")

}

function onFriendRequest(payload) {
    let friendRequest = JSON.parse(payload.body);
    let id = 'friendRequest' + friendRequest.senderId
    console.log(friendRequest);
    if (friendRequest.type == "REQUEST") {
        let container = document.getElementById("friend-request");
        container.innerHTML =
            "<div class=\"card border-0 mb-5\" id='friendRequest" + friendRequest.senderId + "'><div class=\"card-body\"><div class=\"row gx-5\"><div class=\"col-auto\"><a href=\"#\" class=\"avatar\">" +

            "<img class=\"avatar-img\" src=\"" + friendRequest.image + "\" alt=\"\">" +

            "<div class=\"badge badge-circle bg-primary border-outline position-absolute bottom-0 end-0\">" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-user\">" +
            "<path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg></div></a></div>" +
            "<div class=\"col\"><div class=\"d-flex align-items-center mb-2\"><h5 class=\"me-auto mb-0\">" +

            "<a href=\"#\">" + friendRequest.fullname + "</a>" +

            "</h5><span class=\"extra-small text-muted ms-2\">" + friendRequest.time + "</span></div>" +

            "<div class=\"d-flex\"><div class=\"me-auto\">Đã gửi cho bạn lời mời kết bạn.</div>" +
            "</div></div></div></div><div class=\"card-footer\"><div class=\"row gx-5\">" +
            "<div class=\"col\">" +
            "<a href=\"#\" class=\"btn btn-sm btn-secondary w-100\" onclick='refuseFriendRequest(" + friendRequest.senderId + ")'>Xoá</a>" +
            "</div>" +
            "<div class=\"col\">" +

            "<a href=\"#\" class=\"btn btn-sm btn-primary w-100\" onclick='agreeFriendRequest(\"" + friendRequest.senderId + "\",\"" + friendRequest.fullname + "\",\"" + friendRequest.username + "\",\"" + friendRequest.image + "\")'>Chấp nhận</a>" +
            "</div></div></div>" +
            "</div>" + container.innerHTML;

        let countFriendRequest = document.getElementById("countFriendRequest");
        let divCountFriendRequest = document.getElementById("divCountFriendRequest");
        if (countFriendRequest == null) {
            divCountFriendRequest.className = 'icon icon-xl icon-badged';
            divCountFriendRequest.innerHTML = divCountFriendRequest.innerHTML +
                "<div id='divSlgFR' class=\"badge badge-circle bg-primary\">" +
                "<span id=\"countFriendRequest\">1</span>" +
                "</div>";
        } else {
            countFriendRequest.innerText = Number(countFriendRequest.innerText) + 1;
        }
        toastInfo("Thông báo", friendRequest.fullname + " đã gửi cho bạn lời mời kết bạn")
    } else if (friendRequest.type == 'CANCEL') {
        let divFriendRequest = document.getElementById("friend-request");
        console.log("lmhb can xoa la: " + id)
        let friendRequest = document.getElementById(id);
        if (friendRequest != null) {
            divFriendRequest.removeChild(friendRequest);
        }

        resetCountFriendRequest();
    } else if (friendRequest.type == 'AGREE') {
        toastInfo("Thông báo", friendRequest.fullname + " đã chấp nhận lời mời kết bạn");
        addDivFriend(friendRequest.fullname, friendRequest.username, friendRequest.userId, friendRequest.image)
        console.log(friendRequest);
        addDivNotification(friendRequest.fullname, friendRequest.image, friendRequest.fullname + " đã chấp nhận lời mời kết bạn", friendRequest.timeString);
    }
}

function cancelFriendRequest(userId, event) {
    event.id = 'cancel' + userId;
    event.innerHTML = "Kết bạn";
    document.getElementById('cancel' + userId).onclick = sendFriendRequest.bind(this, event, userId);
    let friendRequest = {
        senderId: userIdOnline,
        userId: userId,
        fullname: "",
        image: "",
        type: "CANCEL",
        time: ""
    }
    stompClientSystem.send("/app/system/friend/" + userId,
        {},
        JSON.stringify(friendRequest)
    );
    toastInfo("Thông báo", "Đã hủy yêu cầu kết bạn");
}

function refuseFriendRequest(userId) {
    $.ajax({
        url: 'api/friend/delete',
        data: {
            userId: userId,
            friendId: userIdOnline
        },
        error: function () {
            console.log("error")
        },
        success: async function (data) {
            console.log("done");
            let friendRequest = document.getElementById("friendRequest" + userId);
            document.getElementById("friend-request").removeChild(friendRequest);
            toastInfo("Xoá", "Đã xóa yêu cầu kết bạn");
            resetCountFriendRequest();
        },
        type: 'PUT'
    });
}

function agreeFriendRequest(userId, fullname, username, image) {
    let friendRequest = {
        senderId: userId,
        userId: userIdOnline,
        fullname: fullname,
        image: "",
        type: "AGREE",
        time: ""
    }
    stompClientSystem.send("/app/system/friend/" + userId,
        {},
        JSON.stringify(friendRequest)
    );
    toastInfo("Thông báo", "Bạn và " + fullname + " đã trở thành bạn bè");
    resetCountFriendRequest();
    let containerFriendRequest = document.getElementById("friend-request");
    let friendRequestDiv = document.getElementById("friendRequest" + userId);
    containerFriendRequest.removeChild(friendRequestDiv);
    addDivFriend(fullname, username, userId, image);
}

function resetCountFriendRequest() {
    let countFriendRequest = document.getElementById("countFriendRequest");
    countFriendRequest.innerText = Number(countFriendRequest.innerText) - 1;
    if (countFriendRequest.innerText == 0) {
        let divCountFriendRequest = document.getElementById("divCountFriendRequest");
        divCountFriendRequest.className = 'icon icon-xl';
        let divSlgFR = document.getElementById("divSlgFR");
        divCountFriendRequest.removeChild(divSlgFR);
    }
}

function addDivFriend(fullname, username, id, image) {
    let containerFriend = document.getElementById("containerFriend");
    containerFriend.innerHTML =
        "<div class=\"card border-0\" id=\"friend" + username + "\">" +
        "<div class=\"card-body\">" +
        "<div class=\"row align-items-center gx-5\">" +
        "<div class=\"col-auto\">" +
        "<a href=\"#\" class=\"avatar avatar-online\" id=\"" + username + "Friend\">" +
        "<img class=\"avatar-img\" src=\"" + image + "\" alt=\"\">" +
        "</a>" +
        "</div>" +
        "<div class=\"col\">" +
        "<h5><a href=\"#\">" + fullname + "</a></h5>" +
        "<p class=\"text-truncate\" id=\"statusOnFriend\" class=\"" + username + "Friend\">Đang hoạt động</p>" +
        "</div>" +
        "<div class=\"col-auto\">" +
        "<div class=\"dropdown\">" +
        "<a class=\"icon text-muted\" href=\"#\" role=\"button\" data-bs-toggle=\"dropdown\" aria-expanded=\"false\">" +
        "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-more-vertical\">" +
        "<circle cx=\"12\" cy=\"12\" r=\"1\"></circle>" +
        "<circle cx=\"12\" cy=\"5\" r=\"1\"></circle>" +
        "<circle cx=\"12\" cy=\"19\" r=\"1\"></circle>" +
        "</svg>" +
        "</a>" +
        "<ul class=\"dropdown-menu\">" +
        "<li><a class=\"dropdown-item\" onclick=checkRoomByUser(\"" + username + "\",\"" + id + "\") href=\"#\">Gửi tin nhắn</a></li>" +
        "<li>" +
        "<hr class=\"dropdown-divider\">" +
        "</li>" +
        "<li>" +
        "<a class=\"dropdown-item text-danger\" href=\"#\">Chặn</a>" +
        "</li>" +
        "</ul>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" + containerFriend.innerHTML;
}

function addDivNotification(title, image, content, time){
    let containerNotification = document.getElementById("containerNotification");
    containerNotification.innerHTML =
        "<div class=\"card border-0 mb-5\">"+
            "<div class=\"card-body\">"+
                "<div class=\"row gx-5\">"+
                    "<div class=\"col-auto\">"+
                        "<a href=\"#\" class=\"avatar\">"+
                            "<img class=\"avatar-img\" src=\""+image+"\" alt=\"\">"+
                                "<div class=\"badge badge-circle bg-primary border-outline position-absolute bottom-0 end-0\">"+
                                    "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-user\">"+
                                        "<path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path>"+
                                        "<circle cx=\"12\" cy=\"7\" r=\"4\"></circle>"+
                                        "</svg>"+
                                    "</div>"+
                                "</a>"+
                        "</div>"+
                    "<div class=\"col\">"+
                        "<div class=\"d-flex align-items-center mb-2\">"+
                            "<h5 class=\"me-auto mb-0\">"+
                                "<a href=\"#\"><b>"+title+"</b></a>"+
                                "</h5>"+
                            "<span class=\"extra-small text-muted ms-2\"><b>"+time+"</b></span>"+
                            "</div>"+
                        "<div class=\"d-flex\">"+
                            "<div class=\"me-auto\"><b>"+content+"</b></div>"+
                            "</div>"+
                        "</div>"+
                    "</div>"+
                "</div>"+
            "</div>" + containerNotification.innerHTML;
        let countNotification = document.getElementById("countNotification");
        let divCountNotification = document.getElementById("divCountNotification");
        if(countNotification != null){
            countNotification.innerText = Number(countNotification.innerText) + 1;
        } else {
            divCountNotification.className = "icon icon-xl icon-badged";
            divCountNotification.innerHTML +=
                "<div id=\"onRemoveNotification\" class=\"badge badge-circle bg-primary\">"+
                    "<span id=\"countNotification\">1</span>"+
                "</div>";
        }

}