/**
 * Created by darrenfu on 2016/4/20.
 */
var http = require('http');

http.createServer(function (req, res) {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.write('<h1>helldddo</h1>');
    res.end('<p>Hello f</p>')
}).listen(3000);     //事件监听3000端口
console.log('open 2s ');