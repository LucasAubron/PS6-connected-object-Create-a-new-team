const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

class ClientModel4 extends BaseModel {
  constructor() {
    super('l4', {
        phoneId: Joi.number().required()
    });
  }
}

module.exports = new ClientModel4();

