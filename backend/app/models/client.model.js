const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

class ClientModel extends BaseModel {
  constructor() {
    super('Client', {
        phoneId: Joi.number().required()
    });
  }
}

module.exports = new ClientModel();

