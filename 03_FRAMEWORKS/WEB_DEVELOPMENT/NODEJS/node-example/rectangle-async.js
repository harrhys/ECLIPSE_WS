module.exports = (x,y,callback) => {

    if (x <= 0 || y <= 0)
        setTimeout(
                        () => {
                            var err = new Error("Rectangle dimensions should be greater than zero: l = "
                            + x + ", and b = " + y);
                            callback(err, null);
                        }
                        ,
                        2000
            );
    else
        setTimeout(
                    () => {
                        var rect = {
                            perimeter: () => (2*(x+y)),
                            area:() => (x*y)
                        };
                        callback(null, rect )
                    }
                    , 
                    5000
            );
}

perimeter =  (x, y) => (2*(x+y));

area = (x, y) => (x*y);
