var a = API.groups.getMembers({"group_id": Args.groupName, "offset": Args.offset, "count": "1000", "v": "5.103"}).items;
var offset = Args.offset - 0;
var count = Args.count - 0;
var i = 1;
while ((i < 25) && (offset < count))
{
    offset = offset + 1000;
    a = a + API.groups.getMembers({"group_id": Args.groupName, "offset": offset, "count": "1000", "v": "5.103"}).items;
    i = i + 1;
};
return a;