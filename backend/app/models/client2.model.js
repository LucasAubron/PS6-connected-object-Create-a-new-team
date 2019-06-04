const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

class ClientModel2 extends BaseModel {
  constructor() {
    super('l2', {
        phoneId: Joi.number().required()
    });
  }
}

module.exports = new ClientModel2();

