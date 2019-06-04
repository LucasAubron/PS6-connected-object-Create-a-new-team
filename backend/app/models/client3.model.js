const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

class ClientModel3 extends BaseModel {
  constructor() {
    super('l3', {
        phoneId: Joi.number().required()
    });
  }
}

module.exports = new ClientModel3();

