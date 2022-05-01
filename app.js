var person = {
    firstName : "",
    lastName : "",
    getFuLLName : function() {
        return this.firstName + " " + this.lastName
    }
}

var Hoa = Object.create(person)
Hoa.firstName = "Nguyen"
Hoa.lastName = "Hoa"
console.log(Hoa.getFuLLName())