const { Router } = require('express');
const ClientRouter = require('./clients');

const router = new Router();
router.get('/status', (req, res) => res.status(200).json('ok'));
router.use('/clients', ClientRouter);

module.exports = router;
