var idsList = Args.idsList.split(",");
var response = API.users.get({"user_ids":idsList.splice(0,1000),
    "fields":"sex, bdate, city, country"});
if (idsList.length > 0){
    while (idsList.length > 0){
        response = response + API.users.get({"user_ids":idsList.splice(0,1000),
            "fields":"sex, bdate, city, country"});
    };
}
return response.length;