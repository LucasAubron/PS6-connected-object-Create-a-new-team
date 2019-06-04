const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

class ClientModel1 extends BaseModel {
  constructor() {
    super('l1', {
        phoneId: Joi.number().required()
    });
  }
}

module.exports = new ClientModel1();

