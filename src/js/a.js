var b = require('./b');

/*
 *用来遍历指定对象所有的属性名称和值
 *obj需要遍历的对象
 *author:JetMah
 */
function allPrpos(obj)
{
//用来保存所有的属性名称和值
    var props = "";
//开始遍历
    for (var p in obj) {
//方法
        if (typeof(obj[p]) == "function") {
            console.log(p);
            //obj[p]();
        } else {
//p为属性名称，obj[p]为对应属性的值
            props += p + "=" + obj[p] + "\t";
        }
    }
//最后显示所有的属性
    console.log('###############');
    console.log(props);
    console.log('###############');

}

allPrpos(b)
//console.log(b);
b.hello();
b.bye();
b.obj();


