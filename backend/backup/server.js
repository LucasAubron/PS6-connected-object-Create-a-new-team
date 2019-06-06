var express = require('express');
var app = express();

var liste = [];

function jsonSauf(numero) {
    var obj = {
        table: []
    };
    for (var i = 0; i != liste.length; i++) {
        if (liste[i] != numero) {
            obj.table.push({
                "numero": liste[i],
                "nombre": i,
            })
        }
        else {
            obj.table.push({
                "numero": suivant,
                "texte": "Taille de la file",
                "nombre": liste.length,
                "texte_bouton": "Rejoindre la file"
            })
        }
    }
    return JSON.stringify(obj);
}

require('./router/main')(app);
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');
app.engine('html', require('ejs').renderFile);

app.get('/ajouterNum', function (req, res) {///ajouterNum?numero=
    console.log("Ajout de numéro");
    var num = req.query.numero;
    liste.push(num);
    res.json({
        "numero": req.query.numero,
        "texte": "Votre place",
        "nombre": liste.length,
        "texte_bouton": "Quitter la file"
    })
}).get('/getTailleListe', function (req, res) {///getTailleListe
    console.log("Demande de la taille");
    res.json({ "taille": liste.length })
}).get('/getPositionNum', function (req, res) {///getPositionNum?numero=
    console.log("Demande de position");
    var numero = req.query.numero;
    res.json({
        "numero": numero,
        "nombre": liste.indexOf(numero)
    });
}).get('/suivant', function (req, res) {///suivant
    console.log("Suivant !");
    if (liste.length > 0) {
        var json = jsonSauf(liste[0]);
        liste.shift();
        //Création du json
        res.json(json);
    }
}).get('/supprNum', function (req, res) {///supprNum?numero=
    console.log("Suppression de numéro")
    var position = liste.indexOf(req.query.numero);
    if (position != -1) {
        var json = jsonSauf(liste[position]);
        liste.splice(position, 1);
        res.json(json);
    }
}).get('/getInfoBase', function (req, res) {///getInfoBase?numero=
    console.log("Info de base demandées");
    res.json({
        "numero": req.query.numero,
        "texte": "Taille de la file",
        "nombre": liste.length,
        "texte_bouton": "Rejoindre la file"
    })
});

var server = app.listen(3000, function () {
    console.log("Express is running on port 3000");
});