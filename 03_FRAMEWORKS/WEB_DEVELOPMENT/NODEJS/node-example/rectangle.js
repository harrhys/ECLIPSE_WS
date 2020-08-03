module.exports = (x,y,callback) => {

    if (x <= 0 || y <= 0){

        var err = new Error(
            "Rectangle dimensions should be greater than zero: l = " + x + ", and b = " + y
        );
        callback(err, null);
    }
    else{

        var rect = {
            perimeter: () => (2*(x+y)),
            area:() => (x*y)
        };
        callback(null, rect )
    }
}

perimeter =  (x, y) => (2*(x+y));

area = (x, y) => (x*y);
