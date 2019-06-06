var express = require('express');
var app = express();

var liste = [];

require('./router/main')(app);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

app.get('/ajouterNum', function(req, res){///ajouterNum?numero=
    console.log("Ajout de numéro");
    var num = req.query.numero;
    liste.push(num);
}).get('/getTailleListe', function(req, res){///getTailleListe
    console.log("Demande de la taille");
    res.json({"taille":liste.length})
}).get('/getPositionNum', function(req, res){///getPositionNum?numero=
    console.log("Demande de position");
    var numero = req.query.numero;
    res.json({"position":liste.indexOf(numero)});
});

var server = app.listen(3000, function () {
    console.log("Express is running on port 3000");
});