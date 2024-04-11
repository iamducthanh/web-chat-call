ALTER TABLE room
    ADD public_key text;
ALTER TABLE room
    ADD private_key text;
delete from attach;
delete FROM message;
delete from roomdetail;
delete from room;