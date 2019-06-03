const Joi = require('joi');
const BaseModel = require('../utils/base-model.js');

class ClientModel extends BaseModel {
  constructor() {
    super('Client', {
        nom: Joi.string().required(),
        prenom: Joi.string().required()
    });
  }
}

module.exports = new ClientModel();

