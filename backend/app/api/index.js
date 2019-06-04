const { Router } = require('express');
const ClientRouteur1 = require('./clients/index1');
const ClientRouteur2 = require('./clients/index2');
const ClientRouteur3 = require('./clients/index3');
const ClientRouteur4 = require('./clients/index4');

const router = new Router();
router.get('/status', (req, res) => res.status(200).json('ok'));
router.use('/clients/l1', ClientRouteur1);
router.use('/clients/l2', ClientRouteur2);
router.use('/clients/l3', ClientRouteur3);
router.use('/clients/l4', ClientRouteur4);

module.exports = router;
