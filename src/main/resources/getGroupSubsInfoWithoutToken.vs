//некоторые группы можно вытаскивать по 12 тысяч пользователей сразу
//но на каких то вытаскиавает не больше 9 тысяч
//а где-то не больше 8 тысяч, и если больше выкидывает
//Internal API server error. Wrong status code: 500. Content: ERROR
//i в условии while ставит ограничение на количество возвращаемых
//тысяч
var i = 1;
var offset = Args.offset - 0;
var count = Args.count - 0;
var tempSubsArray = API.groups.getMembers({"group_id": Args.groupName
    , "offset": Args.offset, "count": "1000", "v": "5.103"}).items;
var subsInfoArray = API.users.get({"user_ids":tempSubsArray.splice(0,1000),
    "fields":"sex, bdate, city, country"});
offset = offset + 1000;
while ((i < 8) && (offset < count))
{
    tempSubsArray = API.groups.getMembers({"group_id": Args.groupName
        , "offset": offset, "count": "1000", "v": "5.103"}).items;
    subsInfoArray = subsInfoArray + API.users.get({"user_ids":tempSubsArray.splice(0,1000),
        "fields":"sex, bdate, city, country"});
    offset = offset + 1000;
    i = i + 1;
};
return subsInfoArray;