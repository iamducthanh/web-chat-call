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
        time: ""
    }
    stompClientSystem.send("/app/system/friend/" + userId,
        {},
        JSON.stringify(friendRequest)
    );
}

function onFriendRequest(payload) {
    let friendRequest = JSON.parse(payload.body);
    let id = 'friendRequest' + friendRequest.senderId
    console.log(friendRequest);
    if(friendRequest.type == "REQUEST"){
        let container = document.getElementById("friend-request");
        container.innerHTML =
            "<div class=\"card border-0 mb-5\" id='friendRequest"+friendRequest.senderId+"'><div class=\"card-body\"><div class=\"row gx-5\"><div class=\"col-auto\"><a href=\"#\" class=\"avatar\">" +

            "<img class=\"avatar-img\" src=\""+friendRequest.image+"\" alt=\"\">" +

            "<div class=\"badge badge-circle bg-primary border-outline position-absolute bottom-0 end-0\">" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-user\">" +
            "<path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg></div></a></div>" +
            "<div class=\"col\"><div class=\"d-flex align-items-center mb-2\"><h5 class=\"me-auto mb-0\">" +

            "<a href=\"#\">"+friendRequest.fullname+"</a>" +

            "</h5><span class=\"extra-small text-muted ms-2\">"+friendRequest.time+"</span></div>" +

            "<div class=\"d-flex\"><div class=\"me-auto\">Đã gửi cho bạn lời mời kết bạn.</div>" +
            "</div></div></div></div><div class=\"card-footer\"><div class=\"row gx-5\">" +
            "<div class=\"col\">" +
            "<a href=\"#\" class=\"btn btn-sm btn-secondary w-100\">Xoá</a>" +
            "</div>" +
            "<div class=\"col\">" +
            "<a href=\"#\" class=\"btn btn-sm btn-primary w-100\">Chấp nhận</a>" +
            "</div></div></div>" +
            "</div>" + container.innerHTML;

        let countFriendRequest = document.getElementById("countFriendRequest");
        let divCountFriendRequest = document.getElementById("divCountFriendRequest");
        if(countFriendRequest == null){
            divCountFriendRequest.className = 'icon icon-xl icon-badged';
            divCountFriendRequest.innerHTML = divCountFriendRequest.innerHTML +
                "<div class=\"badge badge-circle bg-primary\">"+
                "<span id=\"countFriendRequest\">1</span>"+
                "</div>";
        } else {
            countFriendRequest.innerText = Number(countFriendRequest.innerText) + 1;
        }
    } else if(friendRequest.type == 'CANCEL'){
        let divCountFriendRequest = document.getElementById("friend-request");
        console.log("lmhb can xoa la: " + id)
        let friendRequest = document.getElementById(id);
        if(friendRequest != null){
            divCountFriendRequest.removeChild(friendRequest);
        }
    }
}

function cancelFriendRequest(userId, event){
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
}