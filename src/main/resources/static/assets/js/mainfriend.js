function sendFriendRequest(event,senderId){
    event.style.display = 'none'
    let friendRequest = {
        senderId: userIdOnline ,
        userId: senderId,
        fullname: "",
        image: "",
        type: "REQUEST",
        time: ""
    }
    stompClientSystem.send("/app/system/friend/" + senderId,
        {},
        JSON.stringify(friendRequest)
    );
}

function onFriendRequest(){
    console.log(userOnline)
}